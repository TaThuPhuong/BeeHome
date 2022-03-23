package net.fpl.beehome.DAO;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DichVuDAO {

    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final Context context;

    public DichVuDAO(Context context) {
        this.context = context;
    }

    public void thongbao(int type, String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (type == 0){
            View view = View.inflate(context,R.layout.dialog_thong_bao_dich_vu_success, null);
            builder.setView(view);

            AlertDialog dialog = builder.create();
            dialog.show();
            Button btn = view.findViewById(R.id.btn_success);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else if(type == 1) {
            View view = View.inflate(context, R.layout.dialog_thong_bao_dich_vu_fail, null);
            builder.setView(view);

            AlertDialog dialog = builder.create();
            dialog.show();
            Button btn = view.findViewById(R.id.btn_fail);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }

    public void showDialog(int type){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        if (type == 0){
            View view = View.inflate(context, R.layout.dialog_them_dich_vu, null);
            dialog.setView(view);
            EditText edTenDichVu= view.findViewById(R.id.ed_tenDichVu);
            EditText edChiSo = view .findViewById(R.id.ed_chiSo);
            EditText edGia = view.findViewById(R.id.ed_giaDichVu);
            Button btnThem = view.findViewById(R.id.btn_themDichVu);
            Button btnHuy = view.findViewById(R.id.btn_huy);

            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public void insertDichVu(DichVu dichVu){

        DocumentReference reference = db.collection(DichVu.TB_NAME).document(DichVu.COL_ID);

        Map<String, Object> map = new HashMap<>();
        map.put("IdDichVu", String.valueOf(dichVu.getIdDichVu()));
        map.put("TenDichVu", dichVu.getTenDichVu());
        map.put("DonViTinh", dichVu.getDonViTinh());
        map.put("Gia", dichVu.getGia());

        reference.set(map, SetOptions.merge())
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                thongbao(0, "Thêm dịch vụ thành công");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                thongbao(1, "Thêm dịch vụ thất bại");
            }
        });
    }

    public void updateDichVu(DichVu dichVu){


        DocumentReference reference = db.collection(DichVu.TB_NAME).document(DichVu.COL_ID);

        Map<String, Object> map = new HashMap<>();
        map.put("IdDichVu", String.valueOf(dichVu.getIdDichVu()));
        map.put("TenDichVu", dichVu.getTenDichVu());
        map.put("DonViTinh", dichVu.getDonViTinh());
        map.put("Gia", dichVu.getGia());

        reference.update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        thongbao(0, "Sửa dịch vụ thành công");
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                thongbao(1, "Sửa dịch vụ thất bại");
            }
        });
    }

    public void deleteDichVu(DichVu dichVu){
        DocumentReference reference = db.collection(DichVu.TB_NAME).document(DichVu.COL_ID);

        Map<String, Object> map = new HashMap<>();
        map.put("IdDichVu", FieldValue.delete());
        map.put("TenDichVu", FieldValue.delete());
        map.put("DonViTinh", FieldValue.delete());
        map.put("Gia", FieldValue.delete());

        reference.delete();
        reference.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                thongbao(0, "Xóa dịch vụ thành công");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                thongbao(1, "Xóa dịch vụ thất bại");
            }
        });
    }

    public DichVu getDichVu(int id){

        DocumentReference reference = db.collection(DichVu.TB_NAME).document(String.valueOf(id));
        reference.get();

        DichVu dichVu = new DichVu();



        return dichVu;
    }

    public ArrayList<DichVu> getAll(){
        ArrayList<DichVu> list = new ArrayList<>();
        return list;
    }
}
