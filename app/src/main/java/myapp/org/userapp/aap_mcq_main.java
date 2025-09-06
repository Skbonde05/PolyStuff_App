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

public class aap_mcq_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aap_mcq_main);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        RelativeLayout relativeLayout = findViewById(R.id.rl);
        relativeLayout.startAnimation(animation);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(
                new Product(
                        1,
                        "UNIT 1: Introduction to Android Operating System\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FAAP%20U1%20MCQ's_186_N3.pdf?alt=media&token=0257e221-f2ac-41b1-8f30-35c9e4cc3e12"
               ));

        productList.add(
                new Product(
                        1,
                        "UNIT 2: Installation and configuration of Android Environment\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FAAP%20U2%20MCQ's_186_N3.pdf?alt=media&token=5774256e-af31-45be-b62e-21bbba350a82"
                 ));

        productList.add(
                new Product(
                        1,
                        "UNIT 3: Fundamental of UI Components and Layouts\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FAAP%20U3%20MCQ's_186_N3.pdf?alt=media&token=895045e7-abab-490f-9878-b429c892f9a3"
                ));

        productList.add(
                new Product(
                        1,
                        "UNIT 4: Designing User Interface\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FAAP%20U4%20MCQ's_186_N3.pdf?alt=media&token=717b4ab4-0c79-418e-9092-6264fd9d67bb"
                ));

        productList.add(
                new Product(
                        1,
                        "UNIT 5: Activity, Multimedia and Databases\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FAAP%20U5%20MCQ's_186_N3.pdf?alt=media&token=67bdc725-645b-4c00-8cb7-93e077d9ad24"
                        ));

        productList.add(
                new Product(
                        1,
                        "UNIT 6: Security and Application Deployment\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FAAP%20U6%20MCQ's_186_N3.pdf?alt=media&token=ca89e80a-90e9-4708-8240-ae6c513c5d10"
               ));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
