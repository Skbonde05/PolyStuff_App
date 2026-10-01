package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import myapp.org.userapp.adapter.SubjectContentAdapter;
import myapp.org.userapp.data.ContentRepository;
import myapp.org.userapp.model.ContentItem;
import myapp.org.userapp.model.Subject;

public class SubjectDetailActivity extends AppCompatActivity implements SubjectContentAdapter.OnItemClickListener {

    public static final String EXTRA_SUBJECT_KEY = "subject_key";
    public static final String EXTRA_SUBJECT_ID = "subject_id";
    public static final String EXTRA_SUBJECT_TITLE = "subject_title";

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private SubjectContentAdapter adapter;
    private ContentRepository contentRepository;
    private List<ContentItem> contentItems;
    private String subjectKey;
    private String subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerViewContent);
        contentRepository = ContentRepository.getInstance(this);
        contentItems = new ArrayList<>();

        subjectKey = getIntent().getStringExtra(EXTRA_SUBJECT_KEY);
        subjectId = getIntent().getStringExtra(EXTRA_SUBJECT_ID);
        String title = getIntent().getStringExtra(EXTRA_SUBJECT_TITLE);

        if (title != null) {
            toolbar.setTitle(title);
        } else if (subjectKey != null) {
            toolbar.setTitle(subjectKey.toUpperCase());
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        adapter = new SubjectContentAdapter(this, contentItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        loadContent();
    }

    private void loadContent() {
        String targetId = subjectId != null ? subjectId : subjectKey;
        if (targetId == null || targetId.isEmpty()) {
            Toast.makeText(this, "Invalid subject", Toast.LENGTH_SHORT).show();
            return;
        }

        contentRepository.fetchContentBySubject(targetId, (items, error) -> {
            if (error != null) {
                Toast.makeText(this, "Failed to load content: " + error, Toast.LENGTH_SHORT).show();
                return;
            }
            contentItems.clear();
            contentItems.addAll(items);
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
    }

    @Override
    public void onItemClick(ContentItem item) {
        if ("pdf".equalsIgnoreCase(item.getContentType())) {
            Intent intent = new Intent(this, PdfViewerActivity.class);
            intent.putExtra(PdfViewerActivity.EXTRA_PDF_TITLE, item.getTitle());
            intent.putExtra(PdfViewerActivity.EXTRA_PDF_URL, item.getFileUrl());
            startActivity(intent);
        } else if ("mcq".equalsIgnoreCase(item.getContentType())) {
            Intent intent = new Intent(this, PdfViewerActivity.class);
            intent.putExtra(PdfViewerActivity.EXTRA_PDF_TITLE, item.getTitle());
            intent.putExtra(PdfViewerActivity.EXTRA_PDF_URL, item.getFileUrl());
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, PdfViewerActivity.class);
            intent.putExtra(PdfViewerActivity.EXTRA_PDF_TITLE, item.getTitle());
            intent.putExtra(PdfViewerActivity.EXTRA_PDF_URL, item.getFileUrl());
            startActivity(intent);
        }
    }
}
