package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class iot_it extends AppCompatActivity {

    CardView iot_lab,iot_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iot_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(iot_it.this, levels4b_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });


        iot_ref = findViewById(R.id.iot_ref);

        // Set OnClickListener for jp1_u1 CardView
        iot_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(iot_it.this, iot_ref_main.class);
                startActivity(intent);
            }
        });
    }
}
