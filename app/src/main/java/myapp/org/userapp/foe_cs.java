package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class foe_cs extends AppCompatActivity {

    CardView foe_lab,foe_prac,foe_que,foe_u1,foe_u2,foe_u3,foe_u4,foe_u5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foe_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(foe_cs.this, levels2.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        foe_que = findViewById(R.id.foe_que);

        // Set OnClickListener for jp1_u1 CardView
        foe_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(foe_cs.this, foe_que_main.class);
                startActivity(intent);
            }
        });

        foe_u1 = findViewById(R.id.foe_u1);

        // Set OnClickListener for jp1_u1 CardView
        foe_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(foe_cs.this, foe_u1_main.class);
                startActivity(intent);
            }
        });

        foe_u2 = findViewById(R.id.foe_u2);

        // Set OnClickListener for jp1_u1 CardView
        foe_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(foe_cs.this, foe_u2_main.class);
                startActivity(intent);
            }
        });

        foe_u3 = findViewById(R.id.foe_u3);

        // Set OnClickListener for jp1_u1 CardView
        foe_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(foe_cs.this, foe_u3_main.class);
                startActivity(intent);
            }
        });

        foe_u4 = findViewById(R.id.foe_u4);

        // Set OnClickListener for jp1_u1 CardView
        foe_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(foe_cs.this, foe_u4_main.class);
                startActivity(intent);
            }
        });

        foe_u5 = findViewById(R.id.foe_u5);

        // Set OnClickListener for jp1_u1 CardView
        foe_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(foe_cs.this, foe_u5_main.class);
                startActivity(intent);
            }
        });
    }
}
