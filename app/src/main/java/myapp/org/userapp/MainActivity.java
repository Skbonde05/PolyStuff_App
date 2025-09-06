package myapp.org.userapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import myapp.org.userapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    CardView computerLevelsCard;
    CardView semestersCard;
    CardView labmanualCard;
    CardView mpCard;
    SearchView searchView;
    ListView listView;
    TextView textViewHello;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();
    private AdView adView;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        int themeMode = prefs.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(themeMode);
        setContentView(binding.getRoot());

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {});
        loadInterstitialAd();

        // Banner Ad
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        viewPager2 = findViewById(R.id.viewPager);

        List<SlideIten> sliderItem = new ArrayList<>();
        sliderItem.add(new SlideIten(R.drawable.infosys));
        sliderItem.add(new SlideIten(R.drawable.sider3));
        sliderItem.add(new SlideIten(R.drawable.slider2));
        sliderItem.add(new SlideIten(R.drawable.swayam));
        sliderItem.add(new SlideIten(R.drawable.slider1));
        sliderItem.add(new SlideIten(R.drawable.nptel));

        viewPager2.setAdapter(new SlideAdapter(sliderItem, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositionTransform = new CompositePageTransformer();
        compositionTransform.addTransformer(new MarginPageTransformer(40));
        compositionTransform.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositionTransform);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable, 2000);

            }
        });
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (id == R.id.video) {
                loadFragment(new VideosFragment());
                return true;
            } else if (id == R.id.profile) {
                loadFragment(new ProfileFragment());
                return true;
            } else if (id == R.id.online) {
                loadFragment(new OnlineCoursesFragment());
                return true;
            }
            return false;
        });

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);

        listView.setVisibility(View.GONE);

        arrayList = new ArrayList<>();

        arrayList.add("CM5101");
        arrayList.add("Python");
        arrayList.add("Python Unit 1");
        arrayList.add("Python Unit 2");
        arrayList.add("Python Unit 3");
        arrayList.add("Python Unit 4");
        arrayList.add("Python Unit 5");
        arrayList.add("Python Unit 6");

        arrayList.add("CM3103");
        arrayList.add("DS");
        arrayList.add("DS Unit 1");
        arrayList.add("DS Unit 2");
        arrayList.add("DS Unit 3");
        arrayList.add("DS Unit 4");
        arrayList.add("DS Unit 5");
        arrayList.add("DS Unit 6");
        arrayList.add("DS Question Paper");

        arrayList.add("Data Structure");
        arrayList.add("Data Structure Unit 1");
        arrayList.add("Data Structure Unit 2");
        arrayList.add("Data Structure Unit 3");
        arrayList.add("Data Structure Unit 4");
        arrayList.add("Data Structure Unit 5");
        arrayList.add("Data Structure Unit 6");

        arrayList.add("CM3104");
        arrayList.add("OOP");
        arrayList.add("OOP Unit 1");
        arrayList.add("OOP Unit 2");
        arrayList.add("OOP Unit 3");
        arrayList.add("OOP Unit 4");
        arrayList.add("OOP Unit 5");
        arrayList.add("OOP Unit 6");
        arrayList.add("OOP Question Paper");

        arrayList.add("CPP");
        arrayList.add("CPP Unit 1");
        arrayList.add("CPP Unit 2");
        arrayList.add("CPP Unit 3");
        arrayList.add("CPP Unit 4");
        arrayList.add("CPP Unit 5");
        arrayList.add("CPP Unit 6");
        arrayList.add("CPP Question Paper");

        arrayList.add("CM1101");
        arrayList.add("CE");
        arrayList.add("CE Unit 1");
        arrayList.add("CE Unit 2");
        arrayList.add("CE Unit 3");
        arrayList.add("CE Unit 4");
        arrayList.add("CE Unit 5");
        arrayList.add("CE Unit 6");

        arrayList.add("CM1101");
        arrayList.add("Computing Essentials");
        arrayList.add("Computing Essentials Unit 1");
        arrayList.add("Computing Essentials Unit 2");
        arrayList.add("Computing Essentials Unit 3");
        arrayList.add("Computing Essentials Unit 4");
        arrayList.add("Computing Essentials Unit 5");
        arrayList.add("Computing Essentials Unit 6");

        arrayList.add("ET2107");
        arrayList.add("Fundamentals of Electronics");
        arrayList.add("Fundamentals of Electronics Unit 1");
        arrayList.add("Fundamentals of Electronics Unit 2");
        arrayList.add("Fundamentals of Electronics Unit 3");
        arrayList.add("Fundamentals of Electronics Unit 4");
        arrayList.add("Fundamentals of Electronics Unit 5");
        arrayList.add("Fundamentals of Electronics Question Paper");

        arrayList.add("ET2107");
        arrayList.add("FOE");
        arrayList.add("FOE Unit 1");
        arrayList.add("FOE Unit 2");
        arrayList.add("FOE Unit 3");
        arrayList.add("FOE Unit 4");
        arrayList.add("FOE Unit 5");
        arrayList.add("FOE Question Paper");

        arrayList.add("SC1101");
        arrayList.add("M1");
        arrayList.add("M1 Tutorial");

        arrayList.add("SC1102");
        arrayList.add("M2");
        arrayList.add("M2 Tutorial");

        arrayList.add("SC2102");
        arrayList.add("M3");
        arrayList.add("M3 Tutorial");
        arrayList.add("M3 Unit 1");
        arrayList.add("M3 Unit 2");
        arrayList.add("M3 Unit 3");
        arrayList.add("M3 Unit 4");
        arrayList.add("M3 Unit 5");
        arrayList.add("M3 Question Paper");

        arrayList.add("HU1101");
        arrayList.add("CMS I (English)");
        arrayList.add("CMS I (English) Unit 1");
        arrayList.add("CMS I (English) Unit 2");
        arrayList.add("CMS I (English) Unit 3");
        arrayList.add("CMS I (English) Unit 4");
        arrayList.add("CMS I (English) Unit 5");

        arrayList.add("HU1102");
        arrayList.add("CMS II");
        arrayList.add("CMS II Question Paper");

        arrayList.add("SC1104");
        arrayList.add("Physics");
        arrayList.add("Physics Unit 1");
        arrayList.add("Physics Unit 2");
        arrayList.add("Physics Unit 3");
        arrayList.add("Physics Unit 4");
        arrayList.add("Physics Unit 5");
        arrayList.add("Physics Unit 6");

        arrayList.add("CM2103");
        arrayList.add("Linux");
        arrayList.add("Linux Commands");

        arrayList.add("CM2104");
        arrayList.add("HTML");
        arrayList.add("HTML Unit 1");
        arrayList.add("HTML Unit 2");
        arrayList.add("HTML Unit 3");
        arrayList.add("HTML Unit 4");
        arrayList.add("HTML Unit 5");
        arrayList.add("HTML Question Paper");

        arrayList.add("EE2107");
        arrayList.add("EE");
        arrayList.add("EE Unit 1");
        arrayList.add("EE Unit 2");
        arrayList.add("EE Unit 3");
        arrayList.add("EE Unit 4");
        arrayList.add("EE Unit 5");
        arrayList.add("EE Question Paper");

        arrayList.add("Electrical Engineering");
        arrayList.add("Electrical Engineering Unit 1");
        arrayList.add("Electrical Engineering Unit 2");
        arrayList.add("Electrical Engineering Unit 3");
        arrayList.add("Electrical Engineering Unit 4");
        arrayList.add("Electrical Engineering Unit 5");
        arrayList.add("Electrical Engineering Question Paper");

        arrayList.add("Fundamental of ICT");
        arrayList.add("Fundamental of ICT Commands");

        arrayList.add("FICT");
        arrayList.add("FICT Commands");

        arrayList.add("CM3102");
        arrayList.add("Java I");
        arrayList.add("Java I Unit 1");
        arrayList.add("Java I Unit 2");
        arrayList.add("Java I Unit 3");
        arrayList.add("Java I Unit 4");
        arrayList.add("Java I Unit 5");
        arrayList.add("Java I Unit 6");
        arrayList.add("Java I Question Paper");

        arrayList.add("CM4109");
        arrayList.add("Advanced Java");
        arrayList.add("Advanced Java Unit 1");
        arrayList.add("Advanced Java Unit 2");
        arrayList.add("Advanced Java Unit 3");
        arrayList.add("Advanced Java Unit 4");
        arrayList.add("Advanced Java Unit 5");
        arrayList.add("Advanced Java Unit 6");
        arrayList.add("Advanced Java Question Paper");

        arrayList.add("Java II");
        arrayList.add("Java II Unit 1");
        arrayList.add("Java II Unit 2");
        arrayList.add("Java II Unit 3");
        arrayList.add("Java II Unit 4");
        arrayList.add("Java II Unit 5");
        arrayList.add("Java II Unit 6");
        arrayList.add("Java II Question Paper");

        arrayList.add("CM3101");
        arrayList.add("Operating System");
        arrayList.add("Operating System Unit 1");
        arrayList.add("Operating System Unit 2");
        arrayList.add("Operating System Unit 3");
        arrayList.add("Operating System Unit 4");
        arrayList.add("Operating System Unit 5");
        arrayList.add("Operating System Unit 6");
        arrayList.add("Operating System Question Paper");

        arrayList.add("OS");
        arrayList.add("OS Unit 1");
        arrayList.add("OS Unit 2");
        arrayList.add("OS Unit 3");
        arrayList.add("OS Unit 4");
        arrayList.add("OS Unit 5");
        arrayList.add("OS Unit 6");
        arrayList.add("OS Question Paper");

        arrayList.add("CPHM");
        arrayList.add("CPHM Unit 1");
        arrayList.add("CPHM Unit 2");
        arrayList.add("CPHM Unit 3");
        arrayList.add("CPHM Unit 4");
        arrayList.add("CPHM Unit 5");
        arrayList.add("CPHM Unit 6");
        arrayList.add("CPHM Question Paper");

        arrayList.add("CM3106");
        arrayList.add("Computer Graphics");
        arrayList.add("Computer Graphics Unit 1");
        arrayList.add("Computer Graphics Unit 2");
        arrayList.add("Computer Graphics Unit 3");
        arrayList.add("Computer Graphics Unit 4");
        arrayList.add("Computer Graphics Unit 5");
        arrayList.add("Computer Graphics Question Paper");

        arrayList.add("CG");
        arrayList.add("CS Graphics");
        arrayList.add("CG Unit 1");
        arrayList.add("CG Unit 2");
        arrayList.add("CG Unit 3");
        arrayList.add("CG Unit 4");
        arrayList.add("CG Unit 5");
        arrayList.add("CG Question Paper");

        arrayList.add("CM3108");
        arrayList.add("Computer Network");
        arrayList.add("Computer Network Unit 1");
        arrayList.add("Computer Network Unit 2");
        arrayList.add("Computer Network Unit 3");
        arrayList.add("Computer Network Unit 4");
        arrayList.add("Computer Network Unit 5");
        arrayList.add("Computer Network Question Paper");

        arrayList.add("CN");
        arrayList.add("CN Unit 1");
        arrayList.add("CN Unit 2");
        arrayList.add("CN Unit 3");
        arrayList.add("CN Unit 4");
        arrayList.add("CN Unit 5");
        arrayList.add("CN Question Paper");

        arrayList.add("CM3105");
        arrayList.add("PDTMP");
        arrayList.add("PDTMP Unit 1");
        arrayList.add("PDTMP Unit 2");
        arrayList.add("PDTMP Unit 3");
        arrayList.add("PDTMP Unit 4");
        arrayList.add("PDTMP Unit 5");
        arrayList.add("PDTMP Unit 6");
        arrayList.add("PDTMP Question Paper");

        arrayList.add("DTMP");
        arrayList.add("DTMP Unit 1");
        arrayList.add("DTMP Unit 2");
        arrayList.add("DTMP Unit 3");
        arrayList.add("DTMP Unit 4");
        arrayList.add("DTMP Unit 5");
        arrayList.add("DTMP Unit 6");
        arrayList.add("DTMP Question Paper");

        arrayList.add("MA4101");
        arrayList.add("EDP");
        arrayList.add("EDP Unit 1");
        arrayList.add("EDP Unit 2");
        arrayList.add("EDP Unit 3");
        arrayList.add("EDP Unit 4");

        arrayList.add("Entrepreneurship Development");
        arrayList.add("Entrepreneurship Development Unit 1");
        arrayList.add("Entrepreneurship Development Unit 2");
        arrayList.add("Entrepreneurship Development Unit 3");
        arrayList.add("Entrepreneurship Development Unit 4");

        arrayList.add("MA4106");
        arrayList.add("Information Management");
        arrayList.add("Information Management Unit 1");
        arrayList.add("Information Management Unit 2");
        arrayList.add("Information Management Unit 3");
        arrayList.add("Information Management Unit 4");
        arrayList.add("Information Management Unit 5");

        arrayList.add("IM");
        arrayList.add("IM Unit 1");
        arrayList.add("IM Unit 2");
        arrayList.add("IM Unit 3");
        arrayList.add("IM Unit 4");
        arrayList.add("IM Unit 5");

        arrayList.add("CM4107");
        arrayList.add("Software Testing");
        arrayList.add("Software Testing Unit 1");
        arrayList.add("Software Testing Unit 2");
        arrayList.add("Software Testing Unit 3");
        arrayList.add("Software Testing Unit 4");
        arrayList.add("Software Testing Unit 5");
        arrayList.add("Software Testing Unit 6");
        arrayList.add("Software Testing Question Paper");

        arrayList.add("ST");
        arrayList.add("ST Unit 1");
        arrayList.add("ST Unit 2");
        arrayList.add("ST Unit 3");
        arrayList.add("ST Unit 4");
        arrayList.add("ST Unit 5");
        arrayList.add("ST Unit 6");
        arrayList.add("ST Question Paper");

        arrayList.add("CM4108");
        arrayList.add("ACN");
        arrayList.add("ACN Unit 1");
        arrayList.add("ACN Unit 2");
        arrayList.add("ACN Unit 3");
        arrayList.add("ACN Unit 4");
        arrayList.add("ACN Unit 5");
        arrayList.add("ACN Unit 6");
        arrayList.add("ACN Question Paper");

        arrayList.add("Advanced Computer Network");
        arrayList.add("Advanced Computer Network Unit 1");
        arrayList.add("Advanced Computer Network Unit 2");
        arrayList.add("Advanced Computer Network Unit 3");
        arrayList.add("Advanced Computer Network Unit 4");
        arrayList.add("Advanced Computer Network Unit 5");
        arrayList.add("Advanced Computer Network Unit 6");
        arrayList.add("Advanced Computer Network Question Paper");

        arrayList.add("CM4110");
        arrayList.add("Computer Security");
        arrayList.add("Computer Security Unit 1");
        arrayList.add("Computer Security Unit 2");
        arrayList.add("Computer Security Unit 3");
        arrayList.add("Computer Security Unit 4");
        arrayList.add("Computer Security Unit 5");
        arrayList.add("Computer Security Question Paper");

        arrayList.add("CS");
        arrayList.add("CS Unit 1");
        arrayList.add("CS Unit 2");
        arrayList.add("CS Unit 3");
        arrayList.add("CS Unit 4");
        arrayList.add("CS Unit 5");
        arrayList.add("CS Question Paper");

        arrayList.add("CM4111");
        arrayList.add("RDBMS");
        arrayList.add("RDBMS Unit 1");
        arrayList.add("RDBMS Unit 2");
        arrayList.add("RDBMS Unit 3");
        arrayList.add("RDBMS Unit 4");
        arrayList.add("RDBMS Unit 5");
        arrayList.add("RDBMS Unit 6");
        arrayList.add("RDBMS Question Paper");

        arrayList.add("CM5104");
        arrayList.add("Android");
        arrayList.add("Android Unit 1");
        arrayList.add("Android Unit 2");
        arrayList.add("Android Unit 3");
        arrayList.add("Android Unit 4");
        arrayList.add("Android Unit 5");
        arrayList.add("Android Unit 6");

        arrayList.add("CM5108");
        arrayList.add("Cloud Computing");
        arrayList.add("Cloud Computing Unit 1");
        arrayList.add("Cloud Computing Unit 2");
        arrayList.add("Cloud Computing Unit 3");
        arrayList.add("Cloud Computing Unit 4");
        arrayList.add("Cloud Computing Unit 5");
        arrayList.add("Cloud Computing Unit 6");
        arrayList.add("Cloud Computing Question Paper");

        arrayList.add("CC");
        arrayList.add("CC Unit 1");
        arrayList.add("CC Unit 2");
        arrayList.add("CC Unit 3");
        arrayList.add("CC Unit 4");
        arrayList.add("CC Unit 5");
        arrayList.add("CC Unit 6");
        arrayList.add("CC Question Paper");

        arrayList.add("Multimedia and Animations");
        arrayList.add("Multimedia References");

        arrayList.add("MA");
        arrayList.add("MA References");

        arrayList.add("DCN");
        arrayList.add("DCN Unit 1");
        arrayList.add("DCN Unit 2");
        arrayList.add("DCN Unit 3");
        arrayList.add("DCN Unit 4");
        arrayList.add("DCN Unit 5");
        arrayList.add("DCN Unit 6");
        arrayList.add("DCN Question Paper");

        arrayList.add("Data Communication and Networking");
        arrayList.add("Data Communication and Networking Unit 1");
        arrayList.add("Data Communication and Networking Unit 2");
        arrayList.add("Data Communication and Networking Unit 3");
        arrayList.add("Data Communication and Networking Unit 4");
        arrayList.add("Data Communication and Networking Unit 5");
        arrayList.add("Data Communication and Networking Unit 6");
        arrayList.add("Data Communication and Networking Question Paper");

        arrayList.add("Database Management System");
        arrayList.add("Database Management System Unit 1");
        arrayList.add("Database Management System Unit 2");
        arrayList.add("Database Management System Unit 3");
        arrayList.add("Database Management System Unit 4");
        arrayList.add("Database Management System Unit 5");
        arrayList.add("Database Management System Unit 6");
        arrayList.add("Database Management System Question Paper");

        arrayList.add("DBMS");
        arrayList.add("DBMS Unit 1");
        arrayList.add("DBMS Unit 2");
        arrayList.add("DBMS Unit 3");
        arrayList.add("DBMS Unit 4");
        arrayList.add("DBMS Unit 5");
        arrayList.add("DBMS Unit 6");
        arrayList.add("DBMS Question Paper");

        arrayList.add("Software Engineering");
        arrayList.add("Software Engineering Unit 1");
        arrayList.add("Software Engineering Unit 2");
        arrayList.add("Software Engineering Unit 3");
        arrayList.add("Software Engineering Unit 4");
        arrayList.add("Software Engineering Unit 5");
        arrayList.add("Software Engineering Unit 6");
        arrayList.add("Software Engineering Question Paper");

        arrayList.add("SE");
        arrayList.add("SE Unit 1");
        arrayList.add("SE Unit 2");
        arrayList.add("SE Unit 3");
        arrayList.add("SE Unit 4");
        arrayList.add("SE Unit 5");
        arrayList.add("SE Unit 6");
        arrayList.add("SE Question Paper");

        arrayList.add("CM4107");
        arrayList.add("Software Engineering and Testing");
        arrayList.add("Software Engineering and Testing Unit 1");
        arrayList.add("Software Engineering and Testing Unit 2");
        arrayList.add("Software Engineering and Testing Unit 3");
        arrayList.add("Software Engineering and Testing Unit 4");
        arrayList.add("Software Engineering and Testing Unit 5");
        arrayList.add("Software Engineering and Testing Unit 6");
        arrayList.add("Software Engineering and Testing Question Paper");

        arrayList.add("SET");
        arrayList.add("SET Unit 1");
        arrayList.add("SET Unit 2");
        arrayList.add("SET Unit 3");
        arrayList.add("SET Unit 4");
        arrayList.add("SET Unit 5");
        arrayList.add("SET Unit 6");
        arrayList.add("SET Question Paper");

        arrayList.add("IOT");
        arrayList.add("IOT References");

        arrayList.add("Internet Of Things");
        arrayList.add("Internet Of Things References");

        arrayList.add("NMA");
        arrayList.add("NMA References");

        arrayList.add("Network Management and Administration");
        arrayList.add("Network Management and Administration References");

        arrayList.add("PHP");
        arrayList.add("PHP References");

        arrayList.add("Cloud Technologies");
        arrayList.add("Cloud Technologies Unit 1");
        arrayList.add("Cloud Technologies Unit 2");
        arrayList.add("Cloud Technologies Unit 3");
        arrayList.add("Cloud Technologies Unit 4");
        arrayList.add("Cloud Technologies Unit 5");
        arrayList.add("Cloud Technologies Unit 6");
        arrayList.add("Cloud Technologies Question Paper");

        arrayList.add("CT");
        arrayList.add("CT Unit 1");
        arrayList.add("CT Unit 2");
        arrayList.add("CT Unit 3");
        arrayList.add("CT Unit 4");
        arrayList.add("CT Unit 5");
        arrayList.add("CT Unit 6");
        arrayList.add("CT Question Paper");

        arrayList.add("Graphics and Gaming Technology");
        arrayList.add("Graphics and Gaming Technology Unit 1");
        arrayList.add("Graphics and Gaming Technology Unit 2");
        arrayList.add("Graphics and Gaming Technology Unit 3");
        arrayList.add("Graphics and Gaming Technology Unit 4");
        arrayList.add("Graphics and Gaming Technology Unit 5");
        arrayList.add("Graphics and Gaming Technology Question Paper");

        arrayList.add("GGT");
        arrayList.add("GGT Unit 1");
        arrayList.add("GGT Unit 2");
        arrayList.add("GGT Unit 3");
        arrayList.add("GGT Unit 4");
        arrayList.add("GGT Unit 5");
        arrayList.add("GGT Question Paper");

        arrayList.add("Information Security");
        arrayList.add("Information Security Unit 1");
        arrayList.add("Information Security Unit 2");
        arrayList.add("Information Security Unit 3");
        arrayList.add("Information Security Unit 4");
        arrayList.add("Information Security Unit 5");
        arrayList.add("Information Security Unit 6");
        arrayList.add("Information Security Question Paper");

        arrayList.add("IS");
        arrayList.add("IS Unit 1");
        arrayList.add("IS Unit 2");
        arrayList.add("IS Unit 3");
        arrayList.add("IS Unit 4");
        arrayList.add("IS Unit 5");
        arrayList.add("IS Unit 6");
        arrayList.add("IS Question Paper");

        adapter = new CustomArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        // Set item click listener for the ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            switch (selectedItem) {
                //                    Python
                case "Python":
                    startActivity(new Intent(MainActivity.this, python_cs.class));
                    break;
                case "CM5101":
                    startActivity(new Intent(MainActivity.this, python_cs.class));
                    break;
                case "Python Unit 1":
                    startActivity(new Intent(MainActivity.this, pwp_mcq_main.class));
                    break;
                case "Python Unit 2":
                    startActivity(new Intent(MainActivity.this, pwp_mcq_main.class));
                    break;
                case "Python Unit 3":
                    startActivity(new Intent(MainActivity.this, pwp_mcq_main.class));
                    break;
                case "Python Unit 4":
                    startActivity(new Intent(MainActivity.this, pwp_mcq_main.class));
                    break;
                case "Python Unit 5":
                    startActivity(new Intent(MainActivity.this, pwp_mcq_main.class));
                    break;
                case "Python Unit 6":
                    startActivity(new Intent(MainActivity.this, pwp_mcq_main.class));
                    break;

                //                    Data Structure
                case "Data Structure Curriculum":
                    startActivity(new Intent(MainActivity.this, ds_cs.class));
                    break;
                case "DS Curriculum":
                    startActivity(new Intent(MainActivity.this, ds_cs.class));
                    break;
                case "Data Structure":
                    startActivity(new Intent(MainActivity.this, ds_cs.class));
                    break;
                case "DS":
                    startActivity(new Intent(MainActivity.this, ds_cs.class));
                    break;
                case "CM3103":
                    startActivity(new Intent(MainActivity.this, ds_cs.class));
                    break;
                case "DS Unit 1":
                    startActivity(new Intent(MainActivity.this, ds_u1_main.class));
                    break;
                case "DS Unit 2":
                    startActivity(new Intent(MainActivity.this, ds_u2_main.class));
                    break;
                case "DS Unit 3":
                    startActivity(new Intent(MainActivity.this, ds_u3_main.class));
                    break;
                case "DS Unit 4":
                    startActivity(new Intent(MainActivity.this, ds_u4_main.class));
                    break;
                case "DS Unit 5":
                    startActivity(new Intent(MainActivity.this, ds_u5_main.class));
                    break;
                case "DS Unit 6":
                    startActivity(new Intent(MainActivity.this, ds_u6_main.class));
                    break;
                case "DS Question Paper":
                    startActivity(new Intent(MainActivity.this, ds_que_main.class));
                    break;
                case "Data Structure Unit 1":
                    startActivity(new Intent(MainActivity.this, ds_u1_main.class));
                    break;
                case "Data Structure Unit 2":
                    startActivity(new Intent(MainActivity.this, ds_u2_main.class));
                    break;
                case "Data Structure Unit 3":
                    startActivity(new Intent(MainActivity.this, ds_u3_main.class));
                    break;
                case "Data Structure Unit 4":
                    startActivity(new Intent(MainActivity.this, ds_u4_main.class));
                    break;
                case "Data Structure Unit 5":
                    startActivity(new Intent(MainActivity.this, ds_u5_main.class));
                    break;
                case "Data Structure Unit 6":
                    startActivity(new Intent(MainActivity.this, ds_u6_main.class));
                    break;
                case "Data Structure Question Paper":
                    startActivity(new Intent(MainActivity.this, ds_que_main.class));
                    break;

                //                    Object Oriented Programming

                case "OOP":
                    startActivity(new Intent(MainActivity.this, oop_cs.class));
                    break;
                case "CM3104":
                    startActivity(new Intent(MainActivity.this, oop_cs.class));
                    break;
                case "CPP":
                    startActivity(new Intent(MainActivity.this, oop_cs.class));
                    break;
                case "OOP Unit 1":
                    startActivity(new Intent(MainActivity.this, oop_u1_main.class));
                    break;
                case "OOP Unit 2":
                    startActivity(new Intent(MainActivity.this, oop_u2_main.class));
                    break;
                case "OOP Unit 3":
                    startActivity(new Intent(MainActivity.this, oop_u3_main.class));
                    break;
                case "OOP Unit 4":
                    startActivity(new Intent(MainActivity.this, oop_u4_main.class));
                    break;
                case "OOP Unit 5":
                    startActivity(new Intent(MainActivity.this, oop_u5_main.class));
                    break;
                case "OOP Unit 6":
                    startActivity(new Intent(MainActivity.this, oop_u6_main.class));
                    break;
                case "OOP Question Paper":
                    startActivity(new Intent(MainActivity.this, oop_que_main.class));
                    break;
                case "CCP Unit 1":
                    startActivity(new Intent(MainActivity.this, oop_u1_main.class));
                    break;
                case "CPP Unit 2":
                    startActivity(new Intent(MainActivity.this, oop_u2_main.class));
                    break;
                case "CPP Unit 3":
                    startActivity(new Intent(MainActivity.this, oop_u3_main.class));
                    break;
                case "CPP Unit 4":
                    startActivity(new Intent(MainActivity.this, oop_u4_main.class));
                    break;
                case "CPP Unit 5":
                    startActivity(new Intent(MainActivity.this, oop_u5_main.class));
                    break;
                case "CPP Unit 6":
                    startActivity(new Intent(MainActivity.this, oop_u5_main.class));
                    break;
                case "CPP Quetion Paper":
                    startActivity(new Intent(MainActivity.this, oop_que_main.class));
                    break;


                //                    Computing Essentials

                case "Computing Essentials":
                    startActivity(new Intent(MainActivity.this, ce_cs.class));
                    break;
                case "CM1101":
                    startActivity(new Intent(MainActivity.this, ce_cs.class));
                    break;
                case "CE":
                    startActivity(new Intent(MainActivity.this, ce_cs.class));
                    break;
                case "CE Unit 1":
                    startActivity(new Intent(MainActivity.this, ce_u1_main.class));
                    break;
                case "CE Unit 2":
                    startActivity(new Intent(MainActivity.this, ce_u2_main.class));
                    break;
                case "CE Unit 3":
                    startActivity(new Intent(MainActivity.this, ce_u3_main.class));
                    break;
                case "CE Unit 4":
                    startActivity(new Intent(MainActivity.this, ce_u4_main.class));
                    break;
                case "CE Unit 5":
                    startActivity(new Intent(MainActivity.this, ce_u5_main.class));
                    break;
                case "CE Unit 6":
                    startActivity(new Intent(MainActivity.this, ce_u6_main.class));
                    break;
                case "Computing Essentials Unit 1":
                    startActivity(new Intent(MainActivity.this, ce_u1_main.class));
                    break;
                case "Computing Essentials Unit 2":
                    startActivity(new Intent(MainActivity.this, ce_u2_main.class));
                    break;
                case "Computing Essentials Unit 3":
                    startActivity(new Intent(MainActivity.this, ce_u3_main.class));
                    break;
                case "Computing Essentials Unit 4":
                    startActivity(new Intent(MainActivity.this, ce_u4_main.class));
                    break;
                case "Computing Essentials Unit 5":
                    startActivity(new Intent(MainActivity.this, ce_u5_main.class));
                    break;
                case "Computing Essentials Unit 6":
                    startActivity(new Intent(MainActivity.this, ce_u6_main.class));
                    break;
