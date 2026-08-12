package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import myapp.org.userapp.auth.SessionManager;
import myapp.org.userapp.model.UserProfile;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsername, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            String username = loginUsername.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();
            if (validateInput(username, password)) {
                loginUser(username, password);
            }
        });

        signupRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            loginUsername.setError("Email is required");
            loginUsername.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            loginUsername.setError("Enter a valid email");
            loginUsername.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            loginPassword.setError("Password is required");
            loginPassword.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            loginPassword.setError("Password must be at least 6 characters");
            loginPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void loginUser(String username, String password) {
        // ============================================================
        // MIGRATION: Firebase Auth -> Supabase Auth
        // ------------------------------------------------------------
        // The implementation below uses Firebase Authentication to sign
        // the user in. When Supabase Auth is configured, this Firebase
        // call chain should be replaced with:
        //     AuthService.getInstance().signInWithEmail(username, password)
        // and this Firebase-based path can be removed/retired.
        // ============================================================
        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            redirectToMainActivity(user.getEmail());
                        }
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Authentication failed";
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void redirectToMainActivity(String userEmail) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query query = reference.orderByChild("email").equalTo(userEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String username = dataSnapshot.child("username").getValue(String.class);
                        Boolean isAdmin = dataSnapshot.child("isAdmin").getValue(Boolean.class);
                        String role = dataSnapshot.child("role").getValue(String.class);
                        boolean adminStatus = isAdmin != null && isAdmin || "admin".equalsIgnoreCase(role);

                        SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        UserProfile userProfile = new UserProfile();
                        userProfile.setId(dataSnapshot.getKey());
                        userProfile.setName(name);
                        userProfile.setEmail(email);
                        userProfile.setUsername(username);
                        userProfile.setAdmin(adminStatus);
                        sessionManager.saveSession(userProfile);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        intent.putExtra("username", username);
                        intent.putExtra("isAdmin", adminStatus);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: When Supabase Auth is configured, replace the Firebase login
    // flow above (loginUser) with AuthService.getInstance().signInWithEmail(...)
    // invoked from here.
    private void loginWithSupabase(String email, String password) {
        // Placeholder: real Supabase Auth call not wired up yet.
        Toast.makeText(this, "Supabase login not yet configured", Toast.LENGTH_SHORT).show();
    }
}
