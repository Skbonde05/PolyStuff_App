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

public class os extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.os); // Ensure this layout file exists

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity
                Intent intent = new Intent(os.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new DataSetList("Introduction To Operating System", "https://www.youtube.com/watch?v=8XBtAjKwCm4"));
        videoList.add(new DataSetList("OS Services And Components", "https://www.youtube.com/watch?v=8XBtAjKwCm4"));
        videoList.add(new DataSetList("Process Management", "https://www.youtube.com/watch?v=8XBtAjKwCm4"));
        videoList.add(new DataSetList("CPU Scheduling And Deadlock", "https://www.youtube.com/watch?v=8XBtAjKwCm4"));
        videoList.add(new DataSetList("Memory Management", "https://www.youtube.com/watch?v=8XBtAjKwCm4"));
        videoList.add(new DataSetList("Files Management", "https://www.youtube.com/watch?v=8XBtAjKwCm4"));

        YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }
}
