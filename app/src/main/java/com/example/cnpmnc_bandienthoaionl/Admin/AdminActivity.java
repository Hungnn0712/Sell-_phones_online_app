package com.example.cnpmnc_bandienthoaionl.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cnpmnc_bandienthoaionl.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ChipNavigationBar chipNavigationBar = findViewById(R.id.nav_admin);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment =null;
                switch (id){
                    case R.id.mnNhanVien:
                        fragment = new NhanVienFragment();
                        break;
                    case R.id.mnKhachHang:
                        fragment = new KhachHangFragment();
                        break;
                }
                LoadFragment(fragment);
            }
        });
        chipNavigationBar.setItemSelected(R.id.mnNhanVien,true);
    }
    private void LoadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frm_admin,fragment);
        transaction.commit();
    }
}