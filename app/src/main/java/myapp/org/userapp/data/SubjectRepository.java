package myapp.org.userapp.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import myapp.org.userapp.model.Subject;
import myapp.org.userapp.supabase.SupabaseConfig;

public class SubjectRepository {

    private static SubjectRepository instance;
    private final Context context;
    private final Gson gson;

    private SubjectRepository(Context context) {
        this.context = context.getApplicationContext();
        this.gson = new Gson();
    }

    public static synchronized SubjectRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SubjectRepository(context);
        }
        return instance;
    }

    public interface SubjectsCallback {
        void onResult(List<Subject> subjects, String error);
    }

    public void fetchAllSubjects(SubjectsCallback callback) {
        new Thread(() -> {
            try {
                String urlString = SupabaseConfig.getSupabaseUrl() + "/rest/v1/subjects?select=*&is_active=eq.true&order=sort_order.asc";
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
                    Type listType = TypeToken.getParameterized(ArrayList.class, Subject.class).getType();
                    List<Subject> subjects = gson.fromJson(jsonArray.toString(), listType);
                    callback.onResult(subjects != null ? subjects : new ArrayList<>(), null);
                } else {
                    callback.onResult(new ArrayList<>(), "HTTP " + responseCode + ": " + response.toString());
                }

            } catch (Exception e) {
                callback.onResult(new ArrayList<>(), e.getMessage());
            }
        }).start();
    }

    public interface SubjectByKeyCallback {
        void onResult(Subject subject, String error);
    }

    public void fetchSubjectByKey(String key, SubjectByKeyCallback callback) {
        new Thread(() -> {
            try {
                String encodedKey = key.replace("'", "''");
                String urlString = SupabaseConfig.getSupabaseUrl() + "/rest/v1/subjects?select=*&key=eq." + encodedKey + "&is_active=eq.true&limit=1";
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
                    if (jsonArray.length() > 0) {
                        Subject subject = gson.fromJson(jsonArray.getJSONObject(0).toString(), Subject.class);
                        callback.onResult(subject, null);
                    } else {
                        callback.onResult(null, "Subject not found");
                    }
                } else {
                    callback.onResult(null, "HTTP " + responseCode);
                }

            } catch (Exception e) {
                callback.onResult(null, e.getMessage());
            }
        }).start();
    }
}
