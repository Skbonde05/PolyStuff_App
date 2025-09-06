package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class jp1_cs extends AppCompatActivity {

    CardView jp1_u1, jp1_u2,jp1_u3,jp1_u4,jp1_u5,jp1_u6,jp1_prac,jp1_lab,jp1_que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp1_cs);

        // Initialize back button
        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(jp1_cs.this, levels3.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        jp1_que = findViewById(R.id.jp1_que);

        // Set OnClickListener for jp1_u1 CardView
        jp1_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp1_cs.this, jp1_que_main.class);
                startActivity(intent);
            }
        });

        // Initialize CardView for jp1_u1
        jp1_u1 = findViewById(R.id.jp1_u1);

        // Set OnClickListener for jp1_u1 CardView
        jp1_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp1_cs.this, jp1_u1_main.class);
                startActivity(intent);
            }
        });

        jp1_u2 = findViewById(R.id.jp1_u2);

        // Set OnClickListener for jp1_u1 CardView
        jp1_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp1_cs.this, jp1_u2_main.class);
                startActivity(intent);
            }
        });

        jp1_u3 = findViewById(R.id.jp1_u3);

        // Set OnClickListener for jp1_u1 CardView
        jp1_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp1_cs.this, jp1_u3_main.class);
                startActivity(intent);
            }
        });

        jp1_u4 = findViewById(R.id.jp1_u4);

        // Set OnClickListener for jp1_u1 CardView
        jp1_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp1_cs.this, jp1_u4_main.class);
                startActivity(intent);
            }
        });

        jp1_u5 = findViewById(R.id.jp1_u5);

        // Set OnClickListener for jp1_u1 CardView
        jp1_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp1_cs.this, jp1_u5_main.class);
                startActivity(intent);
            }
        });

        jp1_u6 = findViewById(R.id.jp1_u6);

        // Set OnClickListener for jp1_u1 CardView
        jp1_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp1_cs.this, jp1_u6_main.class);
                startActivity(intent);
            }
        });

    }
}
