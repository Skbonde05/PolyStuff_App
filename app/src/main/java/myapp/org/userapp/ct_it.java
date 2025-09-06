package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ct_it extends AppCompatActivity {

    CardView cc_u1,cc_u2,cc_u3,cc_u4,cc_u5,cc_u6,cc_prac,cc_lab,cc_que;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(ct_it.this, levels5_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        cc_que = findViewById(R.id.cc_que);

        // Set OnClickListener for jp1_u1 CardView
        cc_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ct_it.this, cc_que_main.class);
                startActivity(intent);
            }
        });


        cc_u1 = findViewById(R.id.cc_u1);

        // Set OnClickListener for jp1_u1 CardView
        cc_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ct_it.this, cc_u1_main.class);
                startActivity(intent);
            }
        });

        cc_u2 = findViewById(R.id.cc_u2);

        cc_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct_it.this, cc_u2_main.class);
                startActivity(intent);
            }
        });

        cc_u3 = findViewById(R.id.cc_u3);

        cc_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct_it.this, cc_u3_main.class);
                startActivity(intent);
            }
        });

        cc_u4 = findViewById(R.id.cc_u4);

        cc_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct_it.this, cc_u4_main.class);
                startActivity(intent);
            }
        });

        cc_u5 = findViewById(R.id.cc_u5);

        cc_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct_it.this, cc_u5_main.class);
                startActivity(intent);
            }
        });

        cc_u6 = findViewById(R.id.cc_u6);

        cc_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct_it.this, cc_u6_main.class);
                startActivity(intent);
            }
        });

    }
}
