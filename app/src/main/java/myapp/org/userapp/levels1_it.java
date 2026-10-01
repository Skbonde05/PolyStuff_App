package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class levels1_it extends AppCompatActivity {

    private CardView cms1_it, cms2_it, m1_it, m2_it, physics_it;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";
    private final String BANNER_AD_ID = "ca-app-pub-8830492032016236/7956628985";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level1_it);

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // Load banner ad (null-safe)
        AdView adView = findViewById(R.id.adView);
        if (adView != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }

        // Load interstitial ad
        loadInterstitialAd();

        // -------- Back button (null-safe, ImageButton OR Toolbar fallback) --------
        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> showInterstitialAd(() -> {
                startActivity(new Intent(levels1_it.this, ItLevels.class));
                finish();
            }));
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(v -> showInterstitialAd(() -> {
                    startActivity(new Intent(levels1_it.this, ItLevels.class));
                    finish();
                }));
            }
        }

        // Initialize card views and set click listeners
        initializeCardViews();
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, INTERSTITIAL_AD_ID, adRequest,
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
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    loadInterstitialAd();
                    afterAdClosed.run();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    afterAdClosed.run();
                    loadInterstitialAd();
                }
            });
            mInterstitialAd.show(this);
        } else {
            afterAdClosed.run();
            loadInterstitialAd();
        }
    }

    private void initializeCardViews() {
        cms1_it    = findViewById(R.id.cms1_it);
        cms2_it    = findViewById(R.id.cms2_it);
        m1_it      = findViewById(R.id.m1_it);
        m2_it      = findViewById(R.id.m2_it);
        physics_it = findViewById(R.id.physics_it);

        if (cms1_it != null) {
            cms1_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels1_it.this, "cms1_it")));
        }

        if (cms2_it != null) {
            cms2_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels1_it.this, "cms2_it")));
        }

        if (m1_it != null) {
            m1_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels1_it.this, "m1_it")));
        }

        if (m2_it != null) {
            m2_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels1_it.this, "m2_it")));
        }

        if (physics_it != null) {
            physics_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels1_it.this, "physics_it")));
        }
    }
}