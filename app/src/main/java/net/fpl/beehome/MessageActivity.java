package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import net.fpl.beehome.Adapter.Message.MessageAdapter;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rcvMess;
    TextView tv_mess;
    EditText ed_mess;
    ImageView img_mess;
    ArrayList<String> list;
    MessageAdapter messageAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rcvMess = findViewById(R.id.rcv_mess);
        tv_mess = findViewById(R.id.tv_mess);
        ed_mess = findViewById(R.id.ed_mess);
        img_mess = findViewById(R.id.img_send);
        messageAdapter = new MessageAdapter(this, list);

        img_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strMess= ed_mess.getText().toString().trim();
                if (TextUtils.isEmpty(strMess)){
                    return;
                } else {
                    addMess(strMess);
                }
            }
        });

    }

    public void addMess(String strMess){
        DatabaseReference reference = database.getReference("mess");
        reference.setValue(strMess);
    }

    public void getMess(){
        DatabaseReference reference = database.getReference("mess");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMess();
    }
}