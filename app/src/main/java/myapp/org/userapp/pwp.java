package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class pwp extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataSetList> videoList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pwp); // Ensure this layout file exists

        // -------- Toolbar back button --------
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> {
                Intent intent = new Intent(pwp.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }

        // -------- RecyclerView --------
        recyclerView = findViewById(R.id.recyclerView1);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(false);

            // Initialize video list
            videoList = new ArrayList<>();
            videoList.add(new DataSetList("Introduction to Python Programming", "https://youtu.be/qYhrd2Lhq0g?si=xW9XFzdTCgE8Ln1P"));
            videoList.add(new DataSetList("Python Operators and Control Flow", "https://youtu.be/uqPIk_0q940?si=jwnsMisgXBtD0bg6"));
            videoList.add(new DataSetList("Data Structures in Python", "https://www.youtube.com/watch?v=TW_e9FFEDeY&list=PLEJyjB1oGzx3iTZvOVedkT8nZ2cG105U7"));
            videoList.add(new DataSetList("Python Functions, Modules and Packages ", "https://youtu.be/RoheSK9KwOU?si=qVXtYxFxHR5fXFDF"));
            videoList.add(new DataSetList("Object Oriented Programming in Python", "https://youtu.be/Rq_3gA2h1RA?si=kksryAVZdC9hx_DO"));
            videoList.add(new DataSetList("File And Exception Handling", "https://youtu.be/qrueF1F9ndE?si=GAKEFkK1cJzmMFmW"));

            YoutubeAdapter adapter = new YoutubeAdapter(videoList, this);
            recyclerView.setAdapter(adapter);
        }
    }
}