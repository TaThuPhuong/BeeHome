package net.fpl.beehome.DAO;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhongDAO {
    FirebaseFirestore db;
    Context context;


    public PhongDAO(FirebaseFirestore db, Context context) {
        this.db = db;
        this.context = context;
    }

    public void thongBao(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.thong_bao_dialog, null);
        builder.setView(view);
        TextView tvMess = view.findViewById(R.id.tv_thong_bao);
        TextView btnOK = view.findViewById(R.id.btn_ok);
        tvMess.setText(mess);


        Dialog dialog = builder.create();
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
        View view = View.inflate(context, R.layout.them_phong_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edSoPhong, edGiaPhong, edVatTu, edTrangThai, edSoNuocDau, edSoDienDau;
        Button btnThem, btnHuy;
        CheckBox chkGiuong, chkTu, chkDieuHoa, chkNL, chkMayGiat, chkBan, chkBep;
        ImageButton btnChon, btnCancel;
        RadioGroup rdgTrangThai;
        edSoPhong = view.findViewById(R.id.ed_so_phong);
        edGiaPhong = view.findViewById(R.id.ed_gia_phong);
        edVatTu = view.findViewById(R.id.ed_vat_tu);
        edTrangThai = view.findViewById(R.id.ed_trang_thai);
        edSoDienDau = view.findViewById(R.id.ed_so_dien_dau);
        edSoNuocDau = view.findViewById(R.id.ed_so_nuoc_dau);
        btnThem = view.findViewById(R.id.btn_them_phong);
        btnHuy = view.findViewById(R.id.btn_huy);
//        GridView lv = view.findViewById(R.id.listVT);
        btnChon = view.findViewById(R.id.btn_chon);
        btnCancel = view.findViewById(R.id.btn_cancel);
        chkGiuong = view.findViewById(R.id.chk_vt_giuong);
        chkBan = view.findViewById(R.id.chk_vt_ban);
        chkBep = view.findViewById(R.id.chk_vt_bep);
        chkDieuHoa = view.findViewById(R.id.chk_vt_dieu_hoa);
        chkNL = view.findViewById(R.id.chk_vt_binh_nl);
        chkTu = view.findViewById(R.id.chk_vt_tu);
        chkMayGiat = view.findViewById(R.id.chk_vt_may_giat);
        rdgTrangThai = view.findViewById(R.id.rdgTrangThai);


//        ArrayList<String> lsVT = new ArrayList<String>();
//        ArrayList<Integer> indexVT = new ArrayList<>();
//        lsVT.add("Giường");
//        lsVT.add("Tủ");
//        lsVT.add("Bàn");
//        lsVT.add("Bếp");
//        lsVT.add("Điều hòa");
//        lsVT.add("Máy giặt");
//        lsVT.add("Nóng lạnh");
//        Adapter adapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_list_item_multiple_choice, lsVT);
//        lv.setAdapter((ListAdapter) adapter);
//
//        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                indexVT.add(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//
//        });
//
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
                int soPhong = Integer.parseInt(edSoPhong.getText().toString());
                String strVatTu = edVatTu.getText().toString();
                int giaPhong = Integer.parseInt(edGiaPhong.getText().toString());
                String strTrangThai = edTrangThai.getText().toString();
                int soDienDau = Integer.parseInt(edSoDienDau.getText().toString());
                int soNuocDau = Integer.parseInt(edSoNuocDau.getText().toString());
                Phong phong = new Phong();
                phong.setIDPhong("P" + soPhong);
                phong.setGiaPhong(giaPhong);
                phong.setSoPhong("P" + soPhong);
                phong.setSoDienDau(soDienDau);
                phong.setTrangThai(strTrangThai);
                phong.setSoNuocDau(soNuocDau);
                phong.setVatTu(strVatTu);
                themPhong(phong);
                dialog.dismiss();
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


        db.collection(Phong.TB_NAME).document((String) phong.getIDPhong(Phong.COL_ID))
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
    public void getData(){

    }
    public void updatePhong(Phong phong){

    }
    public void deletePhong(String IDphong){

    }
    public void getPhong(String IDPhong){

    }

}
