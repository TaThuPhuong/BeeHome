package net.fpl.beehome.Adapter.hoaDon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import net.fpl.beehome.detail.hoaDon.Tab.HoaDonChuaThanhToan;
import net.fpl.beehome.detail.hoaDon.Tab.HoaDonDaThanhToan;
import net.fpl.beehome.detail.hoaDon.Tab.HoaDonQuaHanThanhToan;

public class HoaDonPagerAdapter extends FragmentStateAdapter {
    int soLuongTab = 3;

    public HoaDonPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return  new HoaDonChuaThanhToan();
            case 1:
                return new HoaDonDaThanhToan();
            case 2:
                return  new HoaDonQuaHanThanhToan();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return soLuongTab;
    }
}
