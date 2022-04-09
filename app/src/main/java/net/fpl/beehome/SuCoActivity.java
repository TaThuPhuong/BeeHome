package net.fpl.beehome;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.SuCo.SuCoAdapter;
import net.fpl.beehome.Adapter.SuCo.SuCoAdapter2;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.SuCo;

import java.util.ArrayList;
import java.util.Calendar;

public class SuCoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    FloatingActionButton btn_add;
    RecyclerView rv_cs;
    FirebaseFirestore fb;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<SuCo> arr;
    NguoiThue objNguoiThue;
    SuCoAdapter suCoAdapter;
    SuCoAdapter2 suCoAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_co);

        btn_add = findViewById(R.id.btn_add);
        rv_cs = findViewById(R.id.rv_sc);
        fb = FirebaseFirestore.getInstance();
        swipeRefreshLayout = findViewById(R.id.sw_rv);

        rv_cs.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        String quyen = intent.getStringExtra("quyen");
        objNguoiThue = (NguoiThue) intent.getSerializableExtra("nt");
        Log.d("arrHDP", "onViewCreated: " + objNguoiThue);

        if (quyen.equalsIgnoreCase("admin")) {
            btn_add.setVisibility(View.GONE);
            arr = getAll2();
            suCoAdapter2 = new SuCoAdapter2(arr, objNguoiThue, SuCoActivity.this, fb);
            rv_cs.setAdapter(suCoAdapter2);
        } else {
            arr = getSuCoPhong(objNguoiThue.getId_phong());
            suCoAdapter = new SuCoAdapter(arr, objNguoiThue, SuCoActivity.this, fb);
            rv_cs.setAdapter(suCoAdapter);
        }


        swipeRefreshLayout.setOnRefreshListener(this);

        rv_cs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(SuCoActivity.this, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_add_sc);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                TextInputLayout ed_mota = dialog.findViewById(R.id.ed_mota);
                TextInputLayout ed_ngbao = dialog.findViewById(R.id.ed_ngaybaocao);
                TextView tv_phong = dialog.findViewById(R.id.tv_p_d);
                tv_phong.setText(objNguoiThue.getId_phong());
                Button btn_bc = dialog.findViewById(R.id.btn_bc);

                Calendar calendar = Calendar.getInstance();
                final int y = calendar.get(Calendar.YEAR);
                final int m = calendar.get(Calendar.MONTH);
                final int d = calendar.get(Calendar.DAY_OF_MONTH);

                ed_ngbao.getEditText().setText(y + "/" + (m + 1) + "/" + d);

                btn_bc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ed_mota.getEditText().getText().toString().length() == 0 || ed_mota.getEditText().getText().toString().length() < 5) {
                            ed_mota.setError("Độ dài ký tự không hợp lệ (5 đến 30 ký tự)");
                            return;
                        } else if (!checkMT(ed_mota.getEditText().getText().toString())) {
                            ed_mota.setError("Bạn đã báo cáo sự cố này rồi");
                            return;
                        }

                        SuCo objSuCo = new SuCo();
                        objSuCo.setId_suco(tv_phong.getText().toString() + ed_mota.getEditText().getText().toString());
                        objSuCo.setId_phong(tv_phong.getText().toString());
                        objSuCo.setMoTa(ed_mota.getEditText().getText().toString());
                        objSuCo.setNgayBaoCao(ed_ngbao.getEditText().getText().toString());


                        fb.collection(SuCo.TB_NAME).document(tv_phong.getText().toString() + ed_mota.getEditText().getText().toString())
                                .set(objSuCo)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SuCoActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        suCoAdapter.notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SuCoActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                suCoAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    public ArrayList<SuCo> getAll2() {
        ArrayList<SuCo> arr = new ArrayList<>();

        fb.collection(SuCo.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    SuCo objSuCo = document.toObject(SuCo.class);
                    arr.add(objSuCo);
                    Log.d("aaaaaaa", document.getId() + " => " + document.getData());
                }
                suCoAdapter2.notifyDataSetChanged();
            }
        });

        return arr;
    }


    public ArrayList<SuCo> getSuCoPhong(String idphong) {
        ArrayList<SuCo> arr = new ArrayList<>();

        fb.collection(SuCo.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    SuCo objSuCo = document.toObject(SuCo.class);
                    if (objSuCo.getId_phong().equalsIgnoreCase(idphong)) {
                        arr.add(objSuCo);
                        Log.d("aaaaaaa", document.getId() + " => " + document.getData());
                    }
                }
                suCoAdapter.notifyDataSetChanged();
            }
        });
        return arr;
    }

    public boolean checkMT(String str) {
        for (SuCo objSuCo : arr) {
            if (objSuCo.getMoTa().equalsIgnoreCase(str)) {
                return false;
            }
        }
        return true;
    }
}