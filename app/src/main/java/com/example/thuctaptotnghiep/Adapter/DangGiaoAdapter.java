package com.example.thuctaptotnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.example.thuctaptotnghiep.Object.HoaDon;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;


import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DangGiaoAdapter extends RecyclerView.Adapter<DangGiaoAdapter.DangGiaoViewHolder>{
    private List<HoaDon> hoaDonList;
    private Context mContext;

    public DangGiaoAdapter(List<HoaDon> hoaDonList, Context mContext) {
        this.hoaDonList = hoaDonList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DangGiaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danggiao,parent,false);
        return new DangGiaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DangGiaoViewHolder holder, int position) {
        HoaDon hd = hoaDonList.get(position);
        holder.txttensp.setText(hd.getTenproduct());
        holder.txtslmua.setText("x"+hd.getSlmua());

        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String thanhtien = numberFormat.format(hd.getThanhtien());
        String gia = numberFormat.format(hd.getGia());
        holder.txtthanhtien.setText(thanhtien);
        holder.txtgia.setText(gia);
        Glide.with(mContext).load(hd.getImg()).into(holder.img);
        if (hd.getDanhan() == 0){
            holder.btndanhanhang.setEnabled(false);
            holder.btndanhanhang.setBackgroundColor(Color.parseColor("#FFD6D6D6"));
        }
        else{
            holder.btndanhanhang.setEnabled(true);
            holder.btndanhanhang.setBackgroundColor(Color.parseColor("#F1811F"));
            holder.btndanhanhang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    khachHangXacNhanDonHang(String.valueOf(hd.getId()));
                    notifyDataSetChanged();
                }
            });

        }
        try {

            Date date = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(hd.getNgaydat());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngay = simpleDateFormat.format(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(ngay));
            if (hd.getDiaChiNhan().toLowerCase().contains("hà nội")){
                calendar.add(Calendar.DATE,3);
                String ngaydukien = simpleDateFormat.format(calendar.getTime());
                holder.txtTimeNhan.setText("Đang chờ nhận sản phẩm và thanh toán khi nhận hàng dự kiến vào # "+ngaydukien);
            }
            else {
                calendar.add(Calendar.DATE,5);
                String ngaydukiennhan = simpleDateFormat.format(calendar.getTime());
                holder.txtTimeNhan.setText("Đăng chờ nhận sản phẩm và thanh toán khi nhận hàng dự kiến vào # "+ngaydukiennhan);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class DangGiaoViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txttensp,txtslmua,txtgia,txtthanhtien,txtTimeNhan;
        Button btndanhanhang;

        public DangGiaoViewHolder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.image_item_danggiao);
            txttensp = itemView.findViewById(R.id.txt_tensp_item_danggiao);
            txtslmua = itemView.findViewById(R.id.txt_slmua_item_danggiao);
            txtgia = itemView.findViewById(R.id.txt_gia_item_danggiao);
            txtthanhtien = itemView.findViewById(R.id.txt_thanhtien_item_danggiao);
            btndanhanhang = itemView.findViewById(R.id.btn_danhanduochang);
            txtTimeNhan = itemView.findViewById(R.id.txt_thoigian_dukien_giao);
        }
    }

    private void khachHangXacNhanDonHang(String id){
        String khachhangxacnhan = "1";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_khachhangxacnhanhoadon, new Response.Listener<String>() {
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
                params.put("khachhangxacnhan",khachhangxacnhan);
                return params;
            }
        };
        Volley.newRequestQueue(mContext).add(stringRequest);

    }
}
