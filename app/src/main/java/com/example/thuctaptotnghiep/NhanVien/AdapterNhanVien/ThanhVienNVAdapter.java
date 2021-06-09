package com.example.thuctaptotnghiep.NhanVien.AdapterNhanVien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.thuctaptotnghiep.Admin.AdapterAdmin.ThanhVienAdapter;
import com.example.thuctaptotnghiep.Admin.Interface.IClickItemSuaThanhVienAdmin;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.NhanVien.InterfaceNhanVien.IClickItemSuaThanhVienNhanVien;
import com.example.thuctaptotnghiep.NhanVien.ObjectNhanVien.ThanhVienNV;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThanhVienNVAdapter extends RecyclerView.Adapter<ThanhVienNVAdapter.ThanhVienNVViewholder>{
    private List<ThanhVienNV> thanhVienList;
    public Context context;
    IClickItemSuaThanhVienNhanVien mIClickItemSuaThanhVienNhanVien;

    public ThanhVienNVAdapter(List<ThanhVienNV> thanhVienList, Context context, IClickItemSuaThanhVienNhanVien iClickItemSuaThanhVienNhanVien) {
        this.thanhVienList = thanhVienList;
        this.context = context;
        this.mIClickItemSuaThanhVienNhanVien = iClickItemSuaThanhVienNhanVien;
    }

    @NonNull
    @Override
    public ThanhVienNVAdapter.ThanhVienNVViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhanvien_item_thanhvien,parent,false);
        return new ThanhVienNVAdapter.ThanhVienNVViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienNVAdapter.ThanhVienNVViewholder holder, int position) {
        ThanhVienNV thanhVien = thanhVienList.get(position);
        holder.txtten.setText(thanhVien.getTen());
        holder.txtemail.setText(thanhVien.getEmail());
        holder.txtdiachi.setText(thanhVien.getDiachi());
        Glide.with(context).load(thanhVien.getPhoto()).into(holder.img);
        holder.imgBaCham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayoutAdmin.setVisibility(View.VISIBLE);
                holder.imgBaCham.setVisibility(View.GONE);
            }
        });
        holder.linearLayoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayoutAdmin.setVisibility(View.GONE);
                holder.imgBaCham.setVisibility(View.VISIBLE);
            }
        });
        holder.linearLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThanhVien(String.valueOf(thanhVien.getId()));

                thanhVienList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.linearLayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemSuaThanhVienNhanVien.onClickSua(thanhVien);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thanhVienList.size();
    }

    public class ThanhVienNVViewholder extends RecyclerView.ViewHolder {
        private ImageView img,imgBaCham;
        private LinearLayout linearLayoutAdmin,linearLayoutEdit,linearLayoutDelete,linearLayoutClose;
        private TextView txtten,txtemail,txtdiachi;
        public ThanhVienNVViewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.nhanvien_image_item_thanhvien);
            imgBaCham=itemView.findViewById(R.id.nhanvien_ba_cham);
            linearLayoutAdmin = itemView.findViewById(R.id.nhanvien_thanhvien_menu);
            linearLayoutEdit = itemView.findViewById(R.id.nhanvien_edit_item_thanhvien);
            linearLayoutDelete = itemView.findViewById(R.id.nhanvien_delete_item_thanhvien);
            linearLayoutClose = itemView.findViewById(R.id.nhanvien_close_item_thanhvien);
            txtten = itemView.findViewById(R.id.nhanvien_txtten_item_thanhvien);
            txtemail = itemView.findViewById(R.id.nhanvien_txtemail_item_thanhvien);
            txtdiachi = itemView.findViewById(R.id.nhanvien_txtdiachi_item_thanhvien);
        }
    }
    private void deleteThanhVien(String id){
        String daxoa = "1";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_deleteThanhVienAdmin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("delete successfully")){
                    Toast.makeText(context, "xóa thành công", Toast.LENGTH_SHORT).show();
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
                params.put("iduser",id);
                params.put("daxoa",daxoa);
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);

    }
}
