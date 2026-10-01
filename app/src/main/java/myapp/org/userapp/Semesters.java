package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class Semesters extends AppCompatActivity {

    CardView ce_cs;
    CardView cms1_cs;
    CardView cms2_cs;
    CardView m1_cs;
    CardView m2__cs;
    CardView physics_cs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level1);

        // -------- Back button handling (safe for both ImageButton and Toolbar) --------

        // Try the ImageButton first (the one your code currently expects)
        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                startActivity(new Intent(Semesters.this, ComputerLevels.class));
                finish();
            });
        } else {
            // Fall back to a MaterialToolbar with ID @+id/toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(v -> {
                    startActivity(new Intent(Semesters.this, ComputerLevels.class));
                    finish();
                });
            }
        }

        // -------- Card views — all null-checked so they can't crash --------

        ce_cs = findViewById(R.id.ce_cs);
        if (ce_cs != null) {
            ce_cs.setOnClickListener(v ->
                    SubjectListActivity.launch(Semesters.this, "ce_cs"));
        }

        cms1_cs = findViewById(R.id.cms1_cs);
        if (cms1_cs != null) {
            cms1_cs.setOnClickListener(v ->
                    SubjectListActivity.launch(Semesters.this, "cms1_cs"));
        }

        cms2_cs = findViewById(R.id.cms2_cs);
        if (cms2_cs != null) {
            cms2_cs.setOnClickListener(v ->
                    SubjectListActivity.launch(Semesters.this, "cms2_cs"));
        }

        m1_cs = findViewById(R.id.m1_cs);
        if (m1_cs != null) {
            m1_cs.setOnClickListener(v ->
                    SubjectListActivity.launch(Semesters.this, "m1_cs"));
        }

        m2__cs = findViewById(R.id.m2_cs);
        if (m2__cs != null) {
            m2__cs.setOnClickListener(v ->
                    SubjectListActivity.launch(Semesters.this, "m2_cs"));
        }

        physics_cs = findViewById(R.id.physics_cs);
        if (physics_cs != null) {
            physics_cs.setOnClickListener(v ->
                    SubjectListActivity.launch(Semesters.this, "physics_cs"));
        }
    }
}