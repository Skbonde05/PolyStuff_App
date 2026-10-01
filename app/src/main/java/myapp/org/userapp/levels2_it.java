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

public class levels2_it extends AppCompatActivity {

    private CardView foe_it, linux_it, html_it, ee_it, ict_it, m3_it;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";
    private final String BANNER_AD_ID = "ca-app-pub-8830492032016236/7956628985";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level2_it);

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
                startActivity(new Intent(levels2_it.this, ItLevels.class));
                finish();
            }));
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(v -> showInterstitialAd(() -> {
                    startActivity(new Intent(levels2_it.this, ItLevels.class));
                    finish();
                }));
            }
        }

        // Initialize card views
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
        foe_it   = findViewById(R.id.foe_it);
        linux_it = findViewById(R.id.linux_it);
        html_it  = findViewById(R.id.html_it);
        ee_it    = findViewById(R.id.ee_it);
        ict_it   = findViewById(R.id.ict_it);
        m3_it    = findViewById(R.id.m3_it);

        if (foe_it != null) {
            foe_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels2_it.this, "foe_it")));
        }

        if (linux_it != null) {
            linux_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels2_it.this, "linux_it")));
        }

        if (html_it != null) {
            html_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels2_it.this, "html_it")));
        }

        if (ee_it != null) {
            ee_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels2_it.this, "ee_it")));
        }

        if (ict_it != null) {
            ict_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels2_it.this, "ict_it")));
        }

        if (m3_it != null) {
            m3_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels2_it.this, "m3_it")));
        }
    }
}