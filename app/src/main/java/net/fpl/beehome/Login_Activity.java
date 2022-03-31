package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    Button btnDangNhap, btnClear;
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

    public void init() {
        edNguoidung = findViewById(R.id.ed_sdt);
        edMatkhau = findViewById(R.id.ed_pass);
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnClear = findViewById(R.id.btn_clear);
        chk = findViewById(R.id.chk_remember);
        tvQuenMK = findViewById(R.id.tv_quen_mk);
        constraintLayout = findViewById(R.id.aniLogin);
        phongFragment = new PhongFragment();
        fb = FirebaseFirestore.getInstance();
        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        animation = AnimationUtils.loadAnimation(this, R.anim.login);
        constraintLayout.startAnimation(animation);
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
                    admin = getAdmin(user);
                    nguoiThue = getNguoiThue(user);
                    if (admin == null && nguoiThue == null) {
                        edMatkhau.setError("Sai tên đăng nhập hoặc mật khẩu");
                    } else if (admin != null && admin.getPassword().equals(pass)) {
                        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("ad", admin);
                        startActivity(intent);
                        nhoMatKhau(chk.isChecked(), user, pass);
                    } else if (nguoiThue != null && nguoiThue.getPassword().equals(pass)) {
                        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("nt", nguoiThue);
                        startActivity(intent);
                        nhoMatKhau(chk.isChecked(), user, pass);
                    }
                }
            }

        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edMatkhau.getEditText().setText("");
                edNguoidung.getEditText().setText("");
            }
        });
        tvQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public Admin getAdmin(String str) {
        fb.collection(Admin.TB_NAME).document(str)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            admin = task.getResult().toObject(Admin.class);
                        }
                    }
                });
        return admin;
    }

    public NguoiThue getNguoiThue(String str) {
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
        return nguoiThue;
    }

    private void nhoMatKhau(boolean b, String user, String pass) {
        if (b) {
            mySharedPreferences.saveUser(MySharedPreferences.USER_KEY, user);
            mySharedPreferences.savePassword(MySharedPreferences.PASSWORD_KEY, pass);
            mySharedPreferences.saveStatus(MySharedPreferences.STATUS_KEY, chk.isChecked());
            Toast.makeText(Login_Activity.this, "Đã ghi nhớ ", Toast.LENGTH_SHORT).show();
        } else {
            mySharedPreferences.clear();
            mySharedPreferences.saveUser(MySharedPreferences.USER_KEY, user);
            mySharedPreferences.savePassword(MySharedPreferences.PASSWORD_KEY, pass);
        }
    }

}