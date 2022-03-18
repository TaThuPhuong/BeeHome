package net.fpl.beehome;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import net.fpl.beehome.databinding.ActivityMainBinding;
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

        //        set toolbar thay the cho actionBar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled(true);

        ////        dung fragment PhieuMuon lam home
        FragmentManager manager = getSupportFragmentManager();
        HomeFragment h_fragment = new HomeFragment();
        manager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, h_fragment)
                .commit();

        NavigationView nv = findViewById(R.id.nav_view);

        //        Dieu huong Navigation
        nv.setNavigationItemSelectedListener((item) ->{

            switch (item.getItemId()){
                case R.id.nav_home:
                    HomeFragment homeFragment = new HomeFragment();
                    manager.beginTransaction().replace(R.id.nav_host_fragment_content_main,homeFragment).commit();
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