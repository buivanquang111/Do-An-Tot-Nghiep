package com.example.thuctaptotnghiep.NhanVien.FragmentNhanVien;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thuctaptotnghiep.Admin.AdapterAdmin.ThanhVienAdapter;
import com.example.thuctaptotnghiep.Admin.AdminActivity;
import com.example.thuctaptotnghiep.Admin.Interface.IClickItemSuaThanhVienAdmin;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.Admin.ThemThanhVienActivity;
import com.example.thuctaptotnghiep.NhanVien.AdapterNhanVien.ThanhVienNVAdapter;
import com.example.thuctaptotnghiep.NhanVien.InterfaceNhanVien.IClickItemSuaThanhVienNhanVien;
import com.example.thuctaptotnghiep.NhanVien.NhanVienActivity;
import com.example.thuctaptotnghiep.NhanVien.ObjectNhanVien.ThanhVienNV;
import com.example.thuctaptotnghiep.NhanVien.ThemThanhVienNVActivity;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThanhVienNVFragment extends Fragment {
    ImageView imgTimKiem;
    EditText edTimKiem;
    TextView txtTongThanhVien;
    FloatingActionButton btnThem;
    RecyclerView rvDSThanhVien;

    List<ThanhVienNV> thanhVienList;
    List<ThanhVienNV> searchList;
    ThanhVienNV thanhVien;
    ThanhVienNVAdapter mAdapter;
    String ten;
    int slThanhVien;

    NhanVienActivity mNhanVienActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nhanvien_fragment_quanly_thanhvien,container,false);

        mNhanVienActivity= (NhanVienActivity) getActivity();
        imgTimKiem = view.findViewById(R.id.nhanvien_image_timkiem);
        edTimKiem = view.findViewById(R.id.nhanvien_ed_timkiem);
        txtTongThanhVien = view.findViewById(R.id.nhanvien_txt_tongthanhvien);
        btnThem = view.findViewById(R.id.floating_action_button_nhanvien);
        rvDSThanhVien = view.findViewById(R.id.nhanvien_rvdanhsach_thanhvien);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvDSThanhVien.setLayoutManager(linearLayoutManager);
        thanhVienList=new ArrayList<>();
        getThanhVienNV();
        mAdapter = new ThanhVienNVAdapter(thanhVienList, getContext(), new IClickItemSuaThanhVienNhanVien() {
            @Override
            public void onClickSua(ThanhVienNV thanhVien) {
                mNhanVienActivity.goToSuaThanhVienNhanVienFragment(thanhVien);
            }
        });
        rvDSThanhVien.setAdapter(mAdapter);
        imgTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ten = edTimKiem.getText().toString().trim();

                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
                rvDSThanhVien.setLayoutManager(linearLayoutManager);
                searchList = new ArrayList<>();
                getThanhVienSearch(ten);
                mAdapter = new ThanhVienNVAdapter(searchList, getContext(), new IClickItemSuaThanhVienNhanVien() {
                    @Override
                    public void onClickSua(ThanhVienNV thanhVien) {
                        mNhanVienActivity.goToSuaThanhVienNhanVienFragment(thanhVien);
                    }
                });
                rvDSThanhVien.setAdapter(mAdapter);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mNhanVienActivity, ThemThanhVienNVActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void getThanhVienNV() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Server.url_getThanhVienAdmin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String ten = "";
                String sdt = "";
                String photo = "";
                String email = "";
                String diachi = "";
                String matkhau = "";
                int daxoa=0;
                int quyentruycap=0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        id = object.getInt("id");
                        ten = object.getString("name");
                        sdt = object.getString("sdt");
                        photo = object.getString("photo");
                        email = object.getString("email");
                        diachi = object.getString("diachi");
                        matkhau = object.getString("password");
                        daxoa = object.getInt("daxoa");
                        quyentruycap = object.getInt("quyentruycap");

                        if (!ten.toLowerCase().equals("admin") && daxoa == 0 && quyentruycap == 3){
                            thanhVien = new ThanhVienNV(id,ten,sdt,photo,email,diachi,matkhau,daxoa);
                            thanhVienList.add(thanhVien);
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                    slThanhVien = thanhVienList.size();
                    txtTongThanhVien.setText("Tổng số thành viên: "+slThanhVien);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void getThanhVienSearch(String name) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getSearchThanhVienAdmin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String ten = "";
                String sdt = "";
                String photo = "";
                String email = "";
                String diachi = "";
                String matkhau = "";
                int daxoa=0;
                int quyentruycap=0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id = object.getInt("id");
                        ten = object.getString("name");
                        sdt = object.getString("sdt");
                        photo = object.getString("photo");
                        email = object.getString("email");
                        diachi = object.getString("diachi");
                        matkhau = object.getString("password");
                        daxoa = object.getInt("daxoa");
                        quyentruycap = object.getInt("quyentruycap");

                        if (!ten.toLowerCase().equals("admin") && daxoa == 0 && quyentruycap == 2){
                            thanhVien = new ThanhVienNV(id,ten,sdt,photo,email,diachi,matkhau,daxoa);
                            searchList.add(thanhVien);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("name",name);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
