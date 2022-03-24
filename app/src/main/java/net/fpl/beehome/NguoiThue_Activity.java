package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.Adapter.NguoiThue.NguoiThueAdapter;
import net.fpl.beehome.DAO.NguoiThueDAO;
import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;
import java.util.Arrays;

public class NguoiThue_Activity extends AppCompatActivity {
    FloatingActionButton fladd;
    FirebaseFirestore firestore;
    EditText ed_ten, ed_sodt, ed_email,ed_cccd ;
    Spinner sp_nguoithue;
    RecyclerView rc_nguoithue;
    Button btn_them, btn_huy;
    NguoiThueDAO nguoiThueDAO;
    NguoiThueAdapter nguoiThueAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_thue);
        rc_nguoithue = findViewById(R.id.rc_nguoithue);
        fladd = findViewById(R.id.fl_nguoithue);
        firestore = FirebaseFirestore.getInstance();
        nguoiThueDAO = new NguoiThueDAO(firestore,NguoiThue_Activity.this);
        nguoiThueAdapter = new NguoiThueAdapter(nguoiThueDAO);
        rc_nguoithue.setAdapter(nguoiThueAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nguoiThueAdapter.notifyDataSetChanged();
            }
        },6000);


        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
    }
    private void opendialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_them_nguoithue, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        ed_ten = view.findViewById(R.id.ed_hovatennguoithue);
        ed_sodt = view.findViewById(R.id.ed_sodienthoainguoithue);
        ed_email = view.findViewById(R.id.ed_emailnguoithue);
        ed_cccd = view.findViewById(R.id.ed_cmndnguoithue);
        sp_nguoithue = view.findViewById(R.id.sp_phong);
        btn_them = view.findViewById(R.id.btn_dangkinguoithue);
        btn_huy = view.findViewById(R.id.btn_huynguoithue);
         btn_huy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dialog.dismiss();
             }
         });
         btn_them.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String ten = ed_ten.getText().toString();
                 String sodt = ed_sodt.getText().toString();
                 String email = ed_email.getText().toString();
                 String cccd = ed_cccd.getText().toString();
                 if (TextUtils.isEmpty(ten) || TextUtils.isEmpty(sodt)||TextUtils.isEmpty(email)||TextUtils.isEmpty(cccd)){
                     Toast.makeText(NguoiThue_Activity.this, "Thieu", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     NguoiThue nguoiThue = new NguoiThue();
                     nguoiThue.setID_thanhvien(sodt);
                     nguoiThue.setHoTen(ten);
                     nguoiThue.setSDT(sodt);
                     nguoiThue.setPassword(sodt);
                     nguoiThue.setEmail(email);
                     nguoiThue.setCCCD(cccd);
                     nguoiThue.setID_phong("P123");
                     String[] val = {"P101","P102","P103","P104"};
                     ArrayList<String> arr_p = new ArrayList<>(Arrays.asList(val));
                     ArrayAdapter arrayAdapter = new ArrayAdapter(NguoiThue_Activity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr_p);
                     sp_nguoithue.setAdapter(arrayAdapter);
                     themNguoiThue(nguoiThue);
                     dialog.dismiss();
                 }
             }
         });

    }
    private void themNguoiThue(NguoiThue nguoiThue){
        firestore.collection(NguoiThue.TB_NGUOITHUE).document(nguoiThue.getSDT())
                .set(nguoiThue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(NguoiThue_Activity.this, "Them Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NguoiThue_Activity.this, "Them That Bai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}