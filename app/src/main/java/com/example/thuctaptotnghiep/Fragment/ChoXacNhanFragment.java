package com.example.thuctaptotnghiep.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thuctaptotnghiep.Adapter.ChoXacNhanAdapter;
import com.example.thuctaptotnghiep.MainActivity;
import com.example.thuctaptotnghiep.Object.GioHang;
import com.example.thuctaptotnghiep.Object.HoaDon;
import com.example.thuctaptotnghiep.Object.User;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoXacNhanFragment extends Fragment {

    public static final String TAG=ChoXacNhanFragment.class.getName();
    User user;
    HoaDon hoaDon;
    List<HoaDon> hoaDonList;
    ChoXacNhanAdapter mAdapter;
    MainActivity mainActivity;
    RecyclerView rvSP;
    ImageView imgback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choxacnhan,container,false);

        mainActivity = (MainActivity) getActivity();
        rvSP = view.findViewById(R.id.rvSPChoXacNhan);
        imgback = view.findViewById(R.id.image_back_choxacnhan);

        hoaDonList = new ArrayList<>();

        Bundle bundle=getArguments();
        if (bundle!=null){
            user= (User) bundle.get("object_user_choxacnhan");
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager()!=null){
                    getFragmentManager().popBackStack();
                }
            }
        });

        GridLayoutManager manager=new GridLayoutManager(getContext(),1);
        rvSP.setLayoutManager(manager);
        getHoaDonChoXacNhan(String.valueOf(user.getId()));
        mAdapter = new ChoXacNhanAdapter(hoaDonList,getContext());
        rvSP.setAdapter(mAdapter);


        return view;
    }

    private void getHoaDonChoXacNhan(String id){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getHoaDonChoXacNhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tensp = "";
                String img = "";
                int gia = 0;
                int slmua = 0;
                int xacnhan = 0;
                int danhan = 0;
                int thanhtien = 0;
                String diachinhan = "";
                String ngaydat = "";
                int id = 0;

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id = object.getInt("idhoadon");
                        tensp = object.getString("title");
                        img = object.getString("image");
                        gia = object.getInt("price");
                        slmua = object.getInt("slmua");
                        xacnhan = object.getInt("xacnhan");
                        danhan = object.getInt("danhan");
                        diachinhan = object.getString("diachinhan");
                        ngaydat = object.getString("ngaydat");
                        thanhtien = slmua*gia;
                        hoaDon = new HoaDon(id,tensp,img,gia,slmua,xacnhan,danhan,thanhtien,diachinhan,ngaydat);
                        if (hoaDon.getXacnhan() == 0){
                            hoaDonList.add(hoaDon);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("iduser",id);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}
