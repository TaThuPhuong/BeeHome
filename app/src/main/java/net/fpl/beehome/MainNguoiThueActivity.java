package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import net.fpl.beehome.model.Admin;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.ui.doiMatKhau.DoiMatKhauFragment;
import net.fpl.beehome.ui.gioiThieu.GioiThieuFragment;
import net.fpl.beehome.ui.home.HomeNguoiThueFragment;

public class MainNguoiThueActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBar ab;
    View view;
    NavigationView nv;
    TextView tvName;
    NguoiThue nguoiThue;
    Bundle bundle;
    String user;
    BottomNavigationView bnavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nguoi_thue);

        drawer = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar_hoa_don);
        bnavigation = findViewById(R.id.bottomnav);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        bundle = new Bundle();
        Log.d("TAG", "onCreate: " + user);
        //        set toolbar thay the cho actionBar
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        setTitle("Trang Chủ");

        FragmentManager manager = getSupportFragmentManager();
        HomeNguoiThueFragment h_fragment = new HomeNguoiThueFragment();
        manager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, h_fragment)
                .commit();
        view = nv.inflateHeaderView(R.layout.nav_header_main);
        tvName = view.findViewById(R.id.tv_name);

        nguoiThue = (NguoiThue) intent.getSerializableExtra("nt");
        tvName.setText(nguoiThue.getHoTen());
        bundle.putString(Admin.TB_NAME, "tb_admin");
        bundle.putString(Admin.COL_PASS, nguoiThue.getPassword());
        loadFragment(new HomeNguoiThueFragment());
        bnavigation.setVisibility(View.GONE);

//                Dieu huong Navigation
        nv.setNavigationItemSelectedListener((item) -> {

            switch (item.getItemId()) {
                case R.id.nav_trang_chu:
                    setTitle("Xin chào");
                    HomeNguoiThueFragment homeFragment = new HomeNguoiThueFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main, homeFragment).commit();
                    break;


                case R.id.nav_doiMatKhau:
                    setTitle("Đổi mật khẩu");
                    DoiMatKhauFragment matKhauFragment = new DoiMatKhauFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main, matKhauFragment).commit();
                    matKhauFragment.setArguments(bundle);
                    break;

                case R.id.nav_gT:
                    setTitle("Giới thiệu");
                    GioiThieuFragment gioiThieuFragment = new GioiThieuFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main, gioiThieuFragment).commit();
                    break;

                case R.id.sub_Logout:
                    startActivity(new Intent(MainNguoiThueActivity.this, Login_Activity.class));
                    finish();
                    break;
            }
            drawer.closeDrawers();
            return true;
        });


    }

    //nhấn vào icon menu thì mở ra
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.) {
//            drawer.openDrawer(GravityCompat.START);
//        }
        switch (item.getItemId()) {
            case R.id.menu_user:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_thong_bao:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    public NguoiThue getNguoiThue() {
        return nguoiThue;
    }


}