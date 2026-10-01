package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchManager {

    private static SearchManager instance;
    private final Context context;
    private final List<SearchEntry> searchEntries;

    private SearchManager(Context context) {
        this.context = context.getApplicationContext();
        this.searchEntries = new ArrayList<>();
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
            String json = AssetUtils.loadJsonFromAssets(context, "subjects_data.json");
            JSONArray subjects = new JSONArray(json);

            for (int i = 0; i < subjects.length(); i++) {
                JSONObject subject = subjects.getJSONObject(i);
                String key = subject.optString("key", "");
                JSONArray pdfs = subject.optJSONArray("pdfs");

                if (pdfs != null && pdfs.length() > 0) {
                    for (int j = 0; j < pdfs.length(); j++) {
                        JSONObject pdfObj = pdfs.getJSONObject(j);
                        String title = pdfObj.optString("title", "")
                                .replace("\\n", "")
                                .trim();
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

    /**
     * Tries to navigate to a matching screen for the query.
     * First checks known top-level courses (Python, DS, OS, etc.),
     * then checks the JSON-based subject/PDF entries.
     */
    public boolean navigateTo(String query) {
        if (query == null || query.trim().isEmpty()) return false;

        // 1. Try top-level course shortcuts first
        if (navigateToCourse(query.trim())) return true;

        String normalizedQuery = query.trim().toLowerCase(Locale.ROOT);

        // 2. Exact title match
        for (SearchEntry entry : searchEntries) {
            if (entry.getDisplayName().equalsIgnoreCase(query.trim())) {
                return launchEntry(entry);
            }
        }

        // 3. Partial match
        for (SearchEntry entry : searchEntries) {
            if (entry.getDisplayName().toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
                return launchEntry(entry);
            }
        }

        return false;
    }

    /**
     * Handles top-level course keywords and opens the corresponding activity.
     * Returns true if a course was matched and launched.
     */
    private boolean navigateToCourse(String query) {
        String q = query.toLowerCase(Locale.ROOT);

        try {
            if (q.contains("python") || q.contains("pwp")) {
                launchActivity(pwp.class);
            } else if (q.contains("data structure") || q.contains("dsa") || q.equals("ds")) {
                launchActivity(ds.class);
            } else if (q.contains("oop") || q.contains("object oriented")
                    || q.contains("c++") || q.contains("cpp")) {
                launchActivity(cpp.class);
            } else if (q.contains("operating system") || q.equals("os")) {
                launchActivity(os.class);
            } else if (q.contains("computer network") || q.contains("network") || q.contains("acn")) {
                launchActivity(acn.class);
            } else if (q.contains("cloud") || q.equals("cc")) {
                launchActivity(cc.class);
            } else if (q.contains("android") || q.equals("aap")) {
                launchActivity(aap.class);
            } else if (q.contains("java")) {
                launchActivity(jp2.class);
            } else if (q.contains("data mining") || q.equals("dmi")) {
                launchActivity(dmi.class);
            } else if (q.contains("php") || q.contains("sql")) {
                launchActivity(php_information.class);
            } else if (q.contains("iot") || q.contains("internet of things")) {
                launchActivity(iot_information.class);
            } else if (q.contains("c programming") || q.equals("c")) {
                launchActivity(c_information.class);
            } else if (q.contains("java information") || q.contains("java course")) {
                launchActivity(java_information.class);
            } else if (q.contains("php information")) {
                launchActivity(php_information.class);
            } else if (q.contains("feedback")) {
                launchActivity(feedback.class);
            } else if (q.contains("rate") || q.contains("rating")) {
                launchActivity(rateus.class);
            } else if (q.contains("notification")) {
                launchActivity(notifications.class);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            android.util.Log.e("SearchManager", "Course navigation failed", e);
            return false;
        }
    }

    private void launchActivity(Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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
            android.util.Log.e("SearchManager", "Entry navigation failed", e);
        }
        return false;
    }

    public int getSize() {
        return searchEntries.size();
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

        String getSubjectKey()    { return subjectKey; }
        String getDisplayName()   { return displayName; }
        String getPdfUrl()        { return pdfUrl; }
    }
}