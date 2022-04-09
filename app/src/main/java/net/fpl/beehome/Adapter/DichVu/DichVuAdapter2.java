package net.fpl.beehome.Adapter.DichVu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;

import java.util.ArrayList;

public class DichVuAdapter2 extends RecyclerView.Adapter<DichVuAdapter2.DichVu2ViewHolder> {
    ArrayList<DichVu> arr;

    public DichVuAdapter2(ArrayList<DichVu> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public DichVu2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DichVu2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dich_vu2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DichVu2ViewHolder holder, int position) {
        DichVu dichVu = arr.get(position);

        holder.tv_tenDV.setText("Tên dịch vụ: " + dichVu.getTenDichVu());
        holder.tv_gia.setText("Giá: " + String.valueOf(dichVu.getGia()) + " đ / " + dichVu.getDonVi());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class DichVu2ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tenDV, tv_gia;

        public DichVu2ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenDV = itemView.findViewById(R.id.tv_tenDV);
            tv_gia = itemView.findViewById(R.id.tv_gia);
        }
    }
}
