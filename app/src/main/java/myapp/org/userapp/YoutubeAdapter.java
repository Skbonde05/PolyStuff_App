package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeViewHolder> {
    private ArrayList<DataSetList> videoList;
    private Context context;

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

        // Convert the YouTube link to an embed URL
        String videoUrl = current.getUrl().replace("https://youtu.be/", "https://www.youtube.com/embed/");

        // Load the video URL in the WebView
        String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        holder.webView.loadData(videoHtml, "text/html", "utf-8");

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoFullScreen.class);
                intent.putExtra("link", current.getUrl());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
