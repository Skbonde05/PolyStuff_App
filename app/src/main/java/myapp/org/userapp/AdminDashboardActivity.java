package myapp.org.userapp;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboardActivity extends AppCompatActivity {

    private static final String TAG = "AdminDashboardActivity";

    private TextView tvTotalSubjects, tvTotalPdfs;
    private ProgressBar adminProgressBar;
    private RecyclerView adminRecyclerView;
    private MaterialButton btnAddContent, btnRefreshData;
    private AdminContentAdapter adapter;
    private List<AdminItemModel> itemList = new ArrayList<>();
    private DatabaseReference subjectsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Smart Notes Admin CMS");
        }

        tvTotalSubjects = findViewById(R.id.tvTotalSubjects);
        tvTotalPdfs = findViewById(R.id.tvTotalPdfs);
        adminProgressBar = findViewById(R.id.adminProgressBar);
        adminRecyclerView = findViewById(R.id.adminRecyclerView);
        btnAddContent = findViewById(R.id.fabAddContent);
        btnRefreshData = null;

        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminContentAdapter(itemList);
        adminRecyclerView.setAdapter(adapter);

        subjectsRef = FirebaseDatabase.getInstance().getReference("subjects");

        verifyAdminAndInit();
    }

    private void verifyAdminAndInit() {
        adminProgressBar.setVisibility(View.VISIBLE);
        AdminManager.getInstance().checkIsAdmin(isAdmin -> {
            if (!isAdmin) {
                adminProgressBar.setVisibility(View.GONE);
                Toast.makeText(AdminDashboardActivity.this, "Access Denied: Admin privileges required.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                setupAdminFeatures();
                loadContentFromFirebase();
            }
        });
    }

    private void setupAdminFeatures() {
        if (btnAddContent != null) {
            btnAddContent.setOnClickListener(v -> showAddEditDialog(null));
        }
        if (btnRefreshData != null) {
            btnRefreshData.setOnClickListener(v -> loadContentFromFirebase());
        }
    }

    private void loadContentFromFirebase() {
        adminProgressBar.setVisibility(View.VISIBLE);
        subjectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                int totalSubjectsCount = 0;
                int totalPdfsCount = 0;

                if (snapshot.exists()) {
                    for (DataSnapshot subjectSnap : snapshot.getChildren()) {
                        totalSubjectsCount++;
                        String subjectKey = subjectSnap.getKey();
                        DataSnapshot pdfsSnap = subjectSnap.child("pdfs");

                        for (DataSnapshot pdfSnap : pdfsSnap.getChildren()) {
                            totalPdfsCount++;
                            String pdfKey = pdfSnap.getKey();
                            String title = pdfSnap.child("title").getValue(String.class);
                            String url = pdfSnap.child("url").getValue(String.class);

                            itemList.add(new AdminItemModel(subjectKey, pdfKey, title, url));
                        }
                    }
                }

                tvTotalSubjects.setText(String.valueOf(totalSubjectsCount));
                tvTotalPdfs.setText(String.valueOf(totalPdfsCount));

                adminProgressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                adminProgressBar.setVisibility(View.GONE);
                Log.e(TAG, "Database read error: " + error.getMessage());
                Toast.makeText(AdminDashboardActivity.this, "Failed to load content: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddEditDialog(AdminItemModel itemToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(itemToEdit == null ? "Add New Note/PDF" : "Edit Note");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 20);

        final EditText etSubjectKey = new EditText(this);
        etSubjectKey.setHint("Subject Key (e.g. acn_u1_main)");
        if (itemToEdit != null) etSubjectKey.setText(itemToEdit.subjectKey);
        layout.addView(etSubjectKey);

        final EditText etTitle = new EditText(this);
        etTitle.setHint("Note Title (e.g. Unit 1 Introduction)");
        if (itemToEdit != null) etTitle.setText(itemToEdit.title);
        layout.addView(etTitle);

        final EditText etUrl = new EditText(this);
        etUrl.setHint("PDF Filename or CDN Link");
        if (itemToEdit != null) etUrl.setText(itemToEdit.url);
        layout.addView(etUrl);

        builder.setView(layout);

        builder.setPositiveButton(itemToEdit == null ? "Add" : "Save", (dialog, which) -> {
            String subjectKey = etSubjectKey.getText().toString().trim();
            String title = etTitle.getText().toString().trim();
            String url = etUrl.getText().toString().trim();

            if (subjectKey.isEmpty() || title.isEmpty() || url.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            saveContentToFirebase(subjectKey, itemToEdit != null ? itemToEdit.pdfKey : null, title, url);
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void saveContentToFirebase(String subjectKey, String pdfKey, String title, String url) {
        DatabaseReference pdfRef;
        if (pdfKey != null && !pdfKey.isEmpty()) {
            pdfRef = subjectsRef.child(subjectKey).child("pdfs").child(pdfKey);
        } else {
            pdfRef = subjectsRef.child(subjectKey).child("pdfs").push();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("url", url);

        pdfRef.setValue(data).addOnSuccessListener(aVoid -> {
            Toast.makeText(AdminDashboardActivity.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
            loadContentFromFirebase();
        }).addOnFailureListener(e -> {
            Toast.makeText(AdminDashboardActivity.this, "Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void deleteItem(AdminItemModel item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete '" + item.title + "'?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    subjectsRef.child(item.subjectKey).child("pdfs").child(item.pdfKey)
                            .removeValue()
                            .addOnSuccessListener(aVoid -> Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class AdminItemModel {
        String subjectKey;
        String pdfKey;
        String title;
        String url;

        AdminItemModel(String subjectKey, String pdfKey, String title, String url) {
            this.subjectKey = subjectKey;
            this.pdfKey = pdfKey;
            this.title = title;
            this.url = url;
        }
    }

    private class AdminContentAdapter extends RecyclerView.Adapter<AdminContentAdapter.ViewHolder> {

        private final List<AdminItemModel> list;

        AdminContentAdapter(List<AdminItemModel> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AdminItemModel item = list.get(position);
            holder.tvAdminItemTitle.setText(item.title);
            holder.tvAdminItemSubject.setText("Subject: " + item.subjectKey);
            holder.tvAdminItemUrl.setText("URL: " + item.url);

            holder.btnEditItem.setOnClickListener(v -> showAddEditDialog(item));
            holder.btnDeleteItem.setOnClickListener(v -> deleteItem(item));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvAdminItemTitle, tvAdminItemSubject, tvAdminItemUrl;
            MaterialButton btnEditItem, btnDeleteItem;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvAdminItemTitle = itemView.findViewById(R.id.tvAdminItemTitle);
                tvAdminItemSubject = itemView.findViewById(R.id.tvAdminItemSubject);
                tvAdminItemUrl = itemView.findViewById(R.id.tvAdminItemUrl);
                btnEditItem = itemView.findViewById(R.id.btnEditItem);
                btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);
            }
        }
    }
}