//                case "Computing Essentials Question Paper":
//                    startActivity(new Intent(MainActivity.this, ce_que_main.class));
//                    break;

                //                    Fundamental of Electronics

                case "Fundamentals of Electronics":
                    startActivity(new Intent(MainActivity.this, foe_cs.class));
                    break;
                case "FOE":
                    startActivity(new Intent(MainActivity.this, foe_cs.class));
                    break;
                case "ET2107":
                    startActivity(new Intent(MainActivity.this, foe_cs.class));
                    break;
                case "FOE Unit 1":
                    startActivity(new Intent(MainActivity.this, foe_u1_main.class));
                    break;
                case "FOE Unit 2":
                    startActivity(new Intent(MainActivity.this, foe_u2_main.class));
                    break;
                case "FOE Unit 3":
                    startActivity(new Intent(MainActivity.this, foe_u3_main.class));
                    break;
                case "FOE Unit 4":
                    startActivity(new Intent(MainActivity.this, foe_u4_main.class));
                    break;
                case "FOE Unit 5":
                    startActivity(new Intent(MainActivity.this, foe_u5_main.class));
                    break;
                case "FOE Quetion Paper":
                    startActivity(new Intent(MainActivity.this, foe_que_main.class));
                    break;
                case "Fundamental of Electronics Unit 1":
                    startActivity(new Intent(MainActivity.this, foe_u1_main.class));
                    break;
                case "Fundamental of Electronics Unit 2":
                    startActivity(new Intent(MainActivity.this, foe_u2_main.class));
                    break;
                case "Fundamental of Electronics Unit 3":
                    startActivity(new Intent(MainActivity.this, foe_u3_main.class));
                    break;
                case "Fundamental of Electronics Unit 4":
                    startActivity(new Intent(MainActivity.this, foe_u4_main.class));
                    break;
                case "Fundamental of Electronics Unit 5":
                    startActivity(new Intent(MainActivity.this, foe_u5_main.class));
                    break;
                case "Fundamental of Electronics Quetion Paper":
                    startActivity(new Intent(MainActivity.this, foe_que_main.class));
                    break;

                //                    M1

                case "M1":
                    startActivity(new Intent(MainActivity.this, m1_cs.class));
                    break;
                case "SC1101":
                    startActivity(new Intent(MainActivity.this, m1_cs.class));
                    break;
                case "M1 Tutorial":
                    startActivity(new Intent(MainActivity.this, m1_tut_main.class));
                    break;

                case "M2":
                    startActivity(new Intent(MainActivity.this, m2_cs.class));
                    break;
                case "SC1102":
                    startActivity(new Intent(MainActivity.this, m2_cs.class));
                    break;
                case "M2 Unit 1":
                    startActivity(new Intent(MainActivity.this, m2_u1_main.class));
                    break;
                case "M2 Unit 2":
                    startActivity(new Intent(MainActivity.this, m2_u2_main.class));
                    break;
                case "M2 Unit 3":
                    startActivity(new Intent(MainActivity.this, m2_u3_main.class));
                    break;
                case "M2 Unit 4":
                    startActivity(new Intent(MainActivity.this, m2_u4_main.class));
                    break;
                case "M2 Unit 5":
                    startActivity(new Intent(MainActivity.this, m2_u5_main.class));
                    break;
                case "M2 Tutorial":
                    startActivity(new Intent(MainActivity.this, m2_tut_main.class));
                    break;
                case "M2 Question Paper":
                    startActivity(new Intent(MainActivity.this, m2_que_main.class));
                    break;

                //                    Advanced Computer Network
                case "Advanced Computer Network":
                    startActivity(new Intent(MainActivity.this, acn_cs.class));
                    break;
                case "ACN":
                    startActivity(new Intent(MainActivity.this, acn_cs.class));
                    break;
                case "CM4108":
                    startActivity(new Intent(MainActivity.this, acn_cs.class));
                    break;
                case "ACN Unit 1":
                    startActivity(new Intent(MainActivity.this, acn_u1_main.class));
                    break;
                case "ACN Unit 2":
                    startActivity(new Intent(MainActivity.this, acn_u2_main.class));
                    break;
                case "ACN Unit 3":
                    startActivity(new Intent(MainActivity.this, acn_u3_main.class));
                    break;
                case "ACN Unit 4":
                    startActivity(new Intent(MainActivity.this, acn_u4_main.class));
                    break;
                case "ACN Unit 5":
                    startActivity(new Intent(MainActivity.this, acn_u5_main.class));
                    break;
                case "ACN Unit 6":
                    startActivity(new Intent(MainActivity.this, acn_u6_main.class));
                    break;
                case "ACN Question Paper":
                    startActivity(new Intent(MainActivity.this, acn_que_main.class));
                    break;
                case "Advanced Computer Network Unit 1":
                    startActivity(new Intent(MainActivity.this, acn_u1_main.class));
                    break;
                case "Advanced Computer Network Unit 2":
                    startActivity(new Intent(MainActivity.this, acn_u2_main.class));
                    break;
                case "Advanced Computer Network Unit 3":
                    startActivity(new Intent(MainActivity.this, acn_u3_main.class));
                    break;
                case "Advanced Computer Network Unit 4":
                    startActivity(new Intent(MainActivity.this, acn_u4_main.class));
                    break;
                case "Advanced Computer Network Unit 5":
                    startActivity(new Intent(MainActivity.this, acn_u5_main.class));
                    break;
                case "Advanced Computer Network Unit 6":
                    startActivity(new Intent(MainActivity.this, acn_u6_main.class));
                    break;
                case "Advanced Computer Network Question Paper":
                    startActivity(new Intent(MainActivity.this, acn_que_main.class));
                    break;

                //                    Cloud Computing

                case "Cloud Computing":
                    startActivity(new Intent(MainActivity.this, cc_cs.class));
                    break;
                case "CC":
                    startActivity(new Intent(MainActivity.this, cc_cs.class));
                    break;
                case "CM5108":
                    startActivity(new Intent(MainActivity.this, cc_cs.class));
                    break;
                case "CC Unit 1":
                    startActivity(new Intent(MainActivity.this, cc_u1_main.class));
                    break;
                case "CC Unit 2":
                    startActivity(new Intent(MainActivity.this, cc_u2_main.class));
                    break;
                case "CC Unit 3":
                    startActivity(new Intent(MainActivity.this, cc_u3_main.class));
                    break;
                case "CC Unit 4":
                    startActivity(new Intent(MainActivity.this, cc_u4_main.class));
                    break;
                case "CC Unit 5":
                    startActivity(new Intent(MainActivity.this, cc_u5_main.class));
                    break;
                case "CC Unit 6":
                    startActivity(new Intent(MainActivity.this, cc_u6_main.class));
                    break;
                case "Cloud Computing Unit 1":
                    startActivity(new Intent(MainActivity.this, cc_u1_main.class));
                    break;
                case "Cloud Computing Unit 2":
                    startActivity(new Intent(MainActivity.this, cc_u2_main.class));
                    break;
                case "Cloud Computing Unit 3":
                    startActivity(new Intent(MainActivity.this, cc_u3_main.class));
                    break;
                case "Cloud Computing Unit 4":
                    startActivity(new Intent(MainActivity.this, cc_u4_main.class));
                    break;
                case "Cloud Computing Unit 5":
                    startActivity(new Intent(MainActivity.this, cc_u5_main.class));
                    break;
                case "Cloud Computing Unit 6":
                    startActivity(new Intent(MainActivity.this, cc_u6_main.class));
                    break;
                case "Cloud Computing Question Paper":
                    startActivity(new Intent(MainActivity.this, cc_que_main.class));
                    break;

                //                    Communication Skills I

                case "CMS I":
                    startActivity(new Intent(MainActivity.this, cms1_cs.class));
                    break;
                case "HU1101":
                    startActivity(new Intent(MainActivity.this, cms1_cs.class));
                    break;
                case "CMS I (English) Unit 1":
                    startActivity(new Intent(MainActivity.this, cms1_u1_main.class));
                    break;
                case "CMS I (English) Unit 2":
                    startActivity(new Intent(MainActivity.this, cms1_u2_main.class));
                    break;
                case "CMS I (English) Unit 3":
                    startActivity(new Intent(MainActivity.this, cms1_u3_main.class));
                    break;
                case "CMS I (English) Unit 4":
                    startActivity(new Intent(MainActivity.this, cms1_u4_main.class));
                    break;
                case "CMS I (English) Unit 5":
                    startActivity(new Intent(MainActivity.this, cms1_u5_main.class));
                    break;
