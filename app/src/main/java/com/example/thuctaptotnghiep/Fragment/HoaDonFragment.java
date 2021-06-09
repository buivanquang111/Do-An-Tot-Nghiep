package com.example.thuctaptotnghiep.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thuctaptotnghiep.Adapter.GioHangAdapter;
import com.example.thuctaptotnghiep.Adapter.HoaDonAdapter;
import com.example.thuctaptotnghiep.LoginActivity;
import com.example.thuctaptotnghiep.MainActivity;
import com.example.thuctaptotnghiep.Object.GioHang;
import com.example.thuctaptotnghiep.Object.User;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HoaDonFragment extends Fragment {

    public static GioHang gioHang;
    public static List<GioHang> listGioHang;
    public static List<GioHang> listSPHoaDon;
    /*EditText ed_hoten, ed_sdt, ed_email, ed_diachinhan;*/
    Button btn_dathang;
    ImageView img_back;
    TextView txt_tongtien, txt_phuongthucthanhtoan,txtTenNguoiNhan,txtSDTNguoiNhan,txtEmailNguoiNhan,txtDiaChi, txtTongTienHang_HD, txtTongTienPVC_HD, txtTongTienThanhToan_HD, txtPVC_HD;
    ImageView imgSetThongTin;
    RecyclerView rvSP;

    MainActivity mainActivity;
    double tongtien;
    User user;
    String diachi;
    String hoTenSua,sdtSua,emailSua,diaChiSua;
    HoaDonAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoadon, container, false);

        /*ed_hoten = view.findViewById(R.id.ed_hoten_fragmenthoadon);
        ed_sdt = view.findViewById(R.id.ed_sdt_fragmenthoadon);
        ed_email = view.findViewById(R.id.ed_email_fragmenthoadon);
        ed_diachinhan = view.findViewById(R.id.ed_diachinhan_fragmenthoadon);*/

        btn_dathang = view.findViewById(R.id.btn_dathang_fragmenthoadon);
        img_back = view.findViewById(R.id.image_back_hoadon);
        txt_tongtien = view.findViewById(R.id.txttongthanhtoan);
        txt_phuongthucthanhtoan = view.findViewById(R.id.txt_phuongthucthanhtoan_hoadon);

        //txtPVC_HD = view.findViewById(R.id.txtPVC_HD);
        txtTongTienHang_HD = view.findViewById(R.id.txtTongTienHang_HD);
        //txtTongTienPVC_HD = view.findViewById(R.id.txtTongTienPVC_HD);
        txtTongTienThanhToan_HD = view.findViewById(R.id.txtTongThanhToan_HD);

        txtTenNguoiNhan=view.findViewById(R.id.txt_tennguoinhan_hoadon);
        txtSDTNguoiNhan = view.findViewById(R.id.txt_sdtnguoinhan_hoadon);
        txtEmailNguoiNhan= view.findViewById(R.id.txt_emailnguoinhan_hoadon);
        txtDiaChi = view.findViewById(R.id.txt_diachinhan_hoadon);
        imgSetThongTin = view.findViewById(R.id.image_set_thongtin);
        rvSP = view.findViewById(R.id.rvSPHoaDon);


        mainActivity = (MainActivity) getActivity();
        mainActivity.bottomNavigationView.setVisibility(View.GONE);




        txt_phuongthucthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhuongThucThanhToan(Gravity.CENTER);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHangFragment.tongtien = 0;
                getFragmentManager().popBackStack();
            }
        });


        listGioHang = new ArrayList<>();
        Bundle bundle = getArguments();
        tongtien = bundle.getDouble("tongthanhtoan");
        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String gia = numberFormat.format(tongtien);
       // txt_tongtien.setText(gia);
        txtTongTienHang_HD.setText(gia);
        //txtTongTienPVC_HD.setText(txtPVC_HD.getText().toString());
        //double phiVanCHuyen = Double.parseDouble(txtTongTienPVC_HD.getText().toString());
        double tongthanhtoan = tongtien ;
        Locale locale1 = new Locale("vi", "VN");
        NumberFormat numberFormat1 = NumberFormat.getCurrencyInstance(locale);
        String giaThanhToan = numberFormat.format(tongthanhtoan);
        txtTongTienThanhToan_HD.setText(giaThanhToan);
        txt_tongtien.setText(giaThanhToan);

        String iduUser=bundle.getString("iduser_giohang");
        //Toast.makeText(mainActivity, iduUser, Toast.LENGTH_SHORT).show();
        getUser(iduUser);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvSP.setLayoutManager(linearLayoutManager);
        listSPHoaDon=new ArrayList<>();
        getgiohang(iduUser);
        mAdapter=new HoaDonAdapter(listSPHoaDon,getContext());
        rvSP.setAdapter(mAdapter);

        imgSetThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = txtTenNguoiNhan.getText().toString();
                String sdt = txtSDTNguoiNhan.getText().toString();
                String email = txtEmailNguoiNhan.getText().toString();
                String diachinhan = txtDiaChi.getText().toString();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                DiaChiFragment diaChiFragment = new DiaChiFragment();
                Bundle bundle = new Bundle();
                bundle.putString("hoten",hoten);
                bundle.putString("sdt",sdt);
                bundle.putString("email",email);
                bundle.putString("diachi",diachinhan);
                bundle.putString("idu",iduUser);
                bundle.putDouble("tongtien",tongtien);
                diaChiFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_layout,diaChiFragment);
                fragmentTransaction.addToBackStack(DiaChiFragment.TAG);
                fragmentTransaction.commit();

            }
        });

        /*//sua thong tin dia chi nhan
        //Bundle bundle1 = getArguments();
        hoTenSua = bundle.getString("hotensua");
        emailSua = bundle.getString("emailsua");
        sdtSua = bundle.getString("sdtsua");
        diaChiSua = bundle.getString("diachisua");
        txtTenNguoiNhan.setText(hoTenSua);
        txtSDTNguoiNhan.setText(sdtSua);
        txtEmailNguoiNhan.setText(emailSua);
        txtDiaChi.setText(diaChiSua);*/



        btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iduser = bundle.getString("iduser_giohang");
                String hoten = txtTenNguoiNhan.getText().toString();
                String sdt = txtSDTNguoiNhan.getText().toString();
                String email = txtEmailNguoiNhan.getText().toString();
                String diachinhan = txtDiaChi.getText().toString();
                String tongtien = String.valueOf(tongthanhtoan);
                //lay ngay hien tại
                Date date = new Date();
                DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                String ngay = dateformat.format(date);
                String xacnhan = "0";
                String danhan = "0";


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.url_getgiohang, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        String idproduct = "";
                        String tendangnhap = "";
                        String title = "";
                        double price = 0;
                        String image = "";
                        int soluong_mua = 0;

                        double tien = 0;

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                id = object.getInt("idg");
                                idproduct = object.getString("idproduct");
                                tendangnhap = object.getString("tendangnhap");
                                title = object.getString("title");
                                price = object.getDouble("price");
                                image = object.getString("image");
                                soluong_mua = object.getInt("soluongmua");

                                gioHang = new GioHang(id, idproduct, tendangnhap, title, price, image, soluong_mua);
                                listGioHang.add(gioHang);

                            }

                            //
                            for (int i = 0; i < listGioHang.size(); i++) {

                                String idproductgiohang = listGioHang.get(i).getIdproduct();
                                String slmua = String.valueOf(listGioHang.get(i).getSoluong_mua());
                                String money = String.valueOf(listGioHang.get(i).getSoluong_mua()*listGioHang.get(i).getPrice());
                                //Toast.makeText(getContext(), idproductgiohang, Toast.LENGTH_SHORT).show();

                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.url_inserthoadon, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if (response.equals("insert successfully")) {

                                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Server.url_deletegiohang, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> para = new HashMap<>();
                                                    para.put("iduser", iduser);
                                                    para.put("idproduct", idproductgiohang);

                                                    return para;
                                                }
                                            };
                                            Volley.newRequestQueue(getContext()).add(stringRequest2);

                                            Toast.makeText(getContext(), "đã thêm vào hoa đơn", Toast.LENGTH_SHORT).show();
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame_layout, new HomeFragment());
                                            transaction.addToBackStack(null);
                                            transaction.commit();
                                        }
                                        if (response.equals("some error occured")){
                                            Toast.makeText(mainActivity, "okok", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(mainActivity, "sai", Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> param = new HashMap<>();
                                        param.put("iduser", iduser);
                                        param.put("idproduct", idproductgiohang);
                                        param.put("slmua", slmua);
                                        param.put("hoten", hoten);
                                        param.put("sdt", sdt);
                                        param.put("email", email);
                                        param.put("diachinhan", diachinhan);
                                        param.put("tongtien",money);
                                        param.put("ngaydat",ngay);
                                        param.put("xacnhan",xacnhan);
                                        param.put("danhan",danhan);

                                        return param;
                                    }
                                };
                                Volley.newRequestQueue(getContext()).add(stringRequest1);


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
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("iduser", iduser);
                        return params;
                    }
                };
                Volley.newRequestQueue(getContext()).add(stringRequest);
            }
        });


        return view;
    }

    private void openPhuongThucThanhToan(int gravity) {
        final Dialog dialog = new Dialog(mainActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_phuongthucthanhtoan);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        Button btnOk, btnCancel;
        btnOk = dialog.findViewById(R.id.btn_ok);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainActivity, "click ok", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getUser(String id){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getDetailUserWithID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int id=0;
                String name="";
                String email="";
                String password="";
                String tendangnhap="";
                String sdt="";
                String dc="";

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
                        dc=object.getString("diachi");

                        user=new User(id,name,email,password,tendangnhap,sdt);
                    }
                    txtTenNguoiNhan.setText(user.getName());
                    txtSDTNguoiNhan.setText(user.getSdt());
                    txtEmailNguoiNhan.setText(user.getEmail());
                    diachi=dc;
                    txtDiaChi.setText(diachi);
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
                Map<String,String> params=new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        Volley.newRequestQueue(mainActivity).add(stringRequest);

    }

    private void getgiohang(String id){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getgiohang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String idproduct="";
                String tendangnhap="";
                String title="";
                double price=0;
                String image="";
                int soluong_mua=0;

                double tien=0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id=object.getInt("idg");
                        idproduct=object.getString("idproduct");
                        tendangnhap=object.getString("tendangnhap");
                        title=object.getString("title");
                        price=object.getDouble("price");
                        image=object.getString("image");
                        soluong_mua=object.getInt("soluongmua");

                        gioHang=new GioHang(id,idproduct,tendangnhap,title,price,image,soluong_mua);
                        listSPHoaDon.add(gioHang);
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
                params.put("iduser",id);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}
