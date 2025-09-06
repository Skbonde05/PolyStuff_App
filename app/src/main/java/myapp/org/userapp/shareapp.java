package myapp.org.userapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class shareapp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shareapp);

        Button btnShareApp = findViewById(R.id.shareapp);
        btnShareApp.setOnClickListener(v -> shareApp());
    }

    private void shareApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + getApkPath()));
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            Toast.makeText(this, "Error sharing app", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String getApkPath() {
        return getApplicationInfo().publicSourceDir;
    }
}