//                case "CMS I (English) Question Paper":
//                    startActivity(new Intent(MainActivity.this, cms1_que_main.class));
//                    break;


                //                    Communication Skills II
                case "CMS II":
                    startActivity(new Intent(MainActivity.this, cms2_cs.class));
                    break;
                case "HU1102":
                    startActivity(new Intent(MainActivity.this, cms2_cs.class));
                    break;
                case "CMS II Unit 4":
                    startActivity(new Intent(MainActivity.this, cms2_u4_main.class));
                    break;
                case "CMS II Question Paper":
                    startActivity(new Intent(MainActivity.this, cms2_que_main.class));
                    break;

                //                    M3
                case "M3":
                    startActivity(new Intent(MainActivity.this, m3_cs.class));
                    break;
                case "SC2102":
                    startActivity(new Intent(MainActivity.this, m3_cs.class));
                    break;
                case "M3 Unit 1":
                    startActivity(new Intent(MainActivity.this, m3_u1_main.class));
                    break;
                case "M3 Unit 2":
                    startActivity(new Intent(MainActivity.this, m3_u2_main.class));
                    break;
                case "M3 Unit 3":
                    startActivity(new Intent(MainActivity.this, m3_u3_main.class));
                    break;
                case "M3 Unit 4":
                    startActivity(new Intent(MainActivity.this, m3_u4_main.class));
                    break;
                case "M3 Unit 5":
                    startActivity(new Intent(MainActivity.this, m3_u5_main.class));
                    break;
                case "M3 Tutorial":
                    startActivity(new Intent(MainActivity.this, m3_tut_main.class));
                    break;
                case "M3 Question Paper":
                    startActivity(new Intent(MainActivity.this, m3_que_main.class));
                    break;

                //                    Physics
                case "Physics":
                    startActivity(new Intent(MainActivity.this, physics_cs.class));
                    break;
                case "SC1104":
                    startActivity(new Intent(MainActivity.this, physics_cs.class));
                    break;
                case "Physics Unit 1":
                    startActivity(new Intent(MainActivity.this, phy_u1_main.class));
                    break;
                case "Physics Unit 2":
                    startActivity(new Intent(MainActivity.this, phy_u2_main.class));
                    break;
                case "Physics Unit 3":
                    startActivity(new Intent(MainActivity.this, phy_u3_main.class));
                    break;
                case "Physics Unit 4":
                    startActivity(new Intent(MainActivity.this, phy_u4_main.class));
                    break;
                case "Physics Unit 5":
                    startActivity(new Intent(MainActivity.this, phy_u5_main.class));
                    break;
                case "Physics Unit 6":
                    startActivity(new Intent(MainActivity.this, phy_u6_main.class));
                    break;

                case "Computer Graphics":
                    startActivity(new Intent(MainActivity.this, cg_cs.class));
                    break;
                case "CG":
                    startActivity(new Intent(MainActivity.this, cg_cs.class));
                    break;
                case "CM3106":
                    startActivity(new Intent(MainActivity.this, cg_cs.class));
                    break;
                case "CG Unit 1":
                    startActivity(new Intent(MainActivity.this, cg_u1_main.class));
                    break;
                case "CG Unit 2":
                    startActivity(new Intent(MainActivity.this, cg_u2_main.class));
                    break;
                case "CG Unit 3":
                    startActivity(new Intent(MainActivity.this, cg_u3_main.class));
                    break;
                case "CG Unit 4":
                    startActivity(new Intent(MainActivity.this, cg_u4_main.class));
                    break;
                case "CG Unit 5":
                    startActivity(new Intent(MainActivity.this, cg_u5_main.class));
                    break;
                case "CG Question Paper":
                    startActivity(new Intent(MainActivity.this, cg_que_main.class));
                    break;
                case "Computer Graphics Unit 1":
                    startActivity(new Intent(MainActivity.this, cg_u1_main.class));
                    break;
                case "Computer Graphics Unit 2":
                    startActivity(new Intent(MainActivity.this, cg_u2_main.class));
                    break;
                case "Computer Graphics Unit 3":
                    startActivity(new Intent(MainActivity.this, cg_u3_main.class));
                    break;
                case "Computer Graphics Unit 4":
                    startActivity(new Intent(MainActivity.this, cg_u4_main.class));
                    break;
                case "Computer Graphics Unit 5":
                    startActivity(new Intent(MainActivity.this, cg_u5_main.class));
                    break;
                case "Computer Graphics Question":
                    startActivity(new Intent(MainActivity.this, cg_que_main.class));
                    break;

                //                    Computer Network
                case "Computer Network":
                    startActivity(new Intent(MainActivity.this, cn_cs.class));
                    break;
                case "CN":
                    startActivity(new Intent(MainActivity.this, cn_cs.class));
                    break;
                case "CM3108":
                    startActivity(new Intent(MainActivity.this, cn_cs.class));
                    break;
                case "CN Unit 1":
                    startActivity(new Intent(MainActivity.this, cn_u1_main.class));
                    break;
                case "CN Unit 2":
                    startActivity(new Intent(MainActivity.this, cn_u2_main.class));
                    break;
                case "CN Unit 3":
                    startActivity(new Intent(MainActivity.this, cn_u3_main.class));
                    break;
                case "CN Unit 4":
                    startActivity(new Intent(MainActivity.this, cn_u4_main.class));
                    break;
                case "CN Unit 5":
                    startActivity(new Intent(MainActivity.this, cn_u5_main.class));
                    break;
                case "CN Question Paper":
                    startActivity(new Intent(MainActivity.this, cn_que_main.class));
                    break;
                case "Computer Network Unit 1":
                    startActivity(new Intent(MainActivity.this, cn_u1_main.class));
                    break;
                case "Computer Network Unit 2":
                    startActivity(new Intent(MainActivity.this, cn_u2_main.class));
                    break;
                case "Computer Network Unit 3":
                    startActivity(new Intent(MainActivity.this, cn_u3_main.class));
                    break;
                case "Computer Network Unit 4":
                    startActivity(new Intent(MainActivity.this, cn_u4_main.class));
                    break;
                case "Computer Network Unit 5":
                    startActivity(new Intent(MainActivity.this, cn_u5_main.class));
                    break;
                case "Computer Network Question Paper":
                    startActivity(new Intent(MainActivity.this, cn_que_main.class));
                    break;

                //                    Computer Peripherals and Hardware Maintenance
                case "CPHM":
                    startActivity(new Intent(MainActivity.this, cphm_cs.class));
                    break;
                case "CPHM Unit 1":
                    startActivity(new Intent(MainActivity.this, cphm_u1_main.class));
                    break;
                case "CPHM Unit 2":
                    startActivity(new Intent(MainActivity.this, cphm_u2_main.class));
                    break;
                case "CPHM Unit 3":
                    startActivity(new Intent(MainActivity.this, cphm_u3_main.class));
                    break;
                case "CPHM Unit 4":
                    startActivity(new Intent(MainActivity.this, cphm_u4_main.class));
                    break;
                case "CPHM Unit 5":
                    startActivity(new Intent(MainActivity.this, cphm_u5_main.class));
                    break;
                case "CPHM Unit 6":
                    startActivity(new Intent(MainActivity.this, cphm_u6_main.class));
                    break;
                case "CPHM Question":
                    startActivity(new Intent(MainActivity.this, cphm_que_main.class));
                    break;

                //                    Computer Security
                case "Computer Security":
                    startActivity(new Intent(MainActivity.this, cs_cs.class));
                    break;
                case "CS":
                    startActivity(new Intent(MainActivity.this, cs_cs.class));
                    break;
                case "CM4110":
                    startActivity(new Intent(MainActivity.this, cs_cs.class));
                    break;
                case "CS Unit 1":
                    startActivity(new Intent(MainActivity.this, cs_u1_main.class));
                    break;
                case "CS Unit 2":
                    startActivity(new Intent(MainActivity.this, cs_u2_main.class));
                    break;
                case "CS Unit 3":
                    startActivity(new Intent(MainActivity.this, cs_u3_main.class));
                    break;
                case "CS Unit 4":
                    startActivity(new Intent(MainActivity.this, cs_u4_main.class));
                    break;
                case "CS Unit 5":
                    startActivity(new Intent(MainActivity.this, cs_u5_main.class));
                    break;
                case "CS Question Paper":
                    startActivity(new Intent(MainActivity.this, cs_que_main.class));
                    break;
                case "Computer Security Unit 1":
                    startActivity(new Intent(MainActivity.this, cs_u1_main.class));
                    break;
                case "Computer Security Unit 2":
                    startActivity(new Intent(MainActivity.this, cs_u2_main.class));
                    break;
                case "Computer Security Unit 3":
                    startActivity(new Intent(MainActivity.this, cs_u3_main.class));
                    break;
                case "Computer Security Unit 4":
                    startActivity(new Intent(MainActivity.this, cs_u4_main.class));
                    break;
                case "Computer Security Unit 5":
                    startActivity(new Intent(MainActivity.this, cs_u5_main.class));
                    break;
                case "Computer Security Question Paper":
                    startActivity(new Intent(MainActivity.this, cs_que_main.class));
                    break;

                //                    Data Mining
                case "Data Mining":
                    startActivity(new Intent(MainActivity.this, dmi_cs.class));
                    break;
                case "DMI":
                    startActivity(new Intent(MainActivity.this, dmi_cs.class));
                    break;
                case "CM5107":
                    startActivity(new Intent(MainActivity.this, dmi_cs.class));
                    break;
                case "DMI Unit 1":
                    startActivity(new Intent(MainActivity.this, dmi_u1_main.class));
                    break;
                case "DMI Unit 2":
                    startActivity(new Intent(MainActivity.this, dmi_u2_main.class));
                    break;
                case "DMI Unit 3":
                    startActivity(new Intent(MainActivity.this, dmi_u3_main.class));
                    break;
                case "DMI Unit 4":
                    startActivity(new Intent(MainActivity.this, dmi_u4_main.class));
                    break;
                case "DMI Unit 5":
                    startActivity(new Intent(MainActivity.this, dmi_u5_main.class));
                    break;
                case "DMI Unit 6":
                    startActivity(new Intent(MainActivity.this, dmi_u6_main.class));
                    break;
                case "DMI Question Paper":
                    startActivity(new Intent(MainActivity.this, dmi_que_main.class));
                    break;
                case "Data Mining Unit 1":
                    startActivity(new Intent(MainActivity.this, dmi_u1_main.class));
                    break;
                case "Data Mining Unit 2":
                    startActivity(new Intent(MainActivity.this, dmi_u2_main.class));
                    break;
                case "Data Mining Unit 3":
                    startActivity(new Intent(MainActivity.this, dmi_u3_main.class));
                    break;
                case "Data Mining Unit 4":
                    startActivity(new Intent(MainActivity.this, dmi_u4_main.class));
                    break;
                case "Data Mining Unit 5":
                    startActivity(new Intent(MainActivity.this, dmi_u5_main.class));
                    break;
                case "Data Mining Unit 6":
                    startActivity(new Intent(MainActivity.this, dmi_u6_main.class));
                    break;
                case "Data Mining Question Paper":
                    startActivity(new Intent(MainActivity.this, dmi_que_main.class));
                    break;

                //                    Digital Techniques and Microprocessor
                case "PDTMP Curriculum":
                    startActivity(new Intent(MainActivity.this, pdtmp_cs.class));
                    break;
                case "DTMP Curriculum":
                    startActivity(new Intent(MainActivity.this, pdtmp_cs.class));
                    break;
                case "PDTMP":
                    startActivity(new Intent(MainActivity.this, pdtmp_cs.class));
                    break;
                case "DTMP":
                    startActivity(new Intent(MainActivity.this, pdtmp_cs.class));
                    break;
                case "CM3105":
                    startActivity(new Intent(MainActivity.this, pdtmp_cs.class));
                    break;
                case "DTMP Unit 1":
                    startActivity(new Intent(MainActivity.this, dtmp_u1_main.class));
                    break;
                case "DTMP Unit 2":
                    startActivity(new Intent(MainActivity.this, dtmp_u2_main.class));
                    break;
                case "DTMP Unit 3":
                    startActivity(new Intent(MainActivity.this, dtmp_u3_main.class));
                    break;
                case "DTMP Unit 4":
                    startActivity(new Intent(MainActivity.this, dtmp_u4_main.class));
                    break;
                case "DTMP Unit 5":
                    startActivity(new Intent(MainActivity.this, dtmp_u5_main.class));
                    break;
                case "DTMP Unit 6":
                    startActivity(new Intent(MainActivity.this, dtmp_u6_main.class));
                    break;
                case "DTMP Question Paper":
                    startActivity(new Intent(MainActivity.this, dtmp_que_main.class));
                    break;
                case "PDTMP Unit 1":
                    startActivity(new Intent(MainActivity.this, dtmp_u1_main.class));
                    break;
                case "PDTMP Unit 2":
                    startActivity(new Intent(MainActivity.this, dtmp_u2_main.class));
                    break;
                case "PDTMP Unit 3":
                    startActivity(new Intent(MainActivity.this, dtmp_u3_main.class));
                    break;
                case "PDTMP Unit 4":
                    startActivity(new Intent(MainActivity.this, dtmp_u4_main.class));
                    break;
                case "PDTMP Unit 5":
                    startActivity(new Intent(MainActivity.this, dtmp_u5_main.class));
                    break;
                case "PDTMP Unit 6":
                    startActivity(new Intent(MainActivity.this, dtmp_u6_main.class));
                    break;
                case "PDTMP Question Paper":
                    startActivity(new Intent(MainActivity.this, dtmp_que_main.class));
                    break;

                //                    EDP
                case "EDP":
                    startActivity(new Intent(MainActivity.this, edp_cs.class));
                    break;
                case "Entreprenuership Development":
                    startActivity(new Intent(MainActivity.this, edp_cs.class));
                    break;
                case "MA4101":
                    startActivity(new Intent(MainActivity.this, edp_cs.class));
                    break;
                case "EDP Unit 1":
                    startActivity(new Intent(MainActivity.this, edp_u1_main.class));
                    break;
                case "EDP Unit 2":
                    startActivity(new Intent(MainActivity.this, edp_u2_main.class));
                    break;
                case "EDP Unit 3":
                    startActivity(new Intent(MainActivity.this, edp_u3_main.class));
                    break;
                case "EDP Unit 4":
                    startActivity(new Intent(MainActivity.this, edp_u4_main.class));
                    break;
                case "Entreprenuership Development Unit 1":
                    startActivity(new Intent(MainActivity.this, edp_u1_main.class));
                    break;
                case "Entreprenuership Development Unit 2":
                    startActivity(new Intent(MainActivity.this, edp_u2_main.class));
                    break;
                case "Entreprenuership Development Unit 3":
                    startActivity(new Intent(MainActivity.this, edp_u3_main.class));
                    break;
                case "Entreprenuership Development Unit 4":
                    startActivity(new Intent(MainActivity.this, edp_u4_main.class));
                    break;

                //                    Electrical Engineering

                case "Electrical Engineering":
                    startActivity(new Intent(MainActivity.this, ee_cs.class));
                    break;
                case "EE":
                    startActivity(new Intent(MainActivity.this, ee_cs.class));
                    break;
                case "EE2107":
                    startActivity(new Intent(MainActivity.this, ee_cs.class));
                    break;
                case "EE Unit 1":
                    startActivity(new Intent(MainActivity.this, ee_u1_main.class));
                    break;
                case "EE Unit 2":
                    startActivity(new Intent(MainActivity.this, ee_u2_main.class));
                    break;
                case "EE Unit 3":
                    startActivity(new Intent(MainActivity.this, ee_u3_main.class));
                    break;
                case "EE Unit 4":
                    startActivity(new Intent(MainActivity.this, ee_u4_main.class));
                    break;
                case "EE Unit 5":
                    startActivity(new Intent(MainActivity.this, ee_u5_main.class));
                    break;
                case "EE Question Paper":
                    startActivity(new Intent(MainActivity.this, ee_que_main.class));
                    break;
                case "Electrical Engineering Unit 1":
                    startActivity(new Intent(MainActivity.this, ee_u1_main.class));
                    break;
                case "Electrical Engineering Unit 2":
                    startActivity(new Intent(MainActivity.this, ee_u2_main.class));
                    break;
                case "Electrical Engineering Unit 3":
                    startActivity(new Intent(MainActivity.this, ee_u3_main.class));
                    break;
                case "Electrical Engineering Unit 4":
                    startActivity(new Intent(MainActivity.this, ee_u4_main.class));
                    break;
                case "Electrical Engineering Unit 5":
                    startActivity(new Intent(MainActivity.this, ee_u5_main.class));
                    break;
                case "Electrical Engineering Question Paper":
                    startActivity(new Intent(MainActivity.this, ee_que_main.class));
                    break;

                //                    Fundamental of ICT

                case "Fundamental of ICT":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "FICT":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "FICT Unit 1":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "FICT Unit 2":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "FICT Unit 3":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "FICT Unit 4":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "FICT Unit 5":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "FICT Practicals":
                    startActivity(new Intent(MainActivity.this, ee_u2_main.class));
                    break;
                case "Fundamental of ICT Unit 1":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "Fundamental of ICT Unit 2":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "Fundamental of ICT Unit 3":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "Fundamental of ICT Unit 4":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "Fundamental of ICT Unit 5":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;
                case "Fundamental of ICT Practicals":
                    startActivity(new Intent(MainActivity.this, ict_cs.class));
                    break;

                //                    HTML
                case "HTML":
                    startActivity(new Intent(MainActivity.this, html_cs.class));
                    break;
                case "CM2104":
                    startActivity(new Intent(MainActivity.this, html_cs.class));
                    break;
                case "HTML Unit 1":
                    startActivity(new Intent(MainActivity.this, html_u1_main.class));
                    break;
                case "HTML Unit 2":
                    startActivity(new Intent(MainActivity.this, html_u2_main.class));
                    break;
                case "HTML Unit 3":
                    startActivity(new Intent(MainActivity.this, html_u3_main.class));
                    break;
                case "HTML Unit 4":
                    startActivity(new Intent(MainActivity.this, html_u4_main.class));
                    break;
                case "HTML Unit 5":
                    startActivity(new Intent(MainActivity.this, html_u5_main.class));
                    break;


