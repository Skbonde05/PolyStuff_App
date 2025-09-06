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

public class pwp_mcq_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pwp_mcq_main);

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
                        "UNIT 1: Introduction to Python Programming\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FPWP%20U1%20MCQ's_186_N3.pdf?alt=media&token=a8fba8af-02be-43dc-a111-8487ada4e2b3"
                        ));

        productList.add(
                new Product(
                        1,
                        "UNIT 2: Python Operators and Control Flow\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FPWP%20U2%20MCQ's_186_N3.pdf?alt=media&token=06d8b7f9-bee3-40ee-a702-ea63e4e4f3a4"
                ));

        productList.add(
                new Product(
                        1,
                        "UNIT 3: Data Structures in Python\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FPWP%20U3%20MCQ's_186_N3.pdf?alt=media&token=4ba9a5ba-fd89-4e6b-963e-e6c64deffcf5"
                 ));

        productList.add(
                new Product(
                        1,
                        "UNIT 4: Python Functions, Modules and Packages\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FPWP%20U4%20MCQ's_186_N3.pdf?alt=media&token=6a3825fe-99eb-4525-ad7e-2c8072935958"
                 ));

        productList.add(
                new Product(
                        1,
                        "UNIT 5: Object Oriented Programming in Python\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FPWP%20U5%20MCQ's_186_N3.pdf?alt=media&token=db549461-4170-4b38-8382-53e2d95cdedd"
                ));

        productList.add(
                new Product(
                        1,
                        "UNIT 6: File and Exception Handling\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FPWP%20U6%20MCQ's_186_N3.pdf?alt=media&token=fcc66abc-75f5-4ce9-8713-40cb4bd6c89e"
                ));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
