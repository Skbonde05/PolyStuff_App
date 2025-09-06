package myapp.org.userapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.List;
import com.makeramen.roundedimageview.RoundedImageView;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SLideViewHolder> {

    private List<SlideIten> slideItems;
    private ViewPager2 viewPager2;

    SlideAdapter(List<SlideIten> slideItems, ViewPager2 viewPager2) {
        this.slideItems = slideItems;
        this.viewPager2 = viewPager2;
    }


    @NonNull
    @Override
    public SLideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SLideViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SLideViewHolder holder, int position) {

        holder.setImage(slideItems.get(position));
        if(position == slideItems.size()-2)
        {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    class SLideViewHolder extends RecyclerView.ViewHolder
    {
        private RoundedImageView imageView;

        SLideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SlideIten slideItem)
        {
            imageView.setImageResource(slideItem.getImage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            slideItems.addAll(slideItems);
            notifyDataSetChanged();
        }
    };
}
