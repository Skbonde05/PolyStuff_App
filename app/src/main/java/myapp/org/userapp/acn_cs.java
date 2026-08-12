package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class acn_cs extends AppCompatActivity {

    CardView acn_u1,acn_u2,acn_u3,acn_u4,acn_u5,acn_u6,acn_que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acn_cs);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(acn_cs.this, levels4b.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        acn_que = findViewById(R.id.acn_que);

        // Set OnClickListener for jp1_u1 CardView
        acn_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(acn_cs.this, SubjectContentActivity.class);
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_KEY, "acn_que_main");
                startActivity(intent);
            }
        });

        acn_u1 = findViewById(R.id.acn_u1);

        // Set OnClickListener for jp1_u1 CardView
        acn_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(acn_cs.this, SubjectContentActivity.class);
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_KEY, "acn_u1_main");
                startActivity(intent);
            }
        });

        acn_u2 = findViewById(R.id.acn_u2);

        // Set OnClickListener for jp1_u1 CardView
        acn_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(acn_cs.this, SubjectContentActivity.class);
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_KEY, "acn_u2_main");
                startActivity(intent);
            }
        });

        acn_u3 = findViewById(R.id.acn_u3);

        // Set OnClickListener for jp1_u1 CardView
        acn_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(acn_cs.this, SubjectContentActivity.class);
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_KEY, "acn_u3_main");
                startActivity(intent);
            }
        });

        acn_u4 = findViewById(R.id.acn_u4);

        // Set OnClickListener for jp1_u1 CardView
        acn_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(acn_cs.this, SubjectContentActivity.class);
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_KEY, "acn_u4_main");
                startActivity(intent);
            }
        });

        acn_u5 = findViewById(R.id.acn_u5);

        // Set OnClickListener for jp1_u1 CardView
        acn_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(acn_cs.this, SubjectContentActivity.class);
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_KEY, "acn_u5_main");
                startActivity(intent);
            }
        });

        acn_u6 = findViewById(R.id.acn_u6);

        // Set OnClickListener for jp1_u1 CardView
        acn_u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(acn_cs.this, SubjectContentActivity.class);
                intent.putExtra(SubjectContentActivity.EXTRA_SUBJECT_KEY, "acn_u6_main");
                startActivity(intent);
            }
        });

    }
}

