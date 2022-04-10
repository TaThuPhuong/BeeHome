package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import net.fpl.beehome.ui.phong.PhongFragment;

public class QuenMatKhauActivity extends AppCompatActivity {

    ImageView logo, img1;
    Animation animation, animation2;
    ConstraintLayout constraintLayout;
    TextInputLayout edEmail;
    Button btnKhoiPhuc, btn_clear;
    FirebaseAuth fba;
    PhongFragment phongFragment;


    public void init() {
        logo = findViewById(R.id.img_logo);
        img1 = findViewById(R.id.img1);
        constraintLayout = findViewById(R.id.anichangepass);
        edEmail = findViewById(R.id.ed_email);
        btnKhoiPhuc = findViewById(R.id.btn_khoi_phuc);
        btn_clear = findViewById(R.id.btn_clear);
        fba = FirebaseAuth.getInstance();
        phongFragment = new PhongFragment();
        edEmail.setError(null);

        animation = AnimationUtils.loadAnimation(QuenMatKhauActivity.this, R.anim.frombottom);
        animation2 = AnimationUtils.loadAnimation(QuenMatKhauActivity.this, R.anim.fromtop);
        constraintLayout.startAnimation(animation);
        img1.startAnimation(animation2);
        logo.startAnimation(animation2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        init();
        phongFragment.setUnErr(edEmail);
        btnKhoiPhuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getEditText().getText().toString();
                if (TextUtils.isEmpty(email)) {
                    edEmail.setError("Nhập email");
                    return;
                } else {
                    fba.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(QuenMatKhauActivity.this, "Đã gửi link khôi phục mật khẩu tới email " + email, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(QuenMatKhauActivity.this, Login_Activity.class));
                                    }
                                }
                            });
                }
            }
        });

    }
}