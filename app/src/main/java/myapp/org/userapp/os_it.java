package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class os_it extends AppCompatActivity {

    CardView os_lab,os_prac,os_que,os_u1,os_u2,os_u3,os_u4,os_u5,os_u6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.os_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(os_it.this, levels3_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        os_que = findViewById(R.id.os_que);

        // Set OnClickListener for jp1_u1 CardView
        os_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(os_it.this, os_que_main.class);
                startActivity(intent);
            }
        });

        os_u1 = findViewById(R.id.os_u1);

        // Set OnClickListener for jp1_u1 CardView
        os_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(os_it.this, os_u1_main.class);
                startActivity(intent);
            }
        });

        os_u2 = findViewById(R.id.os_u2);

        // Set OnClickListener for jp1_u1 CardView
        os_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(os_it.this, os_u2_main.class);
                startActivity(intent);
            }
        });

        os_u3 = findViewById(R.id.os_u3);

        // Set OnClickListener for jp1_u1 CardView
        os_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(os_it.this, os_u3_main.class);
                startActivity(intent);
            }
        });

        os_u4 = findViewById(R.id.os_u4);

        // Set OnClickListener for jp1_u1 CardView
        os_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(os_it.this, os_u4_main.class);
                startActivity(intent);
            }
        });

        os_u5 = findViewById(R.id.os_u5);

        // Set OnClickListener for jp1_u1 CardView
        os_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(os_it.this, os_u5_main.class);
                startActivity(intent);
            }
        });
        os_u6 = findViewById(R.id.os_u6);

        // Set OnClickListener for jp1_u1 CardView
        os_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(os_it.this, os_u6_main.class);
                startActivity(intent);
            }
        });
    }
}
