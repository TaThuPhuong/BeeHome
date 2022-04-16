package net.fpl.beehome;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.LienHe.ContactAdminAdapter;
import net.fpl.beehome.Adapter.LienHe.ContactUserAdapter;
import net.fpl.beehome.model.Admin;
import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;


public class ContactActivity extends AppCompatActivity {

    RecyclerView rcvContact;
    ArrayList<NguoiThue> listUser;
    ArrayList<Admin> listAdmin;
    ContactUserAdapter contactUserAdapter;
    ContactAdminAdapter contactAdminAdapter;
    SearchView searchView;
    Toolbar toolbar;
    String quyen;
    TextView tv;
    private Animation animationUp, animationDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        listUser = new ArrayList<>();
        listAdmin = new ArrayList<>();
        rcvContact = findViewById(R.id.rcv_contact);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down);
        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up);
        tv = findViewById(R.id.tv);
        toolbar = findViewById(R.id.toolbar_nguoithue);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        quyen = intent.getStringExtra("quyen");
        Admin admin = (Admin) intent.getSerializableExtra("admin");
        NguoiThue nguoiThue = (NguoiThue) intent.getSerializableExtra("user");

        if (quyen.equalsIgnoreCase("admin")) {
            FirebaseFirestore db1 = FirebaseFirestore.getInstance();
            db1.collection(NguoiThue.TB_NGUOITHUE).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    listUser.clear();
                    for (DocumentSnapshot snapshot : value) {
                        NguoiThue nguoiThue = snapshot.toObject(NguoiThue.class);
                        listUser.add(nguoiThue);
                        contactUserAdapter.notifyDataSetChanged();
                    }
                }
            });
            contactUserAdapter = new ContactUserAdapter(listUser, admin, this);
            rcvContact.setAdapter(contactUserAdapter);
        } else {
            toolbar.setTitle("Chat với admin");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(Admin.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    listAdmin.clear();
                    for (DocumentSnapshot snapshot : value) {
                        Admin admin = snapshot.toObject(Admin.class);
                        listAdmin.add(admin);
                        contactAdminAdapter.notifyDataSetChanged();
                    }
                }
            });
            contactAdminAdapter = new ContactAdminAdapter(listAdmin, this, nguoiThue);
            rcvContact.setAdapter(contactAdminAdapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Vui lòng cấp quyền gọi điện", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (quyen.equalsIgnoreCase("admin")) {
            getMenuInflater().inflate(R.menu.menu_search, menu);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    contactUserAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    contactUserAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return true;
    }
}