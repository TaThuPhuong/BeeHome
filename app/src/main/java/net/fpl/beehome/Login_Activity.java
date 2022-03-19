package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Login_Activity extends AppCompatActivity {
    EditText edNguoidung, edMatkhau;
    Button btnDangNhap,btnClear;

    Animation animation;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edNguoidung = findViewById(R.id.ed_username);
        edMatkhau = findViewById(R.id.ed_password);
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnClear = findViewById(R.id.btn_clear);
        constraintLayout = findViewById(R.id.aniLogin);

        animation = AnimationUtils.loadAnimation(this, R.anim.login);
        constraintLayout.startAnimation(animation);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (edNguoidung.getText().toString().equals("admin")&&edMatkhau.getText().toString().equals("123")){
//                    Intent intent = new Intent(Login_Activity.this,MainActivity.class);
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(getBaseContext(), "Tên Đăng Nhập Hoăc Mật Khẩu Không Chính Xác", Toast.LENGTH_SHORT).show();
//                }
                Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edMatkhau.setText("");
                edNguoidung.setText("");
            }
        });
    }
    // h comment
}