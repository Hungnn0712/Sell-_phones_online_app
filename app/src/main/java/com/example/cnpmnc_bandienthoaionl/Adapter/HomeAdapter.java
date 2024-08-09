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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private ArrayList<DienThoai> dsm;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private Context c;
    DienThoai dienThoai;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private thinhhanh thinhhanh;

    public interface thinhhanh{
        void thinhhanh(DienThoai dienThoai);
    }
    public HomeAdapter(Context c, ArrayList<DienThoai> dsm,thinhhanh thinhhanh1) {
        this.thinhhanh = thinhhanh1;
        this.dsm = dsm;
        this.c = c;
    }


    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_topth2, parent, false);
        HomeViewHolder viewHolder = new HomeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.HomeViewHolder holder, int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Giá: "+formatter.format(lg.getGiaTien()));
//        byte[] manghinh = Base64.getDecoder().decode(lg.getLinkAnh());
//        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
//        holder.anhdt.setImageBitmap(bm);
        Picasso.get()
                .load(lg.getLinkAnh())

                .into(holder.anhdt);
        holder.anhdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thinhhanh.thinhhanh(lg);
            }
        });
        holder.tendt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thinhhanh.thinhhanh(lg);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tendt,giadt;
        ImageView anhdt;
        CardView cardView;

        public HomeViewHolder(View view) {
            super(view);

            tendt =view.findViewById(R.id.tendttop);
            giadt = view.findViewById(R.id.giadttop);
            anhdt = view.findViewById(R.id.anhdttop);
            cardView = view.findViewById(R.id.carddttop);


        }
    }
}
