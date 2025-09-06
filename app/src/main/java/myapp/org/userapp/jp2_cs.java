package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class jp2_cs extends AppCompatActivity {

    CardView jp2_lab,jp2_prac,jp2_que,jp2_u1,jp2_u2,jp2_u3,jp2_u4,jp2_u5,jp2_u6;

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp2_cs);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(jp2_cs.this, levels4b.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        jp2_que = findViewById(R.id.jp2_que);

        // Set OnClickListener for jp1_u1 CardView
        jp2_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp2_cs.this, jp2_que_main.class);
                startActivity(intent);
            }
        });

        jp2_u1 = findViewById(R.id.jp2_u1);

        // Set OnClickListener for jp1_u1 CardView
        jp2_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp2_cs.this, jp2_u1_main.class);
                startActivity(intent);
            }
        });
        jp2_u2 = findViewById(R.id.jp2_u2);

        // Set OnClickListener for jp1_u1 CardView
        jp2_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp2_cs.this, jp2_u2_main.class);
                startActivity(intent);
            }
        });
        jp2_u3 = findViewById(R.id.jp2_u3);

        // Set OnClickListener for jp1_u1 CardView
        jp2_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp2_cs.this, jp2_u3_main.class);
                startActivity(intent);
            }
        });
        jp2_u4 = findViewById(R.id.jp2_u4);

        // Set OnClickListener for jp1_u1 CardView
        jp2_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp2_cs.this, jp2_u4_main.class);
                startActivity(intent);
            }
        });
        jp2_u5 = findViewById(R.id.jp2_u5);

        // Set OnClickListener for jp1_u1 CardView
        jp2_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp2_cs.this, jp2_u5_main.class);
                startActivity(intent);
            }
        });
        jp2_u6 = findViewById(R.id.jp2_u6);

        // Set OnClickListener for jp1_u1 CardView
        jp2_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(jp2_cs.this, jp2_u6_main.class);
                startActivity(intent);
            }
        });
    }
}