//                    Linux

                case "Linux Commands":
                    startActivity(new Intent(MainActivity.this, linux_cmd_main.class));
                    break;
                case "Linux":
                    startActivity(new Intent(MainActivity.this, linux_cs.class));
                    break;
                case "CM2103":
                    startActivity(new Intent(MainActivity.this, linux_cs.class));
                    break;

                case "Operating System":
                    startActivity(new Intent(MainActivity.this, os_cs.class));
                    break;
                case "OS":
                    startActivity(new Intent(MainActivity.this, os_cs.class));
                    break;
                case "CM3101":
                    startActivity(new Intent(MainActivity.this, os_cs.class));
                    break;
                case "OS Unit 1":
                    startActivity(new Intent(MainActivity.this, os_u1_main.class));
                    break;
                case "OS Unit 2":
                    startActivity(new Intent(MainActivity.this, os_u2_main.class));
                    break;
                case "OS Unit 3":
                    startActivity(new Intent(MainActivity.this, os_u3_main.class));
                    break;
                case "OS Unit 4":
                    startActivity(new Intent(MainActivity.this, os_u4_main.class));
                    break;
                case "OS Unit 5":
                    startActivity(new Intent(MainActivity.this, os_u5_main.class));
                    break;
                case "OS Unit 6":
                    startActivity(new Intent(MainActivity.this, os_u6_main.class));
                    break;
                case "OS Quetion Paper":
                    startActivity(new Intent(MainActivity.this, os_que_main.class));
                    break;
                case "OS Lab Manual":
                    startActivity(new Intent(MainActivity.this, os_que_main.class));
                    break;
                case "Operating System Unit 1":
                    startActivity(new Intent(MainActivity.this, os_u1_main.class));
                    break;
                case "Operating System Unit 2":
                    startActivity(new Intent(MainActivity.this, os_u2_main.class));
                    break;
                case "Operating System Unit 3":
                    startActivity(new Intent(MainActivity.this, os_u3_main.class));
                    break;
                case "Operating System Unit 4":
                    startActivity(new Intent(MainActivity.this, os_u4_main.class));
                    break;
                case "Operating System Unit 5":
                    startActivity(new Intent(MainActivity.this, os_u5_main.class));
                    break;
                case "Operating System Unit 6":
                    startActivity(new Intent(MainActivity.this, os_u6_main.class));
                    break;
                case "Operating System Quetion Paper":
                    startActivity(new Intent(MainActivity.this, os_que_main.class));
                    break;


                case "Information Management":
                    startActivity(new Intent(MainActivity.this, im_cs.class));
                    break;
                case "IM":
                    startActivity(new Intent(MainActivity.this, im_cs.class));
                    break;
                case "MA4106":
                    startActivity(new Intent(MainActivity.this, im_cs.class));
                    break;
                case "IM Unit 1":
                    startActivity(new Intent(MainActivity.this, im_u1_main.class));
                    break;
                case "IM Unit 2":
                    startActivity(new Intent(MainActivity.this, im_u2_main.class));
                    break;
                case "IM Unit 3":
                    startActivity(new Intent(MainActivity.this, im_u3_main.class));
                    break;
                case "IM Unit 4":
                    startActivity(new Intent(MainActivity.this, im_u4_main.class));
                    break;
                case "IM Unit 5":
                    startActivity(new Intent(MainActivity.this, im_u5_main.class));
                    break;
                case "Information Management Unit 1":
                    startActivity(new Intent(MainActivity.this, im_u1_main.class));
                    break;
                case "Information Management Unit 2":
                    startActivity(new Intent(MainActivity.this, im_u2_main.class));
                    break;
                case "Information Management Unit 3":
                    startActivity(new Intent(MainActivity.this, im_u3_main.class));
                    break;
                case "Information Management Unit 4":
                    startActivity(new Intent(MainActivity.this, im_u4_main.class));
                    break;
                case "Information Management Unit 5":
                    startActivity(new Intent(MainActivity.this, im_u5_main.class));
                    break;


                case "Software Engineering and Testing":
                    startActivity(new Intent(MainActivity.this, set_cs.class));
                    break;
                case "SET":
                    startActivity(new Intent(MainActivity.this, set_cs.class));
                    break;
                case "CM4107":
                    startActivity(new Intent(MainActivity.this, set_cs.class));
                    break;
                case "SET Unit 1":
                    startActivity(new Intent(MainActivity.this, set_u1_main.class));
                    break;
                case "SET Unit 2":
                    startActivity(new Intent(MainActivity.this, set_u2_main.class));
                    break;
                case "SET Unit 3":
                    startActivity(new Intent(MainActivity.this, set_u3_main.class));
                    break;
                case "SET Unit 4":
                    startActivity(new Intent(MainActivity.this, set_u4_main.class));
                    break;
                case "SET Unit 5":
                    startActivity(new Intent(MainActivity.this, set_u5_main.class));
                    break;
                case "SET Unit 6":
                    startActivity(new Intent(MainActivity.this, set_u6_main.class));
                    break;
                case "SET Quetion Paper":
                    startActivity(new Intent(MainActivity.this, set_que_main.class));
                    break;
                case "Software Engineering and Testing Unit 1":
                    startActivity(new Intent(MainActivity.this, set_u1_main.class));
                    break;
                case "Software Engineering and Testing Unit 2":
                    startActivity(new Intent(MainActivity.this, set_u2_main.class));
                    break;
                case "Software Engineering and Testing Unit 3":
                    startActivity(new Intent(MainActivity.this, set_u3_main.class));
                    break;
                case "Software Engineering and Testing Unit 4":
                    startActivity(new Intent(MainActivity.this, set_u4_main.class));
                    break;
                case "Software Engineering and Testing Unit 5":
                    startActivity(new Intent(MainActivity.this, set_u5_main.class));
                    break;
                case "Software Engineering and Testing Unit 6":
                    startActivity(new Intent(MainActivity.this, set_u6_main.class));
                    break;
                case "Software Engineering and Testing Quetion Paper":
                    startActivity(new Intent(MainActivity.this, set_que_main.class));
                    break;

