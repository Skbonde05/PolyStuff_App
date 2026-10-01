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
import myapp.org.userapp.model.Unit;

public class UnitCardAdapter extends RecyclerView.Adapter<UnitCardAdapter.ViewHolder> {

    public interface OnUnitClickListener {
        void onUnitClick(Unit unit);
    }

    private final OnUnitClickListener listener;
    private final List<Unit> units;
    private final LayoutInflater inflater;

    public UnitCardAdapter(android.content.Context context, List<Unit> units, OnUnitClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.units = units;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_unit_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Unit unit = units.get(position);
        holder.textViewUnitNumber.setText(String.valueOf(unit.getUnitNumber()));
        holder.textViewTitle.setText(unit.getTitle() != null ? unit.getTitle() : unit.getUnitName());

        if (unit.getUnitNumber() == 0) {
            holder.textViewUnitNumber.setText("QP");
            holder.textViewTitle.setText("Question Paper");
        }

        holder.cardView.setOnClickListener(v -> listener.onUnitClick(unit));
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView textViewUnitNumber;
        TextView textViewTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            textViewUnitNumber = itemView.findViewById(R.id.textViewUnitNumber);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
