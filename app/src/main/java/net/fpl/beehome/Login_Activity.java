package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.model.Admin;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.ui.phong.PhongFragment;

import java.util.ArrayList;

public class Login_Activity extends AppCompatActivity {
    TextInputLayout edNguoidung, edMatkhau;
    Button btnDangNhap;
    CheckBox chk;
    TextView tvQuenMK;
    Animation animation;
    ConstraintLayout constraintLayout;
    MySharedPreferences mySharedPreferences;
    FirebaseFirestore fb;
    FirebaseAuth fba;
    Admin admin;
    NguoiThue nguoiThue;
    PhongFragment phongFragment;
    ProgressBarLoading progressBarLoading;
    ArrayList<NguoiThue> lsNguoiThue;

    public void init() {
        fb = FirebaseFirestore.getInstance();
        edNguoidung = findViewById(R.id.ed_sdt);
        edMatkhau = findViewById(R.id.ed_pass);
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        chk = findViewById(R.id.chk_remember);
        tvQuenMK = findViewById(R.id.tv_quen_mk);
        constraintLayout = findViewById(R.id.aniLogin);
        phongFragment = new PhongFragment();
        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        animation = AnimationUtils.loadAnimation(this, R.anim.login);
        constraintLayout.startAnimation(animation);
        progressBarLoading = new ProgressBarLoading(Login_Activity.this);
        getAdmin();
        getAllNguoiThue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        phongFragment.setUnErr(edNguoidung);
        phongFragment.setUnErr(edMatkhau);

        // set tên đăng nhập vs mk có trong mySharedPreferences

        if (mySharedPreferences.getStatus(MySharedPreferences.STATUS_KEY)) {
            edNguoidung.getEditText().setText(mySharedPreferences.getUser(MySharedPreferences.USER_KEY));
            edMatkhau.getEditText().setText(mySharedPreferences.getPassword(MySharedPreferences.PASSWORD_KEY));
            chk.setChecked(true);
        }
        // khi bấm nút đăng nhập

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edMatkhau.getEditText().getText().toString();
                String user = edNguoidung.getEditText().getText().toString();


                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    if (TextUtils.isEmpty(user)) {
                        edNguoidung.setError("Nhập tên đăng nhập hoặc số điện thoại");
                    }
                    if (TextUtils.isEmpty(pass)) {
                        edMatkhau.setError("Nhập mật khẩu");
                    }
                    return;
                } else {
                    for (NguoiThue nt : lsNguoiThue
                         ) {
                        if(nt.getSdt().equals(nt.getSdt())){
                            nguoiThue =nt;
                        }
                    }
                }
                progressBarLoading.showLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (admin == null && nguoiThue == null) {
                            edNguoidung.setError("Sai tên đăng nhập hoặc mật khẩu");
                            progressBarLoading.hideLoaing();
                        } else {
                            if (admin!= null && admin.getPassword().equals(pass)) {
                                progressBarLoading.hideLoaing();
                                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                intent.putExtra("user", user);
                                intent.putExtra("ad", admin);
                                startActivity(intent);
                                nhoMatKhau(chk.isChecked(), user, pass);
                                Toast.makeText(Login_Activity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();

                            } else if (nguoiThue.getPassword().equals(pass)) {
                                progressBarLoading.hideLoaing();
                                Intent intent = new Intent(Login_Activity.this, MainNguoiThueActivity.class);
                                intent.putExtra("user", user);
                                intent.putExtra("nt", nguoiThue);

                                startActivity(intent);
                                nhoMatKhau(chk.isChecked(), user, pass);
                                Toast.makeText(Login_Activity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();

                            } else {
                                edMatkhau.setError("Sai mật khẩu");
                                progressBarLoading.hideLoaing();
                            }
                        }
                    }
                }, 3000);

            }
        });

        tvQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void getAdmin() {
        fb.collection(Admin.TB_NAME).document("admin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            admin = task.getResult().toObject(Admin.class);
                        }
                    }
                });
//        return admin;
    }

    public void getNguoiThue(String str) {
        fb.collection(NguoiThue.TB_NGUOITHUE).document(str)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            nguoiThue = task.getResult().toObject(NguoiThue.class);
//                            Log.d("TAG", "onComplete: " + nguoiThue.toString());
                        }
                    }
                });
//        return nguoiThue;
    }

    public ArrayList<NguoiThue> getAllNguoiThue() {
        lsNguoiThue = new ArrayList<>();
        fb.collection(NguoiThue.TB_NGUOITHUE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (NguoiThue nt : value.toObjects(NguoiThue.class)
                ) {
                    lsNguoiThue.add(nt);
                }
            }
        });

        return lsNguoiThue;
    }

    private void nhoMatKhau(boolean b, String user, String pass) {
        if (b) {
            mySharedPreferences.saveUser(MySharedPreferences.USER_KEY, user);
            mySharedPreferences.savePassword(MySharedPreferences.PASSWORD_KEY, pass);
            mySharedPreferences.saveStatus(MySharedPreferences.STATUS_KEY, chk.isChecked());
        } else {
            mySharedPreferences.clear();
            mySharedPreferences.saveUser(MySharedPreferences.USER_KEY, user);
            mySharedPreferences.savePassword(MySharedPreferences.PASSWORD_KEY, pass);
        }
    }

}