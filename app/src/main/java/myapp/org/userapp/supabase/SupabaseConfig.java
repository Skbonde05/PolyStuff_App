package myapp.org.userapp.supabase;

import android.content.Context;
import android.util.Log;

import myapp.org.userapp.BuildConfig;

public class SupabaseConfig {

    private static final String TAG = "SupabaseConfig";

    public static String getSupabaseUrl() {
        return BuildConfig.SUPABASE_URL;
    }

    public static String getSupabaseAnonKey() {
        return BuildConfig.SUPABASE_ANON_KEY;
    }

    public static boolean isConfigured() {
        String url = getSupabaseUrl();
        String key = getSupabaseAnonKey();
        boolean configured = url != null && !url.isEmpty() && !url.contains("your-project")
                && key != null && !key.isEmpty() && !key.contains("your-anon-key")
                && !key.endsWith("...");
        if (!configured) {
            Log.w(TAG, "Supabase is not configured. Update gradle.properties with SUPABASE_URL and SUPABASE_ANON_KEY.");
        }
        return configured;
    }

    public static void logStatus(Context context) {
        Log.i(TAG, "Supabase URL: " + getSupabaseUrl());
        Log.i(TAG, "Supabase configured: " + isConfigured());
    }
}
