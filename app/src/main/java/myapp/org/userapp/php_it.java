package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class php_it extends AppCompatActivity {

    CardView php_lab,php_ref;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.php_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(php_it.this, levels5.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        php_ref = findViewById(R.id.php_ref);

        // Set OnClickListener for jp1_u1 CardView
        php_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(php_it.this, php_ref_main.class);
                startActivity(intent);
            }
        });
    }
}
