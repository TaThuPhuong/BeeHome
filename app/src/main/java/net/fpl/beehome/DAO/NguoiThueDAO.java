package net.fpl.beehome.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;

public class NguoiThueDAO {
    FirebaseFirestore firestore;
    Context context;

    public NguoiThueDAO(FirebaseFirestore firestore , Context context){
        this.firestore = firestore;
        this.context = context;
    }
    public ArrayList<NguoiThue> getall(){
        ArrayList<NguoiThue> arr = new ArrayList<>();
        firestore.collection(NguoiThue.TB_NGUOITHUE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                NguoiThue objNguoiThue = document.toObject(NguoiThue.class);

                                arr.add(objNguoiThue);
                                Log.d("dddddddd", document.getId() + " = > " + document.getData());
                            }
                        }else {
                            Log.d("dddddddddd", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return arr;
    }
}
