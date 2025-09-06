package myapp.org.userapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class edit_profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editName, editEmail, editPassword, editPhone, editCity, editCountry;
    private Button saveChangesButton;
    private ImageView profileImage;
    private Uri imageUri;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    // Declare a boolean variable to track password visibility
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        editName = findViewById(R.id.editname);
        editEmail = findViewById(R.id.editmail);
        editPassword = findViewById(R.id.editpassword);
        editPhone = findViewById(R.id.editphone);
        editCity = findViewById(R.id.editcity);
        editCountry = findViewById(R.id.editcountry);
        saveChangesButton = findViewById(R.id.sendFeedbackButton);
        profileImage = findViewById(R.id.profileImage);
        ImageView eyeIcon = findViewById(R.id.showPasswordIcon);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        // Attach a TextWatcher to the password EditText
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Check if the eye icon is clicked
                if (!isPasswordVisible) {
                    // If not clicked, hide the password
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                }
            }
        });

        // Set OnClickListener for the eye icon
        eyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                isPasswordVisible = !isPasswordVisible;

                // Change the eye icon based on password visibility
                if (isPasswordVisible) {
                    eyeIcon.setImageResource(R.drawable.ic_eye_hidden);
                } else {
                    eyeIcon.setImageResource(R.drawable.ic_eye_visible);
                }

                // Set the inputType of the password EditText to make the password visible
                if (isPasswordVisible) {
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    editPassword.setTransformationMethod(null);
                } else {
                    // If password is not visible, set the inputType to password
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                }

                // Move cursor to the end of the text
                editPassword.setSelection(editPassword.getText().length());
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

    private void saveChanges() {
        final String name = editName.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        final String city = editCity.getText().toString().trim();
        final String country = editCountry.getText().toString().trim();

        if (imageUri != null) {
            progressDialog.show();
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(edit_profile.this, "Upload successful", Toast.LENGTH_SHORT).show();

                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();

                            // Update SharedPreferences with the new data
                            SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("UserName", name);
                            editor.putString("ProfileImageURL", imageUrl);
                            editor.apply();

                            // Save data to Firebase Database
                            Map<String, String> userMap = new HashMap<>();
                            userMap.put("name", name);
                            userMap.put("email", email);
                            userMap.put("password", password);
                            userMap.put("phone", phone);
                            userMap.put("city", city);
                            userMap.put("country", country);
                            userMap.put("imageUrl", imageUrl);

                            databaseReference.push().setValue(userMap)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(edit_profile.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(edit_profile.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(edit_profile.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));
    }
}
