package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class aap_cs extends AppCompatActivity {

    CardView aap_prac,aap_lab,aap_mcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aap_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(aap_cs.this, levels5.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        aap_mcq = findViewById(R.id.aap_mcq);

        // Set OnClickListener for jp1_u1 CardView
        aap_mcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(aap_cs.this, aap_mcq_main.class);
                startActivity(intent);
            }
        });
    }
}
