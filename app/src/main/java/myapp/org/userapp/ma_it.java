package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ma_it extends AppCompatActivity {

    CardView ma_lab,ma_ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ma_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(ma_it.this, levels3_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        ma_ref= findViewById(R.id.ma_ref);

        // Set OnClickListener for jp1_u1 CardView
        ma_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(ma_it.this, ma_ref_main.class);
                startActivity(intent);
            }
        });

    }
}
