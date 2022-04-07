package net.fpl.beehome.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fpl.beehome.Adapter.SlideAdapter;
import net.fpl.beehome.ContactActivity;
import net.fpl.beehome.DichVuActivity;
import net.fpl.beehome.HopDongActivity;
import net.fpl.beehome.MainActivity;
import net.fpl.beehome.NguoiThue_Activity;
import net.fpl.beehome.R;
import net.fpl.beehome.SuCoActivity;
import net.fpl.beehome.detail.hoaDon.HoaDonMain;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.SlideItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    CardView btnDichVu, btnNguoiThue, btnHoaDon, btnSuCo, btnHopDong, btnHuongDan;
    FloatingActionButton button;
    MainActivity mainActivity;
    TextView tv_hl_nd;
    NguoiThue nguoiThue;

    ViewPager2 viewPager2;
    Handler handler = new Handler();


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
        button = view.findViewById(R.id.floating_action_button);
        viewPager2 = view.findViewById(R.id.vpg);
        tv_hl_nd = view.findViewById(R.id.tv_hl_nd);
        mainActivity = (MainActivity) getActivity();

        tv_hl_nd.setText(mainActivity.getAdmin().getHoTen());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogMessage();
            }
        });

        btnDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DichVuActivity.class);
                intent.putExtra("quyen", "admin");
                startActivity(intent);
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
                Intent intent = new Intent(getActivity(), HoaDonMain.class);
                intent.putExtra("quyen", "admin");
                intent.putExtra("ad", mainActivity.getAdmin());
                startActivity(intent);
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

        btnSuCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuCoActivity.class);
                intent.putExtra("quyen", "admin");
                startActivity(intent);
            }
        });



        List<SlideItem> arr = new ArrayList<>();
        arr.add(new SlideItem(R.drawable.p1));
        arr.add(new SlideItem(R.drawable.p2));
        arr.add(new SlideItem(R.drawable.p3));

        viewPager2.setAdapter(new SlideAdapter(arr, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1- Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(slideRunnable);
                handler.postDelayed(slideRunnable, 3000);
            }
        });
    }

    public void showDialogMessage(){

        startActivity(new Intent(HomeFragment.this.getContext(), ContactActivity.class));

    }

    public Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause(){
        super.onPause();
        handler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(slideRunnable, 2000);
    }
    public NguoiThue getNguoiThue() {
        return nguoiThue;
    }
}