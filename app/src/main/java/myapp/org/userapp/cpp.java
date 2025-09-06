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

public class cpp extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cpp); // Ensure this layout file exists

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity
                Intent intent = new Intent(cpp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Initialize video list
        videoList = new ArrayList<>();
        videoList.add(new DataSetList("Introduction To Object Oriented Programming", "https://youtu.be/i2OOPAvCDK0?si=sQb9KO6sDHsa3aZy"));
        videoList.add(new DataSetList("Basics Of Object Oriented Programming", "https://youtu.be/Qe026vn1tbc?si=GCu2quZAdxiZNtcP"));
        videoList.add(new DataSetList("Functions In C++", "https://youtu.be/TcNBtrG1We4?si=G-GehqKzYpO83Eec"));
        videoList.add(new DataSetList("Operator Overloading, Polymorphism And Type Conversion", "https://youtu.be/cPfcLaP_DqA?si=G04ZJMtEVw_tDQFr"));
        videoList.add(new DataSetList("Inheritance And Pointers", "https://youtu.be/7ZfEp71tIec?si=oN7xpdCoVny1yXvC"));
        videoList.add(new DataSetList("Files And Exception Handling ", "https://youtu.be/lMhdiN7l4ig?si=Yp0gKMOnn0cBeQcF"));

        YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }
}
