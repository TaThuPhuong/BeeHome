package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.NguoiThue.NguoiThueAdapter;
import net.fpl.beehome.Adapter.NguoiThue.PhongSpinnerAdapter;
import net.fpl.beehome.DAO.NguoiThueDAO;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;
import java.util.Arrays;

public class NguoiThue_Activity extends AppCompatActivity {
    FloatingActionButton fladd;
    FirebaseFirestore firestore;
    EditText ed_ten, ed_sodt, ed_email, ed_cccd;
    Spinner sp_nguoithue;
    RecyclerView rc_nguoithue;
    Button btn_them, btn_huy;
    NguoiThueDAO nguoiThueDAO;
    NguoiThueAdapter nguoiThueAdapter;
    ArrayList<String> lsPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_thue);
        rc_nguoithue = findViewById(R.id.rc_nguoithue);
        fladd = findViewById(R.id.fl_nguoithue);
        firestore = FirebaseFirestore.getInstance();
        nguoiThueDAO = new NguoiThueDAO(firestore, NguoiThue_Activity.this);
        nguoiThueAdapter = new NguoiThueAdapter(nguoiThueDAO);
        rc_nguoithue.setAdapter(nguoiThueAdapter);
        lsPhong = getIDPhong();
        Log.d("zzzzzzzz", "onComplete: " + lsPhong.size());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nguoiThueAdapter.notifyDataSetChanged();
            }
        }, 3000);


        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
    }

    public ArrayList<String> getIDPhong() {
        ArrayList<String> ls = new ArrayList<>();
        firestore.collection(Phong.TB_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    ls.add(documentSnapshot.getId());
                    Log.d("zzzzzzzz", "onComplete: " + documentSnapshot.getId());
                }
            }
        });
        Log.d("zzzzzzzz", "onComplete: " + ls.size());

        return ls;
    }

    private void opendialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_them_nguoithue, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ed_ten = view.findViewById(R.id.ed_hovatennguoithue);
        ed_sodt = view.findViewById(R.id.ed_sodienthoainguoithue);
        ed_email = view.findViewById(R.id.ed_emailnguoithue);
        ed_cccd = view.findViewById(R.id.ed_cmndnguoithue);
        sp_nguoithue = view.findViewById(R.id.spn_phong);
        btn_them = view.findViewById(R.id.btn_dangkinguoithue);
        btn_huy = view.findViewById(R.id.btn_huynguoithue);
        PhongSpinnerAdapter adapter = new PhongSpinnerAdapter(lsPhong);
        sp_nguoithue.setAdapter(adapter);
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
                if (TextUtils.isEmpty(ten) || TextUtils.isEmpty(sodt) || TextUtils.isEmpty(email) || TextUtils.isEmpty(cccd)) {
                    nguoiThueDAO.thongbaonguoithue(1,"Không Được Để Trống");
                } else {
                    NguoiThue nguoiThue = new NguoiThue();
                    nguoiThue.setID_thanhvien(sodt);
                    nguoiThue.setHoTen(ten);
                    nguoiThue.setSDT(sodt);
                    nguoiThue.setPassword(sodt);
                    nguoiThue.setEmail(email);
                    nguoiThue.setCCCD(cccd);
                    nguoiThue.setID_phong(sp_nguoithue.getSelectedItem().toString());

                    themNguoiThue(nguoiThue);
                    dialog.dismiss();
                }
            }
        });

    }

    private void themNguoiThue(NguoiThue nguoiThue) {
        firestore.collection(NguoiThue.TB_NGUOITHUE).document(nguoiThue.getSDT())
                .set(nguoiThue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        nguoiThueDAO.thongbaonguoithue(0,"Thêm Thành Công");
                        nguoiThueAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        nguoiThueDAO.thongbaonguoithue(1,"Thêm Thất Bại");
                    }
                });
    }


}