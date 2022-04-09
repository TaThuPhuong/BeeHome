package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    Animation animation, animation2 ;

    ConstraintLayout constraintLayout;
    MySharedPreferences mySharedPreferences;
    FirebaseFirestore fb;
    FirebaseAuth fba;
    Admin admin;
    NguoiThue nguoiThue;
    PhongFragment phongFragment;
    ProgressBarLoading progressBarLoading;
    ArrayList<NguoiThue> lsNguoiThue;
    ArrayList<Admin> lsAdmin;
    ImageView logo, img1;


    public void init() {
        fb = FirebaseFirestore.getInstance();
        fba = FirebaseAuth.getInstance();
        edNguoidung = findViewById(R.id.ed_email);
        edMatkhau = findViewById(R.id.ed_pass);
        logo = findViewById(R.id.img_logo);
        img1 = findViewById(R.id.img1);

        btnDangNhap = findViewById(R.id.btn_dangnhap);
        chk = findViewById(R.id.chk_remember);
        tvQuenMK = findViewById(R.id.tv_quen_mk);
        constraintLayout = findViewById(R.id.aniLogin);
        phongFragment = new PhongFragment();
        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        animation = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        constraintLayout.startAnimation(animation);
        img1.startAnimation(animation2);
        logo.startAnimation(animation2);

        progressBarLoading = new ProgressBarLoading(Login_Activity.this);
        getAllAdmin();
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
                progressBarLoading.showLoading();
                String pass = edMatkhau.getEditText().getText().toString();
                String email = edNguoidung.getEditText().getText().toString();

//          kiểm tra chống
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    progressBarLoading.hideLoaing();
                    if (TextUtils.isEmpty(email)) {
                        edNguoidung.setError("Nhập email");
                    }
                    if (TextUtils.isEmpty(pass)) {
                        edMatkhau.setError("Nhập mật khẩu");
                    }
                    return;
                } else {
                    for (NguoiThue nt : lsNguoiThue
                    ) {
                        if (nt.getEmail().equalsIgnoreCase(email)) {
                            nguoiThue = nt;
                            Log.d("TAG", "onComplete: " + nguoiThue.toString());

                            break;
                        }
                    }
                    for (Admin ad : lsAdmin) {
                        if (ad.getEmail().equals(email)) {
                            admin = ad;
                            Log.d("TAG", "onClick: " +ad.toString());
                            break;
                        }
                    }
                    if (admin == null && nguoiThue == null) {
                        progressBarLoading.hideLoaing();
                        edNguoidung.setError("Sai email");
                        return;
                    }
                    if (admin != null) {
                        progressBarLoading.showLoading();
                        fba.signInWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (admin != null && task.isSuccessful()) {
                                            progressBarLoading.hideLoaing();
                                            mySharedPreferences.getDN(MySharedPreferences.NgDung,"Admin");
                                            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                            intent.putExtra("email", email);
                                            intent.putExtra("ad", admin);
                                            progressBarLoading.hideLoaing();
                                            startActivity(intent);
                                            nhoMatKhau(chk.isChecked(), email, pass);
                                            Toast.makeText(Login_Activity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBarLoading.hideLoaing();
                                        edMatkhau.setError("Sai mật khẩu");
                                        progressBarLoading.hideLoaing();
                                        return;
                                    }
                                });
                    } else {
                        progressBarLoading.showLoading();
                        fba.signInWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (nguoiThue != null && task.isSuccessful()) {
                                            progressBarLoading.hideLoaing();
                                            mySharedPreferences.getDN(MySharedPreferences.NgDung,"ngThue");
                                            Intent intent = new Intent(Login_Activity.this, MainNguoiThueActivity.class);
                                            intent.putExtra("email", email);
                                            intent.putExtra("nt", nguoiThue);
                                            startActivity(intent);
                                            nhoMatKhau(chk.isChecked(), email, pass);
                                            Toast.makeText(Login_Activity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBarLoading.hideLoaing();
                                        edMatkhau.setError("Sai mật khẩu");
                                        return;
                                    }
                                });

                    }
                }
            }
        });

        tvQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this, QuenMatKhauActivity.class));
            }
        });
    }

    public ArrayList<Admin> getAllAdmin() {
        lsAdmin = new ArrayList<>();
        fb.collection(Admin.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (Admin ad : value.toObjects(Admin.class)
                ) {
                    lsAdmin.add(ad);
                }
            }
        });
        return lsAdmin;
    }

    public ArrayList<NguoiThue> getAllNguoiThue() {
        lsNguoiThue = new ArrayList<>();
        fb.collection(NguoiThue.TB_NGUOITHUE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (NguoiThue nt : value.toObjects(NguoiThue.class)
                ) {
                    lsNguoiThue.add(nt);
                    Log.d("TAG", "onEvent: " + nt.toString());
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