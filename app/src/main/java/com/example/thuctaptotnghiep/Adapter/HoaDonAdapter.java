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
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.thuctaptotnghiep.Object.GioHang;
import com.example.thuctaptotnghiep.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {

    private List<GioHang> hoaDonList;
    private Context mContext;

    private ViewBinderHelper viewBinderHelper=new ViewBinderHelper();

    public HoaDonAdapter(List<GioHang> hoaDonList, Context mContext) {
        this.hoaDonList = hoaDonList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,parent,false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        GioHang sp = hoaDonList.get(position);
        holder.txtTen.setText(sp.getTitle());

        //chuyển đối số sang đơn vị tiền tệ việt nam
        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);
        String gia=numberFormat.format(sp.getPrice());
        holder.txtGia.setText(gia);
        holder.txtSL.setText("x"+sp.getSoluong_mua());
        Glide.with(mContext).load(sp.getImage()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtTen,txtGia,txtSL;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageSP_itemHD);
            txtTen = itemView.findViewById(R.id.txtTenSP_itemHD);
            txtGia =itemView.findViewById(R.id.txtGiaSP_itemHD);
            txtSL = itemView.findViewById(R.id.txtSLSP_itemHD);

        }
    }
}
