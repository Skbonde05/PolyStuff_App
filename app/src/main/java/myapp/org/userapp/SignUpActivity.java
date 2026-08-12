package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import myapp.org.userapp.auth.SessionManager;
import myapp.org.userapp.model.UserProfile;
import myapp.org.userapp.supabase.SupabaseConfig;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private EditText signupName, signupUsername, signupEmail, signupPassword;
    private TextView loginRedirectText;
    private Button signupButton;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        signupName     = findViewById(R.id.signup_name);
        signupEmail    = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton   = findViewById(R.id.signup_button);
        mAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(v -> {
            String name     = signupName.getText().toString().trim();
            String email    = signupEmail.getText().toString().trim();
            String username = signupUsername.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();

            if (validateInput(name, email, username, password)) {
                performSignUp(name, email, username, password);
            }
        });

        loginRedirectText.setOnClickListener(v ->
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    private void performSignUp(String name, String email, String username, String password) {
        signupButton.setEnabled(false);

        // Step 1: Create user in Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();

                            // Step 2: Save to Firebase Realtime DB (existing behaviour)
                            database  = FirebaseDatabase.getInstance();
                            reference = database.getReference("users").child(userId);
                            HelperClass helperClass = new HelperClass(name, email, username);
                            reference.setValue(helperClass);

                            // Step 3: Save session locally
                            SessionManager sessionManager = new SessionManager(SignUpActivity.this);
                            UserProfile userProfile = new UserProfile();
                            userProfile.setId(userId);
                            userProfile.setName(name);
                            userProfile.setEmail(email);
                            userProfile.setUsername(username);
                            userProfile.setAdmin(false);
                            sessionManager.saveSession(userProfile);

                            // Step 4: Insert row into Supabase public.users (background thread)
                            insertUserIntoSupabase(userId, name, email, username);

                            Toast.makeText(SignUpActivity.this,
                                    "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                    } else {
                        signupButton.setEnabled(true);
                        String msg = task.getException() != null
                                ? task.getException().getMessage() : "Sign up failed";
                        Toast.makeText(SignUpActivity.this, "Sign up failed: " + msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Inserts the newly registered user into Supabase public.users table.
     * Does NOT send an id — the table's uuid_generate_v4() default handles it.
     * Uses "Prefer: resolution=ignore-duplicates" so re-registering the same
     * email doesn't crash (idempotent upsert by email unique constraint).
     */
    private void insertUserIntoSupabase(String firebaseUid, String name, String email, String username) {
        if (!myapp.org.userapp.supabase.SupabaseClientWrapper.isConfigured()) {
            Log.e(TAG, "❌ Cannot insert into Supabase: Anon key in SupabaseConfig.java is truncated or missing.");
            return;
        }
        executor.execute(() -> {
            HttpURLConnection connection = null;
            try {
                String supabaseUrl = SupabaseConfig.getSupabaseUrl();
                String anonKey    = SupabaseConfig.getSupabaseAnonKey();

                URL url = new URL(supabaseUrl + "/rest/v1/users");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("apikey", anonKey);
                connection.setRequestProperty("Authorization", "Bearer " + anonKey);
                connection.setRequestProperty("Content-Type", "application/json");
                // ignore-duplicates: if email already exists, do nothing (no error)
                connection.setRequestProperty("Prefer", "resolution=ignore-duplicates,return=minimal");
                connection.setDoOutput(true);
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                // Provide client-generated UUID for id to avoid null constraint issues if DB default is unset
                JSONObject body = new JSONObject();
                body.put("id",       java.util.UUID.randomUUID().toString());
                body.put("email",    email);
                body.put("name",     name);
                body.put("username", username);
                body.put("role",     "user");
                body.put("is_admin", false);

                OutputStream os = connection.getOutputStream();
                os.write(body.toString().getBytes("UTF-8"));
                os.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_CREATED
                        || responseCode == HttpURLConnection.HTTP_OK
                        || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    Log.d(TAG, "✅ User inserted into Supabase. email=" + email);
                } else {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getErrorStream() != null
                                    ? connection.getErrorStream()
                                    : connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                    reader.close();
                    Log.e(TAG, "❌ Supabase insert failed. HTTP " + responseCode + " | " + sb);
                }

            } catch (Exception e) {
                Log.e(TAG, "Error inserting user into Supabase: " + e.getMessage(), e);
            } finally {
                if (connection != null) connection.disconnect();
            }
        });
    }

    private boolean validateInput(String name, String email, String username, String password) {
        if (name.isEmpty()) {
            signupName.setError("Name is required");
            signupName.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            signupEmail.setError("Email is required");
            signupEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Enter a valid email");
            signupEmail.requestFocus();
            return false;
        }
        if (username.isEmpty()) {
            signupUsername.setError("Username is required");
            signupUsername.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            signupPassword.setError("Password is required");
            signupPassword.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            signupPassword.setError("Password must be at least 6 characters");
            signupPassword.requestFocus();
            return false;
        }
        return true;
    }
}
