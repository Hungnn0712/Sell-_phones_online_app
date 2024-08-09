package com.example.cnpmnc_bandienthoaionl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.cnpmnc_bandienthoaionl.Fragment.DienThoaiFragment;
import com.example.cnpmnc_bandienthoaionl.Fragment.DonHangFragment;
import com.example.cnpmnc_bandienthoaionl.Fragment.GioHangFragment;
import com.example.cnpmnc_bandienthoaionl.Fragment.HomeFragment;
import com.example.cnpmnc_bandienthoaionl.Fragment.TaiKhoanFragment;
import com.example.cnpmnc_bandienthoaionl.QuanLy.QuanLyDienThoaiFragment;
import com.example.cnpmnc_bandienthoaionl.QuanLy.QuanLyDonHangFragment;
import com.example.cnpmnc_bandienthoaionl.QuanLy.QuanLyThongBaoFragment;
import com.example.cnpmnc_bandienthoaionl.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView tend,quyenadmin;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    int vitri=0;
    User user;
    BottomNavigationView bottomNavigationView;
    //private AppBarConfiguration mAppBarConfiguration;

    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String getemail = firebaseAuth.getInstance().getCurrentUser().getEmail();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(vitri);
        tend = (TextView) headerView.findViewById(R.id.header_ten);
        TextView quyend = (TextView) headerView.findViewById(R.id.header_email);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        quyenadmin = (TextView) headerView.findViewById(R.id.header_quyen);

        Menu nav_Menu = navigationView.getMenu();
        quyend.setText(""+getemail);
        nav_Menu.findItem(R.id.nav_qldienthoai).setVisible(false);
        nav_Menu.findItem(R.id.nav_qldonhang).setVisible(false);
//        nav_Menu.findItem(R.id.nav_qlnguoidung).setVisible(false);
        nav_Menu.findItem(R.id.nav_thongbao).setVisible(false);
        nav_Menu.findItem(R.id.nav_thongke).setVisible(false);

        Fragment home = new HomeFragment();
        Fragment giohang = new GioHangFragment();
        Fragment sp = new DienThoaiFragment();
        Fragment dh = new DonHangFragment();
        Fragment tk = new TaiKhoanFragment();

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User dangKy = dataSnapshot.getValue(User.class);
                    if (dangKy != null && dangKy.getTaiKhoan().contains(getemail)) {
                        String phanquyen = dataSnapshot.child("phanQuyen").getValue(String.class);
                        tend.setText("Xin chào : " + dangKy.getHoTen());
//                        quyenadmin.setText(dangKy.getPhanQuyen());
//                        String admin = phanquyen.getText().toString();
                        if (phanquyen.equalsIgnoreCase("NV")) {
                            navigationView = (NavigationView) findViewById(R.id.nav_view);
                            Menu nav_Menu = navigationView.getMenu()    ;
                            nav_Menu.findItem(R.id.nav_qldienthoai).setVisible(true);
                            nav_Menu.findItem(R.id.nav_qldonhang).setVisible(true);
//                            nav_Menu.findItem(R.id.nav_qlnguoidung).setVisible(true);
                            nav_Menu.findItem(R.id.nav_thongbao).setVisible(true);
                            nav_Menu.findItem(R.id.nav_thongke).setVisible(true);
                        }
                        if (!phanquyen.equalsIgnoreCase("NV")) {
                            navigationView = (NavigationView) findViewById(R.id.nav_view);
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_qldienthoai).setVisible(false);
                            nav_Menu.findItem(R.id.nav_qldonhang).setVisible(false);
//                            nav_Menu.findItem(R.id.nav_qlnguoidung).setVisible(false);
                            nav_Menu.findItem(R.id.nav_thongbao).setVisible(false);
                            nav_Menu.findItem(R.id.nav_thongke).setVisible(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ChipNavigationBar chipNavigationBar = findViewById(R.id.nav_main);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment =null;
                switch (id){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.sanpham:
                        chipNavigationBar.showBadge(R.id.sanpham);
                        fragment = new DienThoaiFragment();
                        break;
                    case R.id.giohang:
                        chipNavigationBar.showBadge(R.id.giohang);
                        fragment = new GioHangFragment();
                        break;
                    case R.id.lichsu:
                        chipNavigationBar.showBadge(R.id.lichsu);
                        fragment = new DonHangFragment();
                        break;
                    case R.id.toi:
                        chipNavigationBar.showBadge(R.id.toi);
                        fragment = new TaiKhoanFragment();
                        break;

                }
                LoadFragment(fragment);
            }
        });
        chipNavigationBar.setItemSelected(R.id.mnNhanVien,true);
    }
    private void LoadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
                break;
//            case R.id.nav_dienthoai:
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
            case R.id.nav_qldienthoai:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new QuanLyDienThoaiFragment()).commit();
                break;
            case R.id.nav_qldonhang:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new QuanLyDonHangFragment()).commit();
                break;
            case R.id.nav_thongbao:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new QuanLyThongBaoFragment()).commit();
                break;
            case R.id.nav_thongke:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new QuanLyThongBaoFragment()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public void getNguoiDung(String mail) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference phanquyenef = database.getReference("User");
        phanquyenef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phanquyen = snapshot.getValue(String.class);
                if (phanquyen.equalsIgnoreCase("NV")) {
                    navigationView = (NavigationView) findViewById(R.id.nav_view);
                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_qldienthoai).setVisible(true);
                    nav_Menu.findItem(R.id.nav_qldonhang).setVisible(true);
//                    nav_Menu.findItem(R.id.nav_qlnguoidung).setVisible(true);
                    nav_Menu.findItem(R.id.nav_thongbao).setVisible(true);
                    nav_Menu.findItem(R.id.nav_thongke).setVisible(true);
                }
                if (!phanquyen.equalsIgnoreCase("NV")) {
                    navigationView = (NavigationView) findViewById(R.id.nav_view);
                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_qldienthoai).setVisible(false);
                    nav_Menu.findItem(R.id.nav_qldonhang).setVisible(false);
//                    nav_Menu.findItem(R.id.nav_qlnguoidung).setVisible(false);
                    nav_Menu.findItem(R.id.nav_thongbao).setVisible(false);
                    nav_Menu.findItem(R.id.nav_thongke).setVisible(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void NavigationDrawerMenu (){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        replaceFragment(new HomeFragment());
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}