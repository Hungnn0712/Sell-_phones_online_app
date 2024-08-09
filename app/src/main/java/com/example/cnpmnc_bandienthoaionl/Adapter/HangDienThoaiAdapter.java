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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class HangDienThoaiAdapter extends RecyclerView.Adapter<HangDienThoaiAdapter.HangDienThoaiViewHolder> {
    private List<DienThoai> dsm;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private Context c;
    private chuyen chuyen;
    public interface chuyen{
        void ChuyenFragment(DienThoai dienThoai);
    }

    public HangDienThoaiAdapter(Context c, ArrayList<DienThoai> dsm, chuyen chuyen) {
        this.dsm = dsm;
        this.c = c;
        this.chuyen = chuyen;
    }

    @NonNull
    @Override
    public HangDienThoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hangdt, parent, false);
        HangDienThoaiViewHolder viewHolder = new HangDienThoaiViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HangDienThoaiViewHolder holder, int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Giá: "+formatter.format(lg.getGiaTien()));
        holder.chitiet.setText(""+lg.getChiTiet());
//        byte[] manghinh = Base64.getDecoder().decode(lg.getLinkAnh());
//        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
//
//        holder.anhdt.setImageBitmap(bm);
        Picasso.get().load(lg.getLinkAnh()).into(holder.anhdt);
        holder.sao.setText(""+lg.getSoLike());
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
    public class HangDienThoaiViewHolder extends RecyclerView.ViewHolder {

        TextView tendt, giadt, chitiet, sao;
        ImageView anhdt;

        public HangDienThoaiViewHolder(@NonNull View view) {
            super(view);
            tendt = view.findViewById(R.id.tenhangdt);
            giadt = view.findViewById(R.id.giahangdt);
            chitiet = view.findViewById(R.id.chitiethangdt);
            anhdt = view.findViewById(R.id.anhhangdt);
            sao = view.findViewById(R.id.sotimhangdt);
        }
    }
}

