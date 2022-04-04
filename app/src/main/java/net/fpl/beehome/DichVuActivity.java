package net.fpl.beehome;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import net.fpl.beehome.Adapter.DichVu.DichVuAdapter;
import net.fpl.beehome.DAO.DichVuDAO;
import net.fpl.beehome.model.DichVu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DichVuActivity extends AppCompatActivity {


    RecyclerView rcv_dichVu;
    FloatingActionButton fab_dichVu;
    DichVuDAO dichVuDAO;
    DichVu dichVu;
    DichVuAdapter dichVuAdapter;
    ArrayList<DichVu> list;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_dich_vu);

        dichVuDAO = new DichVuDAO(this);
        dichVuAdapter = new DichVuAdapter(dichVuDAO, list);

        rcv_dichVu = findViewById(R.id.rcv_dichVu);
        fab_dichVu = findViewById(R.id.fab_dichVu);

        dichVuDAO = new DichVuDAO(this);

        fab_dichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dichVuAdapter.showDialog(DichVuActivity.this, 0 , 0);
            }
        });

    }

    public void hienThi(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        dichVuAdapter = new DichVuAdapter(dichVuDAO, list);
        rcv_dichVu.setAdapter(dichVuAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hienThi();
    }

    }
