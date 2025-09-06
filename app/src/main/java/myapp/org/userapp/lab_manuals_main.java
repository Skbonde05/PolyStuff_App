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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class lab_manuals_main extends AppCompatActivity {
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_manuals_main);
        MobileAds.initialize(this, initializationStatus -> {});

        // Load banner ad
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(lab_manuals_main.this, MainActivity.class);
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
                        "Communication Skills\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FCommunication%20Skills.pdf?alt=media&token=e6168447-3c8b-413a-95d5-27df35c89771"
                ));
        productList.add(
                new Product(
                        1,
                        "Communication Skills 1\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F311303%20-%20Communication%20Skills%201.pdf?alt=media&token=389d7175-6af5-4ed2-8210-8950f82c20e8"
                ));
        productList.add(
                new Product(
                        1,
                        "Fundamentals of ICT\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FFundamentals%20of%20ICT.pdf?alt=media&token=657be883-e879-4724-b667-1e2dc14a6c77"
              ));
        productList.add(
                new Product(
                        1,
                        "Applied Physics\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FPhysics.pdf?alt=media&token=b14020f4-bbcd-4427-8ef9-9e06afab2f92"
                        ));
        productList.add(
                new Product(
                        1,
                        "Engineering Physics\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F221012%20I%20Sem%20Practical%20Basic%20science%20Physics-26-4-2018.pdf?alt=media&token=68463b4c-49fa-4200-a5c0-359fd0d6c11c"
                ));
        productList.add(
                new Product(
                        1,
                        "Programming in C\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FProgramming%20in%20C%20(2).pdf?alt=media&token=b570cd27-38f8-4c0b-b28a-a8ecb2033ab7"
                ));
        productList.add(
                new Product(
                        1,
                        "Linux Basics\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FLinux%20Basics.pdf?alt=media&token=f8a4c67d-df10-4bef-a44a-c5f3c29512b9"
                ));
        productList.add(
                new Product(
                        1,
                        "Web Development Using HTML\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F22014%20-%20Web%20Page%20Designing%20with%20HTML.pdf?alt=media&token=3905ab03-8cd4-4eab-a6af-f38f3486a32b"
                ));
        productList.add(
                new Product(
                        1,
                        "Basic Electronics\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F312314%20-%20Basic%20Electronics%201.pdf?alt=media&token=52e7c5cc-7f6f-47ec-a8d4-99eb09cea940"
                ));
        productList.add(
                new Product(
                        1,
                        "Data Structures Using C\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F313301%20-%20DATA%20STRUCTURE%20USING%20C.pdf?alt=media&token=046f05db-393a-48ec-8a65-dce38519db29"
                ));
        productList.add(
                new Product(
                        1,
                        "Fundamental of Electrical Engineering\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F312310%20-%20Fundamental%20of%20Electrical%20Engineering%201.pdf?alt=media&token=72b77ddf-36f9-45c5-8f43-38ff3c55766e"
                ));
        productList.add(
                new Product(
                        1,
                        "Computer Graphics Using C\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F313001%20-%20COMPUTER%20GRAPHICS.pdf?alt=media&token=cbbe08de-caf7-439a-afbb-4685605daab0"
                ));
        productList.add(
                new Product(
                        1,
                        "Database Management System\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F313302%20-%20DATABASE%20MANAGEMENT%20SYSTEM.pdf?alt=media&token=fc17d721-08c8-48e3-9c8c-1ba72049ddcf"
                ));
        productList.add(
                new Product(
                        1,
                        "OOP\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F313304%20-%20OBJECT%20ORIENTED%20PROGRAMMING%20USING%20C%2B%2B.pdf?alt=media&token=aad4662e-2174-4e7d-ab7b-a230aaede2d5"
                ));
        productList.add(
                new Product(
                        1,
                        "Digital Techniques and Microprocessor\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F313305%20-%20DIGITAL%20TECHNIQUES%20AND%20MICROPROCESSORS.pdf?alt=media&token=2f881111-ee0a-4b27-a95d-c5b09159a555"
                ));
        productList.add(
                new Product(
                        1,
                        "Python\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F313011%20-%20BASIC%20PYTHON%20PROGRAMMING.pdf?alt=media&token=c6ae9faa-e2cf-4ffd-92f9-c3ab0d41c3dd"
                 ));
        productList.add(
                new Product(
                        1,
                        "Java Programming\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F22412%20JAVA%20Programming.pdf?alt=media&token=985c660c-0d06-4d55-8f87-9e7849b71eaa"
                ));
        productList.add(
                new Product(
                        1,
                        "Operating System\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F22516_Operating%20Systems%20%20mail%208-5-19.pdf?alt=media&token=b07424e8-5630-43b7-8656-2a04a66b496c"
                ));
        productList.add(
                new Product(
                        1,
                        "Advanced Java Programming\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F22517_Advanced%20Java%20Programming%20(1)04-05-2019%20mail%206-5-19.pdf?alt=media&token=5f6316c9-f662-4825-be0d-d9b447424f2d"
                ));
        productList.add(
                new Product(
                        1,
                        "Software Testing\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F22518_Software%20Testing%20mail%206-5-19.pdf?alt=media&token=e030aadd-5198-4c49-a5db-44978f0f587c"
                ));
        productList.add(
                new Product(
                        1,
                        "Mobile Application Development\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F22617%20Mobile%20Application%20Development.pdf?alt=media&token=ed274b87-297e-46b0-903c-c0a710700e44"
                ));
        productList.add(
                new Product(
                        1,
                        "Computer Networking and Data Communication\n",
                        60000,
                        R.drawable.ic_pdf,
                        R.drawable.ic_downloads,
                        "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2F22634-Computer%20networking%20and%20Data%20Communication.pdf?alt=media&token=5a6c80d0-b5c9-4d6c-89ac-0d6e0ef23a76"
                ));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
