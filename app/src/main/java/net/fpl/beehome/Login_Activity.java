package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Login_Activity extends AppCompatActivity {
    EditText edNguoidung, edMatkhau;
    Button btnDangNhap, btnClear;
    CheckBox chk;
    Animation animation;
    ConstraintLayout constraintLayout;
    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edNguoidung = findViewById(R.id.ed_username);
        edMatkhau = findViewById(R.id.ed_password);
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnClear = findViewById(R.id.btn_clear);
        chk = findViewById(R.id.chk_remember);
        constraintLayout = findViewById(R.id.aniLogin);
        mySharedPreferences = new MySharedPreferences(getApplicationContext());

        animation = AnimationUtils.loadAnimation(this, R.anim.login);
        constraintLayout.startAnimation(animation);
        if (mySharedPreferences.getStatus(MySharedPreferences.STATUS_KEY)) {
            edNguoidung.setText(mySharedPreferences.getUser(MySharedPreferences.USER_KEY));
            edMatkhau.setText(mySharedPreferences.getPassword(MySharedPreferences.PASSWORD_KEY));
            chk.setChecked(true);
        }
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edNguoidung.getText().toString();
                String pass = edMatkhau.getText().toString();
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(getApplicationContext(), "Nhập email lẹ đi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Chưa nhập password nè", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edNguoidung.getText().toString().equals("admin") && edMatkhau.getText().toString().equals("123")) {
                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    intent.putExtra("quyen", "admin");
                    startActivity(intent);
                    if (chk.isChecked()) {
                        mySharedPreferences.saveUser(MySharedPreferences.USER_KEY, user);
                        mySharedPreferences.savePassword(MySharedPreferences.PASSWORD_KEY, pass);
                        mySharedPreferences.saveStatus(MySharedPreferences.STATUS_KEY, chk.isChecked());
                        Toast.makeText(Login_Activity.this, "Đã ghi nhớ ", Toast.LENGTH_SHORT).show();
                    } else {
                        mySharedPreferences.clear();
                        mySharedPreferences.saveUser(MySharedPreferences.USER_KEY, user);
                        mySharedPreferences.savePassword(MySharedPreferences.PASSWORD_KEY, pass);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Tên Đăng Nhập Hoăc Mật Khẩu Không Chính Xác", Toast.LENGTH_SHORT).show();
                }

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