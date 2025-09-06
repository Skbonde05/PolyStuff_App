package myapp.org.userapp;

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

public class levels3_it extends AppCompatActivity {

    private CardView ds_it, oop_it, jp1_it, os_it, ma_it, dcn_it, dbms_it, pdtmp_it;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";
    private final String BANNER_AD_ID = "ca-app-pub-8830492032016236/7956628985";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level3_it);

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
                startActivity(new Intent(levels3_it.this, ItLevels.class));
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
        ds_it = findViewById(R.id.ds_it);
        oop_it = findViewById(R.id.oop_it);
        jp1_it = findViewById(R.id.jp1_it);
        os_it = findViewById(R.id.os_it);
        ma_it = findViewById(R.id.ma_it);
        dcn_it = findViewById(R.id.dcn_it);
        dbms_it = findViewById(R.id.dbms_it);
        pdtmp_it = findViewById(R.id.pdtmp_it);

        // Set click listeners with interstitial ads
        ds_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3_it.this, ds_it.class))));

        oop_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3_it.this, oop_it.class))));

        jp1_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3_it.this, jp1_it.class))));

        os_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3_it.this, os_it.class))));

        ma_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3_it.this, ma_it.class))));

        dcn_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3_it.this, dcn_it.class))));

        dbms_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3_it.this, dbms_it.class))));

        pdtmp_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels3_it.this, pdtmp_it.class))));
    }
}