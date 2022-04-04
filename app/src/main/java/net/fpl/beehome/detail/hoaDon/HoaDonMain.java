package net.fpl.beehome.detail.hoaDon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.Time;
import android.view.MenuItem;
import android.widget.DatePicker;

import net.fpl.beehome.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HoaDonMain extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar ab;

    int mYear, mMouth, mDay;
    SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_main);
        //lấy thời gian hiện tại
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        toolbar = findViewById(R.id.toolbar_hoa_don);

        //        set toolbar thay the cho actionBar
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_date3);
        ab.setDisplayHomeAsUpEnabled(true);
        //set thời gian là tháng, năm này
        int t = 1+today.month;
        if(t<10){
            setTitle("0"+t + "-"+today.year);
        }else {
            setTitle(t + "-"+today.year);
        }

    }

    //click icon date để chọn ngày
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DatePickerDialog.OnDateSetListener thangHoaDon = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMouth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear, mMouth, mDay);
                setTitle(sdf.format(c.getTime()));
            }
        };

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMouth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog d = new DatePickerDialog(HoaDonMain.this, 0, thangHoaDon, mYear, mMouth, mDay);
        d.show();
        return super.onOptionsItemSelected(item);
    }
}