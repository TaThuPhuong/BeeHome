package net.fpl.beehome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.DichVu.DichVuAdapter;
import net.fpl.beehome.model.DichVu;

import java.util.ArrayList;

public class DichVuActivity extends AppCompatActivity {


    RecyclerView rcv_dichVu;
    FloatingActionButton fab_dichVu;
    DichVu dichVu;
    DichVuAdapter dichVuAdapter;
    ArrayList<DichVu> list;
    FirebaseFirestore db;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_dich_vu);
        db = FirebaseFirestore.getInstance();
        dichVuAdapter = new DichVuAdapter(list, this, db);
        rcv_dichVu = findViewById(R.id.rcv_dichVu);
        fab_dichVu = findViewById(R.id.fab_dichVu);
        fab_dichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dichVuAdapter.showDialog(DichVuActivity.this, 0 , 0);
            }
        });

    }

    public void hienThi(){
        list = new ArrayList<>();

        db.collection(DichVu.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    list.clear();
                    for (QueryDocumentSnapshot snapshot : value){
                        DichVu dichVu = snapshot.toObject(DichVu.class);
                        Log.e("TAG", "onComplete: "+snapshot.getId() + " / " + snapshot.getData());
                        list.add(dichVu);
                        dichVuAdapter.notifyDataSetChanged();
                }
            }
        });
        dichVuAdapter = new DichVuAdapter(list,DichVuActivity.this, db);
        rcv_dichVu.setAdapter(dichVuAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hienThi();
    }

    }
