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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.entity.DienThoai;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

public class BanChayAdapter extends RecyclerView.Adapter<BanChayAdapter.BanChayViewHolder>{


    private ArrayList<DienThoai> dsm;
    private Context c;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private banchaynhat banchaynhat;
    public interface banchaynhat{
        void banchay(DienThoai dienThoai);
    }

    public BanChayAdapter(Context c, ArrayList<DienThoai> dsm,banchaynhat banchaynhat1) {
        this.banchaynhat = banchaynhat1;
        this.dsm = dsm;
        this.c = c;
    }

    @NonNull
    @Override
    public BanChayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_banchay2, parent, false);
        BanChayViewHolder viewHolder = new BanChayViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BanChayViewHolder holder, int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Giá : "+formatter.format(lg.getGiaTien()));
//        byte[] manghinh = Base64.getDecoder().decode(lg.getLinkAnh());
//        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
//        holder.anhdt.setImageBitmap(bm);
        Picasso.get()
                .load(lg.getLinkAnh())

                .into(holder.anhdt);
        holder.anhdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banchaynhat.banchay(lg);
            }
        });
        holder.tendt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banchaynhat.banchay(lg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class BanChayViewHolder extends RecyclerView.ViewHolder{
        TextView tendt,giadt;
        ImageView anhdt;
        CardView cardView;
        public BanChayViewHolder(@NonNull View view) {
            super(view);
            tendt =view.findViewById(R.id.tendt1);
            giadt = view.findViewById(R.id.giadt21);
            anhdt = view.findViewById(R.id.anhdt1);
            cardView = view.findViewById(R.id.cardviewdienthoai1111);
        }
    }
}
