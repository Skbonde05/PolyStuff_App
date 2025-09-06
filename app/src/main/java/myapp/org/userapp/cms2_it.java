package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class cms2_it extends AppCompatActivity {

    CardView cms2_ass,cms2_lab,cms2_que,cms2_u1,cms2_u2,cms2_u3,cms2_u4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cms2_it);

        // Find the back button by its ID
        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(cms2_it.this, levels1_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });


        cms2_que = findViewById(R.id.cms2_que);

        // Set OnClickListener for jp1_u1 CardView
        cms2_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cms2_it.this, cms2_que_main.class);
                startActivity(intent);
            }
        });

        cms2_u4 = findViewById(R.id.cms2_u4);

        // Set OnClickListener for jp1_u1 CardView
        cms2_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(cms2_it.this, cms2_u4_main.class);
                startActivity(intent);
            }
        });

    }
}
