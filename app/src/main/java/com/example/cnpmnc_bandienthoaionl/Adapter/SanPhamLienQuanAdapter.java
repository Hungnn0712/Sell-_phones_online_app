package com.example.cnpmnc_bandienthoaionl.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.entity.DienThoai;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SanPhamLienQuanAdapter extends RecyclerView.Adapter<SanPhamLienQuanAdapter.SanPhamLienQuanViewHolder> {
    private List<DienThoai> dsm;
    private Context c;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DienThoai dienThoai;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private chuyen chuyen;
    public interface chuyen{
        void ChuyenFragment(DienThoai dienThoai);
    }
    public SanPhamLienQuanAdapter(Context c, ArrayList<DienThoai> dsm, chuyen chuyen) {
        this.dsm = dsm;
        this.chuyen = chuyen;
        this.c = c;
    }
    @NonNull
    @Override
    public SanPhamLienQuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sanphamlq, parent, false);
        SanPhamLienQuanViewHolder viewHolder = new SanPhamLienQuanViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamLienQuanViewHolder holder, int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Giá: "+formatter.format(lg.getGiaTien()));
        Picasso.get()
                .load(lg.getLinkAnh())
                .into(holder.anhdt);
        holder.anhdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuyen.ChuyenFragment(lg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    class SanPhamLienQuanViewHolder extends RecyclerView.ViewHolder{
        TextView tendt,giadt;
        ImageView anhdt;

        public SanPhamLienQuanViewHolder(@NonNull View view) {
            super(view);
            tendt =view.findViewById(R.id.tensplq);
            giadt = view.findViewById(R.id.giasplq);
            anhdt = view.findViewById(R.id.anhsplq);
        }
    }
}
