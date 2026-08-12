package myapp.org.userapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import myapp.org.userapp.auth.SessionManager;
import myapp.org.userapp.model.UserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    ArrayList<String> searchSuggestions;
    ArrayAdapter<String> adapter;
    private ViewPager2 viewPager2;
    private Handler slideHandler;
    private AdView adView;
    private InterstitialAd mInterstitialAd;
    private long lastAdShownTime = 0;
    private static final long AD_COOLDOWN_MS = 60000;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        int themeMode = prefs.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(themeMode);
        setContentView(binding.getRoot());

        MobileAds.initialize(this, initializationStatus -> {});
        loadInterstitialAd();
        slideHandler = new Handler(Looper.getMainLooper());

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

        loadSearchSuggestions();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            if (!SearchManager.getInstance(MainActivity.this).navigateTo(selectedItem)) {
                Toast.makeText(MainActivity.this, "No content found for: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s) && !SearchManager.getInstance(MainActivity.this).navigateTo(s)) {
                    Toast.makeText(MainActivity.this, "No content found for: " + s, Toast.LENGTH_SHORT).show();
                }
                searchView.clearFocus();
                listView.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listView.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(s)) {
                    adapter.clear();
                    adapter.addAll(searchSuggestions);
                    adapter.notifyDataSetChanged();
                } else {
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
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            textViewHello = findViewById(R.id.textViewHello);
            if (prefs == null) {
                prefs = getSharedPreferences("UserData", MODE_PRIVATE);
            }
            String name = prefs.getString("UserName", "User");
            if (name.equals("User")) {
                fetchUserData(currentUser);
            } else {
                textViewHello.setText("Welcome, " + name);
            }
            
            SessionManager sessionManager = new SessionManager(this);
            UserProfile session = sessionManager.getSession();
            if (session != null && session.isAdmin()) {
                android.util.Log.d("MainActivity", "Admin user detected");
            }
        }
    }

    private void loadSearchSuggestions() {
        searchSuggestions = new ArrayList<>();
        try {
            String json = loadJsonFromAssets("subjects_data.json");
            JSONArray subjects = new JSONArray(json);
            for (int i = 0; i < subjects.length(); i++) {
                JSONObject subject = subjects.getJSONObject(i);
                JSONArray pdfs = subject.getJSONArray("pdfs");
                for (int j = 0; j < pdfs.length(); j++) {
                    String title = pdfs.getJSONObject(j).getString("title").replace("\\n", "").trim();
                    if (!searchSuggestions.contains(title)) {
                        searchSuggestions.add(title);
                    }
                }
            }
        } catch (Exception e) {
            searchSuggestions.add("Python");
            searchSuggestions.add("Data Structure");
            searchSuggestions.add("OOP");
            searchSuggestions.add("Operating System");
            searchSuggestions.add("Computer Network");
        }

        adapter = new CustomArrayAdapter(this, android.R.layout.simple_list_item_1, searchSuggestions);
        listView.setAdapter(adapter);
    }

    private String loadJsonFromAssets(String filename) throws IOException {
        InputStream is = getAssets().open(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        is.close();
        return sb.toString();
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,
                getString(R.string.admob_interstitial_ad_unit),
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
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAdShownTime < AD_COOLDOWN_MS) {
            return;
        }
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);
            lastAdShownTime = currentTime;
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
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String username = dataSnapshot.child("username").getValue(String.class);
                        Boolean isAdmin = dataSnapshot.child("isAdmin").getValue(Boolean.class);
                        String role = dataSnapshot.child("role").getValue(String.class);
                        boolean adminStatus = isAdmin != null && isAdmin || "admin".equalsIgnoreCase(role);

                        textViewHello.setText("Welcome, " + name);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("UserName", name);
                        editor.apply();

                        SessionManager sessionManager = new SessionManager(MainActivity.this);
                        UserProfile userProfile = new UserProfile();
                        userProfile.setId(dataSnapshot.getKey());
                        userProfile.setName(name);
                        userProfile.setEmail(email);
                        userProfile.setUsername(username);
                        userProfile.setAdmin(adminStatus);
                        sessionManager.saveSession(userProfile);
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
        if (slideHandler != null) {
            slideHandler.removeCallbacks(slideRunnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (slideHandler != null) {
            slideHandler.postDelayed(slideRunnable, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (slideHandler != null) {
            slideHandler.removeCallbacksAndMessages(null);
        }
    }
}
