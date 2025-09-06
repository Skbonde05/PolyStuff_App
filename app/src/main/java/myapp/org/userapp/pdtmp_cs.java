package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class pdtmp_cs extends AppCompatActivity {

    CardView dtmp_lab,dtmp_prac,dtmp_que,dtmp_u1,dtmp_u2,dtmp_u3,dtmp_u4,dtmp_u5,dtmp_u6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdtmp_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(pdtmp_cs.this, levels3.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        dtmp_que = findViewById(R.id.dtmp_que);

        // Set OnClickListener for jp1_u1 CardView
        dtmp_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(pdtmp_cs.this, dtmp_que_main.class);
                startActivity(intent);
            }
        });

        dtmp_u1 = findViewById(R.id.dtmp_u1);

        // Set OnClickListener for jp1_u1 CardView
        dtmp_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(pdtmp_cs.this, dtmp_u1_main.class);
                startActivity(intent);
            }
        });

        dtmp_u2 = findViewById(R.id.dtmp_u2);

        // Set OnClickListener for jp1_u1 CardView
        dtmp_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(pdtmp_cs.this, dtmp_u2_main.class);
                startActivity(intent);
            }
        });
        dtmp_u3 = findViewById(R.id.dtmp_u3);

        // Set OnClickListener for jp1_u1 CardView
        dtmp_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(pdtmp_cs.this, dtmp_u3_main.class);
                startActivity(intent);
            }
        });
        dtmp_u4 = findViewById(R.id.dtmp_u4);

        // Set OnClickListener for jp1_u1 CardView
        dtmp_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(pdtmp_cs.this, dtmp_u4_main.class);
                startActivity(intent);
            }
        });
        dtmp_u5 = findViewById(R.id.dtmp_u5);

        // Set OnClickListener for jp1_u1 CardView
        dtmp_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(pdtmp_cs.this, dtmp_u5_main.class);
                startActivity(intent);
            }
        });
        dtmp_u6 = findViewById(R.id.dtmp_u6);

        // Set OnClickListener for jp1_u1 CardView
        dtmp_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(pdtmp_cs.this, dtmp_u6_main.class);
                startActivity(intent);
            }
        });

    }
}
