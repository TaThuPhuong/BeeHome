package net.fpl.beehome.ui.doiMatKhau;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;

public class DoiMatKhauFragment extends Fragment {
    LinearLayout lnL;
    Animation animation;
    EditText edMKHienTai, edMKMoi, edMKNhapLai;
    Button btnLuu, btnXoa;
    FirebaseFirestore fb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        lnL = v.findViewById(R.id.aniLogin);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.login);
        lnL.startAnimation(animation);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edMKHienTai = view.findViewById(R.id.ed_mk_cu);
        edMKMoi = view.findViewById(R.id.ed_mk_moi);
        edMKNhapLai = view.findViewById(R.id.ed_re_mk_moi);
        btnLuu = view.findViewById(R.id.btn_luu_doi_mk);
        btnXoa = view.findViewById(R.id.btn_clear);
        fb = FirebaseFirestore.getInstance();

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edMKHienTai.setText("");
                edMKMoi.setText("");
                edMKNhapLai.setText("");
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strMK = edMKHienTai.getText().toString();
                String strMKMoi = edMKMoi.getText().toString();
                String strMKNhapLai = edMKNhapLai.getText().toString();
                if (TextUtils.isEmpty(strMK) || TextUtils.isEmpty(strMKMoi) || TextUtils.isEmpty(strMKNhapLai)) {
                }else {

                }

            }
        });
    }
}
