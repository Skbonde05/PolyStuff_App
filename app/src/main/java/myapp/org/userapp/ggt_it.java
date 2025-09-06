package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ggt_it extends AppCompatActivity {

    CardView cg_prac,cg_lab,cg_que,cg_u1,cg_u2,cg_u3,cg_u4,cg_u5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ggt_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(ggt_it.this, levels5_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        cg_que = findViewById(R.id.cg_que);

        // Set OnClickListener for jp1_u1 CardView
        cg_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ggt_it.this, cg_que_main.class);
                startActivity(intent);
            }
        });

        cg_u1 = findViewById(R.id.cg_u1);

        // Set OnClickListener for jp1_u1 CardView
        cg_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ggt_it.this, cg_u1_main.class);
                startActivity(intent);
            }
        });

        cg_u2 = findViewById(R.id.cg_u2);

        // Set OnClickListener for jp1_u1 CardView
        cg_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ggt_it.this, cg_u2_main.class);
                startActivity(intent);
            }
        });

        cg_u3 = findViewById(R.id.cg_u3);

        // Set OnClickListener for jp1_u1 CardView
        cg_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ggt_it.this, cg_u3_main.class);
                startActivity(intent);
            }
        });

        cg_u4 = findViewById(R.id.cg_u4);

        // Set OnClickListener for jp1_u1 CardView
        cg_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ggt_it.this, cg_u4_main.class);
                startActivity(intent);
            }
        });

        cg_u5 = findViewById(R.id.cg_u5);

        // Set OnClickListener for jp1_u1 CardView
        cg_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ggt_it.this, cg_u5_main.class);
                startActivity(intent);
            }
        });


    }
}
