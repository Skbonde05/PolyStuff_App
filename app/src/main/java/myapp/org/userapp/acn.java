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

public class acn extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acn); // Ensure this layout file exists

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity
                Intent intent = new Intent(acn.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new DataSetList("Network Layer I", "https://youtube.com/playlist?list=PLBlnK6fEyqRi7E6_6rLC5N_v50TW6qlrf&si=6i6ps_kXGhxDfS5E"));
        videoList.add(new DataSetList("Network Layer II ", "https://youtu.be/tAKx2RCgqow?si=F3qPo411KAeIhdy_"));
        videoList.add(new DataSetList("Transport Layer ", "https://youtube.com/playlist?list=PLHQAlOcTuuT3HbVd9XG42a0WtC1MRKRvO&si=sBR28HzZCk6EcJwk"));
        videoList.add(new DataSetList("Transport Layer ", "https://youtu.be/kAty4mKczEg?si=Kmq-ofISui9i1h9e"));
        videoList.add(new DataSetList("Application Layer I: DOMAIN NAME SYSTEM ", "https://youtu.be/vhfRArT11jc?si=Grxh6jjvswbIINHS"));
        videoList.add(new DataSetList("Application Layer II: WWW AND HTTP", "https://youtu.be/pnoWCK82apU?si=hlB6oyi4eje6eGxn"));
        videoList.add(new DataSetList("Cryptography and Security in the Internet", "https://youtu.be/trHox1bN5es?si=qzkI2SK9j2vLEkvM"));

        YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }
}
