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

public class ComputerLevels extends AppCompatActivity {
    private CardView level1CardView, level2CardView, level3CardView,
            level4CardView, level5CardView, level4bCardView;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_levels2);

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
                startActivity(new Intent(ComputerLevels.this, MainActivity.class));
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
        level1CardView = findViewById(R.id.level1CardView1);
        level2CardView = findViewById(R.id.level2CardView2);
        level3CardView = findViewById(R.id.level3CardView3);
        level4CardView = findViewById(R.id.level4CardView4);
        level5CardView = findViewById(R.id.level4bCardView4);
        level4bCardView = findViewById(R.id.level5CardView5);

        level1CardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ComputerLevels.this, Semesters.class))));

        level2CardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ComputerLevels.this, levels2.class))));

        level3CardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ComputerLevels.this, levels3.class))));

        level4CardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ComputerLevels.this, levels4.class))));

        level5CardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ComputerLevels.this, levels4b.class))));

        level4bCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(ComputerLevels.this, levels5.class))));
    }
}