package net.fpl.beehome;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;

import net.fpl.beehome.Adapter.NguoiThue.NguoiThueSwip;
import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NguoiThue_Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    FloatingActionButton fladd;
    FirebaseFirestore firestore;
    TextInputLayout ed_ten, ed_sodt, ed_email, ed_cccd;
    RecyclerView rc_nguoithue;
    Toolbar toolbar;
    SearchView searchView;
    Button btn_them, btn_huy;
    NguoiThueSwip nguoiThueSwip;
    ArrayList<NguoiThue> arr;
    CountryCodePicker ccp;
    FirebaseAuth fba;
    SwipeRefreshLayout swipeRefreshLayout1;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_thue);
        rc_nguoithue = findViewById(R.id.rc_nguoithue);
        swipeRefreshLayout1 = findViewById(R.id.sw_rv_nguoithue);
        ccp = (CountryCodePicker) findViewById(R.id.cpp);
        fladd = findViewById(R.id.fl_nguoithue);
        toolbar = findViewById(R.id.toolbar_nguoithue);
        firestore = FirebaseFirestore.getInstance();
        fba = FirebaseAuth.getInstance();
        ArrayList<NguoiThue> list = getall();
        nguoiThueSwip = new NguoiThueSwip(list, NguoiThue_Activity.this, firestore);
        setSupportActionBar(toolbar);
        rc_nguoithue.setAdapter(nguoiThueSwip);
        swipeRefreshLayout1.setOnRefreshListener(this);

        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
    }

    private void opendialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_them_nguoithue, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);

        ed_ten = view.findViewById(R.id.ed_hotennguoithue);
        ed_sodt = view.findViewById(R.id.ed_sdtnguoithue);
        ed_email = view.findViewById(R.id.ed_emailnguoithue);
        ed_cccd = view.findViewById(R.id.ed_cccdnguoithue);
        ed_ten.setError(null);
        ed_sodt.setError(null);
        ed_email.setError(null);
        ed_cccd.setError(null);
        btn_them = view.findViewById(R.id.btn_dangkinguoithue);
        btn_huy = view.findViewById(R.id.btn_huynguoithue);
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//ccp.getFullNumber() +
                String ten = ed_ten.getEditText().getText().toString();
                String sodt = ed_sodt.getEditText().getText().toString();
                String email = ed_email.getEditText().getText().toString();
                String cccd = ed_cccd.getEditText().getText().toString();
                setUnErrNguoiThue(ed_ten);
                setUnErrNguoiThue(ed_sodt);
                setUnErrNguoiThue(ed_email);
                setUnErrNguoiThue(ed_cccd);
//                ccp.registerCarrierNumberEditText(ed_sodt);
                if (TextUtils.isEmpty(ten)) {
                    ed_ten.setError("Không Được Để Trống Tên");
                    return;
                } else if (!isNumber(sodt)) {
                    ed_sodt.setError("Không Đúng Số Điện Thoại");
                    return;
                } else if (!isEmail(email)) {
                    ed_email.setError("Không Đúng Định Dạng Email");
                    return;
                } else if (cccd.length() < 12) {
                    ed_cccd.setError("Căn Cước 12 Số");
                    return;
                } else {
                    NguoiThue nguoiThue = new NguoiThue();
                    nguoiThue.setId_thanhvien(sodt);
                    nguoiThue.setHoTen(ten);
                    nguoiThue.setId_phong("Trống");
                    nguoiThue.setSdt(sodt);
                    nguoiThue.setEmail(email);
                    nguoiThue.setCccd(cccd);

                    if (checkIDNguoiThue(nguoiThue) != null) {
                        ed_sodt.setError("Số điện thoại đã được đăng ký");
                        return;
                    } else if (checkCMND(nguoiThue) != null) {
                        ed_cccd.setError("Số CCCD đã được đăng ký");
                        return;
                    } else {
                        themNguoiThue(nguoiThue);
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nguoiThueSwip.getFilter().filter(query );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                nguoiThueSwip.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public static boolean isEmail(CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    public static boolean isNumber(String input) {
        Pattern b = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        Matcher m = b.matcher(input);
        return m.matches();
    }

    private void themNguoiThue(NguoiThue nguoiThue) {
        firestore.collection(NguoiThue.TB_NGUOITHUE).document(nguoiThue.getSdt())
                .set(nguoiThue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        themAuth(nguoiThue);
                        Toast.makeText(NguoiThue_Activity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        nguoiThueSwip.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NguoiThue_Activity.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void themAuth(NguoiThue nguoiThue) {
        Log.d("TAG", "onComplete: " + nguoiThue.getEmail() + nguoiThue.getSdt());

        fba.createUserWithEmailAndPassword(nguoiThue.getEmail(), nguoiThue.getSdt());
    }

    public ArrayList<NguoiThue> getall() {
        arr = new ArrayList<>();
        firestore.collection(NguoiThue.TB_NGUOITHUE)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        arr.clear();
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            NguoiThue objNguoiThue = documentSnapshot.toObject(NguoiThue.class);
                            arr.add(objNguoiThue);
                            nguoiThueSwip.notifyDataSetChanged();
                        }
                    }
                });
        return arr;
    }

    public NguoiThue checkIDNguoiThue(NguoiThue nguoiThue) {
        for (NguoiThue nguoiThue1 : arr) {
            if (nguoiThue.getSdt().equals(nguoiThue1.getSdt())) {
                Toast.makeText(this, "Trùng Số Điện Thoại", Toast.LENGTH_SHORT).show();
                return nguoiThue1;
            }
        }
        return null;
    }

    public NguoiThue checkCMND(NguoiThue nguoiThue) {
        for (NguoiThue nguoiThue1 : arr) {
            if (nguoiThue.getCccd().equals(nguoiThue1.getCccd())) {
                Toast.makeText(this, "Trùng Căn Cước Công Dân", Toast.LENGTH_SHORT).show();
                return nguoiThue1;
            }
        }
        return null;
    }

    public void setUnErrNguoiThue(TextInputLayout textInputLayout) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textInputLayout.getEditText().getText().toString().length() != 0) {
                    textInputLayout.setError(null);
                }
            }
        });
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nguoiThueSwip.notifyDataSetChanged();
                swipeRefreshLayout1.setRefreshing(false);
            }
        }, 1000);
    }
}