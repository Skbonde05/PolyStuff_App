package myapp.org.userapp;

import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PdfViewerActivity extends AppCompatActivity {

    private static final String TAG = "PdfViewerActivity";
    public static final String EXTRA_PDF_TITLE = "pdf_title";
    public static final String EXTRA_PDF_URL = "link";

    private PDFView pdfView;
    private ProgressBar progressBar;
    private ExecutorService executorService;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private String currentPdfUrl = null;
    private String currentPdfTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_PDF_TITLE);
        String pdfUrl = intent.getStringExtra(EXTRA_PDF_URL);

        if (title == null || title.isEmpty()) {
            title = "Document Viewer";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(title);
        }

        if (!isConnected()) {
            showNoInternetDialog();
        }

        if (pdfUrl != null && !pdfUrl.isEmpty()) {
            currentPdfUrl = pdfUrl;
            currentPdfTitle = title;
            loadPdfFromUrl(pdfUrl);
        } else {
            Toast.makeText(this, "PDF URL is missing", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPdfFromUrl(String pdfUrl) {
        progressBar.setVisibility(View.VISIBLE);

        getExecutorService().execute(() -> {
            List<String> urlCandidates = new ArrayList<>();

            // 1. Try jsDelivr CDN first for fast zero-cost delivery
            String jsdelivrUrl = StorageConfig.convertFirebaseUrlToJsdelivr(PdfViewerActivity.this, pdfUrl);
            if (jsdelivrUrl != null) {
                urlCandidates.add(jsdelivrUrl);
            }

            // 2. Try Raw GitHub next
            String rawGithubUrl = StorageConfig.convertFirebaseUrlToRawGithub(pdfUrl);
            if (rawGithubUrl != null && !urlCandidates.contains(rawGithubUrl)) {
                urlCandidates.add(rawGithubUrl);
            }

            // 3. Try original URL as fallback
            if (!urlCandidates.contains(pdfUrl)) {
                urlCandidates.add(pdfUrl);
            }

            FetchResult result = null;
            for (String candidateUrl : urlCandidates) {
                Log.d(TAG, "Trying PDF URL candidate: " + candidateUrl);
                result = fetchPdfBytes(candidateUrl);
                if (result != null && result.bytes != null && result.bytes.length > 0) {
                    break;
                }
            }

            final FetchResult finalResult = result != null ? result : new FetchResult(null, "Failed to load PDF document");
            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                if (finalResult.bytes != null && finalResult.bytes.length > 0) {
                    pdfView.fromBytes(finalResult.bytes)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .defaultPage(0)
                            .onError(t -> {
                                Log.e(TAG, "PDF render error", t);
                                showOpenExternalOption();
                            })
                            .onLoad(nbPages -> Log.d(TAG, "PDF loaded successfully, pages: " + nbPages))
                            .load();
                } else {
                    String error = finalResult.errorMessage != null ? 
                            finalResult.errorMessage : "Failed to load PDF document";
                    Log.e(TAG, error);
                    Toast.makeText(PdfViewerActivity.this, error, Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private FetchResult fetchPdfBytes(String targetUrl) {
        try {
            int maxRedirects = 5;
            int redirectCount = 0;
            String currentUrl = targetUrl;

            while (redirectCount < maxRedirects) {
                URL url = new URL(currentUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(20000);
                connection.setInstanceFollowRedirects(true);

                int responseCode = connection.getResponseCode();
                Log.d(TAG, "HTTP Response Code: " + responseCode + " for URL: " + currentUrl);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (InputStream inputStream = connection.getInputStream();
                         ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        return new FetchResult(outputStream.toByteArray(), null);
                    }
                } else if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
                        || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                        || responseCode == 307
                        || responseCode == 308) {
                    String redirectUrl = connection.getHeaderField("Location");
                    connection.disconnect();
                    if (redirectUrl != null && !redirectUrl.isEmpty()) {
                        currentUrl = redirectUrl;
                        redirectCount++;
                        continue;
                    }
                    break;
                } else if (responseCode == 402) {
                    return new FetchResult(null, "Firebase Storage quota exceeded. CDN fallback failed.");
                } else if (responseCode == 403) {
                    return new FetchResult(null, "Access forbidden (HTTP 403).");
                } else if (responseCode == 404) {
                    return new FetchResult(null, "PDF file not found (HTTP 404).");
                } else {
                    return new FetchResult(null, "Server error (HTTP " + responseCode + ").");
                }
            }
            return new FetchResult(null, "Too many HTTP redirects.");
        } catch (Exception e) {
            Log.e(TAG, "Error fetching PDF from " + targetUrl + ": " + e.getMessage(), e);
            return new FetchResult(null, "Network error: " + e.getLocalizedMessage());
        }
    }

    private static class FetchResult {
        final byte[] bytes;
        final String errorMessage;

        FetchResult(byte[] bytes, String errorMessage) {
            this.bytes = bytes;
            this.errorMessage = errorMessage;
        }
    }

    private void showOpenExternalOption() {
        if (currentPdfUrl == null) return;
        new AlertDialog.Builder(this)
                .setTitle("Cannot display PDF")
                .setMessage("The built-in viewer could not load this PDF. Would you like to open it in a browser?")
                .setPositiveButton("Open in Browser", (dialog, which) -> {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(currentPdfUrl));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                        Toast.makeText(this, "No app available to open PDF", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
            }
        } catch (Exception e) {
            Log.e(TAG, "Connectivity check exception: " + e.getMessage());
        }
        return false;
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection to view this book.")
                .setPositiveButton("Settings", (dialog, which) ->
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private ExecutorService getExecutorService() {
        if (executorService == null || executorService.isShutdown()) {
            executorService = Executors.newSingleThreadExecutor();
        }
        return executorService;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        mainHandler.removeCallbacksAndMessages(null);
    }
}
