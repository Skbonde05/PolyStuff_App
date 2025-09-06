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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(feedback.this, ProfileFragment.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        username = findViewById(R.id.username);
        feedback = findViewById(R.id.feedback);
        audioTextView = findViewById(R.id.audioTextView);

        // Find the "Send Feedback" button by its ID
        findViewById(R.id.sendFeedbackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the feedbacksent() method when the button is clicked
                feedbacksent(v);
            }
        });
    }

    private void feedbacksent(View view) {
        String usernameInput = username.getText().toString();
        String feedbackInput = feedback.getText().toString();

        // Check if username and feedback are not empty
        if (TextUtils.isEmpty(usernameInput) || TextUtils.isEmpty(feedbackInput)) {
            Toast.makeText(this, "Username and Feedback cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress dialog while uploading
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Feedback");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        // Create a unique key for the feedback entry
        String feedbackKey = databaseReference.child("feedback").push().getKey();

        // Create a HashMap to store feedback data
        HashMap<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("username", usernameInput);
        feedbackData.put("feedback", feedbackInput);

        // Upload the audio file if available
        if (audioData != null) {
            uploadAudioAndFeedback(feedbackKey, feedbackData, progressDialog);
        } else {
            uploadFeedbackOnly(feedbackKey, feedbackData, progressDialog);
        }
    }


    private void uploadAudioAndFeedback(String feedbackKey, HashMap<String, Object> feedbackData, ProgressDialog progressDialog) {
        // Create storage reference for the audio file
        StorageReference audioRef = storageReference.child("audio").child(feedbackKey + ".mp3");

        // Upload the audio file to Firebase Storage
        audioRef.putFile(audioData)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL of the uploaded audio file
                    audioRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String audioDownloadUrl = uri.toString();
                        feedbackData.put("audioUrl", audioDownloadUrl);
                        // Store the feedback data in Firebase Realtime Database under "feedback" node with the unique key
                        databaseReference.child("feedback").child(feedbackKey).setValue(feedbackData).addOnCompleteListener(task -> {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(this, "Feedback sent successfully", Toast.LENGTH_SHORT).show();
                                        // Clear input fields
                                        username.setText("");
                                        feedback.setText("");
                                        audioTextView.setText("");
                                        audioData = null; // Reset audioData after upload
                                    } else {
                                        Toast.makeText(this, "Failed to send feedback", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(this, "Failed to upload feedback data", Toast.LENGTH_SHORT).show();
                                    Log.e("Feedback", "Failed to upload feedback data", e);
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Failed to upload audio", Toast.LENGTH_SHORT).show();
                    Log.e("Feedback", "Failed to upload audio", e);
                });
    }

    private void uploadFeedbackOnly(String feedbackKey, HashMap<String, Object> feedbackData, ProgressDialog progressDialog) {
        // Store the feedback data in Firebase Realtime Database under "feedback" node with the unique key
        databaseReference.child("feedback").child(feedbackKey).setValue(feedbackData)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Feedback sent successfully", Toast.LENGTH_SHORT).show();
                        // Clear input fields
                        username.setText("");
                        feedback.setText("");
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


    // Method to select audio file
    public void selectAudioFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, REQUEST_CODE_AUDIO_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AUDIO_FILE && resultCode == Activity.RESULT_OK && data != null) {
            audioData = data.getData();
            if (audioData != null) {
                // Update the TextView to display the selected audio file name
                String audioFileName = getFileName(audioData);
                audioTextView.setText(audioFileName);
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int index = result.lastIndexOf("/");
            if (index != -1) {
                result = result.substring(index + 1);
            }
        }
        return result;
    }
}
