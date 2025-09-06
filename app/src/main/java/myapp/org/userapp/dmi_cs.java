package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class dmi_cs extends AppCompatActivity {

    CardView dmi_lab,dmi_prac,dmi_que,dmi_u1,dmi_u2,dmi_u3,dmi_u4,dmi_u5,dmi_u6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dmi_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(dmi_cs.this, levels5.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        dmi_que = findViewById(R.id.dmi_que);

        // Set OnClickListener for jp1_u1 CardView
        dmi_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dmi_cs.this, dmi_que_main.class);
                startActivity(intent);
            }
        });

        dmi_u1 = findViewById(R.id.dmi_u1);

        // Set OnClickListener for jp1_u1 CardView
        dmi_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dmi_cs.this, dmi_u1_main.class);
                startActivity(intent);
            }
        });

        dmi_u2 = findViewById(R.id.dmi_u2);

        // Set OnClickListener for jp1_u1 CardView
        dmi_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dmi_cs.this, dmi_u2_main.class);
                startActivity(intent);
            }
        });

        dmi_u3 = findViewById(R.id.dmi_u3);

        // Set OnClickListener for jp1_u1 CardView
        dmi_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dmi_cs.this, dmi_u3_main.class);
                startActivity(intent);
            }
        });

        dmi_u4 = findViewById(R.id.dmi_u4);

        // Set OnClickListener for jp1_u1 CardView
        dmi_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dmi_cs.this, dmi_u4_main.class);
                startActivity(intent);
            }
        });

        dmi_u5 = findViewById(R.id.dmi_u5);

        // Set OnClickListener for jp1_u1 CardView
        dmi_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dmi_cs.this, dmi_u5_main.class);
                startActivity(intent);
            }
        });

        dmi_u6 = findViewById(R.id.dmi_u6);

        // Set OnClickListener for jp1_u1 CardView
        dmi_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dmi_cs.this, dmi_u6_main.class);
                startActivity(intent);
            }
        });
    }
}
