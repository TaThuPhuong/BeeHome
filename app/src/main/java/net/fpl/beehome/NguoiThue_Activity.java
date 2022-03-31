package net.fpl.beehome;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;

import net.fpl.beehome.Adapter.NguoiThue.NguoiThueSwip;
import net.fpl.beehome.DAO.NguoiThueDAO;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NguoiThue_Activity extends AppCompatActivity {
    FloatingActionButton fladd;
    FirebaseFirestore firestore;
    TextInputLayout ed_ten, ed_sodt, ed_email, ed_cccd;
    RecyclerView rc_nguoithue;
    Button btn_them, btn_huy;
    NguoiThueDAO nguoiThueDAO;
    NguoiThueSwip nguoiThueSwip;
    ArrayList<NguoiThue> arr ;
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_thue);
        rc_nguoithue = findViewById(R.id.rc_nguoithue);
        ccp = (CountryCodePicker) findViewById(R.id.cpp);
        fladd = findViewById(R.id.fl_nguoithue);
        firestore = FirebaseFirestore.getInstance();
        nguoiThueDAO = new NguoiThueDAO(firestore,getBaseContext());
        ArrayList<NguoiThue> list = getall();
        nguoiThueSwip = new NguoiThueSwip(list,NguoiThue_Activity.this,firestore);

        rc_nguoithue.setAdapter(nguoiThueSwip);

        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
    }

    public ArrayList<Phong> getIDPhong() {
        ArrayList<Phong> ls = new ArrayList<>();
        firestore.collection(Phong.TB_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    Phong phong = documentSnapshot.toObject(Phong.class);
                    if (phong.getTrangThai().equals("Đang thuê")){
                        ls.add(phong);
                    }

                    Log.d("zzzzzzzz", "onComplete: " + documentSnapshot.getId());
                }
            }
        });
        Log.d("zzzzzzzz", "onComplete: " + ls.size());

        return ls;
    }

    private void opendialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_them_nguoithue, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
                String sodt =  ed_sodt.getEditText().getText().toString();
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
                }else if (!isNumber(sodt)){
                    ed_sodt.setError("Không Đúng Số Điện Thoại");
                    return;
                }else if (!isEmail(email)){
                    ed_email.setError("Không Đúng Định Dạng Email");
                    return;
                }else if (cccd.length() != 12 ){
                    ed_cccd.setError("Căn Cước 12 Số");
                    return;
                }
                else {
                    NguoiThue nguoiThue = new NguoiThue();
                    nguoiThue.setID_thanhvien(sodt);
                    nguoiThue.setHoTen(ten);
                    nguoiThue.setID_phong("Trống");
                    nguoiThue.setSDT(sodt);
                    nguoiThue.setPassword(sodt);
                    nguoiThue.setEmail(email);
                    nguoiThue.setCCCD(cccd);

                    if (checkIDNguoiThue(nguoiThue) != null){
                        Toast.makeText(NguoiThue_Activity.this, "Số Điện Thoại Trùng Lặp", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (checkCMND(nguoiThue) != null){
                        Toast.makeText(NguoiThue_Activity.this, "Trùng Căn Cước Công Dân", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        themNguoiThue(nguoiThue);
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static boolean isEmail(CharSequence charSequence){
        return !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }
    public static boolean isNumber(String input){
        Pattern b = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        Matcher m = b.matcher(input);
        return m.matches();
    }

    private void themNguoiThue(NguoiThue nguoiThue) {
        firestore.collection(NguoiThue.TB_NGUOITHUE).document(nguoiThue.getSDT())
                .set(nguoiThue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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
    public ArrayList<NguoiThue> getall(){
        arr = new ArrayList<>();
        firestore.collection(NguoiThue.TB_NGUOITHUE)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        arr.clear();
                        for (QueryDocumentSnapshot documentSnapshot : value){
                            NguoiThue objNguoiThue = documentSnapshot.toObject(NguoiThue.class);
                            arr.add(objNguoiThue);
                            nguoiThueSwip.notifyDataSetChanged();
                        }
                    }
                });
        return arr;
    }
    public NguoiThue checkIDNguoiThue(NguoiThue nguoiThue){
        for (NguoiThue nguoiThue1 : arr){
            if (nguoiThue.getSDT().equals(nguoiThue1.getSDT())){
                Toast.makeText(this, "Trùng Số Điện Thoại", Toast.LENGTH_SHORT).show();
                return nguoiThue1;
            }
        }
        return null;
    }
    public NguoiThue checkCMND(NguoiThue nguoiThue){
        for (NguoiThue nguoiThue1 : arr){
            if (nguoiThue.getCCCD().equals(nguoiThue1.getCCCD())){
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
}