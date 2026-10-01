package myapp.org.userapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.List;

import myapp.org.userapp.R;
import myapp.org.userapp.model.ContentItem;

public class SubjectContentAdapter extends RecyclerView.Adapter<SubjectContentAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ContentItem item);
    }

    private final OnItemClickListener listener;
    private final List<ContentItem> items;
    private final LayoutInflater inflater;

    public SubjectContentAdapter(android.content.Context context, List<ContentItem> items, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentItem item = items.get(position);
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewSubtitle.setText(formatSubtitle(item));

        int iconRes = getIconForType(item.getContentType());
        holder.imageViewIcon.setImageResource(iconRes);

        holder.cardView.setOnClickListener(v -> listener.onItemClick(item));
    }

    private String formatSubtitle(ContentItem item) {
        StringBuilder sb = new StringBuilder();
        if (item.getPageCount() > 0) {
            sb.append(item.getPageCount()).append(" pages");
        }
        if (item.getFileSizeBytes() > 0) {
            if (sb.length() > 0) sb.append(" • ");
            sb.append(formatFileSize(item.getFileSizeBytes()));
        }
        return sb.toString();
    }

    private String formatFileSize(long bytes) {
        if (bytes <= 0) return "";
        String[] units = {"B", "KB", "MB", "GB"};
        int unitIndex = 0;
        double size = bytes;
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        return new DecimalFormat("#.#").format(size) + " " + units[unitIndex];
    }

    private int getIconForType(String type) {
        if (type == null) return R.drawable.ic_pdf;
        switch (type.toLowerCase()) {
            case "pdf":
                return R.drawable.ic_pdf;
            case "mcq":
                return R.drawable.ic_mcq;
            case "video":
                return R.drawable.ic_video;
            case "note":
                return R.drawable.ic_note;
            default:
                return R.drawable.ic_pdf;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView imageViewIcon;
        TextView textViewTitle;
        TextView textViewSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewSubtitle = itemView.findViewById(R.id.textViewSubtitle);
        }
    }
}
