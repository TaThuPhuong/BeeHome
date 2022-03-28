package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    ArrayList<Phong> lsPhong;
    public PhongSpinnerAdapter phongSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_thue);
        rc_nguoithue = findViewById(R.id.rc_nguoithue);
        fladd = findViewById(R.id.fl_nguoithue);
        firestore = FirebaseFirestore.getInstance();
        ArrayList<NguoiThue> list = getall();
        nguoiThueAdapter = new NguoiThueAdapter(list);

        rc_nguoithue.setAdapter(nguoiThueAdapter);
        lsPhong = getIDPhong();
        Log.d("zzzzzzzz", "onComplete: " + lsPhong.size());

        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
    }

    public ArrayList<Phong> getIDPhong() {
        ArrayList<Phong> ls = new ArrayList<>();
        firestore.collection(Phong.TB_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    Phong phong = documentSnapshot.toObject(Phong.class);
                    if (phong.getTrangThai().equals("Đang thuê")){
                        ls.add(phong);
                    }

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
        phongSpinnerAdapter = new PhongSpinnerAdapter(lsPhong);
        sp_nguoithue.setAdapter(phongSpinnerAdapter);
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
                if (TextUtils.isEmpty(ten)) {
                    nguoiThueDAO.thongbaonguoithue(1,"Không Được Để Trống Tên");
                }else if (!isNumber(sodt.toString())){
                    nguoiThueDAO.thongbaonguoithue(1,"Không Đúng Số Điện Thoại");
                }else if (!isEmail(email.toString())){
                    nguoiThueDAO.thongbaonguoithue(1,"Không Đúng Định Dạng Email");
                }else if (cccd.length() != 12 ){
                    nguoiThueDAO.thongbaonguoithue(1,"Căn Cước 12 Số");
                }
                else {
                    NguoiThue nguoiThue = new NguoiThue();
                    nguoiThue.setID_thanhvien(sodt);
                    nguoiThue.setHoTen(ten);
                    nguoiThue.setSDT(sodt);
                    nguoiThue.setPassword(sodt);
                    nguoiThue.setEmail(email);
                    nguoiThue.setCCCD(cccd);
                    Phong phong = (Phong)sp_nguoithue.getSelectedItem();
                    nguoiThue.setID_phong(phong.getIDPhong());

                    themNguoiThue(nguoiThue);
                    dialog.dismiss();
                }
            }
        });

    }
    public static boolean isEmail(CharSequence charSequence){
        return !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }
    public static boolean isNumber(CharSequence charSequence){
        return !TextUtils.isEmpty(charSequence) && Patterns.PHONE.matcher(charSequence).matches();
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
    public ArrayList<NguoiThue> getall(){
        ArrayList<NguoiThue> arr = new ArrayList<>();
        firestore.collection(NguoiThue.TB_NGUOITHUE)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        arr.clear();
                        for (QueryDocumentSnapshot documentSnapshot : value){
                            NguoiThue objNguoiThue = documentSnapshot.toObject(NguoiThue.class);
                            arr.add(objNguoiThue);
                            nguoiThueAdapter.notifyDataSetChanged();
                        }
                    }
                });
        return arr;
    }

}