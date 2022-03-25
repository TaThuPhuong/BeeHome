package net.fpl.beehome.DAO;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhongDAO {
    FirebaseFirestore db;
    Context context;
    ArrayList<Phong> lsPhong;

    public PhongDAO(FirebaseFirestore db, Context context) {
        this.db = db;
        this.context = context;
    }

    public void thongBao(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_thong_bao, null);
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

    @SuppressLint("ResourceType")
    public void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_them_phong, null);
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

    public void themPhong(Phong phong) {
        db.collection(Phong.TB_NAME).document(phong.getIDPhong())
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

    public ArrayList<Phong> getData() {
        lsPhong = new ArrayList<>();
        db.collection(Phong.TB_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Phong phong = document.toObject(Phong.class);
                                Log.d("zzzzzz", "onComplete: " + phong.toString());

                                lsPhong.add(phong);
                            }
                            Log.d("zzzzzz", "List: " + lsPhong.size());
                        } else {
                            Log.w("zzzzz", "Error getting documents.", task.getException());
                        }
                    }
                });
        return lsPhong;
    }

    public void updatePhong(Phong phong) {

    }

    public void deletePhong(String IDphong) {

    }

    public void getPhong(String IDPhong) {

    }

    public boolean checkPhong(String IDPhong) {
        return true;
    }
}
