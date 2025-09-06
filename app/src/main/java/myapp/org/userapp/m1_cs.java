package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class m1_cs extends AppCompatActivity {

    CardView m1_tut,m1_lab,m1_u1,m1_u2,m1_u3,m1_u4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(m1_cs.this, Semesters.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        m1_tut = findViewById(R.id.m1_tut);

        // Set OnClickListener for jp1_u1 CardView
        m1_tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(m1_cs.this, m1_tut_main.class);
                startActivity(intent);
            }
        });

    }
}
