package myapp.org.userapp.supabase;

import android.content.Context;
import android.util.Log;

/**
 * Java-friendly wrapper around the Kotlin SupabaseClient singleton.
 *
 * This class provides static helper methods so the existing Java codebase
 * can reference Supabase configuration without importing Kotlin types directly.
 */
public class SupabaseClientWrapper {

    private static final String TAG = "SupabaseClientWrapper";

    public static String getSupabaseUrl() {
        return SupabaseConfig.getSupabaseUrl();
    }

    public static String getSupabaseAnonKey() {
        return SupabaseConfig.getSupabaseAnonKey();
    }

    public static boolean isConfigured() {
        String url = SupabaseConfig.getSupabaseUrl();
        String key = SupabaseConfig.getSupabaseAnonKey();
        boolean configured = url != null && !url.isEmpty() && !url.contains("your-project")
                && key != null && !key.isEmpty() && !key.contains("your-anon-key")
                && !key.endsWith("...");
        if (!configured) {
            Log.w(TAG, "Supabase is not configured. Update SupabaseConfig.java with real URL and complete anon key.");
        }
        return configured;
    }

    public static void logStatus(Context context) {
        Log.i(TAG, "Supabase URL: " + getSupabaseUrl());
        Log.i(TAG, "Supabase configured: " + isConfigured());
    }
}
