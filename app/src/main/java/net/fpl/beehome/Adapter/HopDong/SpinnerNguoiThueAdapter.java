package net.fpl.beehome.Adapter.HopDong;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;

public class SpinnerNguoiThueAdapter extends BaseAdapter {
    ArrayList<NguoiThue> arr;

    public SpinnerNguoiThueAdapter(ArrayList<NguoiThue> arr) {
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;

        if (view == null){
            itemView = View.inflate(viewGroup.getContext(), R.layout.row_sp_phong_dialogadd, null);
        }else {
            itemView = view;
        }

        final NguoiThue objNguoiThue = arr.get(i);
        TextView tv_tenphong = itemView.findViewById(R.id.tv_sp_tenphong);
        tv_tenphong.setText(objNguoiThue.getHoTen());

        return itemView;
    }
}
