package net.fpl.beehome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.Adapter.SuCo.SuCoAdapter;
import net.fpl.beehome.DAO.SuCoDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SuCoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    FloatingActionButton btn_add;
    RecyclerView rv_cs;
    FirebaseFirestore fb;
    SwipeRefreshLayout swipeRefreshLayout;

    SuCoDAO suCoDAO;
    SuCoAdapter suCoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_co);

        btn_add = findViewById(R.id.btn_add);
        rv_cs = findViewById(R.id.rv_sc);
        fb = FirebaseFirestore.getInstance();
        swipeRefreshLayout = findViewById(R.id.sw_rv);
        suCoDAO = new SuCoDAO(fb ,SuCoActivity.this);
        suCoAdapter = new SuCoAdapter(suCoDAO);
        rv_cs.setAdapter(suCoAdapter);


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                suCoAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(SuCoActivity.this, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_add_sc);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                TextInputLayout ed_mota = dialog.findViewById(R.id.ed_mota);
                TextInputLayout ed_ngbao = dialog.findViewById(R.id.ed_ngaybaocao) ;
                Spinner sp_phong = dialog.findViewById(R.id.sp_hd_phong);

                String[] val = {"P101","P102","P103","P104"};
                ArrayList<String> arr_p = new ArrayList<>(Arrays.asList(val));
                ArrayAdapter arrayAdapter = new ArrayAdapter(SuCoActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr_p);
                sp_phong.setAdapter(arrayAdapter);

                Calendar calendar = Calendar.getInstance();
                final int y = calendar.get(Calendar.YEAR);
                final int m = calendar.get(Calendar.MONTH);
                final int d = calendar.get(Calendar.DAY_OF_MONTH);
                ed_ngbao.getEditText().setText(y +"/"+m+"/"+d);

                dialog.show();
            }
        });
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                suCoAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }

}