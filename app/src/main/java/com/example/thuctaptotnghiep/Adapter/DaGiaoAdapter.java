package com.example.thuctaptotnghiep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thuctaptotnghiep.Object.HoaDon;
import com.example.thuctaptotnghiep.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DaGiaoAdapter extends RecyclerView.Adapter<DaGiaoAdapter.DaGiaoViewHolder>{
    private List<HoaDon> hoaDonList;
    private Context mContext;

    public DaGiaoAdapter(List<HoaDon> hoaDonList, Context mContext) {
        this.hoaDonList = hoaDonList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DaGiaoAdapter.DaGiaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dagiao,parent,false);
        return new DaGiaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaGiaoAdapter.DaGiaoViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return  hoaDonList.size();
    }

    public class DaGiaoViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txttensp,txtslmua,txtgia,txtthanhtien;
        public DaGiaoViewHolder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.image_item_dagiao);
            txttensp = itemView.findViewById(R.id.txt_tensp_item_dagiao);
            txtslmua = itemView.findViewById(R.id.txt_slmua_item_dagiao);
            txtgia = itemView.findViewById(R.id.txt_gia_item_dagiao);
            txtthanhtien = itemView.findViewById(R.id.txt_thanhtien_item_dagiao);
        }
    }
}
