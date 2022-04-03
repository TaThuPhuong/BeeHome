package net.fpl.beehome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.SlideItem;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {
    private List<SlideItem> arr;
    private ViewPager2 viewPager2;

    public SlideAdapter(List<SlideItem> arr, ViewPager2 viewPager2) {
        this.arr = arr;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setImgView(arr.get(position));
        if(position == arr.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView imgView;
        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_slide);
        }

        public void setImgView(SlideItem slideItem){
            imgView.setImageResource(slideItem.getImg());
        }

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            arr.addAll(arr);
            notifyDataSetChanged();
        }
    };
}
