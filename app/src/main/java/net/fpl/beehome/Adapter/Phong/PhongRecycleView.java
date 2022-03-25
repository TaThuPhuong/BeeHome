package net.fpl.beehome.Adapter.Phong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;

import net.fpl.beehome.DAO.PhongDAO;
import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;
import net.fpl.beehome.ui.phong.PhongFragment;

import java.util.ArrayList;

public class PhongRecycleView extends RecyclerView.Adapter<PhongRecycleView.PhongViewHolder> {
    ArrayList<Phong> lsPhong;
    PhongDAO phongDAO;
    Context context;

    public PhongRecycleView(ArrayList<Phong> lsPhong, PhongDAO phongDAO, Context context) {
        this.lsPhong = lsPhong;
        this.phongDAO = phongDAO;
        this.context = context;
    }

    @SuppressLint("ResourceAsColor")
    public void mauTrangThai(String trangThai, TextView tv) {
        switch (trangThai) {
            case "Đang thuê":
                tv.setTextColor(Color.parseColor("#000000"));
                break;
            case "Trống":
                tv.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "Đang sửa chữa":
                tv.setTextColor(Color.parseColor("#5EA3CD"));
                break;
        }
    }

    @NonNull
    @Override
    public PhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PhongViewHolder phongViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item_view = inflater.inflate(R.layout.item_phong, parent, false);
        phongViewHolder = new PhongViewHolder(item_view);
        return phongViewHolder;
//        return new PhongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phong, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhongViewHolder holder, int position) {
        Phong phong = this.lsPhong.get(position);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item);

        Log.d("zzzzz", "onBindViewHolder: " + phong.toString());
        holder.tvSoPhong.setText("Phòng - " + phong.getSoPhong());
        holder.tvTrangThai.setText(phong.getTrangThai());
        mauTrangThai(phong.getTrangThai(), holder.tvTrangThai);
//        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
//        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));
    }


    @Override
    public int getItemCount() {
        return this.lsPhong.size();
    }

    class PhongViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView tvSoPhong, tvTrangThai;
        public LinearLayout llItem;
//        public SwipeLayout swipeLayout;


        public PhongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSoPhong = itemView.findViewById(R.id.tv_phong);
            tvTrangThai = itemView.findViewById(R.id.tv_trang_thai);
            llItem = itemView.findViewById(R.id.ll_item);
//            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            llItem.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), 111, 0, "Chi tiết");
            contextMenu.add(this.getAdapterPosition(), 112, 1, "Cập nhập");
            contextMenu.add(this.getAdapterPosition(), 113, 2, "Xóa");
        }
    }
}
