package com.example.thuctaptotnghiep.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.thuctaptotnghiep.LoginActivity;
import com.example.thuctaptotnghiep.MainActivity;
import com.example.thuctaptotnghiep.Object.HoaDon;
import com.example.thuctaptotnghiep.Object.Product;
import com.example.thuctaptotnghiep.Object.User;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MyPageFragment extends Fragment {

    MainActivity mMainActivity;
    ImageView image_setting;
    TextView textView,txt_so_like,txtNumberChoXacNhan,txtNumberChoLayHang,txtNumberDangGiao,txtNumberDaGiao;
    RelativeLayout linearLayout;
    User user;
    LinearLayout linearLayout_thongtincanhan,linearLayout_dangxuat,linearlayout_choxacnhan,linearlayout_danggiao,linearlayout_cholayhang,linearlayout_dagiao;
    RelativeLayout relativeLayout_yeuthich;
    CircleImageView circleImageView;
    Button btndangnhap;
    HoaDon hoaDon;
    List<HoaDon> hoaDonList;
    List<HoaDon> hoaDonDangGiaoList;
    List<HoaDon> hoaDonChoLayHangList;
    List<HoaDon> hoaDonDaGiaoList;


    public static MyPageFragment getInstance(String ten,String pass){
        MyPageFragment myPageFragment=new MyPageFragment();
        Bundle bundle=new Bundle();
        bundle.putString("tendangnhap",ten);
        bundle.putString("pass",pass);
        myPageFragment.setArguments(bundle);
        return  myPageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mypage,container,false);

        mMainActivity= (MainActivity) getActivity();
        mMainActivity.bottomNavigationView.setVisibility(View.VISIBLE);

        image_setting=view.findViewById(R.id.image_setting_mypager);
        textView=view.findViewById(R.id.txt_tenthanhvien_mypager);
        linearLayout=view.findViewById(R.id.linerlayout_mypager);
        linearLayout_thongtincanhan=view.findViewById(R.id.linerlayout_thongtincanhan);
        linearLayout_dangxuat=view.findViewById(R.id.linerlayout_dangxuat);
        relativeLayout_yeuthich=view.findViewById(R.id.relativelayout_yeuthich);
        linearlayout_choxacnhan = view.findViewById(R.id.linearlayout_choxacnhan);
        linearlayout_danggiao = view.findViewById(R.id.linearlayout_danggiao);
        linearlayout_cholayhang = view.findViewById(R.id.linearlayout_cholayhang);
        linearlayout_dagiao = view.findViewById(R.id.linearlayout_dagiao);

        txt_so_like=view.findViewById(R.id.txt_like_mypage);
        circleImageView=view.findViewById(R.id.profile_image);
        btndangnhap=view.findViewById(R.id.btndangnhap_mypage);
        txtNumberChoXacNhan=view.findViewById(R.id.txtNumberChoXacNhan);
        txtNumberChoLayHang=view.findViewById(R.id.txtNumberChoLayHang);
        txtNumberDangGiao=view.findViewById(R.id.txtNumberDangGiao);
        txtNumberDaGiao= view.findViewById(R.id.txtNumberDaGiao);

        if (LoginActivity.user==null){
            btndangnhap.setVisibility(View.VISIBLE);
            btndangnhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mMainActivity,LoginActivity.class);
                    startActivity(intent);
                }
            });
            linearLayout_thongtincanhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mMainActivity,LoginActivity.class);
                    startActivity(intent);
                }
            });
            relativeLayout_yeuthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mMainActivity,LoginActivity.class);
                    startActivity(intent);
                }
            });
            linearLayout_dangxuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mMainActivity, "bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
                }
            });
            linearlayout_choxacnhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mMainActivity,LoginActivity.class);
                    startActivity(intent);
                }
            });
            linearlayout_danggiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mMainActivity,LoginActivity.class);
                    startActivity(intent);
                }
            });
            linearlayout_danggiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mMainActivity,LoginActivity.class);
                    startActivity(intent);
                }
            });
            linearlayout_cholayhang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mMainActivity,LoginActivity.class);
                    startActivity(intent);
                }
            });
            linearlayout_dagiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mMainActivity,LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            btndangnhap.setVisibility(View.INVISIBLE);
        String ten= (String) getArguments().get("tendangnhap");
        String pas= (String) getArguments().get("pass");
        textView.setText(ten);

        getUser(ten,pas);

        hoaDonList = new ArrayList<>();
        getHoaDonChoXacNhan(String.valueOf(LoginActivity.user.getId()));

        hoaDonDangGiaoList = new ArrayList<>();
        getHoaDonDangGiao(String.valueOf(LoginActivity.user.getId()));

        hoaDonChoLayHangList = new ArrayList<>();
        getHoaDonChoLayHang(String.valueOf(LoginActivity.user.getId()));

        hoaDonDaGiaoList = new ArrayList<>();
        getHoaDonDaGiao(String.valueOf(LoginActivity.user.getId()));

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chuyenDuLieuSangDetailMyPageFragment(user);
            }
        });

        linearLayout_thongtincanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyenDuLieuSangDetailMyPageFragment(user);
            }
        });


        relativeLayout_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.goToYeuThichFragment(user);
            }
        });

        linearlayout_choxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.goToChoXacNhanFragment(user);
            }
        });
        linearlayout_danggiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.goToDangGiaoFragment(user);
            }
        });
        linearlayout_cholayhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.goToCHoLayHangFragment(user);
            }
        });
        linearlayout_dagiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.goToDaGiaoFragment(user);
            }
        });


        linearLayout_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.dangxuat();
            }
        });

