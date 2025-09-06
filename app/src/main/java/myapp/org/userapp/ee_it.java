package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ee_it extends AppCompatActivity {

    CardView ee_lab,ee_prac,ee_que,ee_u1,ee_u2,ee_u3,ee_u4,ee_u5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ee_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(ee_it.this,levels2_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current aclevels3tivity
            }
        });

        ee_que = findViewById(R.id.ee_que);

        // Set OnClickListener for jp1_u1 CardView
        ee_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ee_it.this, ee_que_main.class);
                startActivity(intent);
            }
        });

        ee_u1 = findViewById(R.id.ee_u1);

        // Set OnClickListener for jp1_u1 CardView
        ee_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ee_it.this, ee_u1_main.class);
                startActivity(intent);
            }
        });

        ee_u2 = findViewById(R.id.ee_u2);

        // Set OnClickListener for jp1_u1 CardView
        ee_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ee_it.this, ee_u2_main.class);
                startActivity(intent);
            }
        });

        ee_u3 = findViewById(R.id.ee_u3);

        // Set OnClickListener for jp1_u1 CardView
        ee_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ee_it.this, ee_u3_main.class);
                startActivity(intent);
            }
        });

        ee_u4 = findViewById(R.id.ee_u4);

        // Set OnClickListener for jp1_u1 CardView
        ee_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ee_it.this, ee_u4_main.class);
                startActivity(intent);
            }
        });

        ee_u5 = findViewById(R.id.ee_u5);

        // Set OnClickListener for jp1_u1 CardView
        ee_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ee_it.this, ee_u5_main.class);
                startActivity(intent);
            }
        });

    }
}
