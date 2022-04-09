package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import net.fpl.beehome.ui.phong.PhongFragment;

public class QuenMatKhauActivity extends AppCompatActivity {
    TextInputLayout edEmail;
    Button btnKhoiPhuc;
    FirebaseAuth fba;
    PhongFragment phongFragment;

    public void init() {
        edEmail = findViewById(R.id.ed_email);
        btnKhoiPhuc = findViewById(R.id.btn_khoi_phuc);
        fba = FirebaseAuth.getInstance();
        phongFragment = new PhongFragment();
        edEmail.setError(null);
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