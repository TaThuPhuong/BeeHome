package net.fpl.beehome.Adapter.SuCo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.SuCo;

import java.util.ArrayList;

public class SuCoAdapter extends RecyclerView.Adapter<SuCoAdapter.SuCoViewHolder> {
    ArrayList<SuCo> arr;

    public SuCoAdapter(ArrayList<SuCo> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public SuCoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suco, parent, false);

        return new SuCoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuCoViewHolder holder, int position) {
        final SuCo objSuCo = arr.get(position);
        final int index = position;

        holder.tv_mota.setText("Mô tả: " + objSuCo.getMoTa());
        holder.tv_ngaybc.setText("Ngày báo cáo: "+objSuCo.getNgayBaoCao());
        holder.tv_phong.setText("Phòng: "+ objSuCo.getId_phong());

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class SuCoViewHolder extends RecyclerView.ViewHolder {
        TextView tv_phong, tv_mota, tv_ngaybc;
        public SuCoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_phong = itemView.findViewById(R.id.tv_phong);
            tv_mota = itemView.findViewById(R.id.tv_mota);
            tv_ngaybc = itemView.findViewById(R.id.tv_ngaybc);

        }
    }
}
