package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Product> productList;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jp1_u1_layout, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jp1_u2_layout, parent, false);
                break;
        }

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final Product product = productList.get(position);

        // Apply theme-aware text color
        TypedValue typedValue = new TypedValue();
        Context context = holder.itemView.getContext();
        context.getTheme().resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        holder.textViewTitle.setTextColor(typedValue.data);

        holder.textViewTitle.setText(product.getTitle());
        holder.imageView.setImageResource(product.getImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProductAdapter", "PDF URL: " + product.getLink());
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(product.getLink()), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mCtx.startActivity(intent);
                } catch (Exception e) {
                    Log.e("ProductAdapter", "Error opening PDF: " + e.getMessage());
                    Toast.makeText(mCtx, "Error opening PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        // For simplicity, let's assume the odd positions have jp1_u1_layout and even positions have jp1_u2_layout
        return position % 2 == 0 ? 1 : 2;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageView imageView;
        CardView cardView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