//        circleImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMainActivity.chonFileAnh();
//            }
//        });
        getImageUser(ten,pas);
        }

        return view;

    }


    private void chuyenDuLieuSangDetailMyPageFragment(User user) {
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        DetailMyPageFragment detailMyPageFragment=new DetailMyPageFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("user_detaimypage",user);
        detailMyPageFragment.setArguments(bundle);

        transaction.replace(R.id.frame_layout,detailMyPageFragment);
        transaction.addToBackStack(detailMyPageFragment.TAG);
        transaction.commit();
    }

    private void getUser(String tendangnhap,String pass) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getdetailUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String name="";
                String email="";
                String password="";
                String tendangnhap="";
                String sdt="";

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id=object.getInt("id");
                        name=object.getString("name");
                        email=object.getString("email");
                        password=object.getString("password");
                        tendangnhap=object.getString("tendangnhap");
                        sdt=object.getString("sdt");

                        user=new User(id,name,email,password,tendangnhap,sdt);
                    }
                    //settext số like
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getCountYeuThich, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int count=0;

                            try {
                                JSONArray array=new JSONArray(response);
                                for(int i=0;i<array.length();i++){
                                    JSONObject object=array.getJSONObject(i);

                                    count=object.getInt("count");
                                }
                                txt_so_like.setText(count+" Like");
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
                            params.put("iduser", String.valueOf(user.getId()));

                            return params;
                        }
                    };
                    Volley.newRequestQueue(getContext()).add(stringRequest);
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
                params.put("tendangnhap",tendangnhap);
                params.put("pass",pass);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void getImageUser(String tendangnhap,String pass){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getdetailUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String image="";

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        image=object.getString("photo");

                    }
                    Glide.with(getContext()).load(image).into(circleImageView);
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
                params.put("tendangnhap",tendangnhap);
                params.put("pass",pass);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void getHoaDonChoXacNhan(String id){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getHoaDonChoXacNhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tensp = "";
                String img = "";
                int gia = 0;
                int slmua = 0;
                int xacnhan = 0;
                int danhan = 0;
                int thanhtien = 0;
                String diachinhan="";
                String ngaydat = "";
                int id = 0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id = object.getInt("idhoadon");
                        tensp = object.getString("title");
                        img = object.getString("image");
                        gia = object.getInt("price");
                        slmua = object.getInt("slmua");
                        xacnhan = object.getInt("xacnhan");
                        danhan = object.getInt("danhan");
                        diachinhan = object.getString("diachinhan");
                        ngaydat = object.getString("ngaydat");
                        thanhtien = slmua*gia;
                        hoaDon = new HoaDon(id,tensp,img,gia,slmua,xacnhan,danhan,thanhtien,diachinhan,ngaydat);
                        if (hoaDon.getXacnhan() == 0){
                            hoaDonList.add(hoaDon);
                            txtNumberChoXacNhan.setVisibility(View.VISIBLE);
                            txtNumberChoXacNhan.setText(hoaDonList.size()+"");
                        }
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
                params.put("iduser",id);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void getHoaDonDangGiao(String id){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getHoaDonChoXacNhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tensp = "";
                String img = "";
                int gia = 0;
                int slmua = 0;
                int xacnhan = 0;
                int danhan = 0;
                int thanhtien = 0;
                int nhanvienGH = 0;
                String diachinhan = "";
                String ngaydat = "";
                int khachhangxacnhan = 0;
                int id = 0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id = object.getInt("idhoadon");
                        tensp = object.getString("title");
                        img = object.getString("image");
                        gia = object.getInt("price");
                        slmua = object.getInt("slmua");
                        xacnhan = object.getInt("xacnhan");
                        danhan = object.getInt("danhan");
                        nhanvienGH = object.getInt("nhanviengiaohang");
                        diachinhan = object.getString("diachinhan");
                        ngaydat = object.getString("ngaydat");
                        khachhangxacnhan = object.getInt("khachhangxacnhan");
                        thanhtien = slmua*gia;
                        HoaDon hoaDonCH = new HoaDon(id,tensp,img,gia,slmua,xacnhan,danhan,thanhtien,diachinhan,ngaydat);
                        if (hoaDonCH.getXacnhan() == 1 && nhanvienGH !=0 && khachhangxacnhan == 0){
                            hoaDonDangGiaoList.add(hoaDonCH);
                            txtNumberDangGiao.setVisibility(View.VISIBLE);
                            txtNumberDangGiao.setText(hoaDonDangGiaoList.size()+"");
                        }
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
                params.put("iduser",id);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void getHoaDonChoLayHang(String id){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getHoaDonChoXacNhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tensp = "";
                String img = "";
                int gia = 0;
                int slmua = 0;
                int xacnhan = 0;
                int danhan = 0;
                int thanhtien = 0;
                int nhanvienGH = 0;
                String diachinhan = "";
                String ngaydat = "";
                int id = 0 ;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id = object.getInt("idhoadon");
                        tensp = object.getString("title");
                        img = object.getString("image");
                        gia = object.getInt("price");
                        slmua = object.getInt("slmua");
                        xacnhan = object.getInt("xacnhan");
                        danhan = object.getInt("danhan");
                        nhanvienGH = object.getInt("nhanviengiaohang");
                        diachinhan = object.getString("diachinhan");
                        ngaydat = object.getString("ngaydat");
                        thanhtien = slmua*gia;
                        HoaDon hoaDon = new HoaDon(id,tensp,img,gia,slmua,xacnhan,danhan,thanhtien,diachinhan,ngaydat);
                        if (hoaDon.getXacnhan() == 1 && nhanvienGH == 0){
                            hoaDonChoLayHangList.add(hoaDon);
                            txtNumberChoLayHang.setVisibility(View.VISIBLE);
                            txtNumberChoLayHang.setText(hoaDonChoLayHangList.size()+"");
                        }
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
                params.put("iduser",id);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void getHoaDonDaGiao(String id){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getHoaDonChoXacNhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tensp = "";
                String img = "";
                int gia = 0;
                int slmua = 0;
                int xacnhan = 0;
                int danhan = 0;
                int thanhtien = 0;
                String diachinhan = "";
                String ngaydat = "";
                int khachhangxacnhan = 0;
                int nhanvienGH = 0;
                int id = 0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id = object.getInt("idhoadon");
                        tensp = object.getString("title");
                        img = object.getString("image");
                        gia = object.getInt("price");
                        slmua = object.getInt("slmua");
                        xacnhan = object.getInt("xacnhan");
                        danhan = object.getInt("danhan");
                        diachinhan = object.getString("diachinhan");
                        ngaydat = object.getString("ngaydat");
                        khachhangxacnhan = object.getInt("khachhangxacnhan");
                        nhanvienGH = object.getInt("nhanviengiaohang");
                        thanhtien = slmua*gia;
                        HoaDon hoaDon = new HoaDon(id,tensp,img,gia,slmua,xacnhan,danhan,thanhtien,diachinhan,ngaydat);
                        if (hoaDon.getXacnhan() != 0 && nhanvienGH !=0 && khachhangxacnhan != 0){
                            hoaDonDaGiaoList.add(hoaDon);
                            txtNumberDaGiao.setVisibility(View.VISIBLE);
                            txtNumberDaGiao.setText(hoaDonDaGiaoList.size()+"");
                        }
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
                params.put("iduser",id);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
