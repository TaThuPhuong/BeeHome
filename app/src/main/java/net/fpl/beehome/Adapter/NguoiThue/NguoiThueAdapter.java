package net.fpl.beehome.Adapter.NguoiThue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.fpl.beehome.DAO.NguoiThueDAO;
import net.fpl.beehome.R;
import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;

public class NguoiThueAdapter extends RecyclerView.Adapter<NguoiThueAdapter.NguoiThueViewHodel> {
    ArrayList<NguoiThue> arr;

    public NguoiThueAdapter(ArrayList<NguoiThue>list){
       this.arr = list;
    }


    @NonNull
    @Override
    public NguoiThueViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nguoithue,parent,false);
        return new NguoiThueViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiThueViewHodel holder, int position) {
        final NguoiThue objNguoiThue = arr.get(position);
        holder.tv_name.setText("Tên : "+objNguoiThue.getHoTen());
        holder.tv_phong.setText("Phong : "+objNguoiThue.getID_phong());
        holder.tv_sdt.setText("SDT : "+objNguoiThue.getSDT());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class NguoiThueViewHodel extends RecyclerView.ViewHolder{
        TextView tv_name, tv_sdt,tv_phong;
        public NguoiThueViewHodel(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_tennguoithue);
            tv_phong = itemView.findViewById(R.id.tv_phongnguoithue);
            tv_sdt = itemView.findViewById(R.id.tv_sdtnguoithue);
        }
    }
}
