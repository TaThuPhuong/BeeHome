package net.fpl.beehome;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.HopDong.HopDongAdapter;
import net.fpl.beehome.Adapter.HopDong.SpinnerNguoiThueAdapter;
import net.fpl.beehome.Adapter.HopDong.SpinnerPhongAdapter;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class HopDongActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    FloatingActionButton btn_add;
    RecyclerView rv_hd;
    FirebaseFirestore fb;
    SwipeRefreshLayout swipeRefreshLayout;
    HopDongAdapter hopDongAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hop_dong);
        btn_add = findViewById(R.id.btn_add);
        rv_hd = findViewById(R.id.rv_hd);
        fb = FirebaseFirestore.getInstance();
        swipeRefreshLayout = findViewById(R.id.sw_rv);
        rv_hd.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<HopDong> arr = getAll();
        ArrayList<Phong> arrphong = getSPPHong();
        ArrayList<NguoiThue> arrnguoithue = getSPNguoiThue();
        ArrayList<Phong> arrallphong = getAllPHong();



        hopDongAdapter = new HopDongAdapter(arr, HopDongActivity.this, fb, arrallphong, arrnguoithue);
        rv_hd.setAdapter(hopDongAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);


        //show dialog add
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(HopDongActivity.this, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_add_hopdong);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                Spinner sp_phong = dialog.findViewById(R.id.sp_hd_phong);
                Spinner sp_tvien = dialog.findViewById(R.id.sp_hd_tvien);
                Spinner sp_kyhan = dialog.findViewById(R.id.sp_hd_kyhan);
                Button btn_add = dialog.findViewById(R.id.btn_add);
                TextInputLayout ed_ngayky = dialog.findViewById(R.id.ed_ngaykyhd);
                TextInputLayout ed_ngaybd = dialog.findViewById(R.id.ed_ngaybd);
                TextInputLayout ed_ngaykt = dialog.findViewById(R.id.ed_ngaykt);
                TextInputLayout ed_songuoithue = dialog.findViewById(R.id.ed_songuoithue);
                TextView tv_er_p = dialog.findViewById(R.id.tv_er_phong);
                TextView tv_er_ngthue = dialog.findViewById(R.id.tv_er_ngthue);
                ed_ngaybd.setError(null);
                ed_ngaykt.setError(null);
                ed_ngayky.setError(null);
                ed_songuoithue.setError(null);

                SpinnerPhongAdapter phongAdapter = new SpinnerPhongAdapter(arrphong);
                sp_phong.setAdapter(phongAdapter);

                SpinnerNguoiThueAdapter nguoiThueAdapter = new SpinnerNguoiThueAdapter(arrnguoithue);
                sp_tvien.setAdapter(nguoiThueAdapter);

                Calendar calendar = Calendar.getInstance();
                final int y = calendar.get(Calendar.YEAR);
                final int m = calendar.get(Calendar.MONTH);
                final int d = calendar.get(Calendar.DAY_OF_MONTH);

                ArrayList<Integer> arrkyhan = new ArrayList<>();
                arrkyhan.add(1);
                arrkyhan.add(3);
                arrkyhan.add(6);
                arrkyhan.add(12);
                ArrayAdapter arrayAdapter = new ArrayAdapter(dialog.getContext(), android.R.layout.simple_spinner_dropdown_item, arrkyhan);
                sp_kyhan.setAdapter(arrayAdapter);

                ed_ngayky.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialog1 = new DatePickerDialog(HopDongActivity.this, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String month = String.valueOf(i1);
                                String day = String.valueOf(i2);

                                if(String.valueOf(i1).length() == 1){
                                    month = "0"+i1;
                                }
                                if(String.valueOf(i2).length() == 1){
                                    day = "0"+i2;
                                }

                                ed_ngayky.getEditText().setText(i + "-" +month + "-" + day);
                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });
                ed_ngaybd.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialog1 = new DatePickerDialog(HopDongActivity.this, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String monthbd = String.valueOf(i1);
                                String daybd = String.valueOf(i2);

                                if(String.valueOf(i1).length() == 1){
                                    monthbd = "0"+i1;
                                }
                                if(String.valueOf(i2).length() == 1){
                                    daybd = "0"+i2;
                                }

                                ed_ngaybd.getEditText().setText(i + "-" +monthbd + "-" + daybd);
                                if(Integer.parseInt(sp_kyhan.getSelectedItem()+"")+i1 > 12){
                                    int imonthkt = (Integer.parseInt(sp_kyhan.getSelectedItem()+"")+i1) - 12 ;
                                    int iyearkt = i+1;
                                    String daykt = String.valueOf(i2);
                                    String monthkt = String.valueOf(imonthkt);

                                    if(String.valueOf(imonthkt).length() == 1){
                                        monthkt = "0"+imonthkt;
                                    }
                                    if(String.valueOf(i2).length() == 1){
                                         daykt = "0"+i2;
                                    }
                                    ed_ngaykt.getEditText().setText(iyearkt + "-" +monthkt + "-" + daykt);
                                }else{
                                    int imonthkt = Integer.parseInt(sp_kyhan.getSelectedItem()+"") + i1;
                                    String daykt = String.valueOf(i2);
                                    String monthkt = String.valueOf(imonthkt);
                                    if(String.valueOf(imonthkt).length() == 1){
                                        monthkt = "0"+imonthkt;
                                    }
                                    if(String.valueOf(i2).length() == 1){
                                        daykt = "0"+i2;
                                    }
                                    ed_ngaykt.getEditText().setText(i + "-" +monthkt + "-" + daykt);

                                }

                                sp_kyhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        DatePickerDialog dialog1 = new DatePickerDialog(HopDongActivity.this , R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                                String monthbd = String.valueOf(i1);
                                                String daybd = String.valueOf(i2);

                                                if(String.valueOf(i1).length() == 1){
                                                    monthbd = "0"+i1;
                                                }
                                                if(String.valueOf(i2).length() == 1){
                                                    daybd = "0"+i2;
                                                }

                                                ed_ngaybd.getEditText().setText(i + "-" +monthbd + "-" + daybd);

                                                if(Integer.parseInt(sp_kyhan.getSelectedItem()+"")+i1 > 12){
                                                    int imonthkt = (Integer.parseInt(sp_kyhan.getSelectedItem()+"")+i1) - 12 ;
                                                    int iyearkt = i+1;
                                                    String daykt = String.valueOf(i2);
                                                    String monthkt = String.valueOf(imonthkt);

                                                    if(String.valueOf(imonthkt).length() == 1){
                                                        monthkt = "0"+monthkt;
                                                    }
                                                    if(String.valueOf(i2).length() == 1){
                                                        daykt = "0"+i2;
                                                    }
                                                    ed_ngaykt.getEditText().setText(iyearkt + "-" +monthkt + "-" + daykt);
                                                }else{
                                                    int imonthkt = Integer.parseInt(sp_kyhan.getSelectedItem()+"") + i1;
                                                    String daykt = String.valueOf(i2);
                                                    String monthkt = String.valueOf(imonthkt);
                                                    if(String.valueOf(imonthkt).length() == 1){
                                                        monthkt = "0"+monthkt;
                                                    }
                                                    if(String.valueOf(i2).length() == 1){
                                                        daykt = "0"+i2;
                                                    }
                                                    ed_ngaykt.getEditText().setText(i + "-" +monthkt + "-" + daykt);

                                                }
                                            }
                                        },y,m,d);
                                        dialog1.show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });


