package com.example.cnpmnc_bandienthoaionl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpmnc_bandienthoaionl.QuanLy.QuanLyDienThoaiFragment;
import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.entity.DienThoai;

import java.util.List;

public class QuanLyDienThoaiAdapter extends RecyclerView.Adapter<QuanLyDienThoaiAdapter.QuanLyDienThoaiViewHolder> {
    private Context context;
    private List<DienThoai> dienThoais;
    QuanLyDienThoaiFragment fragment;

    public QuanLyDienThoaiAdapter(Context context, QuanLyDienThoaiFragment fragment, List<DienThoai> dienThoais) {
        this.context = context;
        this.dienThoais = dienThoais;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public QuanLyDienThoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qldienthoai, parent, false);
        return new QuanLyDienThoaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLyDienThoaiViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class QuanLyDienThoaiViewHolder extends RecyclerView.ViewHolder{
        TextView tendt,giadt,ct,solike;
        ImageView anhdt,tim;
        CheckBox box;
        public QuanLyDienThoaiViewHolder(@NonNull View view) {
            super(view);
            tendt =view.findViewById(R.id.tenqldt);
            ct =view.findViewById(R.id.chitietqldt);
            giadt = view.findViewById(R.id.giaqldt);
            anhdt = view.findViewById(R.id.anhqldt);
            solike = view.findViewById(R.id.sotimqldt);
            tim = view.findViewById(R.id.timqldt);
            box = view.findViewById(R.id.checkqldt);
        }
    }
}

