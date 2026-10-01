package myapp.org.userapp;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageConfig {

    private static final String PREF_NAME = "storage_config";
    private static final String KEY_CDN_BASE_URL = "cdn_base_url";

    // PDFs are hosted in this project's own GitHub repo and served via jsDelivr CDN
    private static final String DEFAULT_GITHUB_USER = "Skbonde05";
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
            try {
                return getCdnBaseUrl(context) + java.net.URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            } catch (Exception e) {
                return getCdnBaseUrl(context) + java.net.URLEncoder.encode(fileName).replace("+", "%20");
            }
        }
        return firebaseUrl;
    }

    /**
     * Converts legacy Firebase Storage URL into a Raw GitHub URL.
     */
    public static String convertFirebaseUrlToRawGithub(String firebaseUrl) {
        String fileName = extractFileNameFromFirebaseUrl(firebaseUrl);
        if (fileName != null && !fileName.isEmpty()) {
            try {
                return DEFAULT_RAW_GITHUB_URL + java.net.URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            } catch (Exception e) {
                return DEFAULT_RAW_GITHUB_URL + java.net.URLEncoder.encode(fileName).replace("+", "%20");
            }
        }
        return firebaseUrl;
    }

    public static java.util.List<String> getJsdelivrCdnCandidateUrls(Context context, String firebaseUrl) {
        java.util.List<String> list = new java.util.ArrayList<>();
        String baseUrl = getCdnBaseUrl(context);
        for (String name : generateFileNameVariations(firebaseUrl)) {
            try {
                list.add(baseUrl + java.net.URLEncoder.encode(name, "UTF-8").replace("+", "%20"));
            } catch (Exception e) {
                list.add(baseUrl + java.net.URLEncoder.encode(name).replace("+", "%20"));
            }
        }
        return list;
    }

    public static java.util.List<String> getRawGithubCandidateUrls(String firebaseUrl) {
        java.util.List<String> list = new java.util.ArrayList<>();
        for (String name : generateFileNameVariations(firebaseUrl)) {
            try {
                list.add(DEFAULT_RAW_GITHUB_URL + java.net.URLEncoder.encode(name, "UTF-8").replace("+", "%20"));
            } catch (Exception e) {
                list.add(DEFAULT_RAW_GITHUB_URL + java.net.URLEncoder.encode(name).replace("+", "%20"));
            }
        }
        return list;
    }

    public static java.util.List<String> generateFileNameVariations(String firebaseUrl) {
        java.util.List<String> variations = new java.util.ArrayList<>();
        String fileName = extractFileNameFromFirebaseUrl(firebaseUrl);
        if (fileName == null || fileName.isEmpty()) return variations;

        variations.add(fileName);

        // 1. Uppercase "UNIT" variation (e.g. "HTML UNIT 1.pdf") - User's GitHub naming convention
        String unitCaps = fileName.replaceAll("(?i)\\bunit\\b", "UNIT");
        if (!variations.contains(unitCaps)) {
            variations.add(unitCaps);
        }

        // 2. Lowercase "unit" variation (e.g. "HTML unit 1.pdf")
        String unitLower = fileName.replaceAll("(?i)\\bunit\\b", "unit");
        if (!variations.contains(unitLower)) {
            variations.add(unitLower);
        }

        // 3. Title case "Unit" variation (e.g. "HTML Unit 1.pdf")
        String unitTitle = fileName.replaceAll("(?i)\\bunit\\b", "Unit");
        if (!variations.contains(unitTitle)) {
            variations.add(unitTitle);
        }

        // 4. Handle "_186_N3" suffix variations
        if (fileName.contains("_186_N3")) {
            String withoutSuffix = fileName.replace("_186_N3", "");
            if (!variations.contains(withoutSuffix)) variations.add(withoutSuffix);
            String withoutSuffixUnitCaps = unitCaps.replace("_186_N3", "");
            if (!variations.contains(withoutSuffixUnitCaps)) variations.add(withoutSuffixUnitCaps);
        } else if (fileName.endsWith(".pdf")) {
            String base = fileName.substring(0, fileName.length() - 4);
            String withSuffix = base + "_186_N3.pdf";
            if (!variations.contains(withSuffix)) variations.add(withSuffix);
            String withSuffixUnitCaps = unitCaps.substring(0, unitCaps.length() - 4) + "_186_N3.pdf";
            if (!variations.contains(withSuffixUnitCaps)) variations.add(withSuffixUnitCaps);
        }

        // 5. All Uppercase filename (e.g. "HTML UNIT 1.PDF")
        String allUpper = fileName.toUpperCase();
        if (!variations.contains(allUpper)) {
            variations.add(allUpper);
        }

        return variations;
    }

    /**
     * Extracts filename from Firebase Storage URL format.
     * Handles both URL-encoded (%2F) and regular (/) path separators.
     */
    public static String extractFileNameFromFirebaseUrl(String firebaseUrl) {
        if (firebaseUrl == null || firebaseUrl.trim().isEmpty()) return null;
        try {
            int pdfIndex = firebaseUrl.indexOf("%2F");
            if (pdfIndex != -1) {
                int queryIndex = firebaseUrl.indexOf("?", pdfIndex);
                String fileNameEncoded = queryIndex != -1 ?
                        firebaseUrl.substring(pdfIndex + 3, queryIndex) :
                        firebaseUrl.substring(pdfIndex + 3);
                return java.net.URLDecoder.decode(fileNameEncoded, "UTF-8").trim();
            }

            pdfIndex = firebaseUrl.lastIndexOf("/o/");
            if (pdfIndex != -1) {
                int queryIndex = firebaseUrl.indexOf("?", pdfIndex);
                String fileName = queryIndex != -1 ?
                        firebaseUrl.substring(pdfIndex + 3, queryIndex) :
                        firebaseUrl.substring(pdfIndex + 3);
                return java.net.URLDecoder.decode(fileName, "UTF-8").trim();
            }

            int lastSlash = firebaseUrl.lastIndexOf("/");
            String name = (lastSlash != -1) ? firebaseUrl.substring(lastSlash + 1) : firebaseUrl;
            int queryIdx = name.indexOf("?");
            if (queryIdx != -1) {
                name = name.substring(0, queryIdx);
            }
            return java.net.URLDecoder.decode(name, "UTF-8").trim();
        } catch (Exception e) {
            // Fallback
        }
        return null;
    }
}
