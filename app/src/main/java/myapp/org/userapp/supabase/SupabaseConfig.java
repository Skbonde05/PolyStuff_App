package myapp.org.userapp.supabase;

public class SupabaseConfig {
    public static final String SUPABASE_URL = "https://gvanbazxuootjfoetupd.supabase.co";
    public static final String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imd2YW5iYXp4dW9vdGpmb2V0dXBkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODY1NDg5NDEsImV4cCI6MjEwMjEyNDk0MX0.BPnGtFkUX0-hqN-y3fFya4hCyrL-B31x73KllAesPhI";

    public static String getSupabaseUrl() {
        return SUPABASE_URL;
    }

    public static String getSupabaseAnonKey() {
        return SUPABASE_ANON_KEY;
    }
}
