package com.example.thuctaptotnghiep.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.SanPham;
import com.example.thuctaptotnghiep.Object.ThuongHieu;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemSanPhamActivity extends AppCompatActivity {

    ImageView imgBack;
    CircleImageView imgSanPham;
    EditText edTenSP,edGia,edSoLuong,edMoTa;
    Spinner spThuongHieu,spLoai;
    Button btnThem;
    List<String> listThuongHieu;
    List<String> listLoai;
    Bitmap bitmap;
    String id="";
    String strTenSP,strGia,strSoLuong,strMoTa,strLoai,strThuongHieu;
    SanPham sp;
    List<SanPham> sanPhamList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        imgBack = findViewById(R.id.img_back_themSanPham_admin);
        imgSanPham = findViewById(R.id.image_them_sanpham_admin);
        edTenSP = findViewById(R.id.ed_tensp_themsanpham_admin);
        edGia = findViewById(R.id.ed_gia_themsanpham_admin);
        edSoLuong = findViewById(R.id.ed_soluong_themsanpham_admin);
        edMoTa = findViewById(R.id.ed_mota_themsanpham_admin);
        spThuongHieu = findViewById(R.id.spiner_thuonghieu_themsanpham_admin);
        spLoai = findViewById(R.id.spiner_loai_themsanpham_admin);
        btnThem = findViewById(R.id.btn_them_themsanpham_admin);

        sanPhamList = new ArrayList<>();
        listLoai = new ArrayList<>();
        listThuongHieu = new ArrayList<>();
        getThuongHieu();
        getProduct();
        spThuongHieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listLoai.add("đồng hồ nam");
        listLoai.add("đồng hồ nữ");
        listLoai.add("đồng hồ cặp đôi");
        ArrayAdapter spinnerAdapterLoai = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listLoai);
        spLoai.setAdapter(spinnerAdapterLoai);
        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemSanPhamActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });

        imgSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonFileAnh();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<sanPhamList.size();i++){
                    if (sanPhamList.get(i).getTitle().equals(edTenSP.getText().toString())){
                        Toast.makeText(ThemSanPhamActivity.this, "Tên đăng nhập này đã tồn tại!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                insertSanPham(getStringImage(bitmap));
            }
        });

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
                    ArrayAdapter spinnerAdapterTH = new ArrayAdapter(ThemSanPhamActivity.this,R.layout.support_simple_spinner_dropdown_item,listThuongHieu);
                    spThuongHieu.setAdapter(spinnerAdapterTH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(ThemSanPhamActivity.this).add(stringRequest);
    }
    private void getProduct() {
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
                            sp = new SanPham(id,title,price,image,description,soluong,thuonghieu,loai,daxoa);
                            sanPhamList.add(sp);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(ThemSanPhamActivity.this).add(stringRequest);
    }

    private void insertSanPham(String photo){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        strTenSP = edTenSP.getText().toString();
        strGia = edGia.getText().toString();
        strSoLuong = edSoLuong.getText().toString();
        strMoTa = edMoTa.getText().toString();
        strThuongHieu = spThuongHieu.getSelectedItem().toString();
        strLoai = spLoai.getSelectedItem().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.url_getIdThuongHieu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++ ){
                        JSONObject object=jsonArray.getJSONObject(i);
                        id = object.getString("id");
                    }
                    StringRequest request=new StringRequest(Request.Method.POST, Server.url_themSanPhamAdmin, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Toast.makeText(ThemSanPhamActivity.this, response, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ThemSanPhamActivity.this,AdminActivity.class);
                            startActivity(intent);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(ThemSanPhamActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            Log.d("bbb",error.getMessage().toString());
                        }
                    }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<String,String>();
                            params.put("title",strTenSP);
                            params.put("price",strGia);
                            params.put("description",strMoTa);
                            params.put("soluong",strSoLuong);
                            params.put("thuonghieu",id);
                            params.put("photo",photo);
                            params.put("loai",strLoai);
                            return params;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(ThemSanPhamActivity.this);
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
                Map<String,String> params=new HashMap<String,String>();
                params.put("ten",strThuongHieu);
                return params;
            }
        };
        RequestQueue requestQueue1= Volley.newRequestQueue(ThemSanPhamActivity.this);
        requestQueue1.add(stringRequest);
    }

    //chon anh dai dien
    public void chonFileAnh(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==5 && resultCode == RESULT_OK && data.getData()!=null){
            Uri filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imgSanPham.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageByteArray=byteArrayOutputStream.toByteArray();
        String encodeImage= Base64.encodeToString(imageByteArray,Base64.DEFAULT);
        return encodeImage;
    }

}