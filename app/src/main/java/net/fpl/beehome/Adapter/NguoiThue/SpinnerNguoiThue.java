package net.fpl.beehome.Adapter.NguoiThue; 

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.fpl.beehome.R;

import java.util.ArrayList;

public class SpinnerNguoiThue extends BaseAdapter {
    ArrayList<String> list;

    public SpinnerNguoiThue(ArrayList<String>list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 ;
        if (view == null){
            view1 = View.inflate(viewGroup.getContext(), R.layout.item_spinner_nguoithue,null);
        }else {
            view1 = view;
        }

        TextView tv_spnguoithue;
        tv_spnguoithue = view.findViewById(R.id.tv_spn_nguoithue);
        String obj = list.get(i);
        tv_spnguoithue.setText(obj);
        return view1;
    }
}
