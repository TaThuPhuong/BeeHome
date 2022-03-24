package net.fpl.beehome.DAO;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.firestore.auth.User;

import net.fpl.beehome.model.HopDong;

import java.util.ArrayList;

public class HopDongDAO {
    FirebaseFirestore db;
    Context context;


    public HopDongDAO(FirebaseFirestore db, Context context) {
        this.db = db;
        this.context = context;
    }

    public ArrayList<HopDong> getAll(){
        ArrayList<HopDong> arr = new ArrayList<>();
        db.collection(HopDong.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                                HopDong objHopDong = document.toObject(HopDong.class);
                                arr.add(objHopDong);
                                Log.d("aaaaaaa", document.getId() + " => " + document.getData());
                }
            }
        });

        return arr;
    }
}
