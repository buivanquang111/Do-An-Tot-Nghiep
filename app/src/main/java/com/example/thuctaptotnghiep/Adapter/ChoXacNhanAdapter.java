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
import com.example.thuctaptotnghiep.Object.GioHang;
import com.example.thuctaptotnghiep.Object.HoaDon;
import com.example.thuctaptotnghiep.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ChoXacNhanAdapter extends RecyclerView.Adapter<ChoXacNhanAdapter.ChoXacNhanViewHolder> {

    private List<HoaDon> hoaDonList;
    private Context mContext;

    public ChoXacNhanAdapter(List<HoaDon> hoaDonList, Context mContext) {
        this.hoaDonList = hoaDonList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ChoXacNhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choxacnhan,parent,false);
        return new ChoXacNhanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoXacNhanViewHolder holder, int position) {
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

    public class ChoXacNhanViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txttensp,txtslmua,txtgia,txtthanhtien,txtngaydat;
        public ChoXacNhanViewHolder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.image_item_choxacnhan);
            txttensp = itemView.findViewById(R.id.txt_tensp_item_choxacnhan);
            txtslmua = itemView.findViewById(R.id.txt_slmua_item_choxacnhan);
            txtgia = itemView.findViewById(R.id.txt_gia_item_choxacnhan);
            txtthanhtien = itemView.findViewById(R.id.txt_thanhtien_item_choxacnhan);
            txtngaydat = itemView.findViewById(R.id.txt_ngaydathang_xacnhan);
        }
    }
}
