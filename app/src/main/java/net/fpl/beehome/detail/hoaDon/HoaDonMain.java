package net.fpl.beehome.detail.hoaDon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.MainActivity;
import net.fpl.beehome.MainNguoiThueActivity;
import net.fpl.beehome.R;
import net.fpl.beehome.detail.hoaDon.Tab.HoaDonChuaThanhToan;
import net.fpl.beehome.model.Admin;
import net.fpl.beehome.model.NguoiThue;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HoaDonMain extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar ab;
    FirebaseFirestore fb;

    int mYear, mMouth, mDay;
    SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
    NguoiThue nguoiThue;

    public NguoiThue getNguoiThue() {
        return nguoiThue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_main);
        fb = FirebaseFirestore.getInstance();
        //lấy thời gian hiện tại
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        toolbar = findViewById(R.id.toolbar_hoa_don);
        Intent intent = getIntent();
        String quyen = intent.getStringExtra("quyen");
        if(quyen.equalsIgnoreCase("nt")){
            nguoiThue = (NguoiThue) intent.getSerializableExtra("nt");
            Log.d("arrHDP", "onViewCreated: "+nguoiThue);
        }

        //        set toolbar thay the cho actionBar
        setSupportActionBar(toolbar);
//        ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_date3);
//        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Hóa Đơn");




        //set thời gian là tháng, năm này
//        int t = 1+today.month;
//        if(t<10){
//            setTitle("0"+t + "-"+today.year);
//        }else {
//            setTitle(t + "-"+today.year);
//        }


    }

    //click icon date để chọn ngày
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        DatePickerDialog.OnDateSetListener thangHoaDon = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                mYear = year;
//                mMouth = month;
//                mDay = dayOfMonth;
//                GregorianCalendar c = new GregorianCalendar(mYear, mMouth, mDay);
//                setTitle(sdf.format(c.getTime()));
//            }
//        };
//
//        Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMouth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog d = new DatePickerDialog(HoaDonMain.this, 0, thangHoaDon, mYear, mMouth, mDay);
//        d.show();
//        return super.onOptionsItemSelected(item);
//    }

}