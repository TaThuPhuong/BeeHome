package net.fpl.beehome.ui.doiEmail;

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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.ProgressBarLoading;
import net.fpl.beehome.R;
import net.fpl.beehome.model.Admin;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.ui.phong.PhongFragment;

import java.util.ArrayList;


public class DoiEmailFragment extends Fragment {
    LinearLayout lnL;
    Animation animation;
    TextInputLayout edEmail;
    Button btnLuu, btnXoa;
    FirebaseFirestore fb;
    FirebaseAuth fba;
    FirebaseUser user;
    PhongFragment phongFragment;
    ProgressBarLoading progressBarLoading;
    ArrayList<NguoiThue> lsNguoiThue;
    ArrayList<Admin> lsAdmin;
    int indexAd;
    NguoiThue nguoiThue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doi_email, container, false);
        lnL = v.findViewById(R.id.aniLogin);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.login);
        lnL.startAnimation(animation);
        return v;
    }

    public void init(View view) {
        fb = FirebaseFirestore.getInstance();
        fba = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        phongFragment = new PhongFragment();
        progressBarLoading = new ProgressBarLoading(getContext());
        edEmail = view.findViewById(R.id.ed_email_moi);
        btnLuu = view.findViewById(R.id.btn_luu);
        btnXoa = view.findViewById(R.id.btn_clear);
        edEmail.setError(null);
        getAllAdmin();
        getAllNguoiThue();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        phongFragment.setUnErr(edEmail);

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
                String email = edEmail.getEditText().getText().toString();
                indexAd = -1;
                if (TextUtils.isEmpty(email)) {
                    progressBarLoading.hideLoaing();
                    edEmail.setError("Nhập email mới");
                    return;
                } else {
                    for (NguoiThue nt : lsNguoiThue
                    ) {
                        if (nt.getEmail().equalsIgnoreCase(user.getEmail())) {
                            nguoiThue = nt;
                            Log.d("TAG", "onComplete: " + nguoiThue.toString());
                            break;
                        }
                    }
                    for (int i = 0; i < lsAdmin.size(); i++) {
                        if (lsAdmin.get(i).getEmail().equals(user.getEmail())) {
                            indexAd = i;
                            break;
                        }
                    }
                    if (indexAd >= 0 || nguoiThue != null) {
                        if (indexAd >= 0) {
                            fb.collection(Admin.TB_NAME).document("admin" + indexAd)
                                    .update(Admin.COL_EMAIL, email);
                            getAllAdmin();
                        } else {
                            fb.collection(NguoiThue.TB_NGUOITHUE).document(nguoiThue.getSdt())
                                    .update(NguoiThue.COL_EMAIL, email);
                        }
                        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBarLoading.hideLoaing();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Cập nhập email thành công", Toast.LENGTH_SHORT).show();
                                    clear();
                                }else {
                                    Toast.makeText(getContext(), "Cập nhập email thất bại", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                }
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

    public void clear() {
        edEmail.getEditText().setText("");
    }
}
