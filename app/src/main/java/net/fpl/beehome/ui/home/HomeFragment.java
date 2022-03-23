package net.fpl.beehome.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.fpl.beehome.HopDongActivity;
import net.fpl.beehome.NguoiThue_Activity;
import net.fpl.beehome.R;
import net.fpl.beehome.detail.hoaDon.HoaDonMain;

public class HomeFragment extends Fragment {
    ImageView btnDichVu, btnNguoiThue, btnHoaDon, btnSuCo, btnHopDong, btnHuongDan;
    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDichVu = view.findViewById(R.id.btn_dich_vu);
        btnNguoiThue = view.findViewById(R.id.btn_nguoi_thue);
        btnHoaDon = view.findViewById(R.id.btn_hoa_don);
        btnSuCo = view.findViewById(R.id.btn_su_co);
        btnHopDong = view.findViewById(R.id.brn_hop_dong);
        btnHuongDan = view.findViewById(R.id.btn_huong_dan);
        btnDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NguoiThue_Activity.class);
                startActivity(intent);
            }
        });


        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HoaDonMain.class));
            }
        });

        btnNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NguoiThue_Activity.class);
                startActivity(intent);
            }
        });

        btnHopDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HopDongActivity.class);
                startActivity(intent);
            }
        });


    }
}