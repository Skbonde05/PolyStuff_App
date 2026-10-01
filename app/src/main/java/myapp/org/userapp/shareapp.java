package myapp.org.userapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class shareapp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shareapp);

        Button btnShareApp = findViewById(R.id.shareapp);
        if (btnShareApp != null) {
            btnShareApp.setOnClickListener(v -> shareApp());
        }
    }

    private void shareApp() {
        try {
            // Copy the APK into the app's cache dir so FileProvider can share it
            File apkFile = copyApkToCache();

            // Get a content:// URI via FileProvider
            Uri apkUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    apkFile
            );

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, apkUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            Toast.makeText(this, "Error sharing app: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private File copyApkToCache() throws IOException {
        File sourceApk = new File(getApplicationInfo().publicSourceDir);
        File cacheDir = new File(getCacheDir(), "shared");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File destApk = new File(cacheDir, "app.apk");

        try (InputStream in = new FileInputStream(sourceApk);
             OutputStream out = new FileOutputStream(destApk)) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
        return destApk;
    }
}