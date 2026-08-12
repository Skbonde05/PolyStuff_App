package myapp.org.userapp;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageConfig {

    private static final String PREF_NAME = "storage_config";
    private static final String KEY_CDN_BASE_URL = "cdn_base_url";

    // 100% Free Unlimited CDN via jsDelivr + GitHub (No credit card / no monthly bandwidth limits)
    private static final String DEFAULT_GITHUB_USER = "Shivam154CO";
    private static final String DEFAULT_REPO_NAME = "PolyStuff_App";
    private static final String DEFAULT_BRANCH = "main";

    public static final String DEFAULT_JSDELIVR_CDN_URL = 
            "https://cdn.jsdelivr.net/gh/" + DEFAULT_GITHUB_USER + "/" + DEFAULT_REPO_NAME + "@" + DEFAULT_BRANCH + "/pdf/";

    public static final String DEFAULT_RAW_GITHUB_URL = 
            "https://raw.githubusercontent.com/" + DEFAULT_GITHUB_USER + "/" + DEFAULT_REPO_NAME + "/" + DEFAULT_BRANCH + "/pdf/";

    public static String getCdnBaseUrl(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_CDN_BASE_URL, DEFAULT_JSDELIVR_CDN_URL);
    }

    public static void setCdnBaseUrl(Context context, String newBaseUrl) {
        if (newBaseUrl == null || newBaseUrl.trim().isEmpty()) return;
        if (!newBaseUrl.endsWith("/")) {
            newBaseUrl = newBaseUrl + "/";
        }
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_CDN_BASE_URL, newBaseUrl)
                .apply();
    }

    /**
     * Converts legacy Firebase Storage URL into a high-speed GitHub jsDelivr CDN URL.
     */
    public static String convertFirebaseUrlToJsdelivr(Context context, String firebaseUrl) {
        String fileName = extractFileNameFromFirebaseUrl(firebaseUrl);
        if (fileName != null && !fileName.isEmpty()) {
            return getCdnBaseUrl(context) + java.net.URLEncoder.encode(fileName).replace("+", "%20");
        }
        return firebaseUrl;
    }

    /**
     * Converts legacy Firebase Storage URL into a Raw GitHub URL.
     */
    public static String convertFirebaseUrlToRawGithub(String firebaseUrl) {
        String fileName = extractFileNameFromFirebaseUrl(firebaseUrl);
        if (fileName != null && !fileName.isEmpty()) {
            return DEFAULT_RAW_GITHUB_URL + java.net.URLEncoder.encode(fileName).replace("+", "%20");
        }
        return firebaseUrl;
    }

    /**
     * Extracts filename from Firebase Storage URL format.
     * Handles both URL-encoded (%2F) and regular (/) path separators.
     */
    public static String extractFileNameFromFirebaseUrl(String firebaseUrl) {
        if (firebaseUrl == null) return null;
        try {
            int pdfIndex = firebaseUrl.indexOf("%2F");
            if (pdfIndex != -1) {
                int queryIndex = firebaseUrl.indexOf("?", pdfIndex);
                String fileNameEncoded = queryIndex != -1 ?
                        firebaseUrl.substring(pdfIndex + 3, queryIndex) :
                        firebaseUrl.substring(pdfIndex + 3);
                return java.net.URLDecoder.decode(fileNameEncoded, "UTF-8");
            }

            pdfIndex = firebaseUrl.lastIndexOf("/o/");
            if (pdfIndex != -1) {
                int queryIndex = firebaseUrl.indexOf("?", pdfIndex);
                String fileName = queryIndex != -1 ?
                        firebaseUrl.substring(pdfIndex + 3, queryIndex) :
                        firebaseUrl.substring(pdfIndex + 3);
                return java.net.URLDecoder.decode(fileName, "UTF-8");
            }
        } catch (Exception e) {
            // Fallback
        }
        return null;
    }
}
