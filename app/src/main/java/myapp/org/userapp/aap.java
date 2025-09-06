package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class aap extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aap); // Ensure this layout file exists

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity
                Intent intent = new Intent(aap.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new DataSetList("Introduction to Android Operating System", "https://youtu.be/YNJKj034TmY?si=5TL_oPZbdWW3r5rV"));
        videoList.add(new DataSetList("Installation and configuration of Android Environment", "https://youtu.be/InigFUSiPl8?si=tor2VBoUfU-ghOhA"));
        videoList.add(new DataSetList("Fundamental of UI Components and Layouts", "https://youtu.be/PeCOKgAua7A?si=5EqIiE83f8F7H45m"));
        videoList.add(new DataSetList("Designing User Interface", "https://youtu.be/0TtJwa5vL6Y?si=zPN8POjDSMQsLWbz"));
        videoList.add(new DataSetList("Activity, Multimedia and Databases", "https://youtu.be/kp-oGImpO_c?si=77oj2-uIdhiz2BxH"));
        videoList.add(new DataSetList("Security and Application Deployment", "https://youtu.be/IDDwmybY-Bg?si=CQbr2hSmc-9PhC51"));

        YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }
}
