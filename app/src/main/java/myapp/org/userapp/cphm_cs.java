package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class cphm_cs extends AppCompatActivity {

    CardView cphm_lab,cphm_prac,cphm_que,cphm_u1,cphm_u2,cphm_u3,cphm_u4,cphm_u5,cphm_u6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cphm_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(cphm_cs.this, levels3.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        cphm_que = findViewById(R.id.cphm_que);

        // Set OnClickListener for jp1_u1 CardView
        cphm_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cphm_cs.this, cphm_que_main.class);
                startActivity(intent);
            }
        });

        cphm_u1 = findViewById(R.id.cphm_u1);

        // Set OnClickListener for jp1_u1 CardView
        cphm_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cphm_cs.this, cphm_u1_main.class);
                startActivity(intent);
            }
        });

        cphm_u2 = findViewById(R.id.cphm_u2);

        // Set OnClickListener for jp1_u1 CardView
        cphm_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cphm_cs.this, cphm_u2_main.class);
                startActivity(intent);
            }
        });

        cphm_u3 = findViewById(R.id.cphm_u3);

        // Set OnClickListener for jp1_u1 CardView
        cphm_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cphm_cs.this, cphm_u3_main.class);
                startActivity(intent);
            }
        });

        cphm_u4 = findViewById(R.id.cphm_u4);

        // Set OnClickListener for jp1_u1 CardView
        cphm_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cphm_cs.this, cphm_u4_main.class);
                startActivity(intent);
            }
        });

        cphm_u5 = findViewById(R.id.cphm_u5);

        // Set OnClickListener for jp1_u1 CardView
        cphm_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cphm_cs.this, cphm_u5_main.class);
                startActivity(intent);
            }
        });

        cphm_u6 = findViewById(R.id.cphm_u6);

        // Set OnClickListener for jp1_u1 CardView
        cphm_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cphm_cs.this, cphm_u6_main.class);
                startActivity(intent);
            }
        });


    }
}
