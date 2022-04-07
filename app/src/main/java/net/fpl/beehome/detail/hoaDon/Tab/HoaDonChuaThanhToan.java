package net.fpl.beehome.detail.hoaDon.Tab;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;


import static net.fpl.beehome.MySharedPreferences.NgDung;
import static net.fpl.beehome.MySharedPreferences.USER_KEY;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.hoaDon.HoaDonAdapter;

import net.fpl.beehome.Adapter.hoaDon.HoaDonNguoiThueAdapter;
import net.fpl.beehome.R;
import net.fpl.beehome.detail.hoaDon.HoaDonMain;
import net.fpl.beehome.model.DichVu;
import net.fpl.beehome.model.HoaDon;
import net.fpl.beehome.model.HoaDonChiTiet;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HoaDonChuaThanhToan extends Fragment {
    FloatingActionButton fab;
    FirebaseFirestore fb;
    RecyclerView recyclerView;
    ArrayList<HoaDon> arr;
    ArrayList<HoaDon> arrHDP;
    ArrayList<HoaDon> arrHD;
    ArrayList<HoaDonChiTiet> arrHDCT;
    ArrayList<HopDong> arrHopDong;
    ArrayList<Phong> arrPhong ;
    ArrayList<String> arrTenPhong ;
    ArrayList<DichVu> arrDichVu;
    ArrayList<NguoiThue> arrNguoiThue;
    HoaDonAdapter adapterhd;
    HoaDonNguoiThueAdapter adapternt;
    SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");
    HoaDonMain main;
    NguoiThue objNguoiThue;

    FirebaseUser ngDung;

    String tenP,user,email;
    String  idThang,thang,han,idP,hoTen;
    int tienSoDien, tienSoNuoc,tienNuoc,tienDien,tongTienPhong,TongtienDV,tongHD,dienMoi,nuocMoi, tienDVPhong = 0,month_han,year_han,month_thang,year_thang;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hoa_don_chua_thanh_toan, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ngDung = FirebaseAuth.getInstance().getCurrentUser();
        email = ngDung.getEmail();
        main = (HoaDonMain) getActivity();
        objNguoiThue = main.getNguoiThue();

        SharedPreferences pref = getActivity().getSharedPreferences("MSP_EMAIL_PASSWORD",MODE_PRIVATE);
        user = pref.getString(NgDung,"");



        fb = FirebaseFirestore.getInstance();
        fab = view.findViewById(R.id.btn_them_hdon);
        recyclerView = view.findViewById(R.id.recyclerView_hd);

        LinearLayoutManager llm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);


        arr = getAllHoaDon();

        arrHopDong = getAllHopDong();
        arrDichVu = getAllDichVu();
        arrPhong = getAllPhong();
        arrTenPhong = getTenPhong();
        arrNguoiThue = getAllNguoiThue();

        for(int z =0; z<arrNguoiThue.size();z++){
            if(arrNguoiThue.get(z).getEmail().equalsIgnoreCase(email)){
                idP = arrNguoiThue.get(z).getId_phong();
                break;
            }
        }

        arrHDP = getHoaDonPhong(idP);

        if(user.equalsIgnoreCase("Admin"))
        {
                    adapterhd = new HoaDonAdapter(arr,getContext(),fb,getTenPhong(),getAllPhong(),getAllHopDong(),getAllDichVu());

                    adapterhd.notifyDataSetChanged();

                    recyclerView.setAdapter(adapterhd);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(R.layout.dialog_hoa_don_them);
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

//                ánh xạ
                    Spinner sp_phong = dialog.findViewById(R.id.spn_Phong);
                    TextInputLayout L_hd_thang = dialog.findViewById(R.id.L_thang_hd);
                    TextInputLayout L_hd_han = dialog.findViewById(R.id.L_han_hd);
                    TextInputLayout L_dien_moi = dialog.findViewById(R.id.L_dien_moi);
                    TextInputLayout L_nuoc_moi = dialog.findViewById(R.id.L_nuoc_moi);
                    TextInputLayout L_giam_gia = dialog.findViewById(R.id.L_giamGia);


                    TextInputEditText hd_thang = dialog.findViewById(R.id.thang_hd);
                    TextInputEditText hd_han = dialog.findViewById(R.id.han_hd);
                    TextInputEditText dien_cu = dialog.findViewById(R.id.dien_cu);
                    TextInputEditText dien_moi = dialog.findViewById(R.id.dien_moi);
                    TextInputEditText nuoc_cu = dialog.findViewById(R.id.nuoc_cu);
                    TextInputEditText nuoc_moi = dialog.findViewById(R.id.nuoc_moi);
                    TextInputEditText tienPhong = dialog.findViewById(R.id.tienPhong);
                    TextInputEditText tienDV = dialog.findViewById(R.id.tienDV);
                    TextInputEditText giamGia = dialog.findViewById(R.id.giamGia);

                    LinearLayout lnLDien = dialog.findViewById(R.id.lnLDien);
                    LinearLayout lnLNuoc = dialog.findViewById(R.id.lnLNuoc);

                    ImageButton chotDien = dialog.findViewById(R.id.chotDien);
                    ImageButton chotNuoc = dialog.findViewById(R.id.chotNuoc);

                    TextView tvDien = dialog.findViewById(R.id.tvDien);
                    TextView tvNuoc = dialog.findViewById(R.id.tvNuoc);
                    TextView errAll = dialog.findViewById(R.id.errTongHop);
                    MaterialTextView tvTongHop = dialog.findViewById(R.id.tongHop);

                    TextView tongTien = dialog.findViewById(R.id.tongTien);
                    TextInputLayout ghiChu = dialog.findViewById(R.id.ghiChuHD);

                    Button clear = dialog.findViewById(R.id.btnClear);
                    Button add = dialog.findViewById(R.id.btnAddHd);


                    hd_thang.setError(null);
                    hd_han.setError(null);
                    L_dien_moi.setError(null);
                    L_nuoc_moi.setError(null);
                    nuoc_moi.setError(null);
                    L_giam_gia.setError(null);


                    final Calendar calendar = Calendar.getInstance();
                    int d = calendar.get(Calendar.DAY_OF_MONTH);
                    int m = calendar.get(Calendar.MONTH);
                    int y = calendar.get(Calendar.YEAR);

                    for(int z =0; z<arrDichVu.size();z++){
                        if(arrDichVu.get(z).getDonVi().equals("Phòng")){
                            tienDVPhong += arrDichVu.get(z).getGia();
                        }
                    }

                    tienDV.setText(tienDVPhong+"");

                    hd_thang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.datePicker,new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    final String thangHD = dayOfMonth + "/" + (month + 1) + "/" + year;
                                    month_thang = month + 1;
                                    year_thang = year;
                                    hd_thang.setText(thangHD);
                                    idThang = month_thang+"."+year_thang;
                                    thang = hd_thang.getText().toString();
                                }
                            }, y, m, d);
                            datePickerDialog.show();
                        }
                    });

                    hd_han.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.datePicker,new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    final String hanGD = dayOfMonth + "/" + (month + 1) + "/" + year;
                                    month_han = month + 1;
                                    year_han = year;
                                    hd_han.setText(hanGD);
                                    han = hd_han.getText().toString();
                                }
                            }, y, m, d);
                            datePickerDialog.show();

                        }
                    });

                    ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrTenPhong);
                    sp_phong.setAdapter(arrayAdapter);
                    sp_phong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            tenP = adapterView.getItemAtPosition(position).toString();
                            for(int x = 0; x<arrPhong.size(); x++){
                                if (arrPhong.get(x).getIDPhong().equalsIgnoreCase(tenP)){
                                    Phong objPhong = arrPhong.get(x);
                                    dien_cu.setText(objPhong.getSoDienDau()+"");
                                    nuoc_cu.setText(objPhong.getSoNuocDau()+"");
                                    tienPhong.setText(objPhong.getGiaPhong()+"");
                                }
                            }
                            lnLDien.setVisibility(View.INVISIBLE);
                            lnLNuoc.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    chotDien.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!dien_moi.getText().toString().isEmpty()){
                                lnLDien.setVisibility(VISIBLE);
                                Log.d("zzzzzz", "tienDien " + arrDichVu);
                                for(int x =0; x<arrDichVu.size();x++){
                                    if(arrDichVu.get(x).getTenDichVu().equals("Điện")){
                                        tienSoDien = arrDichVu.get(x).getGia();
                                    }
                                }
                                dienMoi = Integer.parseInt(String.valueOf(dien_moi.getText()));
                                int dienCu = Integer.parseInt(String.valueOf(dien_cu.getText()));
                                if(dienMoi < dienCu){
                                    L_dien_moi.setError("Số điện mới phải > số điện cũ");
                                }else {
                                    L_dien_moi.setError(null);
                                    int sLDien = dienMoi - dienCu;

                                    tienDien = sLDien * tienSoDien;
                                    tvDien.setText(tienDien+"");

                                    TongtienDV = tienDien+tienNuoc+tienDVPhong;
                                    tienDV.setText(TongtienDV+"");
                                }

//


                            }

                        }
                    });

                    chotNuoc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            L_nuoc_moi.setError(null);
                            if(!nuoc_moi.getText().toString().isEmpty()){
                                lnLNuoc.setVisibility(VISIBLE);
                                for(int x =0; x<arrDichVu.size();x++){
                                    if(arrDichVu.get(x).getTenDichVu().equals("Nước")){
                                        tienSoNuoc = arrDichVu.get(x).getGia();
                                    }
                                }
                                int nuocCu = Integer.parseInt(String.valueOf(nuoc_cu.getText()));
                                nuocMoi = Integer.parseInt(String.valueOf(nuoc_moi.getText()));
                                int sLNuoc = nuocMoi - nuocCu;

                                tienNuoc = sLNuoc * tienSoNuoc;

                                tvNuoc.setText(tienNuoc+"");
                                TongtienDV = tienDien+tienNuoc+tienDVPhong;
                                tienDV.setText(TongtienDV+"");
                            }

                        }
                    });

                    tvTongHop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            errAll.setVisibility(View.INVISIBLE);
                            if(giamGia.getText().toString().isEmpty()){
                                L_giam_gia.setError("Nếu không có hãy nhập 0");
                            }else if (lnLDien.getVisibility() != VISIBLE ) {
                                L_dien_moi.setError("Hãy chốt điện!");
                                L_giam_gia.setError(null);
                            }else if(lnLNuoc.getVisibility() != VISIBLE){
                                L_giam_gia.setError(null);
                                L_nuoc_moi.setError("Hãy chốt nước!");
                            }else{
                                L_giam_gia.setError(null);
                                TongtienDV = tienDien+tienNuoc+tienDVPhong;
                                tienDV.setText(TongtienDV+"");
                                int gGia = Integer.parseInt(String.valueOf(giamGia.getText()));
                                tongTienPhong = Integer.parseInt(String.valueOf(tienPhong.getText()));
                                tongHD = TongtienDV+tongTienPhong-gGia;
                                tongTien.setText(tongHD+"");

                            }

                        }
                    });


                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if(month_han - month_thang == 0){
                                L_hd_han.setError("Hạn thanh toán phải sau thời điểm tháng");
                            }else if(year_han - year_thang > 1){
                                L_hd_han.setError("Hạn thanh toán quá xa");
                            }else if(tongHD == 0){
                                tvTongHop.setError("");
                                errAll.setVisibility(VISIBLE);
                            }else{

                                HoaDon objHoaDon = new HoaDon();
                                objHoaDon.setIDHoaDon(tenP+"."+idThang);
                                try {
                                    objHoaDon.setThangHD(dfm.parse(thang));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    objHoaDon.setHanGD(dfm.parse(han));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                objHoaDon.setTongHD(tongHD);
                                objHoaDon.setIDPhong(tenP);
                                objHoaDon.setSoDienCuoi(dienMoi);
                                objHoaDon.setSoNuocCuoi(nuocMoi);
                                if((calendar.getTime().after(objHoaDon.getHanGD()))){
                                    objHoaDon.setTrangThaiHD(2);
                                }else {
                                    objHoaDon.setTrangThaiHD(0);
                                }

                                objHoaDon.setTienDV(TongtienDV);
                                objHoaDon.setTienPhong(tongTienPhong);
                                objHoaDon.setGiamGia(Integer.parseInt(String.valueOf(giamGia.getText())));
                                objHoaDon.setGhiChu(String.valueOf(ghiChu.getEditText().getText()));
                                objHoaDon.setTienDien(tienDien);
                                objHoaDon.setTienNuoc(tienNuoc);
                                objHoaDon.setTienDVC(tienDVPhong);
                                objHoaDon.setNgayGD(null);

                                if (checkIDHD(objHoaDon) != null) {
                                    L_hd_thang.setError("Phòng đã có hóa đơn của tháng "+thang+"! Mời bạn chọn lại!");
                                    return;
                                }else {
                                    fb.collection(HoaDon.TB_NAME).document(objHoaDon.getIDHoaDon())
                                            .set(objHoaDon)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getContext(), "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                                                    Map<String, Object> p = new HashMap<>();
                                                    p.put(Phong.COL_SO_DIEN_DAU, objHoaDon.getSoDienCuoi());
                                                    p.put(Phong.COL_SO_NUOC_DAU, objHoaDon.getSoNuocCuoi());
                                                    fb.collection(Phong.TB_NAME).document(tenP).update(p);
                                                    adapterhd.notifyDataSetChanged();
                                                    dialog.dismiss();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }


                            }
                        }




                    });

                    clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });




                }

            });

        }
        else {
            idP = objNguoiThue.getId_phong();
            fab.setVisibility(View.INVISIBLE);
            adapternt = new HoaDonNguoiThueAdapter(arrHDP,getContext(),fb,arrTenPhong,arrPhong,arrHopDong,arrDichVu,arrNguoiThue);
            adapternt.notifyDataSetChanged();

            Log.d("TAG", "onCreateView: "+arr.size());
            recyclerView.setAdapter(adapternt);


        }



    }


    public ArrayList<HoaDon> getHoaDonPhong(String idphong){
        ArrayList<HoaDon> arr = new ArrayList<>();

        fb.collection(HoaDon.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    HoaDon xHoaDon = document.toObject(HoaDon.class);
                    if(xHoaDon.getIDPhong().equalsIgnoreCase(idphong)){
                        if(xHoaDon.getTrangThaiHD() == 0) {
                            arr.add(xHoaDon);
                        }
                    }
                }
                adapternt.notifyDataSetChanged();
            }
        });
        return arr;
    }
    public ArrayList<NguoiThue> getAllNguoiThue(){
        ArrayList<NguoiThue> arrarrngthue = new ArrayList<>();
        fb.collection(NguoiThue.TB_NGUOITHUE)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        arrarrngthue.clear();
                        for (QueryDocumentSnapshot document : value) {
                            NguoiThue objNguoiThue = document.toObject(NguoiThue.class);
                            arrarrngthue.add(objNguoiThue);

                        }
                    }
                });
        return arrarrngthue;
    }
    public HoaDon checkIDHD(HoaDon z) {
        arrHD = getHoaDon();
        for (HoaDon xyz : arr) {
            if (z.getIDHoaDon().equalsIgnoreCase(xyz.getIDHoaDon())) {
                return xyz;
            }
        }
        return null;
    }

    public ArrayList<String> getTenPhong(){
        ArrayList<String> arrTenPhong = new ArrayList<>();
        fb.collection(Phong.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arrTenPhong.clear();
                for(QueryDocumentSnapshot document : value){
                    Phong objPhong = document.toObject(Phong.class);
                    if(objPhong.getTrangThai().equalsIgnoreCase("Đang Thuê")) {
                        arrTenPhong.add(objPhong.getIDPhong());
                    }
                }
            }
        });
        return arrTenPhong;
    }

    public ArrayList<Phong> getAllPhong(){
        ArrayList<Phong> arrPhong = new ArrayList<>();
        fb.collection(Phong.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arrPhong.clear();
                for(QueryDocumentSnapshot document : value){
                    Phong objPhong = document.toObject(Phong.class);
                    arrPhong.add(objPhong);
                }
            }
        });
        return arrPhong;
    }

    public ArrayList<HopDong> getAllHopDong(){
        ArrayList<HopDong> arrHopDong = new ArrayList<>();
        fb.collection(HopDong.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arrHopDong.clear();
                for(QueryDocumentSnapshot document : value){
                    HopDong objHopDong = document.toObject(HopDong.class);
                    arrHopDong.add(objHopDong);
                }
            }
        });
        return arrHopDong;
    }

    public ArrayList<DichVu> getAllDichVu(){
        ArrayList<DichVu> arrDichVu = new ArrayList<>();
        fb.collection(DichVu.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arrDichVu.clear();
                for(QueryDocumentSnapshot document : value){
                    DichVu objDichVu = document.toObject(DichVu.class);
                    arrDichVu.add(objDichVu);
                }
            }
        });
        return arrDichVu;
    }

    public ArrayList<HoaDon> getAllHoaDon(){
        ArrayList<HoaDon> arr = new ArrayList<>();
        fb.collection(HoaDon.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for(QueryDocumentSnapshot document : value){
                    HoaDon objHoaDon = document.toObject(HoaDon.class);
                    if(objHoaDon.getTrangThaiHD() == 0) {
                        arr.add(objHoaDon);
                        Log.d("hdctt", "onEvent: "+arr);
                        if(user.equalsIgnoreCase("Admin"))
                        {
                            adapterhd.notifyDataSetChanged();
                        }
                        else {
                            adapternt.notifyDataSetChanged();
                        }
                    }

                }
            }
        });
        return arr;
    }

    public ArrayList<HoaDon> getHoaDon(){
        ArrayList<HoaDon> arrHD = new ArrayList<>();
        fb.collection(HoaDon.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arrHD.clear();
                for(QueryDocumentSnapshot document : value){
                    HoaDon objHoaDon = document.toObject(HoaDon.class);
                    arrHD.add(objHoaDon);
                    adapterhd.notifyDataSetChanged();
                }
                adapterhd.notifyDataSetChanged();

            }
        });
        return arrHD;
    }

//    public ArrayList<HoaDonChiTiet> getAllHoaDonCT(){
//        ArrayList<HoaDonChiTiet> arrHDCT = new ArrayList<>();
//        fb.collection(HoaDonChiTiet.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                arrHDCT.clear();
//                for(QueryDocumentSnapshot document : value){
//                    HoaDonChiTiet objHoaDonCT = document.toObject(HoaDonChiTiet.class);
//                    arrHDCT.add(objHoaDonCT);
//
//                }
//            }
//        });
//        return arrHDCT;
//    }


}
