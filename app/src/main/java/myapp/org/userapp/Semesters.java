package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
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

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(Semesters.this, ComputerLevels.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        ce_cs = findViewById(R.id.ce_cs);
        ce_cs.setOnClickListener(v -> {
            Intent intent = new Intent(Semesters.this, ce_cs.class);
            startActivity(intent);
        });

        cms1_cs = findViewById(R.id.cms1_cs);
        cms1_cs.setOnClickListener(v -> {
            Intent intent = new Intent(Semesters.this, cms1_cs.class);
            startActivity(intent);
        });
        cms2_cs = findViewById(R.id.cms2_cs);
        cms2_cs.setOnClickListener(v -> {
            Intent intent = new Intent(Semesters.this, cms2_cs.class);
            startActivity(intent);
        });

        m1_cs = findViewById(R.id.m1_cs);
        m1_cs.setOnClickListener(v -> {
            Intent intent = new Intent(Semesters.this, m1_cs.class);
            startActivity(intent);
        });

        m2__cs = findViewById(R.id.m2_cs);
        m2__cs.setOnClickListener(v -> {
            Intent intent = new Intent(Semesters.this, m2_cs.class);
            startActivity(intent);
        });

        physics_cs = findViewById(R.id.physics_cs);
        physics_cs.setOnClickListener(v -> {
            Intent intent = new Intent(Semesters.this, physics_cs.class);
            startActivity(intent);
        });
    }
}