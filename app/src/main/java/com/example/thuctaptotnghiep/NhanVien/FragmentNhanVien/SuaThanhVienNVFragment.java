package com.example.thuctaptotnghiep.NhanVien.FragmentNhanVien;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.thuctaptotnghiep.Admin.AdminActivity;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.SuaThanhVienFragment;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.ThanhVienFragment;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.NhanVien.NhanVienActivity;
import com.example.thuctaptotnghiep.NhanVien.ObjectNhanVien.ThanhVienNV;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuaThanhVienNVFragment extends Fragment {
    public static final String TAG = SuaThanhVienNVFragment.class.getName();
    CircleImageView anhDaiDien;
    ImageView img_back;
    EditText edHoTen,edSDT,edEmail,edDiaChi,edMatKhau;
    Button btnCapNhat;
    ThanhVienNV thanhVien;
    NhanVienActivity mNhanVienActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nhanvien_fragment_sua_thanhvien,container,false);

        mNhanVienActivity= (NhanVienActivity) getActivity();
        img_back = view.findViewById(R.id.img_back_sua_thanhvien_nhanvien);
        anhDaiDien = view.findViewById(R.id.profile_image_thanhvien_nhanvien);
        edHoTen = view.findViewById(R.id.ed_hoten_thanhvien_nhanvien);
        edSDT = view.findViewById(R.id.ed_sdt_thanhvien_nhanvien);
        edEmail = view.findViewById(R.id.ed_email_thanhvien_nhanvien);
        edDiaChi = view.findViewById(R.id.ed_diachinhan_thanhvien_nhanvien);
        edMatKhau = view.findViewById(R.id.ed_matkhau_thanhvien_nhanvien);
        btnCapNhat = view.findViewById(R.id.btn_thanhvien_nhanvien);

        Bundle bundle = getArguments();
        thanhVien = (ThanhVienNV) bundle.get("thanhviensuanv");
        Glide.with(getContext()).load(thanhVien.getPhoto()).into(anhDaiDien);
        edHoTen.setText(thanhVien.getTen());
        edSDT.setText(thanhVien.getSdt());
        edEmail.setText(thanhVien.getEmail());
        edDiaChi.setText(thanhVien.getDiachi());
        edMatKhau.setText(thanhVien.getMatkhau());

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(getContext());
                progressDialog.setTitle("Please wait...");
                progressDialog.show();

                String strEmail = edEmail.getText().toString();
                String strHoTen = edHoTen.getText().toString();
                String strDiaChi = edDiaChi.getText().toString();
                String strMatKhau = edMatKhau.getText().toString();
                String strSDT = edSDT.getText().toString();

                StringRequest request=new StringRequest(Request.Method.POST, Server.url_updateThanhVienAdmin, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.admin_frame_layout,new ThanhVienFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String,String>();
                        params.put("iduser", String.valueOf(thanhVien.getId()));
                        params.put("name",strHoTen);
                        params.put("sdt",strSDT);
                        params.put("email",strEmail);
                        params.put("diachi",strDiaChi);
                        params.put("matkhau",strMatKhau);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getContext());
                requestQueue.add(request);
            }
        });

        return view;
    }
}
