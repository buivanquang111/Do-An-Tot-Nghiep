package com.example.thuctaptotnghiep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thuctaptotnghiep.Object.Product;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.inteface.IClickItemListener;

import java.util.List;

public class ProductNuAdapter extends RecyclerView.Adapter<ProductNuAdapter.ProductNuViewHolder>{
    private List<Product> dsproduct;
    private Context mcontext;
    IClickItemListener mIClickItemListener;

    public ProductNuAdapter(List<Product> dsproduct, Context mcontext, IClickItemListener mIClickItemListener) {
        this.dsproduct = dsproduct;
        this.mcontext = mcontext;
        this.mIClickItemListener = mIClickItemListener;
    }

    @NonNull
    @Override
    public ProductNuAdapter.ProductNuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductNuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductNuAdapter.ProductNuViewHolder holder, int position) {
        final Product p=dsproduct.get(position);
        holder.txttitle.setText(p.title);
        holder.txtgia.setText(" "+p.price);
        Glide.with(mcontext).load(p.image).into(holder.img);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onClickItemProduct(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dsproduct!=null){
            return dsproduct.size();
        }
        return 0;
    }

    public class ProductNuViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txttitle,txtgia;
        private LinearLayout linearLayout;
        public ProductNuViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgproduct);
            txttitle=itemView.findViewById(R.id.txttitle);
            txtgia=itemView.findViewById(R.id.txtgia);
            linearLayout=itemView.findViewById(R.id.linerlayout_product);
        }
    }
}
