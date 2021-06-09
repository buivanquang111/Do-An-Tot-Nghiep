package com.example.thuctaptotnghiep.NhanVien.AdapterNhanVien;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.thuctaptotnghiep.Admin.AdapterAdmin.DonHangAdapter;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.DonHang;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.NhanVien.ObjectNhanVien.HoaDonNV;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HoaDonNVAdapter extends RecyclerView.Adapter<HoaDonNVAdapter.HoaDonNVViewholder>{
    List<HoaDonNV> donHangList;
    Context mContext;
    List<String> listNhanVien=new ArrayList<>();
    String tenchucvu = "nhân viên";
    String iduser;
    List<HoaDonNV> listDH = new ArrayList<>();
    HoaDonNV hoaDonNV;
    ThanhVien nv;
    List<ThanhVien> listNV;

    public HoaDonNVAdapter(List<HoaDonNV> donHangList, Context mContext) {
        this.donHangList = donHangList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HoaDonNVAdapter.HoaDonNVViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhanvien_item_hoadon,parent,false);
        return new HoaDonNVViewholder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull HoaDonNVAdapter.HoaDonNVViewholder holder, int position) {
        HoaDonNV donHang = donHangList.get(position);
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
        if (donHang.getDaNhan() == 1){
            holder.btnXacNhanGHThanhCong.setEnabled(false);
            holder.btnXacNhanGHThanhCong.setBackgroundColor(R.color.button);
        }
        holder.btnXacNhanGHThanhCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacNhanDaNhanDonHang(String.valueOf(donHang.getId()));
                holder.btnXacNhanGHThanhCong.setEnabled(false);
                holder.btnXacNhanGHThanhCong.setBackgroundColor(R.color.button);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class HoaDonNVViewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTenSP,sl,hoTen,sdt,diaChiNhan,tongTien,txtNgayDat;
        Button btnXacNhanGHThanhCong;
        public HoaDonNVViewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.nhanvien_image_item_hoadon);
            txtTenSP = itemView.findViewById(R.id.nhanvien_txttensp_hoadon);
            sl = itemView.findViewById(R.id.nhanvien_txtsoluong_hoadon);
            hoTen = itemView.findViewById(R.id.nhanvien_txthoten_hoadon);
            sdt = itemView.findViewById(R.id.nhanvien_txtsdt_hoadon);
            diaChiNhan = itemView.findViewById(R.id.nhanvien_txtdiachinhan_hoadon);
            tongTien = itemView.findViewById(R.id.nhanvien_txttongtien_hoadon);
            txtNgayDat = itemView.findViewById(R.id.nhanvien_txtngaydat_hoadon);
            btnXacNhanGHThanhCong = itemView.findViewById(R.id.nhanvien_btnxacnhan_hoadon);

        }
    }
    private void xacNhanDaNhanDonHang(String id){
        String daNhan = "1";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_xacNhanDaNhanDonHangNhanVien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("update successfully")){
                    Toast.makeText(mContext, "Xác nhận đơn hàng đã giao thành công", Toast.LENGTH_SHORT).show();
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
                params.put("danhan",daNhan);
                return params;
            }
        };
        Volley.newRequestQueue(mContext).add(stringRequest);

    }
}
