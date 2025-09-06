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

public class acn_u2_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acn_u2_main);

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
                        "ACN UNIT 2_186_N3\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads, "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FACN%20UNIT%202_186_N3.pdf?alt=media&token=29529331-4c09-437b-a288-c989fddd8fe4"
                ));

        productList.add(
                new Product(
                        1,
                        "ACN UNIT 2\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads, "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FACN%20UNIT%202.pdf?alt=media&token=86c5eac1-1678-470e-8ad4-1512fea611c7"
                ));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
