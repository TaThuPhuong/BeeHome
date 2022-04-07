package net.fpl.beehome.detail.hoaDon.Tab;

import static android.content.Context.MODE_PRIVATE;
import static net.fpl.beehome.MySharedPreferences.NgDung;
import static net.fpl.beehome.MySharedPreferences.USER_KEY;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.hoaDon.HoaDonAdapter;
import net.fpl.beehome.Adapter.hoaDon.HoaDonNguoiThueAdapter;
import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;
import net.fpl.beehome.model.HoaDon;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class HoaDonDaThanhToan extends Fragment {
    FirebaseFirestore fb;
    RecyclerView recyclerView;
    ArrayList<HoaDon> arr;
    ArrayList<HoaDon> arrHD;
    ArrayList<HopDong> arrHopDong;
    ArrayList<NguoiThue> arrNguoiThue;
    ArrayList<Phong> arrPhong ;
    ArrayList<String> arrTenPhong ;
    ArrayList<DichVu> arrDichVu;
    HoaDonAdapter adapterhd;
    HoaDonNguoiThueAdapter adapternt;
    String idP;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hoa_don_da_thanh_toan, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences pref = getActivity().getSharedPreferences("MSP_EMAIL_PASSWORD",MODE_PRIVATE);
        String user = pref.getString(NgDung,"");
        String hoTen = pref.getString(USER_KEY,"");

        fb = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView_hdctt);
        arr = getAllHoaDon();
        arrHD = getHoaDon();
        arrHopDong = getAllHopDong();
        arrDichVu = getAllDichVu();
        arrPhong = getAllPhong();
        arrTenPhong = getTenPhong();
        arrNguoiThue = getAllNguoiThue();

        if(user.equalsIgnoreCase("Admin")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapterhd = new HoaDonAdapter(arr,getContext(),fb,arrTenPhong,arrPhong,arrHopDong,arrDichVu);
                    adapterhd.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterhd);

                }
            },100);


        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int z =0; z<arrNguoiThue.size();z++){
                        if(arrNguoiThue.get(z).getEmail().equalsIgnoreCase(hoTen)){
                            idP = arrNguoiThue.get(z).getId_phong();
                        }
                    }

                    arr = getHoaDonPhong(idP);
                    adapternt = new HoaDonNguoiThueAdapter(arr,getContext(),fb,arrTenPhong,arrPhong,arrHopDong,arrDichVu,arrNguoiThue);
                    adapternt.notifyDataSetChanged();
                    recyclerView.setAdapter(adapternt);

                }
            },100);
        }


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

    public ArrayList<HoaDon> getHoaDonPhong(String idphong){
        ArrayList<HoaDon> arr = new ArrayList<>();

        fb.collection(HoaDon.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    HoaDon xHoaDon = document.toObject(HoaDon.class);
                    if(xHoaDon.getIDPhong().equalsIgnoreCase(idphong)){
                        if(xHoaDon.getTrangThaiHD() == 1) {
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
                    if(objHoaDon.getTrangThaiHD() == 1) {
                        arr.add(objHoaDon);
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

                }
            }
        });
        return arrHD;
    }
}