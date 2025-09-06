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

public class levels5 extends AppCompatActivity {

    private CardView python_cs, aap_cs, dmi_cs, cc_cs;
    private InterstitialAd mInterstitialAd;
    private final String INTERSTITIAL_AD_ID = "ca-app-pub-8830492032016236/3616577654";
    private final String BANNER_AD_ID = "ca-app-pub-8830492032016236/7956628985";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level5);

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
                startActivity(new Intent(levels5.this, ComputerLevels.class));
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
        python_cs = findViewById(R.id.python_cs);
        aap_cs = findViewById(R.id.aap_cs);
        dmi_cs = findViewById(R.id.dmi_cs);
        cc_cs = findViewById(R.id.cc_cs);

        // Set click listeners with interstitial ads
        python_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels5.this, python_cs.class))));

        aap_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels5.this, aap_cs.class))));

        dmi_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels5.this, dmi_cs.class))));

        cc_cs.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(levels5.this, cc_cs.class))));
    }
}