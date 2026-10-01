package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeViewHolder> {

    private final ArrayList<DataSetList> videoList;
    private final Context context;

    public YoutubeAdapter(ArrayList<DataSetList> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public YoutubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_per_row, parent, false);
        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeViewHolder holder, int position) {
        DataSetList current = videoList.get(position);
        holder.titleTextView.setText(current.getTitle());

        String videoUrl = toEmbedUrl(current.getUrl());

        // Configure WebView settings (idempotent)
        WebSettings ws = holder.webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setMediaPlaybackRequiresUserGesture(false);
        holder.webView.setWebChromeClient(new WebChromeClient());

        // Build HTML wrapper for the iframe
        String videoHtml = "<!DOCTYPE html><html><head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<style>html,body{margin:0;padding:0;height:100%;background:#000;}" +
                "iframe{width:100%;height:100%;border:0;}</style></head>" +
                "<body>" +
                "<iframe src=\"" + videoUrl + "\" " +
                "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" " +
                "allowfullscreen></iframe>" +
                "</body></html>";

        holder.webView.loadDataWithBaseURL(
                "https://www.youtube.com",
                videoHtml,
                "text/html",
                "utf-8",
                null
        );

        if (holder.button != null) {
            holder.button.setOnClickListener(v -> {
                Intent intent = new Intent(context, VideoFullScreen.class);
                intent.putExtra("link", current.getUrl());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public void onViewRecycled(@NonNull YoutubeViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.webView != null) {
            holder.webView.loadUrl("about:blank");
            holder.webView.stopLoading();
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    /**
     * Convert any YouTube URL format into an embeddable URL.
     */
    private String toEmbedUrl(String url) {
        if (url == null || url.isEmpty()) return url;

        // Already an embed URL
        if (url.contains("/embed/")) return url;

        // Playlist — leave as-is (YouTube handles or blocks these)
        if (url.contains("list=") && !url.contains("watch?v=")) {
            return url;
        }

        // youtu.be/VIDEO_ID
        if (url.contains("youtu.be/")) {
            String id = url.substring(url.lastIndexOf("youtu.be/") + "youtu.be/".length());
            int q = id.indexOf('?');
            if (q != -1) id = id.substring(0, q);
            return "https://www.youtube.com/embed/" + id;
        }

        // youtube.com/watch?v=VIDEO_ID
        if (url.contains("watch?v=")) {
            String id = url.substring(url.lastIndexOf("watch?v=") + "watch?v=".length());
            int amp = id.indexOf('&');
            if (amp != -1) id = id.substring(0, amp);
            return "https://www.youtube.com/embed/" + id;
        }

        return url;
    }
}