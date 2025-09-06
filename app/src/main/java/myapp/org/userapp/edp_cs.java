package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class edp_cs extends AppCompatActivity {

    CardView edp_lab,edp_u1,edp_u2,edp_u3,edp_u4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edp_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(edp_cs.this, levels4.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });


        edp_u1 = findViewById(R.id.edp_u1);

        // Set OnClickListener for jp1_u1 CardView
        edp_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(edp_cs.this, edp_u1_main.class);
                startActivity(intent);
            }
        });

        edp_u2 = findViewById(R.id.edp_u2);

        // Set OnClickListener for jp1_u1 CardView
        edp_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(edp_cs.this, edp_u2_main.class);
                startActivity(intent);
            }
        });

        edp_u3 = findViewById(R.id.edp_u3);

        // Set OnClickListener for jp1_u1 CardView
        edp_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(edp_cs.this, edp_u3_main.class);
                startActivity(intent);
            }
        });

        edp_u4 = findViewById(R.id.edp_u4);

        // Set OnClickListener for jp1_u1 CardView
        edp_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(edp_cs.this, edp_u4_main.class);
                startActivity(intent);
            }
        });
    }
}
