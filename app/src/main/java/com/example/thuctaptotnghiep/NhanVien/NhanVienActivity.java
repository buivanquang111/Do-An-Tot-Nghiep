package com.example.thuctaptotnghiep.NhanVien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.thuctaptotnghiep.Admin.AdminActivity;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.SuaSanPhamFragment;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.SuaThanhVienFragment;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.SanPham;
import com.example.thuctaptotnghiep.LoginActivity;
import com.example.thuctaptotnghiep.NhanVien.FragmentNhanVien.HoaDonNVFragment;
import com.example.thuctaptotnghiep.NhanVien.FragmentNhanVien.SanPhamNVFragment;
import com.example.thuctaptotnghiep.NhanVien.FragmentNhanVien.SuaSanPhamNVFragment;
import com.example.thuctaptotnghiep.NhanVien.FragmentNhanVien.SuaThanhVienNVFragment;
import com.example.thuctaptotnghiep.NhanVien.FragmentNhanVien.ThanhVienNVFragment;
import com.example.thuctaptotnghiep.NhanVien.ObjectNhanVien.SanPhamNV;
import com.example.thuctaptotnghiep.NhanVien.ObjectNhanVien.ThanhVienNV;
import com.example.thuctaptotnghiep.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class NhanVienActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    TextView txtdangxuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        loadFragmentNhanVien(new ThanhVienNVFragment());
        chipNavigationBar = findViewById(R.id.nhanvien_bottom_nv);
        txtdangxuat = findViewById(R.id.txtDangXuat_NhanVien);
        txtdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.user=null;
                Intent intent=new Intent(NhanVienActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.nhanvien_action_thanhvien:
                        loadFragmentNhanVien(new ThanhVienNVFragment());
                        break;
                    case R.id.nhanvien_action_sanpham:
                        loadFragmentNhanVien(new SanPhamNVFragment());
                        break;
                    case R.id.nhanvien_action_donhang:
                        loadFragmentNhanVien(new HoaDonNVFragment());
                        break;
                }
            }
        });

    }
    private void loadFragmentNhanVien(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nhanvien_frame_layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void goToSuaThanhVienNhanVienFragment(ThanhVienNV thanhVien){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        SuaThanhVienNVFragment suaThanhVienFragment = new SuaThanhVienNVFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("thanhviensuanv",thanhVien);
        suaThanhVienFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.nhanvien_frame_layout,suaThanhVienFragment);
        fragmentTransaction.addToBackStack(SuaThanhVienNVFragment.TAG);
        fragmentTransaction.commit();
    }
    public void goToSuaSanPhamAdminFragment(SanPhamNV sanPham){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        SuaSanPhamNVFragment suaSanPhamFragment = new SuaSanPhamNVFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("sanphamsuanv",sanPham);
        suaSanPhamFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.nhanvien_frame_layout,suaSanPhamFragment);
        fragmentTransaction.addToBackStack(SuaThanhVienNVFragment.TAG);
        fragmentTransaction.commit();
    }
}