package myapp.org.userapp;

import android.content.Context;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {

    public LiveData<List<SubjectPdf>> getSubjectPdfs(String subjectKey, Context context) {
        androidx.lifecycle.MutableLiveData<List<SubjectPdf>> result = new androidx.lifecycle.MutableLiveData<>();
        new Thread(() -> {
            try {
                String json = loadJsonFromAssets(context, "subjects_data.json");
                JSONArray subjects = new JSONArray(json);
                List<SubjectPdf> pdfs = new ArrayList<>();

                for (int i = 0; i < subjects.length(); i++) {
                    JSONObject subject = subjects.getJSONObject(i);
                    if (subject.getString("key").equals(subjectKey)) {
                        JSONArray pdfArray = subject.getJSONArray("pdfs");
                        for (int j = 0; j < pdfArray.length(); j++) {
                            JSONObject pdf = pdfArray.getJSONObject(j);
                            pdfs.add(new SubjectPdf(
                                pdf.getString("title").replace("\\n", "").trim(),
                                pdf.getString("url")
                            ));
                        }
                        break;
                    }
                }
                result.postValue(pdfs);
            } catch (Exception e) {
                result.postValue(new ArrayList<>());
            }
        }).start();
        return result;
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
}