//                    Software Engineering

                case "Software Engineering":
                    startActivity(new Intent(MainActivity.this, se_it.class));
                    break;
                case "SE":
                    startActivity(new Intent(MainActivity.this, se_it.class));
                    break;
                case "Software Engineering Unit 1":
                    startActivity(new Intent(MainActivity.this, set_u1_main.class));
                    break;
                case "Software Engineering Unit 2":
                    startActivity(new Intent(MainActivity.this, set_u2_main.class));
                    break;
                case "Software Engineering Unit 3":
                    startActivity(new Intent(MainActivity.this, set_u3_main.class));
                    break;
                case "Software Engineering Quetion Paper":
                    startActivity(new Intent(MainActivity.this, set_que_main.class));
                    break;
                case "SE Unit 1":
                    startActivity(new Intent(MainActivity.this, set_u1_main.class));
                    break;
                case "SE Unit 2":
                    startActivity(new Intent(MainActivity.this, set_u2_main.class));
                    break;
                case "SE Unit 3":
                    startActivity(new Intent(MainActivity.this, set_u3_main.class));
                    break;
                case "SE Quetion Paper":
                    startActivity(new Intent(MainActivity.this, set_que_main.class));
                    break;

                case "Java I":
                    startActivity(new Intent(MainActivity.this, jp1_cs.class));
                    break;
                case "JP I":
                    startActivity(new Intent(MainActivity.this, jp1_cs.class));
                    break;
                case "CM3102":
                    startActivity(new Intent(MainActivity.this, jp1_cs.class));
                    break;
                case "Java I Unit 1":
                    startActivity(new Intent(MainActivity.this, jp1_u1_main.class));
                    break;
                case "Java I Unit 2":
                    startActivity(new Intent(MainActivity.this, jp1_u2_main.class));
                    break;
                case "Java I Unit 3":
                    startActivity(new Intent(MainActivity.this, jp1_u3_main.class));
                    break;
                case "Java I Unit 4":
                    startActivity(new Intent(MainActivity.this, jp1_u4_main.class));
                    break;
                case "Java I Unit 5":
                    startActivity(new Intent(MainActivity.this, jp1_u5_main.class));
                    break;
                case "Java I Unit 6":
                    startActivity(new Intent(MainActivity.this, jp1_u6_main.class));
                    break;
                case "Java I Quetion Paper":
                    startActivity(new Intent(MainActivity.this, jp1_que_main.class));
                    break;

                case "Advanced Java":
                    startActivity(new Intent(MainActivity.this, jp2_cs.class));
                    break;
                case "JP II":
                    startActivity(new Intent(MainActivity.this, jp2_cs.class));
                    break;
                case "CM4109":
                    startActivity(new Intent(MainActivity.this, jp2_cs.class));
                    break;
                case "Java II Unit 1":
                    startActivity(new Intent(MainActivity.this, jp2_u1_main.class));
                    break;
                case "Java II Unit 2":
                    startActivity(new Intent(MainActivity.this, jp2_u2_main.class));
                    break;
                case "Java II Unit 3":
                    startActivity(new Intent(MainActivity.this, jp2_u3_main.class));
                    break;
                case "Java II Unit 4":
                    startActivity(new Intent(MainActivity.this, jp2_u4_main.class));
                    break;
                case "Java II Unit 5":
                    startActivity(new Intent(MainActivity.this, jp2_u5_main.class));
                    break;
                case "Java II Unit 6":
                    startActivity(new Intent(MainActivity.this, jp2_u6_main.class));
                    break;
                case "Java II Quetion Paper":
                    startActivity(new Intent(MainActivity.this, jp2_que_main.class));
                    break;
                case "Advanced Java Unit 1":
                    startActivity(new Intent(MainActivity.this, jp2_u1_main.class));
                    break;
                case "Advanced Java Unit 2":
                    startActivity(new Intent(MainActivity.this, jp2_u2_main.class));
                    break;
                case "Advanced Java Unit 3":
                    startActivity(new Intent(MainActivity.this, jp2_u3_main.class));
                    break;
                case "Advanced Java Unit 4":
                    startActivity(new Intent(MainActivity.this, jp2_u4_main.class));
                    break;
                case "Advanced Java Unit 5":
                    startActivity(new Intent(MainActivity.this, jp2_u5_main.class));
                    break;
                case "Advanced Java Unit 6":
                    startActivity(new Intent(MainActivity.this, jp2_u6_main.class));
                    break;
                case "Advanced Java Quetion Paper":
                    startActivity(new Intent(MainActivity.this, jp2_que_main.class));
                    break;

