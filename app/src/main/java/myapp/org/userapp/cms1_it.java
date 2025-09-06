package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class cms1_it extends AppCompatActivity {

    CardView cms1_ass,cms1_lab,cms1_u1,cms1_u2,cms1_u3,cms1_u4,cms1_u5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cms1_it);

        // Find the back button by its ID
        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(cms1_it.this, levels1_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        cms1_u1 = findViewById(R.id.cms1_u1);

        // Set OnClickListener for jp1_u1 CardView
        cms1_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cms1_it.this, cms1_u1_main.class);
                startActivity(intent);
            }
        });

        cms1_u2 = findViewById(R.id.cms1_u2);

        // Set OnClickListener for jp1_u1 CardView
        cms1_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cms1_it.this, cms1_u2_main.class);
                startActivity(intent);
            }
        });

        cms1_u3 = findViewById(R.id.cms1_u3);

        // Set OnClickListener for jp1_u1 CardView
        cms1_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cms1_it.this, cms1_u3_main.class);
                startActivity(intent);
            }
        });

        cms1_u4 = findViewById(R.id.cms1_u4);

        // Set OnClickListener for jp1_u1 CardView
        cms1_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cms1_it.this, cms1_u4_main.class);
                startActivity(intent);
            }
        });

        cms1_u5 = findViewById(R.id.cms1_u5);

        // Set OnClickListener for jp1_u1 CardView
        cms1_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cms1_it.this, cms1_u5_main.class);
                startActivity(intent);
            }
        });

    }
}
