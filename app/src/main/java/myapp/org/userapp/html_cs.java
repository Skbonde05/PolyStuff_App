package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class html_cs extends AppCompatActivity {

    CardView html_lab,html_prac,html_u1,html_u2,html_u3,html_u4,html_u5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.html_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(html_cs.this, levels2.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        html_u1 = findViewById(R.id.html_u1);

        // Set OnClickListener for jp1_u1 CardView
        html_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(html_cs.this, html_u1_main.class);
                startActivity(intent);
            }
        });

        html_u2 = findViewById(R.id.html_u2);

        // Set OnClickListener for jp1_u1 CardView
        html_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(html_cs.this, html_u2_main.class);
                startActivity(intent);
            }
        });

        html_u3 = findViewById(R.id.html_u3);

        // Set OnClickListener for jp1_u1 CardView
        html_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(html_cs.this, html_u3_main.class);
                startActivity(intent);
            }
        });

        html_u4 = findViewById(R.id.html_u4);

        // Set OnClickListener for jp1_u1 CardView
        html_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(html_cs.this, html_u4_main.class);
                startActivity(intent);
            }
        });

        html_u5 = findViewById(R.id.html_u5);

        // Set OnClickListener for jp1_u1 CardView
        html_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(html_cs.this, html_u5_main.class);
                startActivity(intent);
            }
        });

    }
}
