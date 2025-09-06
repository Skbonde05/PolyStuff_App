package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class m3_tut_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m3_tut_main);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(m3_tut_main.this, m3_cs.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        RelativeLayout relativeLayout = findViewById(R.id.rl);
        relativeLayout.startAnimation(animation);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(
                new Product(
                        1,
                        "Tut 1_186_N2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FMaths%20Tut%201_186_N2.pdf?alt=media&token=03af2145-afb5-432a-a72b-b69b6f1b3f2b"
                        ));
        productList.add(
                new Product(
                        1,
                        "Tut 2_186_N2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FMaths%20Tut%202_186_N2.pdf?alt=media&token=8d2d25e3-b33e-4d42-a90d-b724edef2b30"
                ));
        productList.add(
                new Product(
                        1,
                    "Tut 3_186_N2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FMaths%20Tut%203_186_N2.pdf?alt=media&token=475c9bd5-e6b7-4785-bb02-3279ee408c1b"
                ));
        productList.add(
                new Product(
                        1,
                        "Tut 4_186_N2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FMaths%20Tut%204_186_N2.pdf?alt=media&token=99e3ddd3-4fd9-4b75-ace0-c9fe8eafed8f"
                ));
        productList.add(
                new Product(
                        1,
                        "Tut 5_186_N2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FMaths%20Tut%205_186_N2.pdf?alt=media&token=96895b7c-ab70-4101-b929-eeaefd9b294f"
                ));
        productList.add(
                new Product(
                        1,
                        "Tut 6_186_N2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FMaths%20Tut%206_186_N2.pdf?alt=media&token=b2100867-11a7-40e7-9091-894e7ff6d12f"
                ));
        productList.add(
                new Product(
                        1,
                        "Tut 7_186_N2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FMaths%20Tut%207_186_N2.pdf?alt=media&token=d7785ead-a93d-4cce-912c-1f2723c5515e"
                ));
        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