//                    Software Testing

                case "Software Testing":
                    startActivity(new Intent(MainActivity.this, st_it.class));
                    break;
                case "ST":
                    startActivity(new Intent(MainActivity.this, st_it.class));
                    break;
                case "Software Testing Unit 1":
                    startActivity(new Intent(MainActivity.this, set_u4_main.class));
                    break;
                case "Software Testing Unit 2":
                    startActivity(new Intent(MainActivity.this, set_u5_main.class));
                    break;
                case "Software Testing Unit 3":
                    startActivity(new Intent(MainActivity.this, set_u6_main.class));
                    break;
                case "Software Testing Quetion Paper":
                    startActivity(new Intent(MainActivity.this, set_que_main.class));
                    break;
                case "ST Unit 1":
                    startActivity(new Intent(MainActivity.this, set_u4_main.class));
                    break;
                case "ST Unit 2":
                    startActivity(new Intent(MainActivity.this, set_u5_main.class));
                    break;
                case "ST Unit 3":
                    startActivity(new Intent(MainActivity.this, set_u6_main.class));
                    break;
                case "ST Quetion Paper":
                    startActivity(new Intent(MainActivity.this, set_que_main.class));
                    break;

                case "RDBMS":
                    startActivity(new Intent(MainActivity.this, rdbms_cs.class));
                    break;
                case "CM4111":
                    startActivity(new Intent(MainActivity.this, rdbms_cs.class));
                    break;
                case "RDBMS Unit 1":
                    startActivity(new Intent(MainActivity.this, rdbms_u1_main.class));
                    break;
                case "RDBMS Unit 2":
                    startActivity(new Intent(MainActivity.this, rdbms_u2_main.class));
                    break;
                case "RDBMS Unit 3":
                    startActivity(new Intent(MainActivity.this, rdbms_u3_main.class));
                    break;
                case "RDBMS Unit 4":
                    startActivity(new Intent(MainActivity.this, rdbms_u4_main.class));
                    break;
                case "RDBMS Unit 5":
                    startActivity(new Intent(MainActivity.this, rdbms_u5_main.class));
                    break;
                case "RDBMS Unit 6":
                    startActivity(new Intent(MainActivity.this, rdbms_u6_main.class));
                    break;
                case "RDBMS Quetion Paper":
                    startActivity(new Intent(MainActivity.this, rdbms_que_main.class));
                    break;

                case "Android":
                    startActivity(new Intent(MainActivity.this, aap_cs.class));
                    break;
                case "CM5104":
                    startActivity(new Intent(MainActivity.this, aap_cs.class));
                    break;
                case "Android Unit 1":
                    startActivity(new Intent(MainActivity.this, aap_mcq_main.class));
                    break;
                case "Android Unit 2":
                    startActivity(new Intent(MainActivity.this, aap_mcq_main.class));
                    break;
                case "Android Unit 3":
                    startActivity(new Intent(MainActivity.this, aap_mcq_main.class));
                    break;
                case "Android Unit 4":
                    startActivity(new Intent(MainActivity.this, aap_mcq_main.class));
                    break;
                case "Android Unit 5":
                    startActivity(new Intent(MainActivity.this, aap_mcq_main.class));
                    break;
                case "Android Unit 6":
                    startActivity(new Intent(MainActivity.this, aap_mcq_main.class));
                    break;

                case "Multimedia":
                    startActivity(new Intent(MainActivity.this, ma_it.class));
                    break;
                case "Multimedia and Animations":
                    startActivity(new Intent(MainActivity.this, ma_it.class));
                    break;
                case "MA":
                    startActivity(new Intent(MainActivity.this, ma_it.class));
                    break;

                case "Data Communication and Networking":
                    startActivity(new Intent(MainActivity.this, dcn_it.class));
                    break;
                case "DCN":
                    startActivity(new Intent(MainActivity.this, dcn_it.class));
                    break;
                case "DCN Unit 1":
                    startActivity(new Intent(MainActivity.this, cn_u1_main.class));
                    break;
                case "DCN Unit 2":
                    startActivity(new Intent(MainActivity.this, cn_u2_main.class));
                    break;
                case "DCN Unit 3":
                    startActivity(new Intent(MainActivity.this, cn_u3_main.class));
                    break;
                case "DCN Unit 4":
                    startActivity(new Intent(MainActivity.this, cn_u4_main.class));
                    break;
                case "DCN Unit 5":
                    startActivity(new Intent(MainActivity.this, cn_u5_main.class));
                    break;
                case "DCN Quetion Paper":
                    startActivity(new Intent(MainActivity.this, cn_que_main.class));
                    break;
                case "Data Communication and Networking Unit 1":
                    startActivity(new Intent(MainActivity.this, cn_u1_main.class));
                    break;
                case "Data Communication and Networking Unit 2":
                    startActivity(new Intent(MainActivity.this, cn_u2_main.class));
                    break;
                case "Data Communication and Networking Unit 3":
                    startActivity(new Intent(MainActivity.this, cn_u3_main.class));
                    break;
                case "Data Communication and Networking Unit 4":
                    startActivity(new Intent(MainActivity.this, cn_u4_main.class));
                    break;
                case "Data Communication and Networking Unit 5":
                    startActivity(new Intent(MainActivity.this, cn_u5_main.class));
                    break;
                case "Data Communication and Networking Quetion Paper":
                    startActivity(new Intent(MainActivity.this, cn_que_main.class));
                    break;

                case "Database Management System":
                    startActivity(new Intent(MainActivity.this, dbms_it.class));
                    break;
                case "DBMS":
                    startActivity(new Intent(MainActivity.this, dbms_it.class));
                    break;
                case "DBMS Unit 1":
                    startActivity(new Intent(MainActivity.this, rdbms_u1_main.class));
                    break;
                case "DBMS Unit 2":
                    startActivity(new Intent(MainActivity.this, rdbms_u2_main.class));
                    break;
                case "DBMS Unit 3":
                    startActivity(new Intent(MainActivity.this, rdbms_u3_main.class));
                    break;
                case "DBMS Unit 4":
                    startActivity(new Intent(MainActivity.this, rdbms_u4_main.class));
                    break;
                case "DBMS Unit 5":
                    startActivity(new Intent(MainActivity.this, rdbms_u5_main.class));
                    break;
                case "DBMS Unit 6":
                    startActivity(new Intent(MainActivity.this, rdbms_u6_main.class));
                    break;
                case "DBMS Quetion Paper":
                    startActivity(new Intent(MainActivity.this, rdbms_que_main.class));
                    break;
                case "Database Management System Unit 1":
                    startActivity(new Intent(MainActivity.this, rdbms_u1_main.class));
                    break;
                case "Database Management System Unit 2":
                    startActivity(new Intent(MainActivity.this, rdbms_u2_main.class));
                    break;
                case "Database Management System Unit 3":
                    startActivity(new Intent(MainActivity.this, rdbms_u3_main.class));
                    break;
                case "Database Management System Unit 4":
                    startActivity(new Intent(MainActivity.this, rdbms_u4_main.class));
                    break;
                case "Database Management System Unit 5":
                    startActivity(new Intent(MainActivity.this, rdbms_u5_main.class));
                    break;
                case "Database Management System Unit 6":
                    startActivity(new Intent(MainActivity.this, rdbms_u6_main.class));
                    break;
                case "Database Management System Quetion Paper":
                    startActivity(new Intent(MainActivity.this, rdbms_que_main.class));
                    break;
                case "Internet of Things":
                    startActivity(new Intent(MainActivity.this, iot_it.class));
                    break;
                case "IOT":
                    startActivity(new Intent(MainActivity.this, iot_it.class));
                    break;
                case "MAD MCQ's":
                    startActivity(new Intent(MainActivity.this, aap_mcq_main.class));
                    break;
                case "Network Management and Administration":
                    startActivity(new Intent(MainActivity.this, nma_it.class));
                    break;
                case "NMA":
                    startActivity(new Intent(MainActivity.this, nma_it.class));
                    break;
                case "PHP":
                    startActivity(new Intent(MainActivity.this, php_it.class));
                    break;
                case "Cloud Technologies":
                    startActivity(new Intent(MainActivity.this, ct_it.class));
                    break;
                case "CT":
                    startActivity(new Intent(MainActivity.this, ct_it.class));
                    break;
                case "CT Unit 1":
                    startActivity(new Intent(MainActivity.this, cc_u1_main.class));
                    break;
                case "CT Unit 2":
                    startActivity(new Intent(MainActivity.this, cc_u2_main.class));
                    break;
                case "CT Unit 3":
                    startActivity(new Intent(MainActivity.this, cc_u3_main.class));
                    break;
                case "CT Unit 4":
                    startActivity(new Intent(MainActivity.this, cc_u4_main.class));
                    break;
                case "CT Unit 5":
                    startActivity(new Intent(MainActivity.this, cc_u5_main.class));
                    break;
                case "CT Unit 6":
                    startActivity(new Intent(MainActivity.this, cc_u6_main.class));
                    break;
                case "CT Quetion Paper":
                    startActivity(new Intent(MainActivity.this, cc_que_main.class));
                    break;
                case "Cloud Technologies Unit 1":
                    startActivity(new Intent(MainActivity.this, cc_u1_main.class));
                    break;
                case "Cloud Technologies Unit 2":
                    startActivity(new Intent(MainActivity.this, cc_u2_main.class));
                    break;
                case "Cloud Technologies Unit 3":
                    startActivity(new Intent(MainActivity.this, cc_u3_main.class));
                    break;
                case "Cloud Technologies Unit 4":
                    startActivity(new Intent(MainActivity.this, cc_u4_main.class));
                    break;
                case "Cloud Technologies Unit 5":
                    startActivity(new Intent(MainActivity.this, cc_u5_main.class));
                    break;
                case "Cloud Technologies Unit 6":
                    startActivity(new Intent(MainActivity.this, cc_u6_main.class));
                    break;
                case "Cloud Technologies Quetion Paper":
                    startActivity(new Intent(MainActivity.this, cc_que_main.class));
                    break;
                case "Graphics and Gaming Technology":
                    startActivity(new Intent(MainActivity.this, ggt_it.class));
                    break;
                case "GGT":
                    startActivity(new Intent(MainActivity.this, ggt_it.class));
                    break;
                case "GGT Unit 1":
                    startActivity(new Intent(MainActivity.this, cg_u1_main.class));
                    break;
                case "GGT Unit 2":
                    startActivity(new Intent(MainActivity.this, cg_u2_main.class));
                    break;
                case "GGT Unit 3":
                    startActivity(new Intent(MainActivity.this, cg_u3_main.class));
                    break;
                case "GGT Unit 4":
                    startActivity(new Intent(MainActivity.this, cg_u4_main.class));
                    break;
                case "GGT Unit 5":
                    startActivity(new Intent(MainActivity.this, cg_u5_main.class));
                    break;
                case "GGT Quetion Paper":
                    startActivity(new Intent(MainActivity.this, cg_que_main.class));
                    break;
                case "Graphics and Gaming Technology Unit 1":
                    startActivity(new Intent(MainActivity.this, cg_u1_main.class));
                    break;
                case "Graphics and Gaming Technology Unit 2":
                    startActivity(new Intent(MainActivity.this, cg_u2_main.class));
                    break;
                case "Graphics and Gaming Technology Unit 3":
                    startActivity(new Intent(MainActivity.this, cg_u3_main.class));
                    break;
                case "Graphics and Gaming Technology Unit 4":
                    startActivity(new Intent(MainActivity.this, cg_u4_main.class));
                    break;
                case "Graphics and Gaming Technology Unit 5":
                    startActivity(new Intent(MainActivity.this, cg_u5_main.class));
                    break;
                case "Graphics and Gaming Technology Quetion Paper":
                    startActivity(new Intent(MainActivity.this, cg_que_main.class));
                    break;
