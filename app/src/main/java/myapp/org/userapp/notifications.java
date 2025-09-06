package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class notifications extends AppCompatActivity {
    private EditText etToken;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        ImageButton backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the Semesters activity
                Intent intent = new Intent(notifications.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        etToken = findViewById(R.id.etToken);

        // Retrieve notification content
        String notificationTitle = getIntent().getStringExtra("title");
        String notificationMessage = getIntent().getStringExtra("message");

        // Set the content of TextViews
        titleTextView = findViewById(R.id.titleTextView);
        TextView messageTextView = findViewById(R.id.messageTextView);

        titleTextView.setText(notificationTitle);
        messageTextView.setText(notificationMessage);

        // Get FCM token
        getFCMToken();
    }

    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log the token
                        System.out.println(token);

                        // You can use the token as needed here, such as sending it to your server

                        // Display a toast message with the token

                        // Set the token in the EditText
                        etToken.setText(token);
                    }
                });
    }
}
