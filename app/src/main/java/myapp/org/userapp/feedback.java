package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class feedback extends AppCompatActivity {

    private static final int REQUEST_CODE_AUDIO_FILE = 1;

    private DatabaseReference databaseReference;
    private EditText username, feedback;
    private Uri audioData;
    private TextView audioTextView;
    private StorageReference storageReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        // -------- Toolbar back button --------
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // -------- Firebase --------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // -------- Views --------
        username      = findViewById(R.id.username);
        feedback      = findViewById(R.id.feedback);
        audioTextView = findViewById(R.id.audioTextView);

        // -------- Audio upload card click -> open file picker --------
        View uploadAudioBtn = findViewById(R.id.uploadAudioBtn);
        if (uploadAudioBtn != null) {
            uploadAudioBtn.setOnClickListener(v -> selectAudioFile(v));
        }

        // -------- Send Feedback button --------
        View sendFeedbackButton = findViewById(R.id.sendFeedbackButton);
        if (sendFeedbackButton != null) {
            sendFeedbackButton.setOnClickListener(this::feedbacksent);
        }
    }

    private void feedbacksent(View view) {
        if (username == null || feedback == null) return;

        String usernameInput = username.getText().toString().trim();
        String feedbackInput = feedback.getText().toString().trim();

        if (TextUtils.isEmpty(usernameInput) || TextUtils.isEmpty(feedbackInput)) {
            Toast.makeText(this, "Username and Feedback cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Feedback");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String feedbackKey = databaseReference.child("feedback").push().getKey();
        if (feedbackKey == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Failed to send feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("username", usernameInput);
        feedbackData.put("feedback", feedbackInput);

        if (audioData != null) {
            uploadAudioAndFeedback(feedbackKey, feedbackData, progressDialog);
        } else {
            uploadFeedbackOnly(feedbackKey, feedbackData, progressDialog);
        }
    }

    private void uploadAudioAndFeedback(String feedbackKey,
                                        HashMap<String, Object> feedbackData,
                                        ProgressDialog progressDialog) {
        StorageReference audioRef = storageReference.child("audio").child(feedbackKey + ".mp3");

        audioRef.putFile(audioData)
                .addOnSuccessListener(taskSnapshot ->
                        audioRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String audioDownloadUrl = uri.toString();
                            feedbackData.put("audioUrl", audioDownloadUrl);
                            databaseReference.child("feedback").child(feedbackKey).setValue(feedbackData)
                                    .addOnCompleteListener(task -> {
                                        progressDialog.dismiss();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(this, "Feedback sent successfully", Toast.LENGTH_SHORT).show();
                                            clearInputs();
                                        } else {
                                            Toast.makeText(this, "Failed to send feedback", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        progressDialog.dismiss();
                                        Toast.makeText(this, "Failed to upload feedback data", Toast.LENGTH_SHORT).show();
                                        Log.e("Feedback", "Failed to upload feedback data", e);
                                    });
                        }))
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Failed to upload audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Feedback", "Failed to upload audio", e);
                });
    }

    private void uploadFeedbackOnly(String feedbackKey,
                                    HashMap<String, Object> feedbackData,
                                    ProgressDialog progressDialog) {
        databaseReference.child("feedback").child(feedbackKey).setValue(feedbackData)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Feedback sent successfully", Toast.LENGTH_SHORT).show();
                        clearInputs();
                    } else {
                        Toast.makeText(this, "Failed to send feedback", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Failed to upload feedback data", Toast.LENGTH_SHORT).show();
                    Log.e("Feedback", "Failed to upload feedback data", e);
                });
    }

    private void clearInputs() {
        if (username != null) username.setText("");
        if (feedback != null) feedback.setText("");
        if (audioTextView != null) audioTextView.setText("Tap to select an audio file");
        audioData = null;
    }

    private void selectAudioFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Audio"), REQUEST_CODE_AUDIO_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AUDIO_FILE && resultCode == Activity.RESULT_OK && data != null) {
            audioData = data.getData();
            if (audioData != null && audioTextView != null) {
                String audioFileName = getFileName(audioData);
                audioTextView.setText(audioFileName != null ? audioFileName : "Audio selected");
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    // Try DISPLAY_NAME first
                    int nameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                    if (nameIndex == -1) {
                        nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
                    }
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            if (result != null) {
                int index = result.lastIndexOf("/");
                if (index != -1) {
                    result = result.substring(index + 1);
                }
            }
        }
        return result;
    }
}