package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class dcn_it extends AppCompatActivity {

    CardView cn_lab,cn_prac,cn_que,cn_u1,cn_u2,cn_u3,cn_u4,cn_u5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dcn_it);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(dcn_it.this, levels3_it.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        cn_que = findViewById(R.id.cn_que);

        // Set OnClickListener for jp1_u1 CardView
        cn_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dcn_it.this, cn_que_main.class);
                startActivity(intent);
            }
        });

        cn_u1 = findViewById(R.id.cn_u1);

        // Set OnClickListener for jp1_u1 CardView
        cn_u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dcn_it.this, cn_u1_main.class);
                startActivity(intent);
            }
        });

        cn_u2 = findViewById(R.id.cn_u2);

        // Set OnClickListener for jp1_u1 CardView
        cn_u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dcn_it.this, cn_u2_main.class);
                startActivity(intent);
            }
        });

        cn_u3 = findViewById(R.id.cn_u3);

        // Set OnClickListener for jp1_u1 CardView
        cn_u3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dcn_it.this, cn_u3_main.class);
                startActivity(intent);
            }
        });

        cn_u4 = findViewById(R.id.cn_u4);

        // Set OnClickListener for jp1_u1 CardView
        cn_u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dcn_it.this, cn_u4_main.class);
                startActivity(intent);
            }
        });

        cn_u5 = findViewById(R.id.cn_u5);

        // Set OnClickListener for jp1_u1 CardView
        cn_u5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open jp1_u1_main activity
                Intent intent = new Intent(dcn_it.this, cn_u5_main.class);
                startActivity(intent);
            }
        });


    }
}
