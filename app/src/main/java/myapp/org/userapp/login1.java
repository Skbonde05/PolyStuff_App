package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class login1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login1);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Dashboard2 activity
                Intent intent = new Intent(login1.this, Dashboard2.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        Button loginButton = findViewById(R.id.button_login);
        Button signupButton = findViewById(R.id.button_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the login activity
                Intent intent = new Intent(login1.this, login.class);
                startActivity(intent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the sign up activity
                Intent intent = new Intent(login1.this, sign_up.class);
                startActivity(intent);
            }
        });
    }
}
