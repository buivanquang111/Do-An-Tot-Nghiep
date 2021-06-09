package com.example.thuctaptotnghiep.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thuctaptotnghiep.Admin.FragmentAdmin.ThanhVienFragment;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import java.util.HashMap;
import java.util.Map;

public class DiaChiFragment extends Fragment {

    public static final  String TAG = DiaChiFragment.class.getName();

    EditText ed_hoten, ed_sdt, ed_email, ed_diachinhan;
    Button btnchon;
    ImageView imgback;

    String strHoTen,strSDT,strEmail,strDiaChi,idUser;
    Double tongtien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diachi,container,false);

        ed_hoten = view.findViewById(R.id.ed_hoten_fragmenthoadon);
        ed_sdt = view.findViewById(R.id.ed_sdt_fragmenthoadon);
        ed_email = view.findViewById(R.id.ed_email_fragmenthoadon);
        ed_diachinhan = view.findViewById(R.id.ed_diachinhan_fragmenthoadon);
        btnchon = view.findViewById(R.id.btnchon_diachi);
        imgback = view.findViewById(R.id.image_back_diachi);

        Bundle bundle = getArguments();
        strHoTen = bundle.getString("hoten");
        strSDT = bundle.getString("sdt");
        strEmail = bundle.getString("email");
        strDiaChi = bundle.getString("diachi");
        idUser = bundle.getString("idu");
        tongtien = bundle.getDouble("tongtien");

        ed_hoten.setText(strHoTen);
        ed_sdt.setText(strSDT);
        ed_email.setText(strEmail);
        ed_diachinhan.setText(strDiaChi);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        btnchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateThongTinNhan(idUser);

            }
        });
        return view;
    }
    private void updateThongTinNhan(String id){
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        String hoten = ed_hoten.getText().toString();
        String sdt = ed_sdt.getText().toString();
        String email = ed_email.getText().toString();
        String diachi = ed_diachinhan.getText().toString();

        StringRequest request=new StringRequest(Request.Method.POST, Server.url_updateDiaChiNguoiNhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("update successfully")){
                    progressDialog.dismiss();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    HoaDonFragment hoaDonFragment = new HoaDonFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putDouble("tongthanhtoan",tongtien);
                    bundle1.putString("iduser_giohang",id);
                    hoaDonFragment.setArguments(bundle1);
                    fragmentTransaction.replace(R.id.frame_layout,hoaDonFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("iduser", id);
                params.put("name",hoten);
                params.put("sdt",sdt);
                params.put("email",email);
                params.put("diachi",diachi);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
}
