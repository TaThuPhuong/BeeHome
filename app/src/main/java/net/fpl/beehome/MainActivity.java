package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import net.fpl.beehome.ui.doiMatKhau.DoiMatKhauFragment;
import net.fpl.beehome.ui.gioiThieu.GioiThieuFragment;
import net.fpl.beehome.ui.phong.PhongFragment;
import net.fpl.beehome.ui.home.HomeFragment;
import net.fpl.beehome.ui.thuChi.thuChiFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBar ab;
    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar_hoa_don);

        //        set toolbar thay the cho actionBar
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.user);
        ab.setDisplayHomeAsUpEnabled(true);
        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        setTitle("Xin chào " + mySharedPreferences.getUser(MySharedPreferences.USER_KEY));

        ////        dung fragment PhieuMuon lam home
        FragmentManager manager = getSupportFragmentManager();
        HomeFragment h_fragment = new HomeFragment();
        manager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, h_fragment)
                .commit();

        NavigationView nv = findViewById(R.id.nav_view);

//                Dieu huong Navigation
        nv.setNavigationItemSelectedListener((item) -> {

            switch (item.getItemId()) {
                case R.id.nav_doiMatKhau:
                    setTitle("Đổi mật khẩu");
                    DoiMatKhauFragment matKhauFragment = new DoiMatKhauFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main, matKhauFragment).commit();
                    break;

                case R.id.nav_gT:
                    setTitle("Giới thiệu");
                    GioiThieuFragment gioiThieuFragment = new GioiThieuFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main, gioiThieuFragment).commit();
                    break;

                case R.id.sub_Logout:
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                    finish();
                    break;
            }
            drawer.closeDrawers();
            return true;
        });

//        bottom menu
        BottomNavigationView bnavigation = findViewById(R.id.bottomnav);
        bnavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new HomeFragment());


    }

    //nhấn vào icon menu thì mở ra
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    setTitle("Xin chào");
                    loadFragment(fragment);
                    ab.show();
                    return true;
                case R.id.nav_phòng:
                    setTitle("Phòng");
                    fragment = new PhongFragment();
                    loadFragment(fragment);
                    ab.hide();
                    return true;
                case R.id.nav_thuChi:
                    setTitle("Thu Chi");
                    fragment = new thuChiFragment();
                    loadFragment(fragment);
                    ab.show();
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}