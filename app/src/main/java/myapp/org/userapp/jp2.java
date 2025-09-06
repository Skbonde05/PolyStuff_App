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

public class jp2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jp2); // Ensure this layout file exists

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity
                Intent intent = new Intent(jp2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new DataSetList("Abstract Windowing Toolkit (AWT)", "https://youtu.be/6u4PUjm8-5Y?si=rSCqz79IuuvW4DAn"));
        videoList.add(new DataSetList("Swing Component", "https://youtu.be/R0USRU90TOo?si=exZx9FBbGkUpP8UY"));
        videoList.add(new DataSetList("Networking Basics", "https://youtu.be/DuFyhu5_GPs?si=ST9amvdjGG-zXl2D"));
        videoList.add(new DataSetList("Java Database Connectivity", "https://youtu.be/MOQfTuOiBys?si=9crRDp9xi4RFc6Oy"));
        videoList.add(new DataSetList("Remote Method Invocation", "https://youtu.be/kyluPcfsyLU?si=jY_zSHmHVfZ4DQ-f"));
        videoList.add(new DataSetList("Servlets", "https://youtu.be/A3x-Fs7UsEc?si=snc78ViU1MFi8l-y"));

        YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }
}
