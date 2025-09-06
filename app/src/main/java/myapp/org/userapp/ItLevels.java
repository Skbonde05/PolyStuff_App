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

public class ItLevels extends AppCompatActivity {
    private CardView level1CardView1, level2CardView2, level3CardView3,
            level4CardView4, level4bCardView4, level5CardView5;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";
    private final String BANNER_AD_ID = "ca-app-pub-8830492032016236/7956628985";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it_levels);

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
                startActivity(new Intent(ItLevels.this, MainActivity.class));
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
        level1CardView1 = findViewById(R.id.level1CardView1);
        level2CardView2 = findViewById(R.id.level2CardView2);
        level3CardView3 = findViewById(R.id.level3CardView3);
        level4CardView4 = findViewById(R.id.level4CardView4);
        level4bCardView4 = findViewById(R.id.level4bCardView4);
        level5CardView5 = findViewById(R.id.level5CardView5);

        level1CardView1.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ItLevels.this, levels1_it.class))));

        level2CardView2.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ItLevels.this, levels2_it.class))));

        level3CardView3.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ItLevels.this, levels3_it.class))));

        level4CardView4.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ItLevels.this, levels4_it.class))));

        level4bCardView4.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ItLevels.this, levels4b_it.class))));

        level5CardView5.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ItLevels.this, levels5_it.class))));
    }
}