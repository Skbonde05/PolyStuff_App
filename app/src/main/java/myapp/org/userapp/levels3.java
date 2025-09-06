package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class levels3 extends AppCompatActivity {

    private CardView ds_cs, oop_cs, jp1_cs, os_cs, cphm_cs, cg_cs, cn_cs, pdtmp_cs;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";
    private final String BANNER_AD_ID = "ca-app-pub-8830492032016236/7956628985";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level3);

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // Load banner ad
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Load interstitial ad
        loadInterstitialAd();

        // Back button with interstitial ad
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            showInterstitialAd(() -> {
                startActivity(new Intent(levels3.this, ComputerLevels.class));
                finish();
            });
        });

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
                    loadInterstitialAd(); // Load next ad
                    afterAdClosed.run(); // Execute the intended action
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    afterAdClosed.run(); // Proceed if ad fails to show
                    loadInterstitialAd(); // Try loading another ad
                }
            });
            mInterstitialAd.show(this);
        } else {
            afterAdClosed.run(); // If no ad loaded, proceed immediately
            loadInterstitialAd(); // Load ad for next time
        }
    }

    private void initializeCardViews() {
        ds_cs = findViewById(R.id.ds_cs);
        oop_cs = findViewById(R.id.oop_cs);
        jp1_cs = findViewById(R.id.jp1_cs);
        os_cs = findViewById(R.id.os_cs);
        cphm_cs = findViewById(R.id.cphm_cs);
        cg_cs = findViewById(R.id.cg_cs);
        cn_cs = findViewById(R.id.cn_cs);
        pdtmp_cs = findViewById(R.id.pdtmp_cs);

        // Set click listeners with interstitial ads
        ds_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3.this, ds_cs.class))));

        oop_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3.this, oop_cs.class))));

        jp1_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3.this, jp1_cs.class))));

        os_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3.this, os_cs.class))));

        cphm_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3.this, cphm_cs.class))));

        cg_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3.this, cg_cs.class))));

        cn_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3.this, cn_cs.class))));

        pdtmp_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3.this, pdtmp_cs.class))));
    }
}