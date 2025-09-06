package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class VideosFragment extends Fragment {
    private InterstitialAd mInterstitialAd;
    private final String AD_UNIT_ID = "ca-app-pub-8830492032016236/3616577654";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videos, container, false);

        // Initialize Mobile Ads SDK
        MobileAds.initialize(requireContext(), initializationStatus -> {});
        loadInterstitialAd();

        ImageButton backButton = rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
        });

        // Initialize all CardViews and set click listeners
        setupCardClickListeners(rootView);

        return rootView;
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(requireContext(), AD_UNIT_ID, adRequest,
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
                    // Load the next interstitial ad
                    loadInterstitialAd();
                    // Execute the intended action after ad is closed
                    afterAdClosed.run();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    // If ad fails to show, proceed with the action anyway
                    afterAdClosed.run();
                    // Try loading another ad for next time
                    loadInterstitialAd();
                }
            });
            mInterstitialAd.show(requireActivity());
        } else {
            // If no ad is loaded, proceed with the action immediately
            afterAdClosed.run();
            // Try loading an ad for next time
            loadInterstitialAd();
        }
    }

    private void setupCardClickListeners(View rootView) {
        CardView dsCardView = rootView.findViewById(R.id.ds);
        CardView cppCardView = rootView.findViewById(R.id.cpp);
        CardView osCardView = rootView.findViewById(R.id.os);
        CardView acnCardView = rootView.findViewById(R.id.acn);
        CardView ccCardView = rootView.findViewById(R.id.cc);
        CardView aapCardView = rootView.findViewById(R.id.aap);
        CardView pwpCardView = rootView.findViewById(R.id.pwp);
        CardView dmiCardView = rootView.findViewById(R.id.dmi);
        CardView jp2CardView = rootView.findViewById(R.id.jpII);

        // Set click listeners with interstitial ads
        dsCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), ds.class))));

        cppCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), cpp.class))));

        osCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), os.class))));

        acnCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), acn.class))));

        ccCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), cc.class))));

        aapCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), aap.class))));

        pwpCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), pwp.class))));

        dmiCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), dmi.class))));

        jp2CardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), jp2.class))));
    }
}