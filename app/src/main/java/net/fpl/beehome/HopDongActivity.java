package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

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

        hopDongAdapter = new HopDongAdapter(arr, HopDongActivity.this, fb, arrphong, arrnguoithue);
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
                Button btn_add = dialog.findViewById(R.id.btn_add);
                TextInputLayout ed_kyhan = dialog.findViewById(R.id.ed_kyhan);
                TextInputLayout ed_ngayky = dialog.findViewById(R.id.ed_ngaykyhd);
                TextInputLayout ed_ngaybd = dialog.findViewById(R.id.ed_ngaybd);
                TextInputLayout ed_ngaykt = dialog.findViewById(R.id.ed_ngaykt);
                TextInputLayout ed_songuoithue = dialog.findViewById(R.id.ed_songuoithue);

                ed_kyhan.setError(null);
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
                ed_ngayky.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialog1 = new DatePickerDialog(HopDongActivity.this, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String date = i +"-" + i1 +"-" + i2;
                                ed_ngayky.getEditText().setText(date);
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
                                String date = i +"-" + i1 +"-" + i2;
                                ed_ngaybd.getEditText().setText(date);
                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });
                ed_ngaykt.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialog1 = new DatePickerDialog(HopDongActivity.this, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String date = i +"-" + i1 +"-" + i2;
                                ed_ngaykt.getEditText().setText(date);
                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });


//              btn add
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String edkyhan = ed_kyhan.getEditText().getText().toString();
                        String ednbd = ed_ngaybd.getEditText().getText().toString();
                        String ednkt = ed_ngaykt.getEditText().getText().toString();
                        String ednky = ed_ngayky.getEditText().getText().toString();
                        String edsn = ed_songuoithue.getEditText().getText().toString();

                        if(edkyhan.length() == 0){
                            ed_kyhan.setError("Trường không được bỏ trống");
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
                        }else if(!checkDateFormat(ednky)){
                            ed_ngayky.setError("Sai định dạng");
                            return;
                        }else if(!checkDateFormat(ednbd)){
                            ed_ngaybd.setError("Sai định dạng");
                            return;
                        }else if(!checkDateFormat(ednkt)){
                            ed_ngaykt.setError("Sai định dạng");
                            return;
                        }



                        HopDong objHopDong = new HopDong();
                        Phong objPhong = (Phong) sp_phong.getSelectedItem();
                        NguoiThue objNguoiThue = (NguoiThue) sp_tvien.getSelectedItem();
                        objHopDong.setId_hop_dong(objPhong.getIDPhong() + objNguoiThue.getHoTen());
                        objHopDong.setId_chu_tro("1");

                        objHopDong.setId_phong(objPhong.getIDPhong());
                        objHopDong.setId_thanh_vien(objNguoiThue.getID_thanhvien());
                        objHopDong.setKyHan(ed_kyhan.getEditText().getText().toString());
                        objHopDong.setNgayKiHD(ed_ngayky.getEditText().getText().toString());
                        objHopDong.setNgayBatDau(ed_ngaybd.getEditText().getText().toString());
                        objHopDong.setNgayKetThuc(ed_ngaykt.getEditText().getText().toString());
                        objHopDong.setSoNguoiThue(Double.parseDouble(ed_songuoithue.getEditText().getText().toString()));


                        fb.collection(HopDong.TB_NAME).document(objHopDong.getId_hop_dong())
                                .set(objHopDong)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
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
                    arr.add(objNguoiThue);
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