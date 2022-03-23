package net.fpl.beehome.Adapter.HopDong;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.DAO.HopDongDAO;
import net.fpl.beehome.R;
import net.fpl.beehome.model.HopDong;

import java.util.ArrayList;

public class HopDongAdapter extends RecyclerView.Adapter<HopDongAdapter.HopDongViewholder> {
    ArrayList<HopDong> arr ;
    HopDongDAO hopDongDAO;

    public HopDongAdapter(HopDongDAO hopDongDAO){
        this.hopDongDAO = hopDongDAO;
        this.arr = hopDongDAO.getAll();
    }



    @NonNull
    @Override
    public HopDongViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hopdong, parent, false);

        return new HopDongViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HopDongViewholder holder, int position) {
        final HopDong objHopDong = arr.get(position);
        final int index = position;

        holder.tv_idphong.setText("Phòng: "+ objHopDong.getId_phong());
        holder.tv_ngayky.setText("Ngày ký: " + objHopDong.getNgayKiHD());
        holder.tv_ngaybd.setText("Ngày Bắt Đầu: "+objHopDong.getNgayBatDau());
        holder.tv_ngaykt.setText("Ngày Kết Thúc: "+objHopDong.getNgayKetThuc());

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class HopDongViewholder extends RecyclerView.ViewHolder {
        TextView tv_idphong, tv_ngayky, tv_ngaybd, tv_ngaykt;

        public HopDongViewholder(@NonNull View itemView) {
            super(itemView);
            tv_idphong = itemView.findViewById(R.id.tv_idphong);
            tv_ngayky = itemView.findViewById(R.id.tv_ngayky);
            tv_ngaybd = itemView.findViewById(R.id.tv_ngaybatdau);
            tv_ngaykt = itemView.findViewById(R.id.tv_ngayketthuc);
        }
    }
}
