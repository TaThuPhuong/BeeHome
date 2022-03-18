package net.fpl.beehome;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import net.fpl.beehome.ui.Phong.PhongFragment;
import net.fpl.beehome.ui.home.HomeFragment;
import net.fpl.beehome.ui.thuChi.thuChiFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        //set toolbar thay thế actionBar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled(true);

        //để frg Home làm main
        FragmentManager manager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,homeFragment).commit();

        NavigationView nv = findViewById(R.id.nav_view);

        //điều hướng Navigation
        nv.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_home:
                    HomeFragment hFragment = new HomeFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,hFragment).commit();
                    break;
                case R.id.nav_phòng:
                    PhongFragment phongFragment = new PhongFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,phongFragment).commit();
                    break;
                case R.id.nav_thuChi:
                    thuChiFragment chiFragment = new thuChiFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,chiFragment).commit();
                    break;

            }
            drawer.closeDrawers();
            return true;
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
}