package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class rateus extends AppCompatActivity {
    Button btn;
    RatingBar rb;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rateus);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(rateus.this, ProfileFragment.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        btn=findViewById(R.id.btn);
        rb=findViewById(R.id.rb);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ratings");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = rb.getRating();
                saveRatingToDatabase(rating);
            }
        });
    }

    private void saveRatingToDatabase(float rating) {
        String userId = databaseReference.push().getKey(); // Generate unique key
        databaseReference.child(userId).setValue(rating)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(rateus.this, "Rating saved successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(rateus.this, "Failed to save rating: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
