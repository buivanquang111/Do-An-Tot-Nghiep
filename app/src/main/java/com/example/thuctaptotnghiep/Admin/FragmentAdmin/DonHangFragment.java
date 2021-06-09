package com.example.thuctaptotnghiep.Admin.FragmentAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thuctaptotnghiep.Admin.AdapterAdmin.DonHangAdapter;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.DonHang;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.SanPham;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DonHangFragment extends Fragment {

    List<DonHang> donHangList;
    DonHang donHang;
    DonHangAdapter mAdapter;
    RecyclerView rvDanhSach;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_quanly_donhang,container,false);

        rvDanhSach = view.findViewById(R.id.rvDSHoaDon);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvDanhSach.setLayoutManager(linearLayoutManager);
        donHangList = new ArrayList<>();
        getHoaDon();
        mAdapter = new DonHangAdapter(donHangList,getContext());
        rvDanhSach.setAdapter(mAdapter);


        return view;
    }

    private void getHoaDon() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Server.url_getHoaDonAdmin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String img = "";
                String tenSp = "";
                int slMua = 0;
                String hoTen = "";
                String sdt = "";
                String diaChiNhan = "";
                double tongTien = 0;
                String ngayDat = "";
                int xacNhan = 0;
                int nvGiaoHang = 0;
                int daNhan = 0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        id = object.getInt("idhoadon");
                        img = object.getString("image");
                        tenSp = object.getString("title");
                        slMua = object.getInt("slmua");
                        hoTen = object.getString("hoten");
                        sdt = object.getString("sdt");
                        diaChiNhan = object.getString("diachinhan");
                        tongTien = object.getDouble("tongtien");
                        ngayDat = object.getString("ngaydat");
                        xacNhan = object.getInt("xacnhan");
                        nvGiaoHang = object.getInt("nhanviengiaohang");
                        daNhan = object.getInt("danhan");

                        donHang = new DonHang(id,img,tenSp,slMua,hoTen,sdt,diaChiNhan,tongTien,ngayDat,xacNhan,nvGiaoHang,daNhan);
                        donHangList.add(donHang);

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

}
