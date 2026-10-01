package myapp.org.userapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import com.bumptech.glide.Glide;
import myapp.org.userapp.auth.SessionManager;
import myapp.org.userapp.model.UserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // Views
    private View headerContainer;
    private View mainScroll;
    private FrameLayout fragmentContainer;
    private BottomNavigationView bottomNavigation;
    private SearchView searchView;
    private ListView listView;
    private TextView textViewHello;
    private ViewPager2 viewPager2;

    // Cards
    CardView computerLevelsCard;
    CardView semestersCard;
    CardView labmanualCard;
    CardView mpCard;

    // State
    ArrayList<String> searchSuggestions;
    ArrayAdapter<String> adapter;
    private Handler slideHandler;
    private AdView adView;
    private InterstitialAd mInterstitialAd;
    private long lastAdShownTime = 0;
    private static final long AD_COOLDOWN_MS = 60000;
    private SharedPreferences prefs;
    private static final String KEY_ACTIVE_TAB = "active_tab_id";
    private int currentTabId = R.id.home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edge-to-edge + manual inset handling
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        if (savedInstanceState != null) {
            currentTabId = savedInstanceState.getInt(KEY_ACTIVE_TAB, R.id.home);
        }

        setContentView(R.layout.activity_main);   // <-- confirm your layout is named activity_main.xml

        prefs = getSharedPreferences("Settings", MODE_PRIVATE);

        // ---- Find views ----
        headerContainer   = findViewById(R.id.headerContainer);
        mainScroll        = findViewById(R.id.mainScroll);
        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigation  = findViewById(R.id.bottomNavigation);
        searchView        = findViewById(R.id.searchView);
        listView          = findViewById(R.id.listView);
        textViewHello     = findViewById(R.id.textViewHello);
        viewPager2        = findViewById(R.id.viewPager);
        adView            = findViewById(R.id.adView);

        // ---- Insets ----
        if (headerContainer != null) {
            ViewCompat.setOnApplyWindowInsetsListener(headerContainer, (view, windowInsets) -> {
                Insets statusBars = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
                view.setPadding(view.getPaddingLeft(), statusBars.top,
                        view.getPaddingRight(), view.getPaddingBottom());
                return windowInsets;
            });
        }
        if (bottomNavigation != null) {
            ViewCompat.setOnApplyWindowInsetsListener(bottomNavigation, (view, windowInsets) -> {
                Insets navBars = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars());
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                        view.getPaddingRight(), navBars.bottom);
                return windowInsets;
            });
        }

        // ---- Ads ----
        MobileAds.initialize(this, initializationStatus -> {});
        loadInterstitialAd();
        slideHandler = new Handler(Looper.getMainLooper());
        if (adView != null) {
            adView.loadAd(new AdRequest.Builder().build());
        }

        // ---- ViewPager2 slider ----
        if (viewPager2 != null) {
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
            if (viewPager2.getChildAt(0) != null) {
                viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
            }

            CompositePageTransformer compositionTransform = new CompositePageTransformer();
            compositionTransform.addTransformer(new MarginPageTransformer(40));
            compositionTransform.addTransformer((page, position) -> {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
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
        }

        // ---- Back handling ----
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (fragmentContainer != null && fragmentContainer.getVisibility() == View.VISIBLE) {
                    currentTabId = R.id.home;
                    if (bottomNavigation != null) bottomNavigation.setSelectedItemId(R.id.home);
                } else if (listView != null && listView.getVisibility() == View.VISIBLE) {
                    listView.setVisibility(View.GONE);
                    if (searchView != null) searchView.clearFocus();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

        // ---- Bottom navigation ----
        if (bottomNavigation != null) {
            bottomNavigation.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                currentTabId = id;
                if (id == R.id.home) {
                    showHomeView();
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

            if (currentTabId != R.id.home) {
                bottomNavigation.setSelectedItemId(currentTabId);
                if (headerContainer != null) headerContainer.setVisibility(View.GONE);
                if (mainScroll != null) mainScroll.setVisibility(View.GONE);
                if (fragmentContainer != null) fragmentContainer.setVisibility(View.VISIBLE);
                Fragment existingFragment = getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
                if (existingFragment == null) {
                    if (currentTabId == R.id.video) {
                        loadFragment(new VideosFragment());
                    } else if (currentTabId == R.id.profile) {
                        loadFragment(new ProfileFragment());
                    } else if (currentTabId == R.id.online) {
                        loadFragment(new OnlineCoursesFragment());
                    }
                }
            } else {
                showHomeView();
            }
        }

        // ---- Search ----
        if (listView != null) listView.setVisibility(View.GONE);
        if (searchView != null) searchView.clearFocus();

        loadSearchSuggestions();

        if (listView != null) {
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedItem = (String) parent.getItemAtPosition(position);
                handleSearchSelection(selectedItem);
                listView.setVisibility(View.GONE);
                if (searchView != null) searchView.clearFocus();
            });
        }

        if (searchView != null) {
            searchView.setOnCloseListener(() -> {
                if (listView != null) listView.setVisibility(View.GONE);
                return false;
            });

            searchView.setOnSearchClickListener(v -> {
                if (listView != null) listView.setVisibility(View.VISIBLE);
                if (adapter != null) adapter.getFilter().filter("");
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (!TextUtils.isEmpty(s)) {
                        handleSearchSelection(s.trim());
                    }
                    searchView.clearFocus();
                    if (listView != null) listView.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (listView == null) return true;
                    if (TextUtils.isEmpty(s) || s.trim().isEmpty()) {
                        listView.setVisibility(View.GONE);
                    } else {
                        listView.setVisibility(View.VISIBLE);
                        if (adapter != null) adapter.getFilter().filter(s);
                    }
                    return true;
                }
            });
        }

        // ---- Home cards ----
        computerLevelsCard = findViewById(R.id.computerLevelsCard);
        if (computerLevelsCard != null) {
            computerLevelsCard.setOnClickListener(v -> showInterstitialAd(() ->
                    startActivity(new Intent(MainActivity.this, ComputerLevels.class))));
        }

        semestersCard = findViewById(R.id.semestersCard);
        if (semestersCard != null) {
            semestersCard.setOnClickListener(v -> showInterstitialAd(() ->
                    startActivity(new Intent(MainActivity.this, ItLevels.class))));
        }

        labmanualCard = findViewById(R.id.labmanualCard);
        if (labmanualCard != null) {
            labmanualCard.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectContentActivity.launch(MainActivity.this, "lab_manuals_main")));
        }

        mpCard = findViewById(R.id.mpCard);
        if (mpCard != null) {
            mpCard.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectContentActivity.launch(MainActivity.this, "mp_main")));
        }

        // ---- Firebase user ----
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            CircleImageView profileHeaderAvatar = findViewById(R.id.profileHeaderAvatar);
            SessionManager sessionManager = new SessionManager(this);
            UserProfile session = sessionManager.getSession();

            if (session != null && session.getName() != null && !session.getName().isEmpty()) {
                if (textViewHello != null) {
                    textViewHello.setText("Welcome, " + session.getName());
                }
                if (profileHeaderAvatar != null && session.getImageUrl() != null
                        && !session.getImageUrl().isEmpty()) {
                    Glide.with(this).load(session.getImageUrl()).into(profileHeaderAvatar);
                }
            } else {
                fetchUserData(currentUser);
            }
        }
    }

    // ---------------------------------------------------------------
    // SEARCH HANDLING
    // ---------------------------------------------------------------

    private void handleSearchSelection(String query) {
        if (query == null || query.trim().isEmpty()) return;

        // Try SearchManager first (handles PDF/subject entries + course keywords)
        if (SearchManager.getInstance(this).navigateTo(query)) {
            return;
        }

        // Fallback: direct course keyword shortcuts
        String q = query.trim().toLowerCase(Locale.ROOT);

        if (q.contains("python") || q.contains("pwp")) {
            showInterstitialAd(() -> startActivity(new Intent(this, pwp.class)));
        } else if (q.contains("data structure") || q.contains("dsa") || q.equals("ds")) {
            showInterstitialAd(() -> startActivity(new Intent(this, ds.class)));
        } else if (q.contains("oop") || q.contains("object oriented")
                || q.contains("c++") || q.contains("cpp")) {
            showInterstitialAd(() -> startActivity(new Intent(this, cpp.class)));
        } else if (q.contains("operating system") || q.equals("os")) {
            showInterstitialAd(() -> startActivity(new Intent(this, os.class)));
        } else if (q.contains("computer network") || q.contains("network") || q.contains("acn")) {
            showInterstitialAd(() -> startActivity(new Intent(this, acn.class)));
        } else if (q.contains("cloud") || q.equals("cc")) {
            showInterstitialAd(() -> startActivity(new Intent(this, cc.class)));
        } else if (q.contains("android") || q.equals("aap")) {
            showInterstitialAd(() -> startActivity(new Intent(this, aap.class)));
        } else if (q.contains("java")) {
            showInterstitialAd(() -> startActivity(new Intent(this, jp2.class)));
        } else if (q.contains("data mining") || q.equals("dmi")) {
            showInterstitialAd(() -> startActivity(new Intent(this, dmi.class)));
        } else if (q.contains("php") || q.contains("sql")) {
            showInterstitialAd(() -> startActivity(new Intent(this, php_information.class)));
        } else if (q.contains("iot") || q.contains("internet of things")) {
            showInterstitialAd(() -> startActivity(new Intent(this, iot_information.class)));
        } else {
            Toast.makeText(this, "No content found for: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSearchSuggestions() {
        searchSuggestions = new ArrayList<>();
        searchSuggestions.add("Python");
        searchSuggestions.add("Data Structure");
        searchSuggestions.add("OOP");
        searchSuggestions.add("Operating System");
        searchSuggestions.add("Computer Network");
        searchSuggestions.add("Cloud Computing");
        searchSuggestions.add("Android App Development");
        searchSuggestions.add("Java Programming");
        searchSuggestions.add("Data Mining");
        searchSuggestions.add("PHP and SQL");
        searchSuggestions.add("Internet of Things");

        adapter = new CustomArrayAdapter(this,
                android.R.layout.simple_list_item_1, searchSuggestions);
        if (listView != null) listView.setAdapter(adapter);

        Executors.newSingleThreadExecutor().execute(() -> {
            Set<String> suggestionsSet = new LinkedHashSet<>(searchSuggestions);
            try {
                String json = AssetUtils.loadJsonFromAssets(MainActivity.this,
                        "subjects_data.json");
                if (json != null) {
                    JSONArray subjects = new JSONArray(json);
                    for (int k = 0; k < subjects.length(); k++) {
                        JSONObject subject = subjects.getJSONObject(k);
                        if (subject.has("pdfs")) {
                            JSONArray pdfs = subject.getJSONArray("pdfs");
                            for (int j = 0; j < pdfs.length(); j++) {
                                String title = pdfs.getJSONObject(j)
                                        .optString("title", "")
                                        .replace("\\n", "")
                                        .trim();
                                if (!title.isEmpty()) {
                                    suggestionsSet.add(title);
                                }
                            }
                        }
                    }
                }
            } catch (Exception ignored) {}

            ArrayList<String> updatedList = new ArrayList<>(suggestionsSet);
            runOnUiThread(() -> {
                if (!isFinishing() && !isDestroyed()) {
                    searchSuggestions = updatedList;
                    adapter = new CustomArrayAdapter(MainActivity.this,
                            android.R.layout.simple_list_item_1, searchSuggestions);
                    if (listView != null) listView.setAdapter(adapter);
                }
            });
        });
    }

    // ---------------------------------------------------------------
    // ADS
    // ---------------------------------------------------------------

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

    private void showInterstitialAd(Runnable afterAdClosed) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAdShownTime < AD_COOLDOWN_MS) {
            afterAdClosed.run();
            return;
        }
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    loadInterstitialAd();
                    afterAdClosed.run();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(
                        com.google.android.gms.ads.AdError adError) {
                    afterAdClosed.run();
                    loadInterstitialAd();
                }
            });
            mInterstitialAd.show(MainActivity.this);
            lastAdShownTime = currentTime;
        } else {
            afterAdClosed.run();
            loadInterstitialAd();
        }
    }

    // ---------------------------------------------------------------
    // FIREBASE USER DATA
    // ---------------------------------------------------------------

    private void fetchUserData(FirebaseUser user) {
        String userEmail = user.getEmail();
        DatabaseReference reference =
                FirebaseDatabase.getInstance().getReference("users");

        reference.orderByChild("email").equalTo(userEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String name = dataSnapshot.child("name").getValue(String.class);
                                String email = dataSnapshot.child("email").getValue(String.class);
                                String username = dataSnapshot.child("username").getValue(String.class);
                                String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                                Boolean isAdmin = dataSnapshot.child("isAdmin").getValue(Boolean.class);
                                String role = dataSnapshot.child("role").getValue(String.class);
                                boolean adminStatus = (isAdmin != null && isAdmin)
                                        || "admin".equalsIgnoreCase(role);

                                if (textViewHello != null) {
                                    textViewHello.setText("Welcome, " + name);
                                }
                                CircleImageView avatar = findViewById(R.id.profileHeaderAvatar);
                                if (avatar != null && imageUrl != null && !imageUrl.isEmpty()) {
                                    Glide.with(MainActivity.this).load(imageUrl).into(avatar);
                                }

                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("UserName", name);
                                editor.putString("ProfileImageURL", imageUrl);
                                editor.apply();

                                SessionManager sessionManager = new SessionManager(MainActivity.this);
                                UserProfile userProfile = new UserProfile();
                                userProfile.setId(dataSnapshot.getKey());
                                userProfile.setName(name);
                                userProfile.setEmail(email);
                                userProfile.setUsername(username);
                                userProfile.setImageUrl(imageUrl);
                                userProfile.setAdmin(adminStatus);
                                sessionManager.saveSession(userProfile);
                            }
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "User details not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this,
                                "Database error: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ---------------------------------------------------------------
    // NAVIGATION HELPERS
    // ---------------------------------------------------------------

    private void showHomeView() {
        currentTabId = R.id.home;
        if (headerContainer != null) headerContainer.setVisibility(View.VISIBLE);
        if (mainScroll != null) mainScroll.setVisibility(View.VISIBLE);
        if (fragmentContainer != null) fragmentContainer.setVisibility(View.GONE);
    }

    private void loadFragment(Fragment fragment) {
        if (headerContainer != null) headerContainer.setVisibility(View.GONE);
        if (mainScroll != null) mainScroll.setVisibility(View.GONE);
        if (fragmentContainer != null) fragmentContainer.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    // ---------------------------------------------------------------
    // LIFECYCLE
    // ---------------------------------------------------------------

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2 != null) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (slideHandler != null) slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (slideHandler != null) slideHandler.postDelayed(slideRunnable, 3000);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_ACTIVE_TAB, currentTabId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (slideHandler != null) slideHandler.removeCallbacksAndMessages(null);
    }
}