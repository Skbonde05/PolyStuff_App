package myapp.org.userapp.repository;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import myapp.org.userapp.model.Flashcard;
import myapp.org.userapp.model.PdfItem;
import myapp.org.userapp.model.Quiz;
import myapp.org.userapp.model.Subject;
import myapp.org.userapp.utils.Result;
import java.util.List;

public class ContentRepository {
    public LiveData<List<Subject>> getAllSubjects() {
        return null;
    }

    public LiveData<Subject> getSubjectById(String id) {
        return null;
    }

    public LiveData<Subject> getSubjectByKey(String key) {
        return null;
    }

    public LiveData<List<PdfItem>> getPdfsBySubjectId(String subjectId) {
        return null;
    }

    public LiveData<List<Flashcard>> getFlashcardsBySubjectId(String subjectId) {
        return null;
    }

    public LiveData<List<Quiz>> getQuizzesBySubjectId(String subjectId) {
        return null;
    }

    public LiveData<List<Subject>> searchSubjects(String query) {
        return null;
    }

    public LiveData<List<PdfItem>> searchPdfs(String query) {
        return null;
    }
}
