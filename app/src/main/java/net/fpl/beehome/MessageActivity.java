package net.fpl.beehome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Value;

import net.fpl.beehome.Adapter.Message.MessageAdapter;
import net.fpl.beehome.model.Mess;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rcvMess;
    TextView tv_mess;
    EditText ed_mess;
    ImageView img_mess;
    ArrayList<Mess> list;
    MessageAdapter messageAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rcvMess = findViewById(R.id.rcv_mess);
        tv_mess = findViewById(R.id.tv_mess);
        ed_mess = findViewById(R.id.ed_mess);
        img_mess = findViewById(R.id.img_send);
        list = new ArrayList<Mess>();
        messageAdapter = new MessageAdapter(this, list);
        rcvMess.setAdapter(messageAdapter);

        img_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strMess= ed_mess.getText().toString().trim();
                if (TextUtils.isEmpty(strMess)){
                    return;
                } else {
                    db.collection("Mesaage").document().set(new Mess(strMess));
                    rcvMess.scrollToPosition(list.size()-1);
                    ed_mess.setText("");
                    Log.e("TAG", "onClick: " + list.size() );
                }
            }
        });

    }

    public void getMess(){
        db.collection("Mesaage").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                for (QueryDocumentSnapshot snapshot : value){
                    Mess strmess = snapshot.toObject(Mess.class);
                    list.add(strmess);
                    messageAdapter.notifyDataSetChanged();
                }
                rcvMess.setAdapter(messageAdapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMess();
    }
}