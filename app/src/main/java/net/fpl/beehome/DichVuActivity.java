package net.fpl.beehome;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import net.fpl.beehome.Adapter.DichVu.DichVuAdapter;
import net.fpl.beehome.DAO.DichVuDAO;
import net.fpl.beehome.model.DichVu;

import java.util.HashMap;
import java.util.Map;

public class DichVuActivity extends AppCompatActivity {

    RecyclerView rcv_dichVu;
    FloatingActionButton fab_dichVu;
    DichVuDAO dichVuDAO;
    DichVu dichVu;
    DichVuAdapter dichVuAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_dich_vu);

        addDichVu();

        dichVuDAO = new DichVuDAO(this);
        dichVuAdapter = new DichVuAdapter(dichVuDAO, dichVuDAO.getAll());

        rcv_dichVu = findViewById(R.id.rcv_dichVu);
        fab_dichVu = findViewById(R.id.fab_dichVu);

        dichVuDAO = new DichVuDAO(this);

        fab_dichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dichVuAdapter.showDialog(0, DichVuActivity.this, 0);
            }
        });

    }

    public void addDichVu(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> dien = new HashMap<>();
        dien.put(DichVu.COL_NAME, "Dien");
        dien.put(DichVu.COL_GIA, 3500);
        dien.put(DichVu.COL_DONVI, "So");
        db.collection(DichVu.TB_NAME).document("Dien").set(dien, SetOptions.merge());

        Map<String, Object> nuoc = new HashMap<>();
        nuoc.put(DichVu.COL_NAME, "Nuoc");
        nuoc.put(DichVu.COL_GIA, 20000);
        nuoc.put(DichVu.COL_DONVI, "Khoi");
        db.collection(DichVu.TB_NAME).document("Nuoc").set(nuoc, SetOptions.merge());

        Map<String, Object> veSinh = new HashMap<>();
        veSinh.put(DichVu.COL_NAME, "Ve sinh");
        veSinh.put(DichVu.COL_GIA, 20000);
        veSinh.put(DichVu.COL_DONVI, "Nguoi");
        db.collection(DichVu.TB_NAME).document("Ve sinh").set(veSinh, SetOptions.merge());
    }

}
