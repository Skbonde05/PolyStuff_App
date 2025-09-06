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

public class acn_u4_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acn_u4_main);

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
                        "ACN UNIT 4_186_N3\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads, "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FACN%20UNIT%204_186_N3.pdf?alt=media&token=90be2d5e-601e-4a8d-aab2-ee11544119e0"
                ));

        productList.add(
                new Product(
                        1,
                        "ACN UNIT 4\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads, "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FACN%20Unit%204.pdf?alt=media&token=0a63058a-118d-4e3e-b4a9-0a9b0de81270"
                ));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
