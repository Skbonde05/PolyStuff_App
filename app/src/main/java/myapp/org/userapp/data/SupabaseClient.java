package myapp.org.userapp.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import myapp.org.userapp.supabase.SupabaseConfig;

public class SupabaseClient {

    private static final String TAG = "SupabaseClient";
    private static SupabaseClient instance;
    private final Context context;
    private final Gson gson;

    private SupabaseClient(Context context) {
        this.context = context.getApplicationContext();
        this.gson = new Gson();
    }

    public static synchronized SupabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new SupabaseClient(context);
        }
        return instance;
    }

    public SupabaseRestResponse executeRequest(String method, String endpoint, String jsonBody) {
        HttpURLConnection conn = null;
        try {
            String urlString = SupabaseConfig.getSupabaseUrl() + "/rest/v1/" + endpoint;
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
                conn.setRequestProperty("apikey", SupabaseConfig.getSupabaseAnonKey());
                conn.setRequestProperty("Authorization", "Bearer " + SupabaseConfig.getSupabaseAnonKey());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Prefer", "return=representation");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoInput(true);

            if (!method.equals("GET") && jsonBody != null && !jsonBody.isEmpty()) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            int responseCode = conn.getResponseCode();
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode >= 200 && responseCode < 300 ? conn.getInputStream() : conn.getErrorStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            return new SupabaseRestResponse(responseCode, response.toString());

        } catch (Exception e) {
            Log.e(TAG, "Request failed: " + e.getMessage(), e);
            return new SupabaseRestResponse(-1, "{\"error\":\"" + e.getMessage() + "\"}");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public <T> List<T> fetchList(String endpoint, Class<T> clazz) {
        SupabaseRestResponse response = executeRequest("GET", endpoint, null);
        if (response.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            try {
                JSONArray jsonArray = new JSONArray(response.getBody());
                Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
                return gson.fromJson(jsonArray.toString(), listType);
            } catch (Exception e) {
                Log.e(TAG, "Parse error: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    public <T> T fetchOne(String endpoint, Class<T> clazz) {
        SupabaseRestResponse response = executeRequest("GET", endpoint, null);
        if (response.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            try {
                JSONObject jsonObject = new JSONObject(response.getBody());
                return gson.fromJson(jsonObject.toString(), clazz);
            } catch (Exception e) {
                Log.e(TAG, "Parse error: " + e.getMessage());
            }
        }
        return null;
    }
}
