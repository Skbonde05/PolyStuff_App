package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class im_it extends AppCompatActivity {

    CardView im_lab,im_u1,im_u2,im_u3,im_u4,im_u5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(im_it.this, levels4_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        im_u1 = findViewById(R.id.im_u1);

        // Set OnClickListener for jp1_u1 CardView
        im_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(im_it.this, im_u1_main.class);
                startActivity(intent);
            }
        });

        im_u2 = findViewById(R.id.im_u2);

        // Set OnClickListener for jp1_u1 CardView
        im_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(im_it.this, im_u2_main.class);
                startActivity(intent);
            }
        });

        im_u3 = findViewById(R.id.im_u3);

        // Set OnClickListener for jp1_u1 CardView
        im_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(im_it.this, im_u3_main.class);
                startActivity(intent);
            }
        });

        im_u4 = findViewById(R.id.im_u4);

        // Set OnClickListener for jp1_u1 CardView
        im_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(im_it.this, im_u4_main.class);
                startActivity(intent);
            }
        });

        im_u5 = findViewById(R.id.im_u5);

        // Set OnClickListener for jp1_u1 CardView
        im_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(im_it.this, im_u5_main.class);
                startActivity(intent);
            }
        });

    }
}
