package myapp.org.userapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SubjectContentActivity extends AppCompatActivity {

    public static final String EXTRA_SUBJECT_KEY = "subject_key";
    public static final String EXTRA_SUBJECT_TITLE = "subject_title";

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private SubjectViewModel viewModel;
    private View rootLayout;
    private View progressOverlay;
    private TextView emptyStateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_content_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rootLayout = findViewById(R.id.rootLayout);
        recyclerView = findViewById(R.id.recyclerView);
        progressOverlay = findViewById(R.id.progressOverlay);
        emptyStateText = findViewById(R.id.emptyStateText);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        rootLayout.startAnimation(animation);

        viewModel = new ViewModelProvider(this).get(SubjectViewModel.class);

        viewModel.getLoadingLiveData().observe(this, isLoading -> {
            if (isLoading != null && isLoading) {
                progressOverlay.setVisibility(View.VISIBLE);
            } else {
                progressOverlay.setVisibility(View.GONE);
            }
        });

        viewModel.getPdfsLiveData().observe(this, pdfs -> {
            if (pdfs != null && !pdfs.isEmpty()) {
                List<Product> products = new ArrayList<>();
                for (SubjectPdf pdf : pdfs) {
                    products.add(new Product(
                        1,
                        pdf.getTitle(),
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        pdf.getUrl()
                    ));
                }
                adapter = new ProductAdapter(products);
                recyclerView.setAdapter(adapter);
                emptyStateText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                emptyStateText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        viewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        String subjectKey = getIntent().getStringExtra(EXTRA_SUBJECT_KEY);
        String subjectTitle = getIntent().getStringExtra(EXTRA_SUBJECT_TITLE);

        if (subjectTitle != null && !subjectTitle.isEmpty()) {
            getSupportActionBar().setTitle(subjectTitle);
        } else if (subjectKey != null) {
            getSupportActionBar().setTitle(deriveTitle(subjectKey));
        }

        if (subjectKey != null) {
            viewModel.loadSubjectFromAssets(subjectKey, this);
        } else {
            Toast.makeText(this, "Subject key missing", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void launch(Context context, String subjectKey) {
        Intent intent = new Intent(context, SubjectContentActivity.class);
        intent.putExtra(EXTRA_SUBJECT_KEY, subjectKey);
        context.startActivity(intent);
    }

    private String deriveTitle(String key) {
        String title = key.replace("_main", "")
                          .replace("_", " ")
                          .toLowerCase(Locale.getDefault());

        if (title.length() > 0) {
            title = Character.toUpperCase(title.charAt(0)) + title.substring(1);
        }

        if (title.contains("Mcq")) title = title.replace("Mcq", "MCQ's");
        if (title.contains("Que")) title = title.replace("Que", "Question Paper");
        if (title.contains("Tut")) title = title.replace("Tut", "Tutorial");
        if (title.contains("Ref")) title = title.replace("Ref", "References");
        if (title.contains("Cmd")) title = title.replace("Cmd", "Commands");
        if (title.contains("U1")) title = title.replace("U1", "Unit 1");
        if (title.contains("U2")) title = title.replace("U2", "Unit 2");
        if (title.contains("U3")) title = title.replace("U3", "Unit 3");
        if (title.contains("U4")) title = title.replace("U4", "Unit 4");
        if (title.contains("U5")) title = title.replace("U5", "Unit 5");
        if (title.contains("U6")) title = title.replace("U6", "Unit 6");

        return title;
    }
}
