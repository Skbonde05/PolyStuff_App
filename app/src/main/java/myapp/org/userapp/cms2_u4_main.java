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

public class cms2_u4_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cms2_u4_main);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(cms2_u4_main.this, cms2_cs.class);
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
                        "Letters\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FLetters.pdf?alt=media&token=8eafdfff-e59b-4a6b-b3a8-13e7a43cb024"
                ));

        productList.add(
                new Product(
                        1,
                        "CMS2 Unit 4: Letter Formats\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FCMS%20Formats.pdf?alt=media&token=936c1179-e334-4255-ba9e-302b3b20cd84"
                ));

        productList.add(
                new Product(
                        1,
                        "CMS2 Unit 4: Complaint Letter\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FComplain%20and%20Joining%20Letter.pdf?alt=media&token=4b01be3e-4ebb-4efc-bec6-35bcb1e5a79f"
                        ));

        productList.add(
                new Product(
                        1,
                        "CMS2 Unit 4: Enquiry Letter\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FEnquiry%20letter.PDF?alt=media&token=af293638-5d9a-4a7e-b33e-73694efcd8af"
                        ));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
