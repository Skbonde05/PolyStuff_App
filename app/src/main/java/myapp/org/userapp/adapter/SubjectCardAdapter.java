package myapp.org.userapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import myapp.org.userapp.R;
import myapp.org.userapp.model.Subject;

public class SubjectCardAdapter extends RecyclerView.Adapter<SubjectCardAdapter.ViewHolder> {

    public interface OnSubjectClickListener {
        void onSubjectClick(Subject subject);
    }

    private final OnSubjectClickListener listener;
    private final List<Subject> subjects;
    private final LayoutInflater inflater;

    public SubjectCardAdapter(android.content.Context context, List<Subject> subjects, OnSubjectClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.subjects = subjects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_subject_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.textViewTitle.setText(subject.getTitle());
        holder.textViewSubtitle.setText(subject.getDescription());
        
        if (subject.getPdfCount() > 0) {
            holder.textViewBadge.setText(subject.getPdfCount() + " PDFs");
        } else {
            holder.textViewBadge.setText("Tap to open");
        }

        holder.cardView.setOnClickListener(v -> listener.onSubjectClick(subject));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView textViewTitle;
        TextView textViewSubtitle;
        TextView textViewBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewSubtitle = itemView.findViewById(R.id.textViewSubtitle);
            textViewBadge = itemView.findViewById(R.id.textViewBadge);
        }
    }
}
