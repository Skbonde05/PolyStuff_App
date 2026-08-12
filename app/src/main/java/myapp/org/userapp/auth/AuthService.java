package myapp.org.userapp.auth;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import myapp.org.userapp.model.UserProfile;

/**
 * Singleton service that abstracts authentication behind a Supabase-based
 * implementation. The current version is a scaffolding placeholder: actual
 * Supabase Auth SDK calls are NOT implemented yet.
 *
 * IMPLEMENTATION NOTE:
 * Once the Supabase Android SDK is added to build.gradle and the project is
 * initialized with a Supabase URL and anonymous key (typically in the
 * Application class), replace the placeholder bodies below with real
 * {@code supabaseClient.auth.signIn(...)}, {@code signUp(...)}, etc. calls
 * and observe auth state via {@code supabaseClient.auth.session()} /
 * {@code supabaseClient.auth.onAuthStateChange(...)}.
 */
public class AuthService {

    private static AuthService instance;

    private Context context;

    private AuthService() {
    }

    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public void initialize(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Signs in a user with email and password.
     * TODO: Replace with a real Supabase Auth
     * {@code supabaseClient.auth.signInWithPassword(...)} call once configured.
     */
    public LiveData<UserResult> signInWithEmail(String email, String password) {
        MutableLiveData<UserResult> result = new MutableLiveData<>();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        com.google.firebase.auth.FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            UserProfile user = new UserProfile();
                            user.setId(firebaseUser.getUid());
                            user.setEmail(firebaseUser.getEmail());
                            user.setAdmin(false);
                            result.setValue(UserResult.success(user));
                        } else {
                            result.setValue(UserResult.error("Login failed: no user returned"));
                        }
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Authentication failed";
                        result.setValue(UserResult.error(error));
                    }
                });
        return result;
    }

    /**
     * Signs up a new user with name, email, username and password.
     * TODO: Replace with a real Supabase Auth
     * {@code supabaseClient.auth.signUp(...)} call (plus a row in the
     * {@code users} table for profile data) once the SDK is configured.
     */
    public LiveData<UserResult> signUp(String name, String email, String username, String password) {
        MutableLiveData<UserResult> result = new MutableLiveData<>();
        // TODO: Implementation will use Supabase Auth SDK once configured.
        result.setValue(UserResult.error("Supabase Auth not yet configured"));
        return result;
    }

    /**
     * Signs out the current user.
     * TODO: Call {@code supabaseClient.auth.signOut()} once configured.
     */
    public void signOut() {
        // TODO: Implementation will use Supabase Auth SDK once configured.
    }

    /**
     * Returns the currently authenticated user, or null if none.
     */
    public LiveData<UserProfile> getCurrentUser() {
        MutableLiveData<UserProfile> liveData = new MutableLiveData<>();
        // TODO: Return current Supabase session user once configured.
        liveData.setValue(null);
        return liveData;
    }

    /**
     * Checks whether the current user has the admin role.
     */
    public LiveData<Boolean> isAdmin() {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        // TODO: Check user role via Supabase (app metadata / users table) once configured.
        liveData.setValue(false);
        return liveData;
    }

    /**
     * Returns the current Supabase auth session, or null if none.
     */
    public LiveData<Session> getSession() {
        MutableLiveData<Session> liveData = new MutableLiveData<>();
        // TODO: Return current Supabase session once configured.
        liveData.setValue(null);
        return liveData;
    }

    private final AuthStateListener authStateListener = new AuthStateListener() {
        @Override
        public void onAuthStateChanged(AuthState state) {
            // TODO: Forward Supabase auth state changes once configured.
        }
    };

    public AuthStateListener getAuthStateListener() {
        return authStateListener;
    }

    /**
     * Result wrapper for sign-in/sign-up operations. Carries either a
     * successful user payload or an error message.
     */
    public static class UserResult {
        private final boolean success;
        private final UserProfile user;
        private final String errorMessage;

        private UserResult(boolean success, UserProfile user, String errorMessage) {
            this.success = success;
            this.user = user;
            this.errorMessage = errorMessage;
        }

        public static UserResult success(UserProfile user) {
            return new UserResult(true, user, null);
        }

        public static UserResult error(String message) {
            return new UserResult(false, null, message);
        }

        public boolean isSuccess() {
            return success;
        }

        public UserProfile getUser() {
            return user;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    /**
     * Represents an authenticated Supabase session.
     */
    public static class Session {
        private final String accessToken;
        private final String refreshToken;
        private final long expiresAt;
        private final UserProfile user;

        public Session(String accessToken, String refreshToken, long expiresAt, UserProfile user) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiresAt = expiresAt;
            this.user = user;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public long getExpiresAt() {
            return expiresAt;
        }

        public UserProfile getUser() {
            return user;
        }
    }
}
