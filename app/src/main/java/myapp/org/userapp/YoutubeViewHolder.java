package myapp.org.userapp;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class YoutubeViewHolder extends RecyclerView.ViewHolder {
    WebView webView;
    TextView titleTextView;
    Button button;

    public YoutubeViewHolder(@NonNull View itemView) {
        super(itemView);
        webView = itemView.findViewById(R.id.video_view);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        button = itemView.findViewById(R.id.fullscreen);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
