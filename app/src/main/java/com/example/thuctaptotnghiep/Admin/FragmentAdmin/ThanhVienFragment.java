package com.example.thuctaptotnghiep.Admin.FragmentAdmin;

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
import com.example.thuctaptotnghiep.Adapter.TinTucAdapter;
import com.example.thuctaptotnghiep.Admin.AdapterAdmin.ThanhVienAdapter;
import com.example.thuctaptotnghiep.Admin.AdminActivity;
import com.example.thuctaptotnghiep.Admin.Interface.IClickItemSuaThanhVienAdmin;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.Admin.ThemThanhVienActivity;
import com.example.thuctaptotnghiep.Object.Product;
import com.example.thuctaptotnghiep.Object.ThuongHieu;
import com.example.thuctaptotnghiep.Object.TinTuc;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;
import com.example.thuctaptotnghiep.inteface.IClickTinTucListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ThanhVienFragment extends Fragment {

    ImageView imgTimKiem;
    EditText edTimKiem;
    TextView txtTongThanhVien;
    FloatingActionButton btnThem;
    RecyclerView rvDSThanhVien;

    List<ThanhVien> thanhVienList;
    List<ThanhVien> searchList;
    ThanhVien thanhVien;
    ThanhVienAdapter mAdapter;
    String ten;
    int slThanhVien;

    AdminActivity mAdminActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_quanly_thanhvien,container,false);

        mAdminActivity = (AdminActivity) getActivity();
        imgTimKiem = view.findViewById(R.id.admin_image_timkiem);
        edTimKiem = view.findViewById(R.id.admin_ed_timkiem);
        txtTongThanhVien = view.findViewById(R.id.admin_txt_tongthanhvien);
        btnThem = view.findViewById(R.id.floating_action_button);
        rvDSThanhVien = view.findViewById(R.id.admin_rvdanhsach_thanhvien);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvDSThanhVien.setLayoutManager(linearLayoutManager);
        thanhVienList=new ArrayList<>();
        getThanhVien();
        mAdapter = new ThanhVienAdapter(thanhVienList, getContext(), new IClickItemSuaThanhVienAdmin() {
            @Override
            public void onClickSua(ThanhVien thanhVien) {
                mAdminActivity.goToSuaThanhVienAdminFragment(thanhVien);
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
                mAdapter = new ThanhVienAdapter(searchList, getContext(), new IClickItemSuaThanhVienAdmin() {
                    @Override
                    public void onClickSua(ThanhVien thanhVien) {
                        mAdminActivity.goToSuaThanhVienAdminFragment(thanhVien);
                    }
                });
                rvDSThanhVien.setAdapter(mAdapter);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mAdminActivity, ThemThanhVienActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void getThanhVien() {
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

                        if (!ten.toLowerCase().equals("admin") && daxoa == 0 ){
                            thanhVien = new ThanhVien(id,ten,sdt,photo,email,diachi,matkhau,daxoa);
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

                        if (!ten.toLowerCase().equals("admin") && daxoa == 0){
                            thanhVien = new ThanhVien(id,ten,sdt,photo,email,diachi,matkhau,daxoa);
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
