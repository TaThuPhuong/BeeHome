package net.fpl.beehome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NguoiThue_Activity extends AppCompatActivity {
    FloatingActionButton fladd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_thue);
        fladd = findViewById(R.id.fl_nguoithue);
        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
    }
    private void opendialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_them_nguoithue, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}