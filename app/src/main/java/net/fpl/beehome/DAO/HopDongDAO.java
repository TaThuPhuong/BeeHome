package net.fpl.beehome.DAO;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.model.HopDong;

import java.util.ArrayList;

public class HopDongDAO {
    FirebaseFirestore db;
    Context context;


    public HopDongDAO(FirebaseFirestore db, Context context) {
        this.db = db;
        this.context = context;
    }

    public ArrayList<HopDong> getAll(){
        ArrayList arr = new ArrayList();

        db.collection(HopDong.TB_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HopDong objHopDong = new HopDong();
                                objHopDong.setIDHopDong(document.get(HopDong.COL_IDHOPDONG).toString());
                                objHopDong.setIDChuTro(document.get(HopDong.COL_IDCHUTRO).toString());
                                objHopDong.setIDPhong(document.get(HopDong.COL_IDPHONG).toString());
                                objHopDong.setIDThanhVien(document.get(HopDong.COL_IDTHANHVIEN).toString());
                                objHopDong.setKyHan(document.get(HopDong.COL_KYHAN).toString());
                                objHopDong.setNgayKiHD(document.get(HopDong.COL_NGAYKYHD).toString());
                                objHopDong.setNgayBatDau(document.get(HopDong.COL_NGAYBATDAU).toString());
                                objHopDong.setNgayKetThuc(document.get(HopDong.COL_NGAYKETTHUC).toString());
                                objHopDong.setSoNguoiThue(Integer.parseInt(document.get(HopDong.COL_SONGUOITHUE).toString()));

                                Log.d("aaaaaaa", document.getId() + " => " + document.getData());

                            }
                        } else {
                            Log.d("aaaaaaa", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return arr;
    }
}
