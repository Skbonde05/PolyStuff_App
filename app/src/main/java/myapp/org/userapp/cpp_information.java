package myapp.org.userapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.card.MaterialCardView;

public class cpp_information extends AppCompatActivity {

    // Change this to the actual C++ course URL on NPTEL
    private static final String COURSE_URL = "https://onlinecourses.nptel.ac.in/noc_cs_course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cpp_information); // verify this matches your layout filename

        // -------- Toolbar back button (null-safe) --------
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        } else {
            android.widget.ImageButton backButton = findViewById(R.id.backButton);
            if (backButton != null) {
                backButton.setOnClickListener(v -> finish());
            }
        }

        // -------- Course Link card — open the URL in a browser --------
        MaterialCardView courseLinkCard = findViewById(R.id.course_link_cardview);
        if (courseLinkCard != null) {
            courseLinkCard.setOnClickListener(v -> openUrl(COURSE_URL));
        }
    }

    private void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No app available to open the link", Toast.LENGTH_SHORT).show();
        }
    }
}