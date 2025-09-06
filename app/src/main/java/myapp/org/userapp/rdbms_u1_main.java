package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class rdbms_u1_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdbms_u1_main);

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
                        "RDBMS Unit 1\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FRDBMS%20Unit%201.pdf?alt=media&token=f4a09503-8bb3-4d90-94be-13514e5024bb"
                ));

        productList.add(
                new Product(
                        1,
                        "Database System Concept\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FUNIT%201.pdf?alt=media&token=d156d23e-75b4-403f-8ef1-afa9b675c3e8"
                ));

        productList.add(
                new Product(
                        1,
                        "RDBMS Unit 1(Part 1)\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FUnit1_A.pdf?alt=media&token=bafe78d9-881c-4f0d-a1e0-eedba3f15e4e"
                ));

        productList.add(
                new Product(
                        1,
                        "RDBMS Unit 1(Part 2)\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FUnit_1_B.pdf?alt=media&token=07ce50b8-15f8-4adb-b167-876f9413ab2d"
                ));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
