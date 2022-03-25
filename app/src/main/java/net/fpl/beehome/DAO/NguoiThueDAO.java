package net.fpl.beehome.DAO;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.R;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class NguoiThueDAO {
    FirebaseFirestore firestore;
    Context context;

    public NguoiThueDAO(FirebaseFirestore firestore , Context context){
        this.firestore = firestore;
        this.context = context;
    }
    public void thongbaonguoithue(int type, String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (type == 0){
            View view = View.inflate(context, R.layout.dialog_thong_bao_nguoithue_success, null);
            builder.setView(view);

            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            TextView btn = view.findViewById(R.id.btn_successnguoithue);
            TextView tv = view.findViewById(R.id.tv_dialognguoithue);
            tv.setText(mess);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else if(type == 1) {
            View view = View.inflate(context, R.layout.dialog_thong_bao_nguoithue_fail, null);
            builder.setView(view);

            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            TextView btn = view.findViewById(R.id.btn_failnguoithue);
            TextView tv = view.findViewById(R.id.tv_dialognguoithue);
            tv.setText(mess);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }
    public ArrayList<NguoiThue> getall(){
        ArrayList<NguoiThue> arr = new ArrayList<>();
        firestore.collection(NguoiThue.TB_NGUOITHUE)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        arr.clear();
                        for (QueryDocumentSnapshot documentSnapshot : value){
                            NguoiThue objNguoiThue = documentSnapshot.toObject(NguoiThue.class);
                            arr.add(objNguoiThue);
                        }
                    }
                });
        return arr;
    }
}
