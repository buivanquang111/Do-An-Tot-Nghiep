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
import com.example.thuctaptotnghiep.Admin.AdapterAdmin.SanPhamAdapter;
import com.example.thuctaptotnghiep.Admin.AdapterAdmin.ThanhVienAdapter;
import com.example.thuctaptotnghiep.Admin.AdminActivity;
import com.example.thuctaptotnghiep.Admin.Interface.IClickItemSuaSanPhamAdmin;
import com.example.thuctaptotnghiep.Admin.Interface.IClickItemSuaThanhVienAdmin;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.SanPham;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.Admin.ThemSanPhamActivity;
import com.example.thuctaptotnghiep.Admin.ThemThanhVienActivity;
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

public class SanPhamFragment extends Fragment {

    ImageView imgTimKiem;
    EditText edTimKiem;
    FloatingActionButton btnThem;
    RecyclerView rvDSSanPham;

    List<SanPham> sanPhamList;
    List<SanPham> searchList;
    SanPham sanPham;
    SanPhamAdapter mAdapter;
    String ten;
    int slThanhVien;

    AdminActivity mAdminActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_quanly_sanpham,container,false);

        mAdminActivity = (AdminActivity) getActivity();
        imgTimKiem = view.findViewById(R.id.admin_image_timkiem_sp);
        edTimKiem = view.findViewById(R.id.admin_ed_timkiem_sp);
        btnThem = view.findViewById(R.id.floating_action_button_sanpham);
        rvDSSanPham = view.findViewById(R.id.admin_rvdanhsach_sanpham);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvDSSanPham.setLayoutManager(linearLayoutManager);
        sanPhamList=new ArrayList<>();
        getSanPham();
        mAdapter = new SanPhamAdapter(sanPhamList, getContext(), new IClickItemSuaSanPhamAdmin() {
            @Override
            public void onClickSua(SanPham sp) {
                mAdminActivity.goToSuaSanPhamAdminFragment(sp);
            }
        });
        rvDSSanPham.setAdapter(mAdapter);

        imgTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ten = edTimKiem.getText().toString().trim();

                LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
                rvDSSanPham.setLayoutManager(linearLayoutManager1);
                searchList = new ArrayList<>();
                getSanPhamSearch(ten);
                mAdapter = new SanPhamAdapter(searchList, getContext(), new IClickItemSuaSanPhamAdmin() {
                    @Override
                    public void onClickSua(SanPham sp) {
                        mAdminActivity.goToSuaSanPhamAdminFragment(sp);
                    }
                });
                rvDSSanPham.setAdapter(mAdapter);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mAdminActivity, ThemSanPhamActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void getSanPham() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Server.url_getSanPhamAdmin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String title="";
                int price=0;
                String image="";
                String description="";
                int soluong=0;
                String thuonghieu="";
                String loai="";
                int daxoa=0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        id = object.getInt("id");
                        title = object.getString("title");
                        price = object.getInt("price");
                        image = object.getString("image");
                        description = object.getString("description");
                        soluong = object.getInt("soluong");
                        thuonghieu = object.getString("name");
                        loai = object.getString("loai");
                        daxoa = object.getInt("daxoaSP");

                        if (daxoa == 0 ){
                            sanPham = new SanPham(id,title,price,image,description,soluong,thuonghieu,loai,daxoa);
                            sanPhamList.add(sanPham);
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
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void getSanPhamSearch(String name) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getSearchSPAdmin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String title="";
                int price=0;
                String image="";
                String description="";
                int soluong=0;
                String thuonghieu="";
                String loai="";
                int daxoa=0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id = object.getInt("id");
                        title = object.getString("title");
                        price = object.getInt("price");
                        image = object.getString("image");
                        description = object.getString("description");
                        soluong = object.getInt("soluong");
                        thuonghieu = object.getString("name");
                        loai = object.getString("loai");
                        daxoa = object.getInt("daxoaSP");

                        if (daxoa == 0 ){
                            sanPham = new SanPham(id,title,price,image,description,soluong,thuonghieu,loai,daxoa);
                            searchList.add(sanPham);
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
