package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchManager {

    private static SearchManager instance;
    private final Context context;
    private List<SearchEntry> searchEntries;

    private SearchManager(Context context) {
        this.context = context.getApplicationContext();
        searchEntries = new ArrayList<>();
        initializeMappings();
    }

    public static synchronized SearchManager getInstance(Context context) {
        if (instance == null) {
            instance = new SearchManager(context);
        }
        return instance;
    }

    private void initializeMappings() {
        searchEntries.clear();
        loadEntriesFromAssets();
    }

    private void loadEntriesFromAssets() {
        try {
            String json = loadJsonFromAssets(context, "subjects_data.json");
            JSONArray subjects = new JSONArray(json);

            for (int i = 0; i < subjects.length(); i++) {
                JSONObject subject = subjects.getJSONObject(i);
                String key = subject.optString("key", "");
                JSONArray pdfs = subject.optJSONArray("pdfs");

                if (pdfs != null && pdfs.length() > 0) {
                    for (int j = 0; j < pdfs.length(); j++) {
                        JSONObject pdfObj = pdfs.getJSONObject(j);
                        String title = pdfObj.optString("title", "").replace("\\n", "").trim();
                        String url = pdfObj.optString("url", "");
                        if (!title.isEmpty()) {
                            searchEntries.add(new SearchEntry(key, title, url));
                        }
                    }
                }
            }

            addLegacyMappings();
        } catch (Exception e) {
            addLegacyMappings();
        }
    }

    private void addLegacyMappings() {
        searchEntries.add(new SearchEntry("ma_it", "Multimedia", null));
        searchEntries.add(new SearchEntry("ma_it", "MA References", null));
        searchEntries.add(new SearchEntry("ma_it", "Multimedia References", null));
        searchEntries.add(new SearchEntry("dcn_it", "DCN References", null));
        searchEntries.add(new SearchEntry("dcn_it", "Data Communication and Networking References", null));
        searchEntries.add(new SearchEntry("dbms_it", "Database Management System References", null));
        searchEntries.add(new SearchEntry("dbms_it", "DBMS References", null));
        searchEntries.add(new SearchEntry("se_it", "Software Engineering References", null));
        searchEntries.add(new SearchEntry("se_it", "SE References", null));
        searchEntries.add(new SearchEntry("iot_it", "IOT References", null));
        searchEntries.add(new SearchEntry("iot_it", "Internet Of Things References", null));
        searchEntries.add(new SearchEntry("nma_it", "NMA References", null));
        searchEntries.add(new SearchEntry("nma_it", "Network Management and Administration References", null));
        searchEntries.add(new SearchEntry("php_it", "PHP References", null));
        searchEntries.add(new SearchEntry("aap_mcq_main", "MAD MCQ's", null));
    }

    public boolean navigateTo(String query) {
        if (query == null || query.trim().isEmpty()) {
            return false;
        }

        String normalizedQuery = query.trim().toLowerCase();

        // 1. Try exact title match first
        for (SearchEntry entry : searchEntries) {
            if (entry.getDisplayName().equalsIgnoreCase(query.trim()) || entry.getDisplayName().toLowerCase().equalsIgnoreCase(normalizedQuery)) {
                return launchEntry(entry);
            }
        }

        // 2. Try partial match
        for (SearchEntry entry : searchEntries) {
            if (entry.getDisplayName().toLowerCase().contains(normalizedQuery)) {
                return launchEntry(entry);
            }
        }

        return false;
    }

    private boolean launchEntry(SearchEntry entry) {
        try {
            if (entry.getPdfUrl() != null && !entry.getPdfUrl().trim().isEmpty()) {
                Intent intent = new Intent(context, PdfViewerActivity.class);
                intent.putExtra(PdfViewerActivity.EXTRA_PDF_TITLE, entry.getDisplayName());
                intent.putExtra(PdfViewerActivity.EXTRA_PDF_URL, entry.getPdfUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            } else if (entry.getSubjectKey() != null && !entry.getSubjectKey().trim().isEmpty()) {
                Intent intent = new Intent(context, SubjectContentActivity.class);
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_KEY, entry.getSubjectKey());
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_TITLE, entry.getDisplayName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Navigation error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public int getSize() {
        return searchEntries.size();
    }

    private String loadJsonFromAssets(Context context, String filename) throws IOException {
        InputStream is = context.getAssets().open(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        is.close();
        return sb.toString();
    }

    static class SearchEntry {
        private final String subjectKey;
        private final String displayName;
        private final String pdfUrl;

        SearchEntry(String subjectKey, String displayName, String pdfUrl) {
            this.subjectKey = subjectKey;
            this.displayName = displayName;
            this.pdfUrl = pdfUrl;
        }

        String getSubjectKey() {
            return subjectKey;
        }

        String getDisplayName() {
            return displayName;
        }

        String getPdfUrl() {
            return pdfUrl;
        }
    }
}
