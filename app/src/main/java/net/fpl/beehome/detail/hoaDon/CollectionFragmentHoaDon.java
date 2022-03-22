package net.fpl.beehome.detail.hoaDon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import net.fpl.beehome.Adapter.hoaDon.HoaDonPagerAdapter;
import net.fpl.beehome.R;

public class CollectionFragmentHoaDon extends Fragment {
    HoaDonPagerAdapter hoaDonPagerAdapter;
    TabLayout tabLayoutHoaDon;
    ViewPager2 viewPagerHoaDon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hoa_don_collection_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // tạo xong các view cho fragment này rồi thì mới bắt đầu làm việc với tab
        hoaDonPagerAdapter = new HoaDonPagerAdapter(this);
        viewPagerHoaDon = view.findViewById(R.id.page_hoa_don);
        viewPagerHoaDon.setAdapter(hoaDonPagerAdapter); // giống set adapter cho listview

        // làm việc với tab
        tabLayoutHoaDon = view.findViewById(R.id.tab_layout_hoa_don);

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayoutHoaDon, viewPagerHoaDon,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if(position == 0){
                            tab.setText("chưa thanh toán");
                        }

                        if(position == 1){
                            tab.setText("Đã thanh toán");
                        }

                        if(position == 2){
                            tab.setText("Quá hạn thanh toán");
                        }

                    }
                }
        );
        mediator.attach();



    }
}
