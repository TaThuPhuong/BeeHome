package net.fpl.beehome;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import net.fpl.beehome.Adapter.ViewPageAdapter;

import me.relex.circleindicator.CircleIndicator;

public class Screen_Activity extends AppCompatActivity {
    ViewPageAdapter adapter;
    TextView skip;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ImageView next;
    RelativeLayout rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        skip = findViewById(R.id.tv_skip);
        viewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.circle_Indicator);
        next = findViewById(R.id.iv_next);
        rel = findViewById(R.id.rel);

        adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //gan view pager vap circle
        circleIndicator.setViewPager(viewPager);
        //skip den trang cuoi cung
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);//chay den trang cuoi cung cua view pager
            }
        });
        //khi bam next
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1); //neu khong phai la trang cuoi thi sang 1 trang
                }
            }
        });
        //lang nghe viewpager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    skip.setVisibility(View.GONE);
                    rel.setVisibility(View.GONE);
                } else {
                    skip.setVisibility(View.VISIBLE);
                    rel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}