package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ds extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds); // Ensure this layout file exists

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity
                Intent intent = new Intent(ds.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new DataSetList("Introduction to Data Structures", "https://youtu.be/-D5u5HJbISc?si=A3Kyw8BSMfSnYR9H"));
        videoList.add(new DataSetList("Searching and Sorting Techniques", "https://youtu.be/K2BEKh-EqqA?si=nSiuudij8UcTLRqK"));
        videoList.add(new DataSetList("Linked Lists Explained", "https://youtu.be/h4HpJMSxcF8?si=a_uXWwu-08wda5YD"));
        videoList.add(new DataSetList("Stacks and Queues", "https://youtu.be/XJyfHJqQOIE?si=60Ovyj2rF0kk6Bns"));
        videoList.add(new DataSetList("Hash Tables", "https://youtu.be/iVJ_avaKDXk?si=KunGtfpAt2zswG8A"));
        videoList.add(new DataSetList("Graphs", "https://youtu.be/lg48ZZe7YUA?si=bqfgyltF1BFoUNpS"));

        YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }
}
