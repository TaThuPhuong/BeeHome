package net.fpl.beehome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.fpl.beehome.Adapter.Message.MessageAdapter;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MessageActivity extends AppCompatActivity {

    public static final String URL_SV = "http://192.168.0.101:3000";
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    RecyclerView rcvMess;
    EditText edMess;
    ImageView imgMess;
    ArrayList<String> list;
    MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mSocket.connect();
        list = new ArrayList<>();
        messageAdapter = new MessageAdapter(list);

        rcvMess = findViewById(R.id.rcv_mess);
        edMess = findViewById(R.id.ed_mess);
        imgMess = findViewById(R.id.img_mess);

        connectSocket();
        mSocket.on("receiver_message", setOnNewMessage);

        messageAdapter = new MessageAdapter(list);
        rcvMess.setAdapter(messageAdapter);

        imgMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strMess = edMess.getText().toString();
                Log.e("TAG", "onClick: " + strMess );
                
                Log.e("TAG", "onClick: " );
                Log.e("TAG", "onClick: " + list.size() );
                edMess.setText("");
            }
        });

    }

    Emitter.Listener setOnNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject)args[0];
                    String msg = data.optString("data");
                }
            });
        }
    };

   void connectSocket(){
        try {
            mSocket = IO.socket(URL_SV);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
   }
}