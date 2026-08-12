package myapp.org.userapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SubjectViewModel extends ViewModel {

    private final MutableLiveData<List<SubjectPdf>> pdfsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>(false);

    public LiveData<List<SubjectPdf>> getPdfsLiveData() {
        return pdfsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void loadSubjectFromAssets(String subjectKey, android.content.Context context) {
        loadingLiveData.setValue(true);
        errorLiveData.setValue(null);

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

            if (pdfs.isEmpty()) {
                errorLiveData.setValue("No PDFs found for this subject");
                pdfsLiveData.setValue(new ArrayList<>());
            } else {
                pdfsLiveData.setValue(pdfs);
            }
        } catch (Exception e) {
            errorLiveData.setValue("Failed to load subject: " + e.getMessage());
            pdfsLiveData.setValue(new ArrayList<>());
        } finally {
            loadingLiveData.setValue(false);
        }
    }

    private String loadJsonFromAssets(android.content.Context context, String filename) throws IOException {
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