//              btn add
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ednbd = ed_ngaybd.getEditText().getText().toString();
                        String ednkt = ed_ngaykt.getEditText().getText().toString();
                        String ednky = ed_ngayky.getEditText().getText().toString();
                        String edsn = ed_songuoithue.getEditText().getText().toString();
                        HopDong objHopDong = new HopDong();
                        Phong objPhong = (Phong) sp_phong.getSelectedItem();
                        NguoiThue objNguoiThue = (NguoiThue) sp_tvien.getSelectedItem();

                        if(objPhong.getSoPhong().equalsIgnoreCase("Trống")&&
                                objNguoiThue.getHoTen().equalsIgnoreCase("Trống")&&
                                ednky.length()==0&&
                                ednbd.length()==0&&
                                ednkt.length()==0&&
                                edsn.length()==0 ){
                            tv_er_p.setTextSize(14);
                            tv_er_p.setText("Không có phòng trống");
                            tv_er_ngthue.setTextSize(14);
                            tv_er_ngthue.setText("Không có người thuê");
                            ed_ngayky.setError("Trường không được bỏ trống");
                            ed_ngaybd.setError("Trường không được bỏ trống");
                            ed_ngaykt.setError("Trường không được bỏ trống");
                            ed_songuoithue.setError("Trường không được bỏ trống");
                            return;

                        }else if(objPhong.getSoPhong().equalsIgnoreCase("Trống")){
                            tv_er_ngthue.setTextSize(14);
                            tv_er_ngthue.setText("Không có người thuê");
                            return;

                        }else if(objNguoiThue.getHoTen().equalsIgnoreCase("Trống")){
                            tv_er_ngthue.setTextSize(14);
                            tv_er_ngthue.setText("Không có người thuê");
                            return;
                        }else if(ednky.length()==0){
                            ed_ngayky.setError("Trường không được bỏ trống");
                            return;
                        }else if(ednbd.length()==0){
                            ed_ngaybd.setError("Trường không được bỏ trống");
                            return;
                        }else if(ednkt.length()==0){
                            ed_ngaykt.setError("Trường không được bỏ trống");
                            return;
                        }else if(edsn.length()==0){
                            ed_songuoithue.setError("Trường không được bỏ trống");
                            return;
                        }else if(ednbd.length() >= 2){
                            ed_songuoithue.setError("Nhập tối da 1 ký tự");
                        }
                        else if(!checkDateFormat(ednky)){
                            ed_ngayky.setError("Sai định dạng");
                            return;
                        }else if(!checkDateFormat(ednbd)){
                            ed_ngaybd.setError("Sai định dạng");
                            return;
                        }else if(!checkDateFormat(ednkt)){
                            ed_ngaykt.setError("Sai định dạng");
                            return;
                        }




                        objHopDong.setId_hop_dong(objPhong.getIDPhong() + objNguoiThue.getHoTen()+ed_songuoithue.getEditText().getText().toString());
                        objHopDong.setId_chu_tro("1");

                        objHopDong.setId_phong(objPhong.getIDPhong());
                        objHopDong.setId_thanh_vien(objNguoiThue.getId_thanhvien());
                        objHopDong.setKyHan(Integer.parseInt(sp_kyhan.getSelectedItem()+""));
                        objHopDong.setNgayKiHD(ed_ngayky.getEditText().getText().toString());
                        objHopDong.setNgayBatDau(ed_ngaybd.getEditText().getText().toString());
                        objHopDong.setNgayKetThuc(ed_ngaykt.getEditText().getText().toString());
                        objHopDong.setSoNguoiThue(Integer.parseInt(ed_songuoithue.getEditText().getText().toString()));


                        fb.collection(HopDong.TB_NAME).document(objHopDong.getId_hop_dong())
                                .set(objHopDong)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        fb.collection(Phong.TB_NAME).document(objPhong.getIDPhong())
                                                .update(Phong.COL_TRANG_THAI, "Đang thuê");
                                        fb.collection(NguoiThue.TB_NGUOITHUE).document(objNguoiThue.getId_thanhvien())
                                                .update(NguoiThue.COL_ID_PHONG, objPhong.getIDPhong());
                                        Toast.makeText(HopDongActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        hopDongAdapter.notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(HopDongActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hopDongAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    public ArrayList<HopDong> getAll(){
        ArrayList<HopDong> arr = new ArrayList<>();
        fb.collection(HopDong.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    HopDong objHopDong = document.toObject(HopDong.class);
                    arr.add(objHopDong);

                    Log.d("aaaaaaa", document.getId() + " => " + document.getData());
                }
                hopDongAdapter.notifyDataSetChanged();
            }
        });

        return arr;
    }

    public ArrayList<Phong> getSPPHong(){
        ArrayList<Phong> arr = new ArrayList<>();
        fb.collection(Phong.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for(QueryDocumentSnapshot document : value){
                    Phong objPhong = document.toObject(Phong.class);
                    if(objPhong.getTrangThai().equalsIgnoreCase("Trống")) {
                        arr.add(objPhong);
                    }
                }
                if(arr.size() == 0){
                    Phong obj = new Phong();
                    obj.setSoPhong("Trống");
                    obj.setIDPhong("Trống");
                    arr.add(obj);
                }
            }
        });

        return arr;
}

    public ArrayList<Phong> getAllPHong(){
        ArrayList<Phong> arr = new ArrayList<>();
        fb.collection(Phong.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for(QueryDocumentSnapshot document : value){
                    Phong objPhong = document.toObject(Phong.class);
                        arr.add(objPhong);
                }
            }
        });
        return arr;
    }

    public ArrayList<NguoiThue> getSPNguoiThue(){
        ArrayList<NguoiThue> arr = new ArrayList<>();
        fb.collection(NguoiThue.TB_NGUOITHUE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for(QueryDocumentSnapshot document : value){
                    NguoiThue objNguoiThue = document.toObject(NguoiThue.class);
                    if(objNguoiThue.getId_phong().equalsIgnoreCase("Trống")){
                        arr.add(objNguoiThue);
                    }
                }
                if(arr.size() == 0){
                    NguoiThue obj = new NguoiThue();
                    obj.setHoTen("Trống");
                    obj.setId_phong("Trống");
                    arr.add(obj);
                }
            }
        });


        return arr;
    }

    public Boolean checkDateFormat(String date){
        if (date == null || !date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"))
            return false;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
        try {
            format.parse(date);
            return true;
        }catch (ParseException e){
            return false;
        }
    }

}