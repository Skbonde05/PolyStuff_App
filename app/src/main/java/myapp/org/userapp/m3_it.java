package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class m3_it extends AppCompatActivity {

    CardView m3_tut,m3_lab,m3_que,m3_u1,m3_u2,m3_u3,m3_u4,m3_u5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m3_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(m3_it.this, levels2_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        m3_tut = findViewById(R.id.m3_tut);

        // Set OnClickListener for jp1_u1 CardView
        m3_tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m3_it.this, m3_tut_main.class);
                startActivity(intent);
            }
        });

        m3_que = findViewById(R.id.m3_que);

        // Set OnClickListener for jp1_u1 CardView
        m3_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m3_it.this, m3_que_main.class);
                startActivity(intent);
            }
        });
        m3_u1 = findViewById(R.id.m3_u1);

        // Set OnClickListener for jp1_u1 CardView
        m3_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m3_it.this, m3_u1_main.class);
                startActivity(intent);
            }
        });
        m3_u2 = findViewById(R.id.m3_u2);

        // Set OnClickListener for jp1_u1 CardView
        m3_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m3_it.this, m3_u2_main.class);
                startActivity(intent);
            }
        });
        m3_u3 = findViewById(R.id.m3_u3);

        // Set OnClickListener for jp1_u1 CardView
        m3_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m3_it.this, m3_u3_main.class);
                startActivity(intent);
            }
        });
        m3_u4 = findViewById(R.id.m3_u4);

        // Set OnClickListener for jp1_u1 CardView
        m3_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m3_it.this, m3_u4_main.class);
                startActivity(intent);
            }
        });
        m3_u5 = findViewById(R.id.m3_u5);

        // Set OnClickListener for jp1_u1 CardView
        m3_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m3_it.this, m3_u5_main.class);
                startActivity(intent);
            }
        });
    }
}
