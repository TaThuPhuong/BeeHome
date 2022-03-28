package net.fpl.beehome.ui.phong;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.Phong.PhongSwipeRecyclerViewAdapter;
import net.fpl.beehome.DAO.PhongDAO;
import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class PhongFragment extends Fragment {
    FloatingActionButton fab;
    FirebaseFirestore fb;
    PhongDAO phongDAO;
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
        phongDAO = new PhongDAO(fb, getContext());
        lsPhong = new ArrayList<>();

        phongSwipeRecyclerViewAdapter = new PhongSwipeRecyclerViewAdapter(getContext(), getLsPhong(), fb);
        recyclerView.setAdapter(phongSwipeRecyclerViewAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phongDAO.showDialogThem();
                phongSwipeRecyclerViewAdapter = new PhongSwipeRecyclerViewAdapter(getContext(), getLsPhong(),fb);
                phongSwipeRecyclerViewAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(phongSwipeRecyclerViewAdapter);
            }
        });
    }

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
                            phongSwipeRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        Log.d("zzzzzz", "List: " + lsPhong.size());

                    }
                });
        return lsPhong;
    }
}