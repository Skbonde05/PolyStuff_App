package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ict_cs extends AppCompatActivity {

    CardView fict_prac,fict_lab,fict_cmd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ict_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(ict_cs.this, levels2.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        fict_cmd = findViewById(R.id.fict_cmd);

        // Set OnClickListener for jp1_u1 CardView
        fict_cmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ict_cs.this, fict_cmd_main.class);
                startActivity(intent);
            }
        });
    }
}
