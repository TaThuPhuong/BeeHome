package net.fpl.beehome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.Message.MessageAdapter;
import net.fpl.beehome.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.Socket;

public class MessageActivity extends AppCompatActivity {

    public static final String URL_SV = "http://192.168.0.101:3000";
    Socket mSocket;
    RecyclerView rcvMess;
    EditText edMess;
    ImageView imgMess;
    ArrayList<Message> list;
    MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        list = new ArrayList<>();
        messageAdapter = new MessageAdapter(list);

        rcvMess = findViewById(R.id.rcv_mess);
        edMess = findViewById(R.id.ed_mess);
        imgMess = findViewById(R.id.img_mess);

        imgMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strMess = edMess.getText().toString();
                Message message = new Message(strMess);
                Log.e("TAG", "onClick: " + strMess );
                sendMessage(message);
                Log.e("TAG", "onClick: " +  message);
                Log.e("TAG", "onClick: " + list.size() );
                edMess.setText("");
            }
        });

    }

    private void sendMessage(Message message) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Message.TB_NAME).document().set(message);
    }

    public void getMess(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        db.collection(Message.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                for (QueryDocumentSnapshot snapshot:value){
                    Message message = snapshot.toObject(Message.class);
                    Log.e("TAG", "onComplete: "+snapshot.getId() + " / " + snapshot.getData());
                    list.add(message);
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });
        messageAdapter = new MessageAdapter(list);
        rcvMess.setAdapter(messageAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMess();
    }
}