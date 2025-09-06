package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class cs_cs extends AppCompatActivity {

    CardView cs_lab,cs_prac,cs_que,cs_u1,cs_u2,cs_u3,cs_u4,cs_u5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cs_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(cs_cs.this, levels4b.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        cs_que = findViewById(R.id.cs_que);

        // Set OnClickListener for jp1_u1 CardView
        cs_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cs_cs.this, cs_que_main.class);
                startActivity(intent);
            }
        });

        cs_u1 = findViewById(R.id.cs_u1);

        // Set OnClickListener for jp1_u1 CardView
        cs_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cs_cs.this, cs_u1_main.class);
                startActivity(intent);
            }
        });

        cs_u2 = findViewById(R.id.cs_u2);

        // Set OnClickListener for jp1_u1 CardView
        cs_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cs_cs.this, cs_u2_main.class);
                startActivity(intent);
            }
        });

        cs_u3 = findViewById(R.id.cs_u3);

        // Set OnClickListener for jp1_u1 CardView
        cs_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cs_cs.this, cs_u3_main.class);
                startActivity(intent);
            }
        });

        cs_u4 = findViewById(R.id.cs_u4);

        // Set OnClickListener for jp1_u1 CardView
        cs_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cs_cs.this, cs_u4_main.class);
                startActivity(intent);
            }
        });

        cs_u5= findViewById(R.id.cs_u5);

        // Set OnClickListener for jp1_u1 CardView
        cs_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cs_cs.this, cs_u5_main.class);
                startActivity(intent);
            }
        });

    }
}
