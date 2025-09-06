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

public class levels2 extends AppCompatActivity {
    private CardView pic_cs, foe_cs, linux_cs, html_cs, ee_cs, ict_cs, m3_cs;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";
    private final String BANNER_AD_ID = "ca-app-pub-8830492032016236/7956628985";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level2);

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // Load banner ad
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Load interstitial ad
        loadInterstitialAd();

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            showInterstitialAd(() -> {
                startActivity(new Intent(levels2.this, ComputerLevels.class));
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
        foe_cs = findViewById(R.id.foe_cs);
        linux_cs = findViewById(R.id.linux_cs);
        html_cs = findViewById(R.id.html_cs);
        ee_cs = findViewById(R.id.ee_cs);
        ict_cs = findViewById(R.id.fict_cs);
        m3_cs = findViewById(R.id.m3_cs);

        foe_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels2.this, foe_cs.class))));

        linux_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels2.this, linux_cs.class))));

        html_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels2.this, html_cs.class))));

        ee_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels2.this, ee_cs.class))));

        ict_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels2.this, ict_cs.class))));

        m3_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels2.this, m3_cs.class))));
    }
}