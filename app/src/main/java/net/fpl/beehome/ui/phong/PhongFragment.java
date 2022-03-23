package net.fpl.beehome.ui.phong;


import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.Phong.PhongAdapter;
import net.fpl.beehome.Adapter.Phong.PhongRecycleView;
import net.fpl.beehome.DAO.PhongDAO;
import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class PhongFragment extends Fragment {
    FloatingActionButton fab;
    FirebaseFirestore fb;
    PhongDAO phongDAO;
    PhongRecycleView phongRecycleView;
    ArrayList<Phong> lsPhong;
    RecyclerView recyclerView;
    TextView tvTongPhong, tvPhongTrong;
    PhongAdapter adapter;
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phong, container, false);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fb = FirebaseFirestore.getInstance();
        phongDAO = new PhongDAO(fb, getContext());
        lsPhong = phongDAO.getData();
//        lsPhong = new ArrayList<>();
//        lsPhong.add(new Phong("P101","P101","Đang thuê","Giường, ",1500000,20,11));
//        lsPhong.add(new Phong("P102","P102","Trống","Giường, ",1800000,20,11));
//        lsPhong.add(new Phong("P103","P103","Đang sửa chữa","Giường, ",1600000,20,11));
//        lsPhong.add(new Phong("P104","P104","Đang thuê","Giường, ",1700000,20,11));
        Log.d("zzzzzz", "List: " + lsPhong.size());
//        for (int i =0 ; i<lsPhong.size();i++){
//            Log.d("zzzzzzzzz", "phong: " + lsPhong.get(i).toString());
//        }

        phongRecycleView = new PhongRecycleView(lsPhong, phongDAO);
        recyclerView = view.findViewById(R.id.rcv_phong);
        recyclerView.setAdapter(phongRecycleView);

//        adapter = new PhongAdapter(lsPhong);
//        lv = view.findViewById(R.id.lv_phong);
//        lv.setAdapter(adapter);
        tvTongPhong = view.findViewById(R.id.tv_tong_phong);
        tvTongPhong.setText("Tổng số phòng - " + lsPhong.size());
        fab = view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phongDAO.showDialogThem();
            }
        });
//        hienThi();
    }
//    public void hienThi(){
//        fb.collection(Phong.TB_NAME)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Phong phong = document.toObject(Phong.class);
//                                Log.d("zzzzzz", "onComplete: " + phong.toString());
//                                lsPhong.add(phong);
//                            }
//                            Log.d("zzzzzz", "List: " + lsPhong.size());
//                        } else {
//                            Log.w("zzzzz", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//        phongRecycleView = new PhongRecycleView(lsPhong, phongDAO);
//        recyclerView.setAdapter(phongRecycleView);
//    }
}