package net.fpl.beehome.ui.doiMatKhau;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.fpl.beehome.Login_Activity;
import net.fpl.beehome.ProgressBarLoading;
import net.fpl.beehome.R;
import net.fpl.beehome.ui.phong.PhongFragment;


public class DoiMatKhauFragment extends Fragment {

    ConstraintLayout constraintLayout;
    Animation animation, animation2;
    TextInputLayout edMKMoi, edMKNhapLai;
    Button btnLuu, btnXoa;
    FirebaseAuth fba;
    FirebaseUser user;
    String email;
    PhongFragment phongFragment;
    ProgressBarLoading progressBarLoading;
    ImageView logo, img1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);

        return v;
    }

    public void init(View view) {
        logo = view.findViewById(R.id.img_logo);
        img1 = view.findViewById(R.id.img1);
        constraintLayout = view.findViewById(R.id.anichangepass);
        fba = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        phongFragment = new PhongFragment();
        progressBarLoading = new ProgressBarLoading(getContext());
        email = user.getEmail();
        edMKMoi = view.findViewById(R.id.ed_mk_moi);
        edMKNhapLai = view.findViewById(R.id.ed_re_mk_moi);
        btnLuu = view.findViewById(R.id.btn_luu_doi_mk);
        btnXoa = view.findViewById(R.id.btn_clear);
        edMKMoi.setError(null);
        edMKNhapLai.setError(null);

        animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.frombottom);
        animation2 = AnimationUtils.loadAnimation(view.getContext(), R.anim.fromtop);
        constraintLayout.startAnimation(animation);
        img1.startAnimation(animation2);
        logo.startAnimation(animation2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        phongFragment.setUnErr(edMKMoi);
        phongFragment.setUnErr(edMKNhapLai);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarLoading.showLoading();
                String strMKMoi = edMKMoi.getEditText().getText().toString();
                String strMKNhapLai = edMKNhapLai.getEditText().getText().toString();
                Log.d("TAG", "onClick: " + strMKMoi + strMKNhapLai);
                if (TextUtils.isEmpty(strMKMoi) || TextUtils.isEmpty(strMKNhapLai)) {
                    progressBarLoading.hideLoaing();

                    if (TextUtils.isEmpty(strMKMoi)) {
                        edMKMoi.setError("Dữ liệu không được bỏ trống");
                        return;
                    }

                    if (strMKMoi.length() < 6){
                        edMKMoi.setError("Mật khẩu phải trên 6 ký tự");
                        return;
                    }
                    if (TextUtils.isEmpty(strMKNhapLai)) {
                        edMKNhapLai.setError("Dữ liệu không được bỏ trống");
                        return;
                    }
                    if (strMKNhapLai.equals(strMKMoi)){
                        edMKNhapLai.setError("Mật khẩu phải trên 6 ký tự");
                        return;
                    }
                } else {
                    if (strMKMoi.equals(strMKNhapLai)) {
                        user.updatePassword(strMKMoi).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBarLoading.hideLoaing();
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(view.getContext(), Login_Activity.class);
                                    startActivity(intent);
                                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    clear();
                                }
                            }
                        });
                    } else {
                        progressBarLoading.hideLoaing();
                        if (!strMKMoi.equals(strMKNhapLai)) {
                            edMKNhapLai.setError("Mật khẩu không khớp");
                        }
                    }
                }
            }
        });
    }
    public void clear() {
        edMKMoi.getEditText().setText("");
        edMKNhapLai.getEditText().setText("");
    }
}
