package com.example.thuctaptotnghiep.Admin.FragmentAdmin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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
import com.example.thuctaptotnghiep.Admin.AdminActivity;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.SanPham;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.Admin.ThemSanPhamActivity;
import com.example.thuctaptotnghiep.LoginActivity;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuaSanPhamFragment extends Fragment {
    public static final String TAG = SuaSanPhamFragment.class.getName();
    ImageView imgBack;
    EditText edTenSP,edGia,edMoTa,edSoLuong;
    Spinner spLoai,spthuonghieu;
    Button btnSua;
    List<String> listLoai;
    List<String> listThuongHieu;
    SanPham sanPham;
    String idthuonghieu = "";
    AdminActivity mAdminActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_sua_sanpham,container,false);


        mAdminActivity = (AdminActivity) getActivity();
        imgBack = view.findViewById(R.id.img_back_sua_sanpham_admin);
        edTenSP = view.findViewById(R.id.ed_title_sanpham_admin);
        edGia = view.findViewById(R.id.ed_gia_sanpham_admin);
        edMoTa = view.findViewById(R.id.ed_description_sanpham_admin);
        edSoLuong = view.findViewById(R.id.ed_soluong_sanpham_admin);
        spLoai = view.findViewById(R.id.spiner_loai_suasanpham_admin);
        spthuonghieu = view.findViewById(R.id.spiner_thuonghieu_suasanpham_admin);
        btnSua = view.findViewById(R.id.btn_suasanpham_admin);

        listThuongHieu = new ArrayList<>();
        listLoai = new ArrayList<>();

        listLoai.add("đồng hồ nam");
        listLoai.add("đồng hồ nữ");
        listLoai.add("đồng hồ cặp đôi");
        ArrayAdapter spinnerAdapterLoai = new ArrayAdapter(mAdminActivity,R.layout.support_simple_spinner_dropdown_item,listLoai);
        spLoai.setAdapter(spinnerAdapterLoai);
        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getThuongHieu();

        Bundle bundle = getArguments();
        sanPham = (SanPham) bundle.get("sanphamsua");
        edTenSP.setText(sanPham.getTitle());
        edGia.setText(sanPham.getPrice()+"");
        edMoTa.setText(sanPham.getDescription());
        edSoLuong.setText(sanPham.getSoluong()+"");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(getContext());
                progressDialog.setTitle("Please wait...");
                progressDialog.show();

                String strTenSP = edTenSP.getText().toString();
                String strGia = edGia.getText().toString();
                String strMoTa = edMoTa.getText().toString();
                String strSL = edSoLuong.getText().toString();
                String strLoai = spLoai.getSelectedItem().toString();
                String strthuonghieu = spthuonghieu.getSelectedItem().toString();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getIdThuongHieu, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                idthuonghieu = object.getString("idthuonghieu");
                            }
                            StringRequest request=new StringRequest(Request.Method.POST, Server.url_updateSanPhamAdmin, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("update successfully")){
                                        progressDialog.dismiss();
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                        transaction.replace(R.id.admin_frame_layout,new SanPhamFragment());
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }
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
                                    params.put("id", String.valueOf(sanPham.getId()));
                                    params.put("tensp",strTenSP);
                                    params.put("gia",strGia);
                                    params.put("mota",strMoTa);
                                    params.put("soluong",strSL);
                                    params.put("thuonghieu", idthuonghieu);
                                    params.put("loai",strLoai);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue= Volley.newRequestQueue(mAdminActivity);
                            requestQueue.add(request);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String,String>();
                        param.put("ten",strthuonghieu);
                        return param;
                    }
                };
                Volley.newRequestQueue(getContext()).add(stringRequest);



            }
        });

        return view;
    }

    private void getThuongHieu() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Server.url_getthuonghieu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = "";
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        name=object.getString("name");
                        listThuongHieu.add(name);
                    }
                    ArrayAdapter spinnerAdapterTH = new ArrayAdapter(mAdminActivity,R.layout.support_simple_spinner_dropdown_item,listThuongHieu);
                    spthuonghieu.setAdapter(spinnerAdapterTH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(mAdminActivity).add(stringRequest);
    }
}
