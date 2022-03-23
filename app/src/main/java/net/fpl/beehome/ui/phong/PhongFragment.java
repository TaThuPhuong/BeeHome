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
    int phongTrong = 0;

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
        phongDAO = new PhongDAO(fb, getContext());
        lsPhong = new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phongDAO.showDialogThem();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        hienThi();
    }

    public void hienThi() {
        fb.collection(Phong.TB_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Phong phong = document.toObject(Phong.class);
                                if (phong.getTrangThai().equals("Trống")) {
                                    phongTrong++;
                                    tvPhongTrong.setText("Phòng trống - " +phongTrong);
                                }
                                Log.d("zzzzzz", "onComplete: " + phong.toString());
                                lsPhong.add(phong);
                                tvTongPhong.setText("Tổng số phòng - " + lsPhong.size());
                                phongRecycleView.notifyDataSetChanged();
                            }
                            Log.d("zzzzzz", "List: " + lsPhong.size());
                        } else {
                            Log.w("zzzzz", "Error getting documents.", task.getException());
                        }
                    }
                });
        phongRecycleView = new PhongRecycleView(lsPhong, phongDAO);
        recyclerView.setAdapter(phongRecycleView);
    }


}