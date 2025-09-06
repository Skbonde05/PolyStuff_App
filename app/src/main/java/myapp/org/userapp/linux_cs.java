package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class linux_cs extends AppCompatActivity {

    CardView linux_lab,linux_prac,linux_cmd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linux_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(linux_cs.this, levels2.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        linux_cmd = findViewById(R.id.linux_cmd);

        // Set OnClickListener for jp1_u1 CardView
        linux_cmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(linux_cs.this, linux_cmd_main.class);
                startActivity(intent);
            }
        });

    }
}
