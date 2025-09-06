package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class python_cs extends AppCompatActivity {

    CardView pwp_lab,pwp_prac,pwp_mcq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.python_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(python_cs.this, levels5.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        pwp_mcq= findViewById(R.id.pwp_mcq);

        // Set OnClickListener for jp1_u1 CardView
        pwp_mcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(python_cs.this, pwp_mcq_main.class);
                startActivity(intent);
            }
        });
    }
}
