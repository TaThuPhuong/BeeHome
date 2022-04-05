package net.fpl.beehome.ui.doiMatKhau;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.fpl.beehome.ProgressBarLoading;
import net.fpl.beehome.R;
import net.fpl.beehome.ui.phong.PhongFragment;


public class DoiMatKhauFragment extends Fragment {
    LinearLayout lnL;
    Animation animation;
    TextInputLayout edMKMoi, edMKNhapLai;
    Button btnLuu, btnXoa;
    FirebaseAuth fba;
    FirebaseUser user;
    String email;
    PhongFragment phongFragment;
    ProgressBarLoading progressBarLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        lnL = v.findViewById(R.id.aniLogin);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.login);
        lnL.startAnimation(animation);
        return v;
    }

    public void init(View view) {
        fba = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        phongFragment = new PhongFragment();
        progressBarLoading = new ProgressBarLoading(getContext());
        email = user.getEmail();
        Log.d("TAG", "init: " + email);
        edMKMoi = view.findViewById(R.id.ed_mk_moi);
        edMKNhapLai = view.findViewById(R.id.ed_re_mk_moi);
        btnLuu = view.findViewById(R.id.btn_luu_doi_mk);
        btnXoa = view.findViewById(R.id.btn_clear);
        edMKMoi.setError(null);
        edMKNhapLai.setError(null);
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
                        edMKMoi.setError("Nhập mật khẩu mới");
                    }
                    if (TextUtils.isEmpty(strMKNhapLai)) {
                        edMKNhapLai.setError("Nhập lại mật khẩu");
                    }
                    return;
                } else {
                    if (strMKMoi.equals(strMKNhapLai)) {
                        user.updatePassword(strMKMoi).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBarLoading.hideLoaing();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Cập nhập mật khẩu thành công", Toast.LENGTH_SHORT).show();
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
