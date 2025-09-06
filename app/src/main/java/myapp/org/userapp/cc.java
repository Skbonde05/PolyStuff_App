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

public class cc extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc); // Ensure this layout file exists

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity
                Intent intent = new Intent(cc.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new DataSetList("Cloud Computing Fundamentals", "https://youtube.com/playlist?list=PL9FuOtXibFjUE8MpVv6uiAWfj5E3dsBZR&si=sUDac8pFyJE5oXdW"));
        videoList.add(new DataSetList("Cloud Architecture", "https://youtu.be/36zducUX16w?si=oqdeq-AxZmvK-8pu"));
        videoList.add(new DataSetList("Service Management and Resource Management in Cloud computing", "https://youtu.be/IJe2L946cYE?si=Zq_5nRQM0vJy6u8C"));
        videoList.add(new DataSetList("Service Management and Resource Management in Cloud computing", "https://youtu.be/RN0eic33IJs?si=ejGWTi9dU-JNIvsj"));
        videoList.add(new DataSetList("Cloud Data Management", "https://youtu.be/efY56Z-X6FM?si=x0bsXYGwC24d6aQN"));
        videoList.add(new DataSetList("Cloud Security ", "https://youtu.be/jI8IKpjiCSM?si=K-C8M7WTzkD84_iW"));
        videoList.add(new DataSetList("Open Source and Commercial Clouds", "https://youtu.be/Pim95uZAGFw?si=RynBBnIzkp8Ehpgk"));

        YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }
}
