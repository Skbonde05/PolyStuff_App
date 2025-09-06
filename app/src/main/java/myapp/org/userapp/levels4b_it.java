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

public class levels4b_it extends AppCompatActivity {

    private CardView se_it, st_it, jp2_it, iot_it, nma_it;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";
    private final String BANNER_AD_ID = "ca-app-pub-8830492032016236/7956628985";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level4b_it);

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
                startActivity(new Intent(levels4b_it.this, ItLevels.class));
                finish();
            });
        });

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
        se_it = findViewById(R.id.se_it);
        st_it = findViewById(R.id.st_it);
        jp2_it = findViewById(R.id.jp2_it);
        iot_it = findViewById(R.id.iot_it);
        nma_it = findViewById(R.id.nma_it);

        // Set click listeners with interstitial ads
        se_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels4b_it.this, se_it.class))));

        st_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels4b_it.this, st_it.class))));

        jp2_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels4b_it.this, jp2_it.class))));

        iot_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels4b_it.this, iot_it.class))));

        nma_it.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels4b_it.this, nma_it.class))));
    }

    @Override
    protected void onDestroy() {
        // Clean up resources
        if (mInterstitialAd != null) {
            mInterstitialAd = null;
        }
        super.onDestroy();
    }
}