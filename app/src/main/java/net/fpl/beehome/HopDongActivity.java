package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.Adapter.HopDong.HopDongAdapter;
import net.fpl.beehome.DAO.HopDongDAO;
import net.fpl.beehome.model.HopDong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class HopDongActivity extends AppCompatActivity {
    FloatingActionButton btn_add;
    RecyclerView rv_hd;
    HopDongDAO hopDongDAO;
    FirebaseFirestore fb;
    HopDongAdapter hopDongAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hop_dong);

        btn_add = findViewById(R.id.btn_add);
        rv_hd = findViewById(R.id.rv_hd);
        fb = FirebaseFirestore.getInstance();
        hopDongDAO = new HopDongDAO(fb, HopDongActivity.this);
        ArrayList<HopDong> arr = hopDongDAO.getAll();
        hopDongAdapter = new HopDongAdapter(hopDongDAO);

        rv_hd.setAdapter(hopDongAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hopDongAdapter.notifyDataSetChanged();
            }
        },3000);
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



                String[] val = {"P101","P102","P103","P104"};
                ArrayList<String> arr_p = new ArrayList<>(Arrays.asList(val));
                ArrayAdapter arrayAdapter = new ArrayAdapter(HopDongActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr_p);
                sp_phong.setAdapter(arrayAdapter);

                String[] val1 = {"Tien","Cuong","Tu","Hien","Phuong"};
                ArrayList<String> arr_tv = new ArrayList<>(Arrays.asList(val1));
                ArrayAdapter arrayAdapter1 = new ArrayAdapter(HopDongActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr_tv);
                sp_tvien.setAdapter(arrayAdapter1);

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
                                String date = i +"/" + i1 +"/" + i2;
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
                                String date = i +"/" + i1 +"/" + i2;
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
                                String date = i +"/" + i1 +"/" + i2;
                                ed_ngaykt.getEditText().setText(date);
                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialog.show();
            }
        });
    }
}