//                    Information Security
                case "Information Security":
                    startActivity(new Intent(MainActivity.this, is_it.class));
                    break;
                case "IS":
                    startActivity(new Intent(MainActivity.this, is_it.class));
                    break;
                case "IS Unit 1":
                    startActivity(new Intent(MainActivity.this, cs_u1_main.class));
                    break;
                case "IS Unit 2":
                    startActivity(new Intent(MainActivity.this, cs_u2_main.class));
                    break;
                case "IS Unit 3":
                    startActivity(new Intent(MainActivity.this, cs_u3_main.class));
                    break;
                case "IS Unit 4":
                    startActivity(new Intent(MainActivity.this, cs_u4_main.class));
                    break;
                case "IS Unit 5":
                    startActivity(new Intent(MainActivity.this, cs_u5_main.class));
                    break;
                case "IS Quetion Paper":
                    startActivity(new Intent(MainActivity.this, cs_que_main.class));
                    break;
                case "Information Security Unit 1":
                    startActivity(new Intent(MainActivity.this, cs_u1_main.class));
                    break;
                case "Information Security Unit 2":
                    startActivity(new Intent(MainActivity.this, cs_u2_main.class));
                    break;
                case "Information Security Unit 3":
                    startActivity(new Intent(MainActivity.this, cs_u3_main.class));
                    break;
                case "Information Security Unit 4":
                    startActivity(new Intent(MainActivity.this, cs_u4_main.class));
                    break;
                case "Information Security Unit 5":
                    startActivity(new Intent(MainActivity.this, cs_u5_main.class));
                    break;
