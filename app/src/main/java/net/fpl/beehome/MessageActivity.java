package net.fpl.beehome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.fpl.beehome.Adapter.Message.MessageAdapter;
import net.fpl.beehome.model.Message;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rcvMess;
    EditText edMess;
    ImageView imgMess;
    ArrayList<Message> list;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rcvMess = findViewById(R.id.rcv_mess);
        edMess = findViewById(R.id.ed_mess);
        imgMess = findViewById(R.id.img_mess);

        list = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        messageAdapter.setData(list);

        rcvMess.setAdapter(messageAdapter);

        imgMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    private void sendMessage() {
        String strMess = edMess.getText().toString().trim();
        if (TextUtils.isEmpty(strMess)) {
            return;
        }
        Log.e("TAG", "sendMessage: " + strMess );

        Message message = new Message();
        message.setMess(strMess);

        list.add(message);
        messageAdapter.notifyDataSetChanged();
        rcvMess.scrollToPosition(list.size() - 1);
        Log.e("TAG", "sendMessage: " + list.size() );
        Log.e("TAG", "sendMessage: " +  message.toString());
        edMess.setText("");

    }

}