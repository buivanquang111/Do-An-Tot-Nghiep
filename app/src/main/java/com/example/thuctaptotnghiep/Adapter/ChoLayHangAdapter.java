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

public class ChoLayHangAdapter extends RecyclerView.Adapter<ChoLayHangAdapter.ChoLayHangViewHolder>{
    private List<HoaDon> hoaDonList;
    private Context mContext;

    public ChoLayHangAdapter(List<HoaDon> hoaDonList, Context mContext) {
        this.hoaDonList = hoaDonList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ChoLayHangAdapter.ChoLayHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cholayhang,parent,false);
        return new ChoLayHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoLayHangAdapter.ChoLayHangViewHolder holder, int position) {
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
        holder.txtngaydat.setText("Ngày đặt hàng: "+hd.getNgaydat());
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class ChoLayHangViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txttensp,txtslmua,txtgia,txtthanhtien,txtngaydat;
        public ChoLayHangViewHolder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.image_item_cholayhang);
            txttensp = itemView.findViewById(R.id.txt_tensp_item_cholayhang);
            txtslmua = itemView.findViewById(R.id.txt_slmua_item_cholayhang);
            txtgia = itemView.findViewById(R.id.txt_gia_item_cholayhang);
            txtthanhtien = itemView.findViewById(R.id.txt_thanhtien_item_cholayhang);
            txtngaydat = itemView.findViewById(R.id.txt_ngaydathang_cholayhang);
        }
    }
}
