package myapp.org.userapp;

import android.content.Intent;
import android.content.Context;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
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
            Log.d(TAG, "Original PDF URL: " + pdfUrl);
            String githubUrl = StorageConfig.convertFirebaseUrlToJsdelivr(PdfViewerActivity.this, pdfUrl);
            if (githubUrl != null && !githubUrl.equals(pdfUrl)) {
                Log.d(TAG, "Converted GitHub CDN URL: " + githubUrl);
            }
            loadPdfFromUrl(pdfUrl);
        } else {
            Toast.makeText(this, "PDF URL is missing", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPdfFromUrl(String pdfUrl) {
        progressBar.setVisibility(View.VISIBLE);

        getExecutorService().execute(() -> {
            List<String> urlCandidates = new ArrayList<>();
            List<String> urlLabels = new ArrayList<>();

            String originalUrl = pdfUrl;

            // 1. Try jsDelivr CDN candidate variations first (e.g. HTML UNIT 1.pdf vs HTML Unit 1.pdf)
            List<String> jsdelivrCandidates = StorageConfig.getJsdelivrCdnCandidateUrls(PdfViewerActivity.this, pdfUrl);
            for (String candidate : jsdelivrCandidates) {
                if (!urlCandidates.contains(candidate)) {
                    urlCandidates.add(candidate);
                    urlLabels.add("jsDelivr CDN");
                }
            }

            // 2. Try Raw GitHub CDN candidate variations next
            List<String> rawGithubCandidates = StorageConfig.getRawGithubCandidateUrls(pdfUrl);
            for (String candidate : rawGithubCandidates) {
                if (!urlCandidates.contains(candidate)) {
                    urlCandidates.add(candidate);
                    urlLabels.add("Raw GitHub CDN");
                }
            }

            File cachedFile = null;
            String usedSource = "";
            for (int i = 0; i < urlCandidates.size(); i++) {
                String candidateUrl = urlCandidates.get(i);
                String label = urlLabels.get(i);
                Log.d(TAG, "Trying PDF URL (" + label + "): " + candidateUrl);
                cachedFile = fetchPdfToCache(candidateUrl);
                if (cachedFile != null && cachedFile.exists() && cachedFile.length() > 0) {
                    usedSource = label;
                    currentPdfUrl = candidateUrl;
                    break;
                } else {
                    Log.w(TAG, "Failed (" + label + "): cached file is null or empty");
                }
            }

            final File finalCachedFile = cachedFile;
            final String finalUsedSource = usedSource;
            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                if (finalCachedFile != null && finalCachedFile.exists() && finalCachedFile.length() > 0) {
                    Log.d(TAG, "PDF loaded successfully from: " + finalUsedSource + " path: " + finalCachedFile.getAbsolutePath());
                    try {
                        pdfView.fromFile(finalCachedFile)
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
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to load PDF from file: " + e.getMessage(), e);
                        Toast.makeText(PdfViewerActivity.this, "Failed to load PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    String error = "Failed to load PDF document";
                    Log.e(TAG, error);
                    Toast.makeText(PdfViewerActivity.this, error + "\nCheck Logcat for URL details", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private File fetchPdfToCache(String targetUrl) {
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
                connection.setInstanceFollowRedirects(false);

                int responseCode = connection.getResponseCode();
                Log.d(TAG, "HTTP " + responseCode + " for URL: " + currentUrl);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String fileName = StorageConfig.extractFileNameFromFirebaseUrl(currentUrl);
                    if (fileName == null || fileName.isEmpty()) {
                        fileName = "downloaded_" + System.currentTimeMillis() + ".pdf";
                    }
                    File cacheDir = new File(getCacheDir(), "pdfs");
                    if (!cacheDir.exists()) {
                        cacheDir.mkdirs();
                    }
                    File outFile = new File(cacheDir, fileName);

                    try (InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                         FileOutputStream outputStream = new FileOutputStream(outFile)) {
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        outputStream.flush();
                        return outFile;
                    }
                } else if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
                        || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                        || responseCode == 307
                        || responseCode == 308) {
                    String redirectUrl = connection.getHeaderField("Location");
                    connection.disconnect();
                    if (redirectUrl != null && !redirectUrl.isEmpty()) {
                        Log.d(TAG, "Redirecting to: " + redirectUrl);
                        currentUrl = redirectUrl;
                        redirectCount++;
                        continue;
                    }
                    break;
                } else if (responseCode == 402) {
                    Log.w(TAG, "Firebase Storage quota exceeded. CDN fallback failed.");
                    return null;
                } else if (responseCode == 403) {
                    Log.w(TAG, "Access forbidden (HTTP 403). File may not be public.");
                    return null;
                } else if (responseCode == 404) {
                    Log.w(TAG, "PDF file not found (HTTP 404). Check filename on GitHub.");
                    return null;
                } else {
                    Log.w(TAG, "Server error (HTTP " + responseCode + ").");
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error fetching PDF from " + targetUrl + ": " + e.getMessage(), e);
            return null;
        }
    }

    private void showOpenExternalOption() {
        if (currentPdfUrl == null) return;
        String browserUrl = currentPdfUrl;

        // If current URL is a Firebase URL, try to convert to GitHub CDN for browser
        if (currentPdfUrl.contains("firebasestorage.googleapis.com")) {
            String githubUrl = StorageConfig.convertFirebaseUrlToJsdelivr(PdfViewerActivity.this, currentPdfUrl);
            if (githubUrl != null) {
                browserUrl = githubUrl;
            }
        }

        String finalBrowserUrl = browserUrl;
        new AlertDialog.Builder(this)
                .setTitle("Cannot display PDF")
                .setMessage("The built-in viewer could not load this PDF. Would you like to open it in a browser?\n\nURL: " + finalBrowserUrl)
                .setPositiveButton("Open in Browser", (dialog, which) -> {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(finalBrowserUrl));
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