//                case "Information Security Unit 6":
//                    startActivity(new Intent(MainActivity.this, cs_u6_main.class));
//                    break;
                case "Information Security Quetion Paper":
                    startActivity(new Intent(MainActivity.this, cs_que_main.class));
                    break;
                case "EVS":
                    startActivity(new Intent(MainActivity.this, ee_cs.class));
                    break;

                default:
                    // Handle default case if needed
                    break;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listView.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(s)) {
                    // If the search query is empty, display the complete list
                    adapter.clear(); // Clear the adapter
                    adapter.addAll(arrayList); // Add the complete list
                    adapter.notifyDataSetChanged(); // Notify adapter changes
                } else {
                    // If there's a search query, apply the filter
                    adapter.getFilter().filter(s);
                }
                return true;
            }
        });

        computerLevelsCard = findViewById(R.id.comp1);
        computerLevelsCard.setOnClickListener(v -> {
            showInterstitialAd();
            startActivity(new Intent(MainActivity.this, ComputerLevels.class));
        });

        semestersCard = findViewById(R.id.itdep);
        semestersCard.setOnClickListener(v -> {
            showInterstitialAd();
            startActivity(new Intent(MainActivity.this, ItLevels.class));
        });

        labmanualCard = findViewById(R.id.lab1);
        labmanualCard.setOnClickListener(v -> {
            showInterstitialAd();
            startActivity(new Intent(MainActivity.this, lab_manuals_main.class));
        });

        mpCard = findViewById(R.id.mp);
        mpCard.setOnClickListener(v -> {
            showInterstitialAd();
            startActivity(new Intent(MainActivity.this, mp_main.class));
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            // No user is logged in, redirect to login activity
            startActivity(new Intent(MainActivity.this, login.class));
            finish();
        } else {
            // User is logged in, retrieve user details from SharedPreferences
            textViewHello = findViewById(R.id.textViewHello);
            prefs = getSharedPreferences("UserData", MODE_PRIVATE);
            String name = prefs.getString("UserName", "User");

            if (name.equals("User")) {
                // If no user data in SharedPreferences, fetch from database
                fetchUserData(currentUser);
            } else {
                // Display user name from SharedPreferences
                textViewHello.setText("Welcome, " + name);
            }
        }
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,
                "ca-app-pub-8830492032016236/3616577654",
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    private void showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    loadInterstitialAd();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    loadInterstitialAd();
                }
            });
        } else {
            loadInterstitialAd();
        }
    }

    private void fetchUserData(FirebaseUser user) {
        String userEmail = user.getEmail();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        textViewHello.setText("Welcome, " + name);
                        // Save user details in SharedPreferences
                        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("UserName", name);
                        editor.apply();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User details not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {

        super.onResume();
        slideHandler.postDelayed(slideRunnable, 3000);

    }

}

