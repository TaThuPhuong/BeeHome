package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.fpl.beehome.Adapter.Message.MessageAdapter;
import net.fpl.beehome.model.Admin;
import net.fpl.beehome.model.Mess;
import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rcvMess;
    TextView tv_mess;
    EditText ed_mess;
    ImageView img_mess;
    ArrayList<Mess> list;
    MessageAdapter messageAdapter;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rcvMess = findViewById(R.id.rcv_mess);
        tv_mess = findViewById(R.id.tv_mess);
        ed_mess = findViewById(R.id.ed_mess);
        img_mess = findViewById(R.id.img_send);
        list = new ArrayList<Mess>();
        db = FirebaseDatabase.getInstance().getReference("Message");
        Intent intent = getIntent();
        NguoiThue nguoiThue = (NguoiThue) intent.getSerializableExtra("user_nhan");
        Admin admin = (Admin) intent.getSerializableExtra("admin_nhan");
        NguoiThue nguoiThueGui = (NguoiThue) intent.getSerializableExtra("user_gui");
        Admin adminGui = (Admin) intent.getSerializableExtra("admin_gui");

        if (adminGui != null) {
            tv_mess.setText(nguoiThue.getHoTen());
            db.child(adminGui.getSdt() + nguoiThue.getSdt()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Mess mess = snapshot1.getValue(Mess.class);
                        list.add(mess);
                    }
                    messageAdapter = new MessageAdapter(list, adminGui.getSdt());
                    rcvMess.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            img_mess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String strMess = ed_mess.getText().toString().trim();
                    Mess mess = new Mess(strMess, adminGui.getSdt());
                    if (TextUtils.isEmpty(strMess)) {
                        return;
                    } else {
                        db.child(adminGui.getSdt() + nguoiThue.getSdt()).push()
                                .setValue(mess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                db.child(nguoiThue.getSdt() + adminGui.getSdt()).push()
                                        .setValue(mess).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                            }
                        });
                        ed_mess.setText("");
                    }
                }
            });
        } else {
            tv_mess.setText(admin.getHoTen());
            db.child(nguoiThueGui.getSdt() + admin.getSdt()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Mess mess = snapshot1.getValue(Mess.class);
                        list.add(mess);
                    }
                    messageAdapter = new MessageAdapter(list, nguoiThueGui.getSdt());
                    rcvMess.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            img_mess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String strMess = ed_mess.getText().toString().trim();
                    Mess mess = new Mess(strMess, nguoiThueGui.getSdt());
                    if (TextUtils.isEmpty(strMess)) {
                        return;
                    } else {
                        db.child(nguoiThueGui.getSdt() + admin.getSdt()).push()
                                .setValue(mess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                db.child(admin.getSdt() + nguoiThueGui.getSdt()).push()
                                        .setValue(mess).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                            }
                        });
                        ed_mess.setText("");
                    }
                }
            });
        }


    }

}

