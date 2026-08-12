package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login1);

        ImageButton backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity1.this, Dashboard2.class);
            startActivity(intent);
            finish();
        });

        Button loginButton = findViewById(R.id.button_login);
        Button signupButton = findViewById(R.id.button_signup);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity1.this, LoginActivity.class);
            startActivity(intent);
        });

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity1.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
