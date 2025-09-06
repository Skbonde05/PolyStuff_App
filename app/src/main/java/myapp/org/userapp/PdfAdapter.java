package myapp.org.userapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

    private List<String> pdfList;
    private Context context;

    public PdfAdapter(List<String> pdfList, Context context) {
        this.pdfList = pdfList;
        this.context = context;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pdf, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        final String pdfFileName = pdfList.get(position);
        holder.textViewFileName.setText(pdfFileName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click event, open PDF file
                // You can implement the logic to open the PDF file here
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFileName;

        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFileName = itemView.findViewById(R.id.textViewFileName);
        }
    }
}
