package myapp.org.userapp.repository;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import myapp.org.userapp.model.Flashcard;
import myapp.org.userapp.model.PdfItem;
import myapp.org.userapp.model.Quiz;
import myapp.org.userapp.model.Subject;
import myapp.org.userapp.utils.Result;

public class AdminRepository {
    public LiveData<Result> addSubject(Subject subject) {
        return null;
    }

    public LiveData<Result> updateSubject(Subject subject) {
        return null;
    }

    public LiveData<Result> deleteSubject(String subjectId) {
        return null;
    }

    public LiveData<Result> addPdf(PdfItem pdf) {
        return null;
    }

    public LiveData<Result> updatePdf(PdfItem pdf) {
        return null;
    }

    public LiveData<Result> deletePdf(String pdfId) {
        return null;
    }

    public LiveData<Result> addFlashcard(Flashcard flashcard) {
        return null;
    }

    public LiveData<Result> addQuiz(Quiz quiz) {
        return null;
    }

    public LiveData<Result> uploadPdfFile(Uri fileUri, String fileName) {
        return null;
    }

    public LiveData<Boolean> isCurrentUserAdmin() {
        return null;
    }
}
