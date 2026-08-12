package myapp.org.userapp;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class SubjectData {
    private final String subjectName;
    private final String subjectCode;
    private final Class<? extends AppCompatActivity> curriculumActivity;
    private final List<Unit> units;
    private final Class<? extends AppCompatActivity> questionPaperMain;
    private final String questionPaperPdf;

    public SubjectData(String subjectName, String subjectCode, Class<? extends AppCompatActivity> curriculumActivity, List<Unit> units, Class<? extends AppCompatActivity> questionPaperMain, String questionPaperPdf) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.curriculumActivity = curriculumActivity;
        this.units = units;
        this.questionPaperMain = questionPaperMain;
        this.questionPaperPdf = questionPaperPdf;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public Class<? extends AppCompatActivity> getCurriculumActivity() {
        return curriculumActivity;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public Class<? extends AppCompatActivity> getQuestionPaperMain() {
        return questionPaperMain;
    }

    public String getQuestionPaperPdf() {
        return questionPaperPdf;
    }

    public static class Unit {
        private final String unitName;
        private final Class<? extends AppCompatActivity> mainActivity;
        private final List<String> pdfUrls;

        public Unit(String unitName, Class<? extends AppCompatActivity> mainActivity, List<String> pdfUrls) {
            this.unitName = unitName;
            this.mainActivity = mainActivity;
            this.pdfUrls = pdfUrls != null ? pdfUrls : Collections.emptyList();
        }

        public String getUnitName() {
            return unitName;
        }

        public Class<? extends AppCompatActivity> getMainActivity() {
            return mainActivity;
        }

        public List<String> getPdfUrls() {
            return pdfUrls;
        }
    }
}
