package myapp.org.userapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        // -------- Toolbar back button (null-safe, ImageButton OR Toolbar fallback) --------
        android.widget.ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(v -> finish());
            }
        }

        etToken = findViewById(R.id.etToken);

        // Retrieve notification content
        String notificationTitle = getIntent().getStringExtra("title");
        String notificationMessage = getIntent().getStringExtra("message");

        // Set the content of TextViews (null-safe)
        titleTextView = findViewById(R.id.titleTextView);
        TextView messageTextView = findViewById(R.id.messageTextView);

        if (titleTextView != null) {
            titleTextView.setText(notificationTitle != null ? notificationTitle : "");
        }
        if (messageTextView != null) {
            messageTextView.setText(notificationMessage != null ? notificationMessage : "");
        }

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
                        System.out.println(token);

                        // Set the token in the EditText (null-safe)
                        if (etToken != null) {
                            etToken.setText(token);
                        }
                    }
                });
    }
}