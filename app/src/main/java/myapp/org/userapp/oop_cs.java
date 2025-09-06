package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class oop_cs extends AppCompatActivity {

    CardView oop_u1,oop_u2,oop_u3,oop_u4,oop_u5,oop_u6,oop_prac,oop_lab,oop_que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oop_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(oop_cs.this, levels3.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        oop_que = findViewById(R.id.oop_que);
        // Set OnClickListener for jp1_u1 CardView
        oop_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(oop_cs.this, oop_que_main.class);
                startActivity(intent);
            }
        });

        oop_u1 = findViewById(R.id.oop_u1);

        // Set OnClickListener for jp1_u1 CardView
        oop_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(oop_cs.this, oop_u1_main.class);
                startActivity(intent);
            }
        });

        oop_u2 = findViewById(R.id.oop_u2);

        // Set OnClickListener for jp1_u1 CardView
        oop_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(oop_cs.this, oop_u2_main.class);
                startActivity(intent);
            }
        });

        oop_u3 = findViewById(R.id.oop_u3);

        // Set OnClickListener for jp1_u1 CardView
        oop_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(oop_cs.this, oop_u3_main.class);
                startActivity(intent);
            }
        });

        oop_u4 = findViewById(R.id.oop_u4);

        // Set OnClickListener for jp1_u1 CardView
        oop_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(oop_cs.this, oop_u4_main.class);
                startActivity(intent);
            }
        });

        oop_u5 = findViewById(R.id.oop_u5);

        // Set OnClickListener for jp1_u1 CardView
        oop_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(oop_cs.this, oop_u5_main.class);
                startActivity(intent);
            }
        });

        oop_u6 = findViewById(R.id.oop_u6);

        // Set OnClickListener for jp1_u1 CardView
        oop_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(oop_cs.this, oop_u6_main.class);
                startActivity(intent);
            }
        });

    }
}
