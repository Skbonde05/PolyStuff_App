package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class physics_cs extends AppCompatActivity {

    CardView phy_prac,phy_u1,phy_u2,phy_u3,phy_u4,phy_u5,phy_u6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.physics_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(physics_cs.this, Semesters.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        phy_u1= findViewById(R.id.phy_u1);

        // Set OnClickListener for jp1_u1 CardView
        phy_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(physics_cs.this, phy_u1_main.class);
                startActivity(intent);
            }
        });

        phy_u2= findViewById(R.id.phy_u2);

        // Set OnClickListener for jp1_u1 CardView
        phy_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(physics_cs.this, phy_u2_main.class);
                startActivity(intent);
            }
        });

        phy_u3= findViewById(R.id.phy_u3);

        // Set OnClickListener for jp1_u1 CardView
        phy_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(physics_cs.this, phy_u3_main.class);
                startActivity(intent);
            }
        });

        phy_u4= findViewById(R.id.phy_u4);

        // Set OnClickListener for jp1_u1 CardView
        phy_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(physics_cs.this, phy_u4_main.class);
                startActivity(intent);
            }
        });

        phy_u5= findViewById(R.id.phy_u5);

        // Set OnClickListener for jp1_u1 CardView
        phy_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(physics_cs.this, phy_u5_main.class);
                startActivity(intent);
            }
        });

        phy_u6= findViewById(R.id.phy_u6);

        // Set OnClickListener for jp1_u1 CardView
        phy_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(physics_cs.this, phy_u6_main.class);
                startActivity(intent);
            }
        });



    }
}
