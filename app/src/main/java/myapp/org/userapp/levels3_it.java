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
                startActivity(new Intent(levels3_it.this, ItLevels.class));
                finish();
            }));
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(v -> showInterstitialAd(() -> {
                    startActivity(new Intent(levels3_it.this, ItLevels.class));
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
        ds_it    = findViewById(R.id.ds_it);
        oop_it   = findViewById(R.id.oop_it);
        jp1_it   = findViewById(R.id.jp1_it);
        os_it    = findViewById(R.id.os_it);
        ma_it    = findViewById(R.id.ma_it);
        dcn_it   = findViewById(R.id.dcn_it);
        dbms_it  = findViewById(R.id.dbms_it);
        pdtmp_it = findViewById(R.id.pdtmp_it);

        if (ds_it != null) {
            ds_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels3_it.this, "ds_it")));
        }

        if (oop_it != null) {
            oop_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels3_it.this, "oop_it")));
        }

        if (jp1_it != null) {
            jp1_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels3_it.this, "jp1_it")));
        }

        if (os_it != null) {
            os_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels3_it.this, "os_it")));
        }

        if (ma_it != null) {
            ma_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels3_it.this, "ma_it")));
        }

        if (dcn_it != null) {
            dcn_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels3_it.this, "dcn_it")));
        }

        if (dbms_it != null) {
            dbms_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels3_it.this, "dbms_it")));
        }

        if (pdtmp_it != null) {
            pdtmp_it.setOnClickListener(v -> showInterstitialAd(() ->
                    SubjectListActivity.launch(levels3_it.this, "pdtmp_it")));
        }
    }
}