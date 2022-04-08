package net.fpl.beehome.ui.thongKe;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.R;
import net.fpl.beehome.model.HoaDon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class thongKeFragment extends Fragment {

    TextInputEditText layoutTuNgay, layoutDenNgay;
    TextView tvTotal, tvTotalMonth, tvSearch;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    FirebaseFirestore db;
    ArrayList<HoaDon> arrHD = new ArrayList<>();

    int total = 0;
    int totalMonth = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        db = FirebaseFirestore.getInstance();
        layoutTuNgay = v.findViewById(R.id.layout_tuNgay);
        layoutDenNgay = v.findViewById(R.id.layout_denNgay);
        tvTotal = v.findViewById(R.id.tv_total);
        tvTotalMonth = v.findViewById(R.id.tv_total_month);
        tvSearch = v.findViewById(R.id.tv_search);
        arrHD = getAllHoaDon();

        db.collection(HoaDon.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot snapshot : value){
                    HoaDon hoaDon = snapshot.toObject(HoaDon.class);
                    if (hoaDon.getTrangThaiHD() == 1){
                        total += hoaDon.getTongHD();
                        Log.e("TAG", "onEvent: trang thai: " + hoaDon.getTrangThaiHD() );
                        Log.e("TAG", "onEvent: ngay gd: " + hoaDon.getNgayGD());
                        Log.e("TAG", "onEvent: tien: " + hoaDon.getTongHD() );
                    }
                }
                tvTotal.setText(String.valueOf(total) + " đ");
            }
        });

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);

        Date date1 = Calendar.getInstance().getTime();

        layoutTuNgay.setText(sdf.format(date1));
        layoutDenNgay.setText(sdf.format(date1));

        layoutTuNgay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String NgayDau = dayOfMonth + "/" + (month + 1) + "/" + year;
                        layoutTuNgay.setText(NgayDau);
                    }
                }, y, m, d);
                datePickerDialog.show();
            }
        });

        layoutDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String NgayCuoi = dayOfMonth + "/" + (month + 1) + "/" + year;
                        layoutDenNgay.setText(NgayCuoi);
                    }
                }, y, m, d);
                datePickerDialog.show();
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bd = layoutTuNgay.getText().toString();
                String kt = layoutDenNgay.getText().toString();
                totalMonth = 0;
                for (int i = 0; i < arrHD.size(); i++) {
                    try {
                        if (arrHD.get(i).getNgayGD().compareTo(sdf.parse(bd)) >= 0 && arrHD.get(i).getNgayGD().compareTo(sdf.parse(kt)) <= 0) {
                            totalMonth += arrHD.get(i).getTongHD();
                            Log.e("TAG", "onClick: " + arrHD.get(i).getNgayGD());
                            Log.e("TAG", "onClick: " + arrHD.get(i).getTongHD() );
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                tvTotalMonth.setText(totalMonth+"đ");
            }
        });


        return v;
    }
    public ArrayList<HoaDon> getAllHoaDon(){
        ArrayList<HoaDon> arr = new ArrayList<>();
        db.collection(HoaDon.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for(QueryDocumentSnapshot document : value){
                    HoaDon objHoaDon = document.toObject(HoaDon.class);
                    if(objHoaDon.getTrangThaiHD() == 1) {
                        arr.add(objHoaDon);
                    }

                }
            }
        });
        return arr;
    }
}