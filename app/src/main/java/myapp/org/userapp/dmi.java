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

public class dmi extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dmi); // Ensure this layout file exists

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity
                Intent intent = new Intent(dmi.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new DataSetList("Basics of Data Mining", "https://youtu.be/dvRZ1VYXMJ0?si=3LUfOpFSeMiYGy4J"));
        videoList.add(new DataSetList("Know Data Concepts", "https://youtu.be/ss3mDm3mjsc?si=jyo5frCR5Q3FHio6"));
        videoList.add(new DataSetList("Data Preprocessing", "https://youtu.be/QRZlYzxEFDg?si=K_9xfVbtnsjWy7jt"));
        videoList.add(new DataSetList("Classification", "https://youtu.be/pqDrdxeWvP8?si=kpOmSHiCMQR4EGYi"));
        videoList.add(new DataSetList("Open Source Data mining Tool", "https://youtu.be/mlaSqKc5KQU?si=1Lo04RcpAjoa_4Lo"));
        videoList.add(new DataSetList("Data Warehousing", "https://youtu.be/NphMcnU8ymU?si=-rJ3Euje6Q4WLSF_"));

        YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }
}
