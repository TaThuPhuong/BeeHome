package net.fpl.beehome.ui.thongKe;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import net.fpl.beehome.R;
import net.fpl.beehome.model.HoaDon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class thongKeFragment extends Fragment {

    LinearLayout layoutTuNgay, layoutDenNgay;
    TextView tvTuNgay, tvDenNgay, tvTotal, tvTotalMonth, tvSearch;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    FirebaseFirestore db;

    int total = 0;
    int totalMonth = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        layoutTuNgay = v.findViewById(R.id.layout_tuNgay);
        layoutDenNgay = v.findViewById(R.id.layout_denNgay);
        tvTuNgay = v.findViewById(R.id.tv_tuNgay);
        tvDenNgay = v.findViewById(R.id.tv_denNgay);
        tvTotal = v.findViewById(R.id.tv_total);
        tvTotalMonth = v.findViewById(R.id.tv_total_month);

        db = FirebaseFirestore.getInstance();

        db.collection("tb_hoaDon").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        tvTuNgay.setText(sdf.format(date1));
        tvDenNgay.setText(sdf.format(date1));

        layoutTuNgay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvTuNgay.setText( i2 + "-"  + (i1+1) + "-" + i);
                        if (TextUtils.isEmpty(tvDenNgay.getText().toString())){
                            return;
                        } else {
                            query();
                        }
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
                        tvDenNgay.setText( i2 + "-"  + (i1+1) + "-" + i);
                        if (TextUtils.isEmpty(tvTuNgay.getText().toString())){
                            return;
                        } else {
                            query();
                        }
                    }
                }, year, month, date);
                dialog.show();
            }
        });

        return v;
    }

    public void query(){
        Date strTuNgay = null;
        try {
            strTuNgay = sdf.parse(tvTuNgay.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date strDenNgay = null;
        try {
            strDenNgay = sdf.parse(tvDenNgay.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        db.collection(HoaDon.TB_NAME)
                .whereGreaterThanOrEqualTo("ngayGD",  strDenNgay)
                .whereLessThanOrEqualTo("ngayGD", strTuNgay)
                .orderBy("ngayGD")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    HoaDon hoaDon = snapshot.toObject(HoaDon.class);
                    totalMonth += hoaDon.getTongHD();
                }
                tvTotalMonth.setText(total + "đ");
            }
        });
    }

}