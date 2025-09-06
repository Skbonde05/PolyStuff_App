package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageButton backButton = view.findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
        });

        SharedPreferences prefs = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userName = prefs.getString("UserName", "");
        String profileImageURL = prefs.getString("ProfileImageURL", "");

        TextView userNameTextView = view.findViewById(R.id.textView10);
        userNameTextView.setText(userName);

        ImageView profileImageView = view.findViewById(R.id.addDeveloperImage);
        if (profileImageURL != null && !profileImageURL.isEmpty()) {
            Glide.with(requireContext()).load(profileImageURL).into(profileImageView);
        } else {
            Glide.with(requireContext()).load(R.drawable.avatar).into(profileImageView);
        }

        ImageView editProfileIcon = view.findViewById(R.id.editProfileIcon);
        editProfileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), edit_profile.class);
            startActivity(intent);
        });

        TextView feedbackButton = view.findViewById(R.id.feedbackButton);
        feedbackButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), feedback.class);
            startActivity(intent);
        });

        TextView rateUsButton = view.findViewById(R.id.rateus);
        rateUsButton.setOnClickListener(v -> {
            try {
                // Open directly in the Play Store app
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + requireActivity().getPackageName())));
            } catch (android.content.ActivityNotFoundException e) {
                // Fallback to browser if Play Store app not found
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().getPackageName())));
            }
        });

        TextView shareAppButton = view.findViewById(R.id.shareapp);
        shareAppButton.setOnClickListener(v -> shareApp());

        TextView privacyTextView = view.findViewById(R.id.privacy);
        privacyTextView.setOnClickListener(v -> {
            String url = "https://skbonde05.github.io/PolyStuff/privacy_policy.html";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        TextView logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> logout());

        // ✅ Theme button click
        TextView themeButton = view.findViewById(R.id.theme); // ID from your XML
        SharedPreferences themePrefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        themeButton.setOnClickListener(v -> {
            String[] themes = {"Light", "Dark", "System Default"};

            int currentMode = themePrefs.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            int checkedItem = (currentMode == AppCompatDelegate.MODE_NIGHT_NO) ? 0 :
                    (currentMode == AppCompatDelegate.MODE_NIGHT_YES) ? 1 : 2;

            new AlertDialog.Builder(requireContext())
                    .setTitle("Choose Theme")
                    .setSingleChoiceItems(themes, checkedItem, null)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", (dialog, which) -> {
                        AlertDialog alert = (AlertDialog) dialog;
                        int selectedPosition = alert.getListView().getCheckedItemPosition();

                        int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                        if (selectedPosition == 0) mode = AppCompatDelegate.MODE_NIGHT_NO;
                        else if (selectedPosition == 1) mode = AppCompatDelegate.MODE_NIGHT_YES;

                        SharedPreferences.Editor editor = themePrefs.edit();
                        editor.putInt("theme_mode", mode);
                        editor.apply();

                        AppCompatDelegate.setDefaultNightMode(mode);
                        requireActivity().recreate(); // Apply theme change
                    })
                    .show();
        });

        return view;
    }

    private void shareApp() {
        String packageName = requireActivity().getPackageName();
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this awesome app!");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out this app on Play Store:\n" + playStoreLink);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        SharedPreferences prefs = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        startActivity(new Intent(requireActivity(), login.class));
        requireActivity().finish();
    }
}
