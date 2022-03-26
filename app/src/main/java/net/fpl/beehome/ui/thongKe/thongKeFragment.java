package net.fpl.beehome.ui.thongKe;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import net.fpl.beehome.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class thongKeFragment extends Fragment {

    LinearLayout layoutTuNgay, layoutDenNgay;
    TextView tvTuNgay, tvDenNgay;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        layoutTuNgay = v.findViewById(R.id.layout_tuNgay);
        layoutDenNgay = v.findViewById(R.id.layout_denNgay);
        tvTuNgay = v.findViewById(R.id.tv_tuNgay);
        tvDenNgay = v.findViewById(R.id.tv_denNgay);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);

        layoutTuNgay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvTuNgay.setText( i2 + " / "  + i1 + " / " + i);
                    }
                }, year, month, date);
                dialog.show();
            }
        });

        layoutDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvDenNgay.setText( i2 + " / "  + i1 + " / " + i);
                    }
                }, year, month, date);
                dialog.show();
            }
        });

        return v;


    }
}