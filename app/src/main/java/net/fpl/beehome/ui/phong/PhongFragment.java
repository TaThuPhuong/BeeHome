package net.fpl.beehome.ui.phong;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.Phong.PhongSwipeRecyclerViewAdapter;
import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class PhongFragment extends Fragment {
    FloatingActionButton fab;
    FirebaseFirestore fb;
    ArrayList<Phong> lsPhong;
    RecyclerView recyclerView;
    TextView tvTongPhong, tvPhongTrong;
    int phongTrong = 0;
    PhongSwipeRecyclerViewAdapter phongSwipeRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phong, container, false);
        return v;
    }

    public void init(View view) {
        fb = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.rcv_phong);
        tvTongPhong = view.findViewById(R.id.tv_tong_phong);
        tvPhongTrong = view.findViewById(R.id.phong_trong);
        fab = view.findViewById(R.id.floating_action_button);
        lsPhong = new ArrayList<>();
        phongSwipeRecyclerViewAdapter = new PhongSwipeRecyclerViewAdapter(getContext(), getLsPhong(), fb, this);
        phongSwipeRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(phongSwipeRecyclerViewAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThem();
                phongSwipeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    // lấy danh sách phong có trên firestore
    public ArrayList<Phong> getLsPhong() {
        fb.collection(Phong.TB_NAME)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        phongTrong = 0;
                        lsPhong.clear();
                        for (QueryDocumentSnapshot document : value) {
                            Phong phong = document.toObject(Phong.class);
                            if (phong.getTrangThai().equals("Trống")) {
                                phongTrong++;
                                tvPhongTrong.setText("Phòng trống - " + phongTrong);
                            }
                            Log.d("zzzzzz", "onComplete: " + phong.toString());
                            lsPhong.add(phong);
                            tvTongPhong.setText("Tổng số phòng - " + lsPhong.size());
                            phongSwipeRecyclerViewAdapter.notifyDataSetChanged();
                        }
                        if (lsPhong.isEmpty()) {
                            tvPhongTrong.setText("Phòng trống - 0");
                            tvTongPhong.setText("Tổng số phòng - 0");

                        }
                        Log.d("zzzzzz", "List: " + lsPhong.size());
                    }
                });
        return lsPhong;
    }

    public void thongBao(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_thong_bao, null);
        builder.setView(view);
        TextView tvMess = view.findViewById(R.id.tv_thong_bao);
        TextView btnOK = view.findViewById(R.id.btn_ok);
        tvMess.setText(mess);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void themPhong(Phong phong) {
        fb.collection(Phong.TB_NAME).document(phong.getIDPhong())
                .set(phong)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        thongBao("Thêm thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        thongBao("Thêm thất bại");
                        Log.w("zzzz", "Error writing document", e);
                    }
                });
    }

    // show dialog thêm
    public void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_them_phong, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText edSoPhong, edGiaPhong, edVatTu, edTrangThai, edSoNuocDau, edSoDienDau;
        Button btnThem, btnHuy;
        CheckBox chkGiuong, chkTu, chkDieuHoa, chkNL, chkMayGiat, chkBan, chkBep;
        ImageButton btnChon, btnCancel, btnChonTatCa;
        RadioGroup rdgTrangThai;
        edSoPhong = view.findViewById(R.id.ed_so_phong);
        edGiaPhong = view.findViewById(R.id.ed_gia_phong);
        edVatTu = view.findViewById(R.id.ed_vat_tu);
        edTrangThai = view.findViewById(R.id.ed_trang_thai);
        edSoDienDau = view.findViewById(R.id.ed_so_dien_dau);
        edSoNuocDau = view.findViewById(R.id.ed_so_nuoc_dau);
        btnThem = view.findViewById(R.id.btn_them_phong);
        btnHuy = view.findViewById(R.id.btn_huy);
        btnChon = view.findViewById(R.id.btn_chon);
        btnChonTatCa = view.findViewById(R.id.btn_chon_tat_ca);
        btnCancel = view.findViewById(R.id.btn_cancel);
        chkGiuong = view.findViewById(R.id.chk_vt_giuong);
        chkBan = view.findViewById(R.id.chk_vt_ban);
        chkBep = view.findViewById(R.id.chk_vt_bep);
        chkDieuHoa = view.findViewById(R.id.chk_vt_dieu_hoa);
        chkNL = view.findViewById(R.id.chk_vt_binh_nl);
        chkTu = view.findViewById(R.id.chk_vt_tu);
        chkMayGiat = view.findViewById(R.id.chk_vt_may_giat);
        rdgTrangThai = view.findViewById(R.id.rdgTrangThai);
        btnChonTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edVatTu.setText("Giường, Bàn, Bếp, Tủ, Điều hòa, Máy giặt, Bình nước nóng");
                chkBan.setChecked(true);
                chkBep.setChecked(true);
                chkDieuHoa.setChecked(true);
                chkGiuong.setChecked(true);
                chkMayGiat.setChecked(true);
                chkNL.setChecked(true);
                chkTu.setChecked(true);
            }
        });
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vt = "";
                if (chkBan.isChecked()) {
                    vt += chkBan.getText().toString() + ", ";
                }
                if (chkBep.isChecked()) {
                    vt += chkBep.getText().toString() + ", ";
                }
                if (chkDieuHoa.isChecked()) {
                    vt += chkDieuHoa.getText().toString() + ", ";
                }
                if (chkGiuong.isChecked()) {
                    vt += chkGiuong.getText().toString() + ", ";
                }
                if (chkMayGiat.isChecked()) {
                    vt += chkMayGiat.getText().toString() + ", ";
                }
                if (chkNL.isChecked()) {
                    vt += chkNL.getText().toString() + ", ";
                }
                if (chkTu.isChecked()) {
                    vt += chkTu.getText().toString() + ", ";
                }
                edVatTu.setText(vt);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edVatTu.setText("");
                chkBan.setChecked(false);
                chkBep.setChecked(false);
                chkDieuHoa.setChecked(false);
                chkGiuong.setChecked(false);
                chkMayGiat.setChecked(false);
                chkNL.setChecked(false);
                chkTu.setChecked(false);
            }
        });
        edTrangThai.setText("Trống");
        rdgTrangThai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rdo_dang_thue:
                        edTrangThai.setText("Đang thuê");
                        break;
                    case R.id.rdo_trong:
                        edTrangThai.setText("Trống");
                        break;
                    case R.id.rdo_dang_sua:
                        edTrangThai.setText("Đang sửa chữa");
                        break;
                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soPhong = edSoPhong.getText().toString();
                String strVatTu = edVatTu.getText().toString();
                String giaPhong = edGiaPhong.getText().toString();
                String strTrangThai = edTrangThai.getText().toString();
                String soDienDau = edSoDienDau.getText().toString();
                String soNuocDau = edSoNuocDau.getText().toString();
                if (TextUtils.isEmpty(soPhong) || TextUtils.isEmpty(strVatTu) || TextUtils.isEmpty(giaPhong) ||
                        TextUtils.isEmpty(strTrangThai) || TextUtils.isEmpty(soDienDau) || TextUtils.isEmpty(soNuocDau)) {
                    thongBao("Điền đầy đủ thông tin các mục");
                    return;
                } else {
                    Phong phong = new Phong();
                    phong.setIDPhong("P" + soPhong);
                    phong.setGiaPhong(Integer.parseInt(giaPhong));
                    phong.setSoPhong("P" + soPhong);
                    phong.setSoDienDau(Integer.parseInt(soDienDau));
                    phong.setTrangThai(strTrangThai);
                    phong.setSoNuocDau(Integer.parseInt(soNuocDau));
                    phong.setVatTu(strVatTu);
                    themPhong(phong);
                    dialog.dismiss();
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}