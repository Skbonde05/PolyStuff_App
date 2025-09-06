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

public class foe_u1_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foe_u1_main);

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
                        "Semiconductors\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        ""
                ));

        productList.add(
                new Product(
                        1,
                        "Filter\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FFilter.pdf?alt=media&token=a7a5946b-3ae3-4075-87ad-f135501c2aeb"
                ));

        productList.add(
                new Product(
                        1,
                        "Transistors\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FTRANSISTOR.pdf?alt=media&token=ea07695d-1e2d-40b0-8f55-e8695fda6cd3"
                ));

        productList.add(
                new Product(
                        1,
                        "Rectifiers\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2Frectifier.pdf?alt=media&token=4bca8392-c377-4317-ae00-b1c1c59cf2c3"
                ));

        productList.add(
                new Product(
                        1,
                        "Relation(a-b)(a-g)\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FRelation(a-b)(a-g).pdf?alt=media&token=5be6b8a1-102b-4387-b4ea-f0583d19cf6f"
                ));

        productList.add(
                new Product(
                        1,
                        "IMP Questions\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FUnit%201-Q.Bank.pdf?alt=media&token=4f63ae0d-f477-4685-a743-ad8ece036396"
                ));


        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
