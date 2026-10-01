package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        // -------- Back button (null-safe, ImageButton OR Toolbar fallback) --------
        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(v -> finish());
            }
        }

        btn = findViewById(R.id.btn);
        rb = findViewById(R.id.rb);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ratings");

        if (btn != null && rb != null) {
            btn.setOnClickListener(v -> {
                float rating = rb.getRating();
                saveRatingToDatabase(rating);
            });
        }
    }

    private void saveRatingToDatabase(float rating) {
        String userId = databaseReference.push().getKey(); // Generate unique key
        if (userId == null) {
            Toast.makeText(rateus.this, "Failed to save rating", Toast.LENGTH_SHORT).show();
            return;
        }
        databaseReference.child(userId).setValue(rating)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(rateus.this, "Rating saved successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(rateus.this, "Failed to save rating: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}