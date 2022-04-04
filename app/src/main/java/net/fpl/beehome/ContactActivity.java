package net.fpl.beehome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.LienHe.ContactAdapter;
import net.fpl.beehome.model.LienHe;
import net.fpl.beehome.model.NguoiThue;

import org.json.JSONObject;

import java.net.Socket;
import java.util.ArrayList;

import io.socket.emitter.Emitter;


public class ContactActivity extends AppCompatActivity {

    RecyclerView rcvMess;
    ArrayList<LienHe> list;
    ContactAdapter messageAdapter;
    private Animation animationUp, animationDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        list = new ArrayList<>();
        messageAdapter = new ContactAdapter(list);
        rcvMess = findViewById(R.id.rcv_contact);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down);
        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(NguoiThue.TB_NGUOITHUE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                for (DocumentSnapshot snapshot : value){
                    NguoiThue nguoiThue = snapshot.toObject(NguoiThue.class);
                    LienHe lienHe = new LienHe(nguoiThue.getHoTen(), nguoiThue.getSDT());
                    list.add(lienHe);
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });
        messageAdapter = new ContactAdapter(list);
        rcvMess.setAdapter(messageAdapter);
    }
}