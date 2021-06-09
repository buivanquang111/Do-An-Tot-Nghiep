package com.example.thuctaptotnghiep.Admin.AdapterAdmin;

import android.content.Context;
import android.text.TextUtils;
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
import com.example.thuctaptotnghiep.Admin.Interface.IClickItemSuaSanPhamAdmin;
import com.example.thuctaptotnghiep.Admin.Interface.IClickItemSuaThanhVienAdmin;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.SanPham;
import com.example.thuctaptotnghiep.Admin.ObjectAdmin.ThanhVien;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewholder>{
    private List<SanPham> sanPhamList;
    public Context context;
    IClickItemSuaSanPhamAdmin mIClickItemSuaSanPhamAdmin;

    public SanPhamAdapter(List<SanPham> sanPhamList, Context context, IClickItemSuaSanPhamAdmin mIClickItemSuaSanPhamAdmin) {
        this.sanPhamList = sanPhamList;
        this.context = context;
        this.mIClickItemSuaSanPhamAdmin = mIClickItemSuaSanPhamAdmin;
    }

    @NonNull
    @Override
    public SanPhamViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_sanpham,parent,false);
        return new SanPhamViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewholder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.txtten.setText(sanPham.getTitle());
        holder.txtsoluong.setText("số lượng: "+sanPham.getSoluong());
        //chuyển đối số sang đơn vị tiền tệ việt nam
        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);
        String gia=numberFormat.format(sanPham.getPrice());
        holder.txtgia.setText("giá: "+gia);

        holder.txtmota.setMaxLines(3);
        holder.txtmota.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtmota.setText(sanPham.getDescription());

        Glide.with(context).load(sanPham.getImage()).into(holder.img);
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
                deleteSanPham(String.valueOf(sanPham.getId()));

                sanPhamList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.linearLayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemSuaSanPhamAdmin.onClickSua(sanPham);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public class SanPhamViewholder extends RecyclerView.ViewHolder {
        private ImageView img,imgBaCham;
        private LinearLayout linearLayoutAdmin,linearLayoutEdit,linearLayoutDelete,linearLayoutClose;
        private TextView txtten,txtgia,txtsoluong,txtmota;

        public SanPhamViewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.admin_image_item_sanpham);
            imgBaCham=itemView.findViewById(R.id.admin_ba_cham_sanpham);
            linearLayoutAdmin = itemView.findViewById(R.id.admin_sanpham_menu);
            linearLayoutEdit = itemView.findViewById(R.id.admin_edit_item_sanpham);
            linearLayoutDelete = itemView.findViewById(R.id.admin_delete_item_sanpham);
            linearLayoutClose = itemView.findViewById(R.id.admin_close_item_sanpham);
            txtten = itemView.findViewById(R.id.admin_txtten_item_sanpham);
            txtgia = itemView.findViewById(R.id.admin_txtgia_item_sanpham);
            txtsoluong = itemView.findViewById(R.id.admin_txtsoluong_item_sanpham);
            txtmota = itemView.findViewById(R.id.admin_txtmota_item_sanpham);
        }
    }

    private void deleteSanPham(String id){
        String daxoa = "1";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_deleteSanPhamAdmin, new Response.Listener<String>() {
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
                params.put("idproduct",id);
                params.put("daxoa",daxoa);
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);

    }
}
