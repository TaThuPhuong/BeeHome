package net.fpl.beehome.Adapter.NguoiThue;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.fpl.beehome.R;

import java.util.ArrayList;

public class PhongSpinnerAdapter extends BaseAdapter {
    ArrayList<String> lsPhong;

    public PhongSpinnerAdapter(ArrayList<String> lsPhong) {
        this.lsPhong = lsPhong;
    }

    @Override
    public int getCount() {
        return this.lsPhong.size();
    }

    @Override
    public String getItem(int i) {
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
            v = View.inflate(viewGroup.getContext(), R.layout.item_phong_spinner, null);
        } else {
            v = view;
        }
        TextView tvPhong = v.findViewById(R.id.tv_phong);
        String phong = lsPhong.get(i);
        tvPhong.setText(phong);
        return v;
    }
}
