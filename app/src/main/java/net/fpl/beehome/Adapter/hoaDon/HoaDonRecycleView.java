package net.fpl.beehome.Adapter.hoaDon;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.fpl.beehome.R;

public class HoaDonRecycleView extends RecyclerView.Adapter<HoaDonRecycleView.HoaDonViewHolder > {
    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class HoaDonViewHolder extends RecyclerView.ViewHolder {
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView tongHD,phong,tienNha,tienDv,giamGia;

            tongHD = itemView.findViewById(R.id.tv_tongHD);
            phong = itemView.findViewById(R.id.tv_idPhongHD);
            tienNha = itemView.findViewById(R.id.tv_tienPhongHD);
            tienDv = itemView.findViewById(R.id.tv_tienDvHD);
            giamGia = itemView.findViewById(R.id.tv_giamGiaHD);
        }
    }
}
