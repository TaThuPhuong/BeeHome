package net.fpl.beehome.DAO;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            TextView btn = view.findViewById(R.id.btn_success);
            TextView tv = view.findViewById(R.id.tv_dialog);
            tv.setText(mess);
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
            TextView btn = view.findViewById(R.id.btn_fail);
            TextView tv = view.findViewById(R.id.tv_dialog);
            tv.setText(mess);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }


    public void insertDichVu(DichVu dichVu){

        Map<String, Object> map = new HashMap<>();
        map.put(DichVu.COL_NAME, dichVu.getTenDichVu());
        map.put(DichVu.COL_GIA, dichVu.getGia() +"");
        map.put(DichVu.COL_DONVI, dichVu.getDonVi());

        db.collection(DichVu.TB_NAME).document(dichVu.getTenDichVu()).set(map)
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

        Map<String, Object> map = new HashMap<>();
        map.put(DichVu.COL_NAME, dichVu.getTenDichVu());
        map.put(DichVu.COL_GIA, dichVu.getGia());
        map.put(DichVu.COL_DONVI, dichVu.getDonVi());

        db.collection(DichVu.TB_NAME).document(dichVu.getTenDichVu()).update(map)
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

        Map<String, Object> map = new HashMap<>();
        map.put(DichVu.COL_NAME, FieldValue.delete());
        map.put(DichVu.COL_GIA, FieldValue.delete());
        map.put(DichVu.COL_DONVI, FieldValue.delete());

        db.collection(DichVu.TB_NAME).document(dichVu.getTenDichVu()).delete();
        db.collection(DichVu.TB_NAME).document(dichVu.getTenDichVu()).update(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public DichVu getDichVu(String name){
        ArrayList<DichVu> list = new ArrayList<>();

        db.collection(DichVu.TB_NAME).whereEqualTo(DichVu.COL_NAME, name).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                        DichVu dichVu = new DichVu();
                        dichVu.setTenDichVu(Objects.requireNonNull(snapshot.get(DichVu.COL_NAME)).toString());
                        dichVu.setGia(Integer.parseInt(Objects.requireNonNull(snapshot.get(DichVu.COL_GIA)).toString()));
                        dichVu.setDonVi(Objects.requireNonNull(snapshot.get(DichVu.COL_DONVI)).toString());

                        boolean check = list.add(dichVu);
                        if (check){
                            thongbao(0, "Tải dữ liệu thành công");
                        } else {
                            thongbao(1, "Tải dữ liệu thất bại");
                        }
                    }
                }
            }
        });
        return list.get(0);
    }


    public ArrayList<DichVu> getAll(){
        ArrayList<DichVu> list = new ArrayList<>();

        db.collection(DichVu.TB_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                        DichVu dichVu = snapshot.toObject(DichVu.class);
                        list.add(dichVu);
                    }
                }
            }
        });
        return list;
    }
}
