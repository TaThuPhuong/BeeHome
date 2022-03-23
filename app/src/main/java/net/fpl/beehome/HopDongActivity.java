package net.fpl.beehome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HopDongActivity extends AppCompatActivity {
    FloatingActionButton btn_add;
    RecyclerView rv_hd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hop_dong);

        btn_add = findViewById(R.id.btn_add);
        rv_hd = findViewById(R.id.rv_hd);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}