package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final Product product = productList.get(position);

        if (holder.textViewTitle != null) {
            TypedValue typedValue = new TypedValue();
            Context context = holder.itemView.getContext();
            if (context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnSurface, typedValue, true)) {
                holder.textViewTitle.setTextColor(typedValue.data);
            } else {
                holder.textViewTitle.setTextColor(0xFF0F172A);
            }
            holder.textViewTitle.setText(product.getTitle());
        }

        if (holder.imageViewIcon != null) {
            holder.imageViewIcon.setImageResource(product.getImage());
        }

        if (holder.textViewSubtitle != null) {
            holder.textViewSubtitle.setText("PDF Document");
        }

        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        View cardView;
        ImageView imageViewIcon;
        TextView textViewTitle;
        TextView textViewSubtitle;
        private Product currentProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            if (cardView == null) {
                cardView = itemView;
            }
            imageViewIcon = itemView.findViewById(R.id.pdfIcon);
            if (imageViewIcon == null) imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            if (imageViewIcon == null) imageViewIcon = itemView.findViewById(R.id.imageView);

            textViewTitle = itemView.findViewById(R.id.pdfTitle);
            if (textViewTitle == null) textViewTitle = itemView.findViewById(R.id.textViewTitle);

            textViewSubtitle = itemView.findViewById(R.id.pdfSize);
            if (textViewSubtitle == null) textViewSubtitle = itemView.findViewById(R.id.textViewSubtitle);

            View clickTarget = cardView != null ? cardView : itemView;
            clickTarget.setOnClickListener(v -> {
                if (currentProduct == null) return;
                Log.d("ProductAdapter", "Opening PDF: " + currentProduct.getTitle() + " URL: " + currentProduct.getLink());
                try {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, PdfViewerActivity.class);
                    intent.putExtra(PdfViewerActivity.EXTRA_PDF_TITLE, currentProduct.getTitle().trim());
                    intent.putExtra(PdfViewerActivity.EXTRA_PDF_URL, currentProduct.getLink());
                    context.startActivity(intent);
                } catch (Exception e) {
                    Log.e("ProductAdapter", "Error opening PDF: " + e.getMessage(), e);
                    android.widget.Toast.makeText(itemView.getContext(), "Error opening PDF: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bind(Product product) {
            currentProduct = product;
        }
    }
}
