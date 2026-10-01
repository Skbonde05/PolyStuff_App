package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import myapp.org.userapp.adapter.SubjectCardAdapter;
import myapp.org.userapp.data.SubjectRepository;
import myapp.org.userapp.model.Subject;

public class CurriculumActivity extends AppCompatActivity implements SubjectCardAdapter.OnSubjectClickListener {

    public static final String EXTRA_SUBJECT_KEY = "subject_key";
    public static final String EXTRA_SUBJECT_ID = "subject_id";
    public static final String EXTRA_SUBJECT_TITLE = "subject_title";

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private TextView textViewEmpty;
    private SubjectCardAdapter adapter;
    private List<Subject> subjects;
    private String subjectKey;
    private String subjectId;
    private boolean isCategoryMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerViewUnits);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        subjects = new ArrayList<>();

        subjectKey = getIntent().getStringExtra(EXTRA_SUBJECT_KEY);
        subjectId = getIntent().getStringExtra(EXTRA_SUBJECT_ID);
        String title = getIntent().getStringExtra(EXTRA_SUBJECT_TITLE);

        if (title != null) {
            toolbar.setTitle(title);
        } else if (subjectKey != null) {
            toolbar.setTitle(subjectKey.toUpperCase() + " Curriculum");
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        adapter = new SubjectCardAdapter(this, subjects, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        loadCurriculum();
    }

    private void loadCurriculum() {
        if (subjectKey == null && subjectId == null) {
            showEmptyState();
            return;
        }

        // Category mode: show all subjects from Supabase
        if ("cs_categories".equals(subjectKey) || "it_categories".equals(subjectKey)) {
            isCategoryMode = true;
            SubjectRepository.getInstance(this).fetchAllSubjects((subjectList, error) -> {
                if (error != null) {
                    Toast.makeText(this, "Failed to load subjects: " + error, Toast.LENGTH_SHORT).show();
                    showEmptyState();
                    return;
                }
                subjects.clear();
                if (subjectList != null && !subjectList.isEmpty()) {
                    subjects.addAll(subjectList);
                }
                updateViewVisibility();
            });
            return;
        }

        // Single subject mode: show units
        String targetId = subjectId != null ? subjectId : subjectKey;
        SubjectRepository.getInstance(this).fetchSubjectByKey(targetId, (subject, error) -> {
            if (error != null || subject == null) {
                Toast.makeText(this, "Subject not found. Please check Supabase data.", Toast.LENGTH_SHORT).show();
                showEmptyState();
                return;
            }

            for (int i = 1; i <= 6; i++) {
                Subject unitSubject = new Subject();
                unitSubject.setId(subject.getId());
                unitSubject.setKey(subject.getKey());
                unitSubject.setTitle("Unit " + i);
                unitSubject.setDescription("Unit " + i + " materials");
                unitSubject.setCategory(subject.getCategory());
                unitSubject.setPdfCount(i);
                unitSubject.setActive(true);
                subjects.add(unitSubject);
            }

            Subject questionPaper = new Subject();
            questionPaper.setId(subject.getId());
            questionPaper.setKey(subject.getKey());
            questionPaper.setTitle("Question Paper");
            questionPaper.setDescription("Previous year question papers");
            questionPaper.setCategory(subject.getCategory());
            questionPaper.setPdfCount(1);
            questionPaper.setActive(true);
            subjects.add(questionPaper);

            updateViewVisibility();
        });
    }

    private void updateViewVisibility() {
        runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
            if (subjects.isEmpty()) {
                showEmptyState();
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                if (textViewEmpty != null) {
                    textViewEmpty.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showEmptyState() {
        runOnUiThread(() -> {
            recyclerView.setVisibility(View.GONE);
            if (textViewEmpty != null) {
                textViewEmpty.setVisibility(View.VISIBLE);
                textViewEmpty.setText("No content available.\nPlease add subjects in Admin CMS or check Supabase connection.");
            }
        });
    }

    @Override
    public void onSubjectClick(Subject subject) {
        if (isCategoryMode) {
            Intent intent = new Intent(this, SubjectDetailActivity.class);
            intent.putExtra(SubjectDetailActivity.EXTRA_SUBJECT_ID, subject.getId());
            intent.putExtra(SubjectDetailActivity.EXTRA_SUBJECT_TITLE, subject.getTitle());
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SubjectDetailActivity.class);
            intent.putExtra(SubjectDetailActivity.EXTRA_SUBJECT_ID, subject.getId());
            intent.putExtra(SubjectDetailActivity.EXTRA_SUBJECT_TITLE, subject.getTitle());
            if ("Question Paper".equals(subject.getTitle())) {
                intent.putExtra("filter_type", "question_paper");
            }
            startActivity(intent);
        }
    }
}
