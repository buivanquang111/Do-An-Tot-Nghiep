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
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.Object.User;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.RegisterActivity;
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

public class ThemThanhVienActivity extends AppCompatActivity {

    ImageView imgBackAdmin;
    CircleImageView imgProfile;
    EditText edHoTen,edTenDangNhap,edSDT,edEmail,edDiaChi,edMatKhau;
    Spinner spChucVu;
    Button btnThem;
    List<String> chucVuList;
    ThanhVien thanhVien;
    List<ThanhVien> thanhVienList;
    String strHoTen,strTenDangNhap,strSDT,strEmail,strDiaChi,strMatKhau,strChucVu;
    String id = "";
    Bitmap bitmap;
    User user;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thanh_vien);

        imgBackAdmin = findViewById(R.id.img_back_themthanhvien_admin);
        imgProfile = findViewById(R.id.image_them_thanhvien_admin);
        edHoTen =findViewById(R.id.ed_name_themthanhvien_admin);
        edTenDangNhap = findViewById(R.id.ed_tendangnhap_themthanhvien_admin);
        edSDT = findViewById(R.id.ed_sdt_themthanhvien_admin);
        edEmail = findViewById(R.id.ed_email_themthanhvien_admin);
        edDiaChi = findViewById(R.id.ed_diachi_themthanhvien_admin);
        edMatKhau = findViewById(R.id.ed_pass_themthanhvien_admin);
        spChucVu = findViewById(R.id.spiner_chucvu_themthanhvien_admin);
        btnThem = findViewById(R.id.btn_Them_ThanhVien_admin);

        chucVuList = new ArrayList<>();
        chucVuList.add("khách hàng");
        chucVuList.add("nhân viên");

        userList = new ArrayList<>();
        getUser();
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,chucVuList);
        spChucVu.setAdapter(spinnerAdapter);
        spChucVu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonFileAnh();
            }
        });
        imgBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemThanhVienActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<userList.size();i++){
                    if (userList.get(i).getTendangnhap().equals(edTenDangNhap.getText().toString())){
                        Toast.makeText(ThemThanhVienActivity.this, "Tên đăng nhập này đã tồn tại!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                insertuser(getStringImage(bitmap));
            }
        });

    }
    private void getUser(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Server.url_getUser, new Response.Listener<String>() {
            int id=0;
            String name="";
            String email="";
            String pass="";
            String tendn="";
            String sdt="";
            int daxoa = 0;

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        id=object.getInt("id");
                        name=object.getString("name");
                        email=object.getString("email");
                        pass=object.getString("password");
                        tendn=object.getString("tendangnhap");
                        sdt=object.getString("sdt");
                        daxoa = object.getInt("daxoa");
                        if (!name.toLowerCase().equals("admin") && daxoa == 0 ){
                            user=new User(id,name,email,pass,tendn,sdt);
                            userList.add(user);
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
        RequestQueue requestQueue= Volley.newRequestQueue(ThemThanhVienActivity.this);
        requestQueue.add(stringRequest);
    }

    //chon anh dai dien
    public void chonFileAnh(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),3);
    }

    private void insertuser(String photo){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        strHoTen = edHoTen.getText().toString().trim();
        strTenDangNhap = edTenDangNhap.getText().toString().trim();
        strSDT = edSDT.getText().toString().trim();
        strEmail = edEmail.getText().toString().trim();
        strDiaChi = edDiaChi.getText().toString().trim();
        strMatKhau = edMatKhau.getText().toString().trim();
        strChucVu = spChucVu.getSelectedItem().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.url_getIDChucVu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++ ){
                        JSONObject object=jsonArray.getJSONObject(i);
                        id = object.getString("idchucvu");
                    }
                    StringRequest request=new StringRequest(Request.Method.POST, Server.url_themThanhVienAdmin, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Toast.makeText(ThemThanhVienActivity.this, response, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ThemThanhVienActivity.this,AdminActivity.class);
                            startActivity(intent);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(ThemThanhVienActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            Log.d("bbb",error.getMessage().toString());
                        }
                    }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<String,String>();
                            params.put("name",strHoTen);
                            params.put("email",strEmail);
                            params.put("password",strMatKhau);
                            params.put("tendn",strTenDangNhap);
                            params.put("sdt",strSDT);
                            params.put("photo",photo);
                            params.put("diachi",strDiaChi);
                            params.put("quyentruycap",id);
                            return params;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(ThemThanhVienActivity.this);
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
                params.put("tenchucvu",strChucVu);
                return params;
            }
        };
        RequestQueue requestQueue1= Volley.newRequestQueue(ThemThanhVienActivity.this);
        requestQueue1.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3 && resultCode == RESULT_OK && data.getData()!=null){
            Uri filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imgProfile.setImageBitmap(bitmap);
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