package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class m2_it extends AppCompatActivity {

    CardView m2_tut,m2_que,m2_lab,m2_u1,m2_u2,m2_u3,m2_u4,m2_u5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m2_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(m2_it.this, levels1_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        m2_tut = findViewById(R.id.m2_tut);

        // Set OnClickListener for jp1_u1 CardView
        m2_tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m2_it.this, m2_tut_main.class);
                startActivity(intent);
            }
        });
        m2_que = findViewById(R.id.m2_que);

        // Set OnClickListener for jp1_u1 CardView
        m2_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m2_it.this, m2_que_main.class);
                startActivity(intent);
            }
        });

        m2_u1 = findViewById(R.id.m2_u1);

        // Set OnClickListener for jp1_u1 CardView
        m2_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m2_it.this, m2_u1_main.class);
                startActivity(intent);
            }
        });

        m2_u2 = findViewById(R.id.m2_u2);

        // Set OnClickListener for jp1_u1 CardView
        m2_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m2_it.this, m2_u2_main.class);
                startActivity(intent);
            }
        });

        m2_u3 = findViewById(R.id.m2_u3);

        // Set OnClickListener for jp1_u1 CardView
        m2_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m2_it.this, m2_u3_main.class);
                startActivity(intent);
            }
        });

        m2_u4 = findViewById(R.id.m2_u4);

        // Set OnClickListener for jp1_u1 CardView
        m2_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m2_it.this, m2_u4_main.class);
                startActivity(intent);
            }
        });

        m2_u5 = findViewById(R.id.m2_u5);

        // Set OnClickListener for jp1_u1 CardView
        m2_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m2_it.this, m2_u5_main.class);
                startActivity(intent);
            }
        });

    }
}
