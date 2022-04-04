package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuenMatKhauActivity extends AppCompatActivity {
    TextInputLayout edUser, edEmail;
    Button btnKhoiPhuc;
    FirebaseFirestore fb;
    FirebaseAuth fba;
    Admin admin;
    NguoiThue nguoiThue;
    ArrayList<NguoiThue> lsNguoiThue;
    PhongFragment phongFragment;
    ProgressBarLoading progressBarLoading;

    public void init() {
        edEmail = findViewById(R.id.ed_email);
        edUser = findViewById(R.id.ed_sdt);
        btnKhoiPhuc = findViewById(R.id.btn_khoi_phuc);
        fb = FirebaseFirestore.getInstance();
        fba = FirebaseAuth.getInstance();
        lsNguoiThue = getAllNguoiThue();
        phongFragment = new PhongFragment();
        progressBarLoading = new ProgressBarLoading(QuenMatKhauActivity.this);
        edUser.setError(null);
        edEmail.setError(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        init();
        getAdmin();
        phongFragment.setUnErr(edUser);
        phongFragment.setUnErr(edEmail);
        btnKhoiPhuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edUser.getEditText().getText().toString();
                String email = edEmail.getEditText().getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(email)) {
                    if (TextUtils.isEmpty(user)) {
                        edUser.setError("Nhập user hoặc số điện thoại");
                    }
                    if (TextUtils.isEmpty(email)) {
                        edEmail.setError("Nhập email");
                    }
                    return;
                } else {
                    for (NguoiThue nt : lsNguoiThue
                    ) {
                        if (nt.getSdt().equals(user)) {
                            nguoiThue = nt;
                        }
                    }
                }
                progressBarLoading.showLoading();
                if(user.equals("admin")){
                    fba.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
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
}