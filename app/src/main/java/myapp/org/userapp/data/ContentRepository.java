package myapp.org.userapp.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import myapp.org.userapp.model.ContentItem;
import myapp.org.userapp.supabase.SupabaseConfig;

public class ContentRepository {

    private static ContentRepository instance;
    private final Context context;
    private final Gson gson;

    // PDFs are hosted in this project's own GitHub repo and served via jsDelivr CDN
    private static final String GITHUB_USER = "Skbonde05";
    private static final String REPO_NAME = "PolyStuff_App";
    private static final String BRANCH = "main";
    private static final String PDF_PATH = "pdf/";
    private static final String JSDELIVR_BASE_URL = "https://cdn.jsdelivr.net/gh/" + GITHUB_USER + "/" + REPO_NAME + "@" + BRANCH + "/" + PDF_PATH;
    private static final String RAW_GITHUB_BASE_URL = "https://raw.githubusercontent.com/" + GITHUB_USER + "/" + REPO_NAME + "/" + BRANCH + "/" + PDF_PATH;

    private ContentRepository(Context context) {
        this.context = context.getApplicationContext();
        this.gson = new Gson();
    }

    public static synchronized ContentRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ContentRepository(context);
        }
        return instance;
    }

    public interface ContentCallback {
        void onResult(List<ContentItem> items, String error);
    }

    public void fetchContentBySubject(String subjectId, ContentCallback callback) {
        new Thread(() -> {
            try {
                String urlString = SupabaseConfig.getSupabaseUrl() + "/rest/v1/content_items?select=*&subject_id=eq." + subjectId + "&order=sort_order.asc";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("apikey", SupabaseConfig.getSupabaseAnonKey());
                conn.setRequestProperty("Authorization", "Bearer " + SupabaseConfig.getSupabaseAnonKey());
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);

                int responseCode = conn.getResponseCode();
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode >= 200 && responseCode < 300 ? conn.getInputStream() : conn.getErrorStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    Type listType = TypeToken.getParameterized(ArrayList.class, ContentItem.class).getType();
                    List<ContentItem> items = gson.fromJson(jsonArray.toString(), listType);
                    List<ContentItem> processedItems = processItemsForGitHubCdn(items);
                    callback.onResult(processedItems != null ? processedItems : new ArrayList<>(), null);
                } else {
                    callback.onResult(new ArrayList<>(), "HTTP " + responseCode);
                }

            } catch (Exception e) {
                callback.onResult(new ArrayList<>(), e.getMessage());
            }
        }).start();
    }

    public void fetchContentByUnit(String unitId, ContentCallback callback) {
        new Thread(() -> {
            try {
                String urlString = SupabaseConfig.getSupabaseUrl() + "/rest/v1/content_items?select=*&unit_id=eq." + unitId + "&order=sort_order.asc";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("apikey", SupabaseConfig.getSupabaseAnonKey());
                conn.setRequestProperty("Authorization", "Bearer " + SupabaseConfig.getSupabaseAnonKey());
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);

                int responseCode = conn.getResponseCode();
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode >= 200 && responseCode < 300 ? conn.getInputStream() : conn.getErrorStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    Type listType = TypeToken.getParameterized(ArrayList.class, ContentItem.class).getType();
                    List<ContentItem> items = gson.fromJson(jsonArray.toString(), listType);
                    List<ContentItem> processedItems = processItemsForGitHubCdn(items);
                    callback.onResult(processedItems != null ? processedItems : new ArrayList<>(), null);
                } else {
                    callback.onResult(new ArrayList<>(), "HTTP " + responseCode);
                }

            } catch (Exception e) {
                callback.onResult(new ArrayList<>(), e.getMessage());
            }
        }).start();
    }

    /**
     * Converts Supabase file paths to GitHub/jsDelivr CDN URLs
     * If URL is already a GitHub URL, keeps it as is
     * If URL is a Firebase URL, extracts filename and converts to GitHub URL
     * If URL is just a filename, constructs full GitHub URL
     */
    private List<ContentItem> processItemsForGitHubCdn(List<ContentItem> items) {
        if (items == null) return new ArrayList<>();
        
        for (ContentItem item : items) {
            String originalUrl = item.getFileUrl();
            if (originalUrl == null || originalUrl.isEmpty()) continue;

            // Already a GitHub/jsDelivr URL
            if (originalUrl.contains("github.com") || originalUrl.contains("jsdelivr.net/gh/")) {
                continue;
            }

            // Firebase Storage URL - extract filename and convert
            if (originalUrl.contains("firebasestorage.googleapis.com")) {
                String fileName = extractFileNameFromFirebaseUrl(originalUrl);
                if (fileName != null && !fileName.isEmpty()) {
                    String jsdelivrUrl = JSDELIVR_BASE_URL + java.net.URLEncoder.encode(fileName).replace("+", "%20");
                    item.setFileUrl(jsdelivrUrl);
                }
            }
            // Relative path or just filename - construct GitHub URL
            else if (!originalUrl.startsWith("http")) {
                String fileName = originalUrl;
                if (fileName.contains("/")) {
                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                }
                String jsdelivrUrl = JSDELIVR_BASE_URL + java.net.URLEncoder.encode(fileName).replace("+", "%20");
                item.setFileUrl(jsdelivrUrl);
            }
        }
        return items;
    }

    private String extractFileNameFromFirebaseUrl(String firebaseUrl) {
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
