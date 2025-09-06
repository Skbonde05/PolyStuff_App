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

public class m1_tut_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1_tut_main);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(m1_tut_main.this, m1_cs.class);
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
                        "M1 Tutorial 1\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTut%201.pdf?alt=media&token=11b722a2-991f-4ff7-ac67-e4ff56c9d5a3"
                ));

        productList.add(
                new Product(
                        1,
                        "M1 Tutorial 2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTut%202.pdf?alt=media&token=ebe51b17-b0d6-49ba-aa73-da4f33cd1d08"
                ));

        productList.add(
                new Product(
                        1,
                        "M1 Tutorial 3\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTut%203.pdf?alt=media&token=2743004f-f6ce-4ea6-b5fe-6d47dec35252"
                ));

        productList.add(
                new Product(
                        1,
                        "M1 Tutorial 4\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTut%204.pdf?alt=media&token=106486c3-1394-4d6a-a7c0-7be6d550f032"
                ));

        productList.add(
                new Product(
                        1,
                        "M1 Tutorial 5\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTut%205.pdf?alt=media&token=beca20ed-bc7a-4d24-bc66-b0d3456f6bda"
                ));

        productList.add(
                new Product(
                        1,
                        "M1 Tutorial 6\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTut%206.pdf?alt=media&token=614b7f8d-482a-432a-8ca7-5ab421c239c6"
                ));

        productList.add(
                new Product(
                        1,
                        "M1 Tutorial 7\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTut%207.pdf?alt=media&token=cf66200f-4555-4b08-ae0f-d60d5fa7c8df"
                ));

        productList.add(
                new Product(
                        1,
                        "M1 Tutorial 8\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTut%208.pdf?alt=media&token=010977e6-68b8-49d6-a864-b1682f2e05da"
                ));


        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
