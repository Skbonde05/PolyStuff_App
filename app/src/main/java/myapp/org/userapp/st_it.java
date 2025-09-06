package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class st_it extends AppCompatActivity {

    CardView set_u4,set_u5,set_u6,set_prac,st_lab,set_que;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(st_it.this, levels4b_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        set_que= findViewById(R.id.set_que);

        // Set OnClickListener for jp1_u1 CardView
        set_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(st_it.this, set_que_main.class);
                startActivity(intent);
            }
        });
        set_u4= findViewById(R.id.set_u4);

        // Set OnClickListener for jp1_u1 CardView
        set_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(st_it.this, set_u4_main.class);
                startActivity(intent);
            }
        });

        set_u5= findViewById(R.id.set_u5);

        // Set OnClickListener for jp1_u1 CardView
        set_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(st_it.this, set_u5_main.class);
                startActivity(intent);
            }
        });

        set_u6= findViewById(R.id.set_u6);

        // Set OnClickListener for jp1_u1 CardView
        set_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(st_it.this, set_u6_main.class);
                startActivity(intent);
            }
        });

    }
}
