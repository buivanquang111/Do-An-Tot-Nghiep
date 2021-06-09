package com.example.thuctaptotnghiep.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.thuctaptotnghiep.Admin.FragmentAdmin.DonHangFragment;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.SanPhamFragment;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.SuaSanPhamFragment;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.SuaThanhVienFragment;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.ThanhVienFragment;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.SanPham;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.Fragment.DetailTinTucFragment;
import com.example.thuctaptotnghiep.LoginActivity;
import com.example.thuctaptotnghiep.MainActivity;
import com.example.thuctaptotnghiep.Object.TinTuc;
import com.example.thuctaptotnghiep.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class AdminActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    TextView txtdangxuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        chipNavigationBar = findViewById(R.id.admin_bottom_nv);
        txtdangxuat = findViewById(R.id.txtDangXuat_Admin);
        txtdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.user=null;
                Intent intent=new Intent(AdminActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        loadFragmentAdmin(new ThanhVienFragment());

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.admin_action_thanhvien:
                        loadFragmentAdmin(new ThanhVienFragment());
                        break;
                    case R.id.admin_action_sanpham:
                        loadFragmentAdmin(new SanPhamFragment());
                        break;
                    case R.id.admin_action_donhang:
                        loadFragmentAdmin(new DonHangFragment());
                        break;
                }
            }
        });


    }

    private void loadFragmentAdmin(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.admin_frame_layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void goToSuaThanhVienAdminFragment(ThanhVien thanhVien){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        SuaThanhVienFragment suaThanhVienFragment = new SuaThanhVienFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("thanhviensua",thanhVien);
        suaThanhVienFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.admin_frame_layout,suaThanhVienFragment);
        fragmentTransaction.addToBackStack(SuaThanhVienFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToSuaSanPhamAdminFragment(SanPham sanPham){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        SuaSanPhamFragment suaSanPhamFragment = new SuaSanPhamFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("sanphamsua",sanPham);
        suaSanPhamFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.admin_frame_layout,suaSanPhamFragment);
        fragmentTransaction.addToBackStack(SuaThanhVienFragment.TAG);
        fragmentTransaction.commit();
    }


}