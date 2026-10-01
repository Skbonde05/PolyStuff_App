package myapp.org.userapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class VideoFullScreen extends AppCompatActivity {

    private WebView webView;
    private FrameLayout fullscreenContainer;
    private View customView;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);

        fullscreenContainer = findViewById(R.id.fullscreen_container);
        webView = findViewById(R.id.full_video);

        if (webView == null) {
            Toast.makeText(this, "WebView not found in layout", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (customView != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                customView = view;
                customViewCallback = callback;
                if (fullscreenContainer != null) {
                    fullscreenContainer.addView(view,
                            new FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                    fullscreenContainer.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                } else {
                    ViewGroup decor = (ViewGroup) getWindow().getDecorView();
                    decor.addView(view);
                }
            }

            @Override
            public void onHideCustomView() {
                if (customView == null) return;
                if (fullscreenContainer != null) {
                    fullscreenContainer.removeView(customView);
                    fullscreenContainer.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                } else {
                    ViewGroup decor = (ViewGroup) getWindow().getDecorView();
                    decor.removeView(customView);
                }
                customView = null;
                if (customViewCallback != null) {
                    customViewCallback.onCustomViewHidden();
                }
                customViewCallback = null;
            }
        });

        // Back press handling
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (customView != null) {
                    if (fullscreenContainer != null) {
                        fullscreenContainer.removeView(customView);
                        fullscreenContainer.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                    }
                    customView = null;
                    if (customViewCallback != null) {
                        customViewCallback.onCustomViewHidden();
                    }
                    customViewCallback = null;
                    return;
                }
                setEnabled(false);
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        String videoUrl = getIntent().getStringExtra("link");
        if (videoUrl == null || videoUrl.isEmpty()) {
            Toast.makeText(this, "No video link provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        videoUrl = videoUrl.replace("watch?v=", "embed/")
                .replace("youtu.be/", "www.youtube.com/embed/");

        String videoHtml = "<!DOCTYPE html><html><head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<style>html,body{margin:0;padding:0;height:100%;background:#000;}" +
                "iframe{width:100%;height:100%;border:0;}</style></head>" +
                "<body>" +
                "<iframe src=\"" + videoUrl + "\" " +
                "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" " +
                "allowfullscreen></iframe>" +
                "</body></html>";

        webView.loadDataWithBaseURL(
                "https://www.youtube.com",
                videoHtml,
                "text/html",
                "utf-8",
                null
        );

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadUrl("about:blank");
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}