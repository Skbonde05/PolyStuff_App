package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ce_cs extends AppCompatActivity {

    CardView ce_u1,ce_u2,ce_u3,ce_u4,ce_u5,ce_u6,ce_ass,ce_lab;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computingessentials);

        // Find the back button by its ID
        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(ce_cs.this, Semesters.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        ce_u1 = findViewById(R.id.ce_u1);

        // Set OnClickListener for jp1_u1 CardView
        ce_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ce_cs.this, ce_u1_main.class);
                startActivity(intent);
            }
        });

        ce_u2 = findViewById(R.id.ce_u2);

        // Set OnClickListener for jp1_u1 CardView
        ce_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ce_cs.this, ce_u2_main.class);
                startActivity(intent);
            }
        });

        ce_u3 = findViewById(R.id.ce_u3);

        // Set OnClickListener for jp1_u1 CardView
        ce_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ce_cs.this, ce_u3_main.class);
                startActivity(intent);
            }
        });

        ce_u4 = findViewById(R.id.ce_u4);

        // Set OnClickListener for jp1_u1 CardView
        ce_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ce_cs.this, ce_u4_main.class);
                startActivity(intent);
            }
        });

        ce_u5 = findViewById(R.id.ce_u5);

        // Set OnClickListener for jp1_u1 CardView
        ce_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ce_cs.this, ce_u5_main.class);
                startActivity(intent);
            }
        });

        ce_u6 = findViewById(R.id.ce_u6);

        // Set OnClickListener for jp1_u1 CardView
        ce_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ce_cs.this, ce_u6_main.class);
                startActivity(intent);
            }
        });

    }
}
