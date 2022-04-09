package net.fpl.beehome.detail.hoaDon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.NguoiThue;

public class HoaDonMain extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseFirestore fb;
    NguoiThue nguoiThue;

    public NguoiThue getNguoiThue() {
        return nguoiThue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_main);
        fb = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar_hoa_don);
        Intent intent = getIntent();
        String quyen = intent.getStringExtra("quyen");
        Log.d("arrHDP", "onViewCreated: " + quyen);
        if (quyen.equalsIgnoreCase("user")) {
            nguoiThue = (NguoiThue) intent.getSerializableExtra("nt");
            Log.d("arrHDP", "onViewCreated: " + nguoiThue);
        }

        setSupportActionBar(toolbar);
        setTitle("Hóa Đơn");

    }
}