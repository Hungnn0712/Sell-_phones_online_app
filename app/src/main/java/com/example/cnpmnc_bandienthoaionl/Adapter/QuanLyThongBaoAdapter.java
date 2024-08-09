package com.example.cnpmnc_bandienthoaionl.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpmnc_bandienthoaionl.QuanLy.QuanLyThongBaoFragment;
import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.entity.ThongBao;

import java.util.ArrayList;

public class QuanLyThongBaoAdapter extends RecyclerView.Adapter<QuanLyThongBaoAdapter.QuanLyThongBaoViewHolder> {
    private ArrayList<ThongBao> dsm;
    private Context c;

    public QuanLyThongBaoAdapter( Context c,ArrayList<ThongBao> dsm) {
        this.dsm = dsm;
        this.c = c;
    }

    @NonNull
    @Override
    public QuanLyThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thongbao, parent, false);
        QuanLyThongBaoViewHolder viewHolder = new QuanLyThongBaoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLyThongBaoViewHolder holder, int position) {
        ThongBao lg = dsm.get(position);
        holder.noidung.setText(lg.getNoiDung());
        holder.tieude.setText(lg.getTieuDe());
    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    class QuanLyThongBaoViewHolder extends RecyclerView.ViewHolder{
        TextView ngay,noidung,tieude;
        CardView cardView;
        public QuanLyThongBaoViewHolder(@NonNull View view) {
            super(view);
            tieude = view.findViewById(R.id.tieudetb);
            noidung = view.findViewById(R.id.noidungtb);
            cardView = view.findViewById(R.id.cardviewloai21);
        }
    }
}
