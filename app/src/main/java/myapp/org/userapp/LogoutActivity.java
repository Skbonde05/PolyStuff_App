package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        TextView logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete user credentials from Firebase Authentication
                firebaseAuth.signOut();

                // Redirect to sign-up activity
                startActivity(new Intent(LogoutActivity.this, sign_up.class));
                finish(); // Finish the current activity
            }
        });
    }
}
