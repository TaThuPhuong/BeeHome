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

import net.fpl.beehome.SuCoActivity;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.SuCo;

import java.util.ArrayList;

public class SuCoDAO {
    FirebaseFirestore db;
    Context context;

    public SuCoDAO(FirebaseFirestore db, Context context) {
        this.db = db;
        this.context = context;
    }

    public ArrayList<SuCo> getAll(){
        ArrayList<SuCo> arr = new ArrayList<>();

        db.collection(SuCo.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    SuCo objSuCo = document.toObject(SuCo.class);
                    arr.add(objSuCo);
                    Log.d("aaaaaaa", document.getId() + " => " + document.getData());
                }
            }
        });

        return arr;
    }
}
