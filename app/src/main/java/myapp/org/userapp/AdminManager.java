package myapp.org.userapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import myapp.org.userapp.supabase.SupabaseConfig;

public class AdminManager {

    private static final String TAG = "AdminManager";
    private static AdminManager instance;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface AdminCheckCallback {
        void onResult(boolean isAdmin);
    }

    private AdminManager() {}

    public static synchronized AdminManager getInstance() {
        if (instance == null) {
            instance = new AdminManager();
        }
        return instance;
    }

    /**
     * Checks admin status by querying Supabase public.users table.
     * Uses the current Firebase Auth user's email as the lookup key.
     * Runs on background thread, delivers result on main thread.
     */
    public void checkIsAdmin(AdminCheckCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            callback.onResult(false);
            return;
        }

        String email = user.getEmail();
        if (email == null || email.isEmpty()) {
            Log.w(TAG, "Firebase user has no email — cannot check Supabase admin status");
            callback.onResult(false);
            return;
        }

        executor.execute(() -> {
            boolean isAdmin = fetchAdminStatusFromSupabase(email);
            mainHandler.post(() -> callback.onResult(isAdmin));
        });
    }

    /**
     * Performs a synchronous GET to Supabase REST API:
     *   GET /rest/v1/users?email=eq.<email>&select=is_admin,role
     * Returns true if is_admin=true OR role='admin'.
     */
    private boolean fetchAdminStatusFromSupabase(String email) {
        HttpURLConnection connection = null;
        try {
            String supabaseUrl = SupabaseConfig.getSupabaseUrl();
            String anonKey = SupabaseConfig.getSupabaseAnonKey();

            // Build Supabase REST API URL
            String encodedEmail = email.replace("@", "%40").replace("+", "%2B");
            String endpoint = supabaseUrl
                    + "/rest/v1/users?email=eq." + encodedEmail
                    + "&select=is_admin,role"
                    + "&limit=1";

            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", anonKey);
            connection.setRequestProperty("Authorization", "Bearer " + anonKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            Log.d(TAG, "Supabase admin check response code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                String responseBody = sb.toString();
                Log.d(TAG, "Supabase admin response: " + responseBody);

                JSONArray results = new JSONArray(responseBody);
                if (results.length() > 0) {
                    JSONObject userRow = results.getJSONObject(0);

                    boolean isAdminFlag = false;
                    if (userRow.has("is_admin") && !userRow.isNull("is_admin")) {
                        isAdminFlag = userRow.getBoolean("is_admin");
                    }

                    String role = "";
                    if (userRow.has("role") && !userRow.isNull("role")) {
                        role = userRow.getString("role");
                    }

                    boolean result = isAdminFlag || "admin".equalsIgnoreCase(role);
                    Log.d(TAG, "Admin check result for " + email + ": " + result
                            + " (is_admin=" + isAdminFlag + ", role=" + role + ")");
                    return result;
                } else {
                    Log.w(TAG, "No user row found in Supabase for email: " + email);
                }
            } else {
                Log.e(TAG, "Supabase returned HTTP " + responseCode + " for admin check");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching admin status from Supabase: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    /**
     * Updates admin role in Supabase public.users table for a given user email.
     */
    public void setAdminRole(String email, boolean isAdmin, AdminCheckCallback callback) {
        executor.execute(() -> {
            boolean success = updateAdminInSupabase(email, isAdmin);
            mainHandler.post(() -> callback.onResult(success));
        });
    }

    private boolean updateAdminInSupabase(String email, boolean isAdmin) {
        HttpURLConnection connection = null;
        try {
            String supabaseUrl = SupabaseConfig.getSupabaseUrl();
            String anonKey = SupabaseConfig.getSupabaseAnonKey();

            String encodedEmail = email.replace("@", "%40").replace("+", "%2B");
            String endpoint = supabaseUrl + "/rest/v1/users?email=eq." + encodedEmail;

            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PATCH");
            connection.setRequestProperty("apikey", anonKey);
            connection.setRequestProperty("Authorization", "Bearer " + anonKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Prefer", "return=minimal");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            String body = "{\"is_admin\":" + isAdmin + ",\"role\":\"" + (isAdmin ? "admin" : "user") + "\"}";
            connection.getOutputStream().write(body.getBytes("UTF-8"));

            int responseCode = connection.getResponseCode();
            Log.d(TAG, "Supabase setAdminRole response: " + responseCode);
            return responseCode == HttpURLConnection.HTTP_OK
                    || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
        } catch (Exception e) {
            Log.e(TAG, "Error updating admin role in Supabase: " + e.getMessage(), e);
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
