package net.fpl.beehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rcvMess;
    TextView tv_mess;
    EditText ed_mess;
    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://beehome-d0968-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rcvMess = findViewById(R.id.rcv_mess);
        tv_mess = findViewById(R.id.tv_mess);
        ed_mess = findViewById(R.id.ed_mess);


    }
}