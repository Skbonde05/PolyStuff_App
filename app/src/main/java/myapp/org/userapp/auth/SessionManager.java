package myapp.org.userapp.auth;

import android.content.Context;
import android.content.SharedPreferences;

import myapp.org.userapp.model.UserProfile;

public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IS_ADMIN = "is_admin";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveSession(UserProfile user) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putBoolean(KEY_IS_ADMIN, user.isAdmin());
        editor.apply();
    }

    public UserProfile getSession() {
        if (!isLoggedIn()) {
            return null;
        }
        UserProfile user = new UserProfile();
        user.setId(prefs.getString(KEY_USER_ID, null));
        user.setName(prefs.getString(KEY_NAME, null));
        user.setEmail(prefs.getString(KEY_EMAIL, null));
        user.setUsername(prefs.getString(KEY_USERNAME, null));
        user.setAdmin(prefs.getBoolean(KEY_IS_ADMIN, false));
        return user;
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return prefs.contains(KEY_USER_ID);
    }

    public void setAdminStatus(boolean isAdmin) {
        prefs.edit().putBoolean(KEY_IS_ADMIN, isAdmin).apply();
    }

    public boolean isAdmin() {
        return prefs.getBoolean(KEY_IS_ADMIN, false);
    }
}
