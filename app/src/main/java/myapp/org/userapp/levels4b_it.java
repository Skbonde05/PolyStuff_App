package myapp.org.userapp;

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
                startActivity(new Intent(levels4b_it.this, ItLevels.class));
                finish();
            }));
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(v -> showInterstitialAd(() -> {
                    startActivity(new Intent(levels4b_it.this, ItLevels.class));
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
        se_it  = findViewById(R.id.se_it);
        st_it  = findViewById(R.id.st_it);
        jp2_it = findViewById(R.id.jp2_it);
        iot_it = findViewById(R.id.iot_it);
        nma_it = findViewById(R.id.nma_it);

        if (se_it != null) {
            se_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels4b_it.this, "se_it")));
        }

        if (st_it != null) {
            st_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels4b_it.this, "st_it")));
        }

        if (jp2_it != null) {
            jp2_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels4b_it.this, "jp2_it")));
        }

        if (iot_it != null) {
            iot_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels4b_it.this, "iot_it")));
        }

        if (nma_it != null) {
            nma_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels4b_it.this, "nma_it")));
        }
    }

    @Override
    protected void onDestroy() {
        if (mInterstitialAd != null) {
            mInterstitialAd = null;
        }
        super.onDestroy();
    }
}