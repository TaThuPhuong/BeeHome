package net.fpl.beehome.Adapter.hoaDon;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.HoaDon;

import java.util.ArrayList;

public class HoaDonAdapter extends BaseAdapter {
    ArrayList<HoaDon> lstHD;

    public HoaDonAdapter(ArrayList<HoaDon> lstHD) {
        this.lstHD = lstHD;
    }

    @Override
    public int getCount() {
        return this.lstHD.size();
    }

    @Override
    public Object getItem(int i) {
        return this.lstHD.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if (view == null) {
            v = View.inflate(viewGroup.getContext(), R.layout.item_chua_thanh_toan, null);
        } else {
            v = view;
        }

        TextView tongHD,phong,tienNha,tienDv,giamGia;

        tongHD = v.findViewById(R.id.tv_tongHD);
        phong = v.findViewById(R.id.tv_idPhongHD);
        tienNha = v.findViewById(R.id.tv_tienPhongHD);
        tienDv = v.findViewById(R.id.tv_tienDvHD);
        giamGia = v.findViewById(R.id.tv_giamGiaHD);

//        HoaDon hd = lstHD.get(i);
//        tongHD.setText(hd.getTongHD());
//        phong.setText(hd.getIDPhong());
//        giamGia.setText(hd.getGiamGia());

        return v;
    }
}
