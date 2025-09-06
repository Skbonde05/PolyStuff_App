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
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class OnlineCoursesFragment extends Fragment {
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "OnlineCoursesFragment";
    private final String AD_UNIT_ID = "ca-app-pub-8830492032016236/3616577654";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_online_courses, container, false);


        loadInterstitialAd();


        ImageButton backButton = rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
        });

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
                    loadInterstitialAd(); // Load a new ad for next time
                    afterAdClosed.run(); // Execute the intended action
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    afterAdClosed.run(); // If ad fails to show, proceed anyway
                }
            });
            mInterstitialAd.show(requireActivity());
        } else {
            afterAdClosed.run(); // If no ad loaded, proceed immediately
            loadInterstitialAd(); // Try loading for next time
        }
    }

    private void setupCardClickListeners(View rootView) {
        CardView cppCardView = rootView.findViewById(R.id.cpp);
        CardView cCardView = rootView.findViewById(R.id.c);
        CardView dsCardView = rootView.findViewById(R.id.ds);
        CardView javaCardView = rootView.findViewById(R.id.java);
        CardView iotCardView = rootView.findViewById(R.id.iot);
        CardView phpCardView = rootView.findViewById(R.id.php);
        CardView pythonCardView = rootView.findViewById(R.id.python);

        cppCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), cpp_information.class))));

        javaCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), java_information.class))));

        iotCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), iot_information.class))));

        phpCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), php_information.class))));

        pythonCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), python_information.class))));

        cCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), c_information.class))));

        dsCardView.setOnClickListener(v -> showInterstitialAd(() ->
                startActivity(new Intent(getActivity(), ds_course_info.class))));
    }
}