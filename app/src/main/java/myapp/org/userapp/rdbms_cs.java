package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class rdbms_cs extends AppCompatActivity {

    CardView rdbms_lab,rdbms_prac,rdbms_que,rdbms_u1,rdbms_u2,rdbms_u3,rdbms_u4,rdbms_u5,rdbms_u6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdbms_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(rdbms_cs.this, levels4b.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        rdbms_que= findViewById(R.id.rdbms_que);

        // Set OnClickListener for jp1_u1 CardView
        rdbms_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(rdbms_cs.this, rdbms_que_main.class);
                startActivity(intent);
            }
        });

        rdbms_u1= findViewById(R.id.rdbms_u1);

        // Set OnClickListener for jp1_u1 CardView
        rdbms_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(rdbms_cs.this, rdbms_u1_main.class);
                startActivity(intent);
            }
        });

        rdbms_u2= findViewById(R.id.rdbms_u2);

        // Set OnClickListener for jp1_u1 CardView
        rdbms_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(rdbms_cs.this, rdbms_u2_main.class);
                startActivity(intent);
            }
        });

        rdbms_u3= findViewById(R.id.rdbms_u3);

        // Set OnClickListener for jp1_u1 CardView
        rdbms_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(rdbms_cs.this, rdbms_u3_main.class);
                startActivity(intent);
            }
        });

        rdbms_u4= findViewById(R.id.rdbms_u4);

        // Set OnClickListener for jp1_u1 CardView
        rdbms_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(rdbms_cs.this, rdbms_u4_main.class);
                startActivity(intent);
            }
        });

        rdbms_u5= findViewById(R.id.rdbms_u5);

        // Set OnClickListener for jp1_u1 CardView
        rdbms_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(rdbms_cs.this, rdbms_u5_main.class);
                startActivity(intent);
            }
        });

        rdbms_u6= findViewById(R.id.rdbms_u6);

        // Set OnClickListener for jp1_u1 CardView
        rdbms_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(rdbms_cs.this, rdbms_u6_main.class);
                startActivity(intent);
            }
        });


    }
}
