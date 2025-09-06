package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class nma_it extends AppCompatActivity {

    CardView nma_lab,nma_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nma_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(nma_it.this, levels4b_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });


        nma_ref = findViewById(R.id.nma_ref);

        // Set OnClickListener for jp1_u1 CardView
        nma_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(nma_it.this, nma_ref_main.class);
                startActivity(intent);
            }
        });
    }
}
