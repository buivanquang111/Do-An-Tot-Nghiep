package com.example.thuctaptotnghiep.Admin.AdapterAdmin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.DonHang;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
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

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewholder>{

    List<DonHang> donHangList;
    Context mContext;
    List<String> listNhanVien=new ArrayList<>();
    String tenchucvu = "nhân viên";
    String iduser;
    List<DonHang> listDH = new ArrayList<>();
    DonHang donHang;
    ThanhVien nv;
    List<ThanhVien> listNV;

    public DonHangAdapter(List<DonHang> donHangList, Context mContext) {
        this.donHangList = donHangList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DonHangViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_hoadon,parent,false);

        return new DonHangViewholder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DonHangViewholder holder, int position) {
        DonHang donHang = donHangList.get(position);
        Glide.with(mContext).load(donHang.getImg()).into(holder.img);
        holder.txtTenSP.setText(donHang.getTenSp());
        holder.sl.setText("Số lượng mua: "+donHang.getSlMua());
        holder.hoTen.setText("Người nhận: "+donHang.getHoTen());
        holder.sdt.setText("Số điện thoại: "+donHang.getSdt());
        holder.diaChiNhan.setText("Địa chỉ nhận: "+donHang.getDiaChiNhan());
        //chuyển đối số sang đơn vị tiền tệ việt nam
        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);
        String gia=numberFormat.format(donHang.getTongTien());
        holder.tongTien.setText("Tổng tiền: "+gia);
        holder.txtNgayDat.setText("Ngày đặt: "+donHang.getNgayDat());

        if (donHang.getXacNhan() == 1){
            holder.btnxacnhan.setEnabled(false);
            holder.btnxacnhan.setBackgroundColor(R.color.button);
        }
        if (donHang.getNvGiaoHang() != 0){
            holder.btnChonNguoiGH.setEnabled(false);
            holder.btnChonNguoiGH.setBackgroundColor(R.color.button);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getNhanVienGiaoHangAdmin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String name="";
                    try {
                        JSONArray array=new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject object=array.getJSONObject(i);
                            name = object.getString("name");
                        }
                        holder.txtNVGiaoHang.setText("Người giao hàng: "+name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param=new HashMap<>();
                    param.put("nhanviengiaohang", String.valueOf(donHang.getNvGiaoHang()));
                    return param;
                }
            };
            Volley.newRequestQueue(mContext).add(stringRequest);
        }

        holder.btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacNhanDonHang(String.valueOf(donHang.getId()));
                holder.btnxacnhan.setEnabled(false);
                holder.btnxacnhan.setBackgroundColor(R.color.button);
                notifyDataSetChanged();
            }
        });
        holder.btnChonNguoiGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getTenNhanVien, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                iduser = object.getString("id");
                                String tennv = object.getString("name");
                                int daxoa = object.getInt("daxoa");
                                if (daxoa == 0 ){
                                    listNhanVien.add(tennv);
                                }
                            }
                            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                            View view = View.inflate(mContext,R.layout.admin_spinner_tennhanvien,null);
                            builder.setTitle("chọn nhân viên giao hàng!!!");
                            Spinner spinner = view.findViewById(R.id.spinner_admin_tennhanvien);
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,listNhanVien);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);

                            builder.setPositiveButton("Chọn", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext,spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

                                    holder.txtNVGiaoHang.setText("Người giao hàng: "+spinner.getSelectedItem().toString());

                                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_updateNhanVienGiaoHangAdmin, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("update successfully")){
                                                Toast.makeText(mContext, "thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
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
                                            params.put("idhoadon", String.valueOf(donHang.getId()));
                                            params.put("nhanviengiaohang",iduser);
                                            return params;
                                        }
                                    };
                                    Volley.newRequestQueue(mContext).add(stringRequest);

                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    listNhanVien.clear();
                                    dialog.dismiss();
                                }
                            });
                            builder.setView(view);
                            AlertDialog dialog = builder.create();
                            dialog.show();


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
                        params.put("tenchucvu",tenchucvu);
                        return params;
                    }
                };
                Volley.newRequestQueue(mContext).add(stringRequest);
            }
        });

    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class DonHangViewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txtTenSP,sl,hoTen,sdt,diaChiNhan,tongTien,txtNgayDat,txtNVGiaoHang;
        Button btnxacnhan,btnChonNguoiGH;

        public DonHangViewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.admin_image_item_hoadon);
            txtTenSP = itemView.findViewById(R.id.admin_txttensp_hoadon);
            sl = itemView.findViewById(R.id.admin_txtsoluong_hoadon);
            hoTen = itemView.findViewById(R.id.admin_txthoten_hoadon);
            sdt = itemView.findViewById(R.id.admin_txtsdt_hoadon);
            diaChiNhan = itemView.findViewById(R.id.admin_txtdiachinhan_hoadon);
            tongTien = itemView.findViewById(R.id.admin_txttongtien_hoadon);
            txtNgayDat = itemView.findViewById(R.id.admin_txtngaydat_hoadon);
            txtNVGiaoHang = itemView.findViewById(R.id.admin_txt_nvGiaoHang_hoadon);
            btnxacnhan = itemView.findViewById(R.id.admin_btnxacnhan_hoadon);
            btnChonNguoiGH = itemView.findViewById(R.id.admin_btnchonNguoiGiaoHang_hoadon);
        }
    }
    private void xacNhanDonHang(String id){
        String xacNhan = "1";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_xacNhanDonHangAdmin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("update successfully")){
                    Toast.makeText(mContext, "Xác nhận đơn hàng thành công", Toast.LENGTH_SHORT).show();
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
                params.put("id",id);
                params.put("xacnhan",xacNhan);
                return params;
            }
        };
        Volley.newRequestQueue(mContext).add(stringRequest);

    }

}
