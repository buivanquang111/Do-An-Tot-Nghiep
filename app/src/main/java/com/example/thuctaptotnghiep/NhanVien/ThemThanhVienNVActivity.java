package com.example.thuctaptotnghiep.NhanVien;

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
import com.example.thuctaptotnghiep.Admin.AdminActivity;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.Admin.ThemThanhVienActivity;
import com.example.thuctaptotnghiep.NhanVien.ObjectNhanVien.ThanhVienNV;
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

public class ThemThanhVienNVActivity extends AppCompatActivity {

    ImageView imgBackAdmin;
    CircleImageView imgProfile;
    EditText edHoTen,edTenDangNhap,edSDT,edEmail,edDiaChi,edMatKhau;
    Button btnThem;
    List<String> chucVuList;
    ThanhVien thanhVien;
    List<ThanhVienNV> thanhVienList;
    String strHoTen,strTenDangNhap,strSDT,strEmail,strDiaChi,strMatKhau;
    String id = "";
    Bitmap bitmap;
    User user;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thanh_vien_n_v);

        imgBackAdmin = findViewById(R.id.img_back_themthanhvien_nhanvien);
        imgProfile = findViewById(R.id.image_them_thanhvien_nhanvien);
        edHoTen =findViewById(R.id.ed_name_themthanhvien_nhanvien);
        edTenDangNhap = findViewById(R.id.ed_tendangnhap_themthanhvien_nhanvien);
        edSDT = findViewById(R.id.ed_sdt_themthanhvien_nhanvien);
        edEmail = findViewById(R.id.ed_email_themthanhvien_nhanvien);
        edDiaChi = findViewById(R.id.ed_diachi_themthanhvien_nhanvien);
        edMatKhau = findViewById(R.id.ed_pass_themthanhvien_nhanvien);
        btnThem = findViewById(R.id.btn_Them_ThanhVien_nhanvien);

        userList = new ArrayList<>();
        getUser();
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonFileAnh();
            }
        });
        imgBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemThanhVienNVActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<userList.size();i++){
                    if (userList.get(i).getTendangnhap().equals(edTenDangNhap.getText().toString())){
                        Toast.makeText(ThemThanhVienNVActivity.this, "Tên nhân viên này đã tồn tại!!!", Toast.LENGTH_SHORT).show();
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
            int quyentruycap = 0;

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
                        quyentruycap = object.getInt("quyentruycap");
                        if (!name.toLowerCase().equals("admin") && daxoa == 0 && quyentruycap == 2){
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
        RequestQueue requestQueue= Volley.newRequestQueue(ThemThanhVienNVActivity.this);
        requestQueue.add(stringRequest);
    }
    //chon anh dai dien
    public void chonFileAnh(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode == RESULT_OK && data.getData()!=null){
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


        StringRequest request=new StringRequest(Request.Method.POST, Server.url_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Toast.makeText(ThemThanhVienNVActivity.this, response, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ThemThanhVienNVActivity.this,NhanVienActivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ThemThanhVienNVActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(ThemThanhVienNVActivity.this);
        requestQueue.add(request);
    }

}