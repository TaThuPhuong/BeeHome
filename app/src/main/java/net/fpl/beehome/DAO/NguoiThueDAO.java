package net.fpl.beehome.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
