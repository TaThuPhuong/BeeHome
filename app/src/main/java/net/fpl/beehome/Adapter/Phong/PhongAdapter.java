package net.fpl.beehome.Adapter.Phong;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class PhongAdapter extends BaseAdapter {
    ArrayList<Phong> lsPhong;

    public PhongAdapter(ArrayList<Phong> lsPhong) {
        this.lsPhong = lsPhong;
    }

    @Override
    public int getCount() {
        return this.lsPhong.size();
    }

    @Override
    public Phong getItem(int i) {
        return this.lsPhong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if (view == null) {
            v = View.inflate(viewGroup.getContext(), R.layout.item_phong, null);
        } else {
            v = view;
        }
        TextView tvSoPhong, tvTrangThai;
        LinearLayout llItem;
        tvSoPhong = v.findViewById(R.id.tv_phong);
        tvTrangThai = v.findViewById(R.id.tv_trang_thai);
        llItem = v.findViewById(R.id.ll_item);
        Phong phong = lsPhong.get(i);
        tvSoPhong.setText(phong.getSoPhong());
        tvTrangThai.setText(phong.getTrangThai());
        return v;
    }
}
