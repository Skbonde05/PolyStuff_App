package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ds_cs extends AppCompatActivity {

    CardView ds_prac,ds_lab,ds_que,ds_u1,ds_u2,ds_u3,ds_u4,ds_u5,ds_u6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(ds_cs.this, levels3.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        ds_que = findViewById(R.id.ds_que);

        // Set OnClickListener for jp1_u1 CardView
        ds_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ds_cs.this, ds_que_main.class);
                startActivity(intent);
            }
        });

        ds_u1 = findViewById(R.id.ds_u1);

        // Set OnClickListener for jp1_u1 CardView
        ds_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ds_cs.this, ds_u1_main.class);
                startActivity(intent);
            }
        });

        ds_u2 = findViewById(R.id.ds_u2);

        // Set OnClickListener for jp1_u1 CardView
        ds_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ds_cs.this, ds_u2_main.class);
                startActivity(intent);
            }
        });

        ds_u3 = findViewById(R.id.ds_u3);

        // Set OnClickListener for jp1_u1 CardView
        ds_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ds_cs.this, ds_u3_main.class);
                startActivity(intent);
            }
        });

        ds_u4 = findViewById(R.id.ds_u4);

        // Set OnClickListener for jp1_u1 CardView
        ds_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ds_cs.this, ds_u4_main.class);
                startActivity(intent);
            }
        });

        ds_u5 = findViewById(R.id.ds_u5);

        // Set OnClickListener for jp1_u1 CardView
        ds_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ds_cs.this, ds_u5_main.class);
                startActivity(intent);
            }
        });

        ds_u6 = findViewById(R.id.ds_u6);

        // Set OnClickListener for jp1_u1 CardView
        ds_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ds_cs.this, ds_u6_main.class);
                startActivity(intent);
            }
        });

    }
}
