package com.example.cnpmnc_bandienthoaionl.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cnpmnc_bandienthoaionl.ChiTietDienThoaiFragment;
import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.entity.DienThoai;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DienThoaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DienThoaiFragment extends Fragment {

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    RecyclerView recyclerView;

    public DienThoaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DienThoaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DienThoaiFragment newInstance(String param1, String param2) {
        DienThoaiFragment fragment = new DienThoaiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dsls.clear();

        recyclerView = view.findViewById(R.id.reviewdt);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onStart() {
        FirebaseRecyclerOptions<DienThoai> options =
                new FirebaseRecyclerOptions.Builder<DienThoai>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("DienThoai")
                        ,DienThoai.class).build();
        FirebaseRecyclerAdapter<DienThoai,DienThoaiFragment.DienThoaiViewHolder> adapter =
                new FirebaseRecyclerAdapter<DienThoai, DienThoaiViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull DienThoaiViewHolder holder, @SuppressLint("RecyclerView")int i, @NonNull DienThoai dienThoai) {

                        holder.tendt.setText("" + dienThoai.getTen());
                        holder.giadt.setText("Giá : " + formatter.format(dienThoai.getGiaTien()));
                        holder.ct.setText("" + dienThoai.getChiTiet());
                        holder.solike.setText("" + dienThoai.getSoLike());
                        Picasso.get()
                                .load(dienThoai.getLinkAnh())
                                .into(holder.anhdt);
//                        byte[] manghinh = Base64.getDecoder().decode(dienThoai.getLinkAnh());
//                        Bitmap bm = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
//                        holder.anhdt.setImageBitmap(bm);
                        holder.anhdt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fragment = new ChiTietDienThoaiFragment();
                                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                Bundle bundle = new Bundle();
                                bundle.putString("name", dienThoai.getTen());
                                bundle.putInt("gia", dienThoai.getGiaTien());
                                bundle.putString("chitiet", dienThoai.getChiTiet());
                                bundle.putString("anh", dienThoai.getLinkAnh());
                                bundle.putFloat("tim", dienThoai.getSoLike());
                                bundle.putInt("daban", dienThoai.getDaBan());
                                bundle.putString("keydt",dienThoai.getId());
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = fmgr.beginTransaction();
                                ft.replace(R.id.content_frame, fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });
                        holder.tendt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fragment = new ChiTietDienThoaiFragment();
                                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                Bundle bundle = new Bundle();
                                bundle.putString("name", dienThoai.getTen());
                                bundle.putInt("gia", dienThoai.getGiaTien());
                                bundle.putString("chitiet", dienThoai.getChiTiet());
                                bundle.putString("anh", dienThoai.getLinkAnh());
                                bundle.putFloat("tim", dienThoai.getSoLike());
                                bundle.putInt("daban", dienThoai.getDaBan());
                                bundle.putString("keydt",dienThoai.getId());
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = fmgr.beginTransaction();
                                ft.replace(R.id.content_frame, fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public DienThoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai, parent, false);
                        DienThoaiViewHolder holder = new DienThoaiViewHolder(view);
                        holder.tendt = view.findViewById(R.id.tendt);
                        holder.giadt = view.findViewById(R.id.giadt);
                        holder.ct = view.findViewById(R.id.chitietdt);
                        holder.anhdt = view.findViewById(R.id.anhdt);
                        holder.solike = view.findViewById(R.id.sotimdt);
                        holder.tim = view.findViewById(R.id.timdt);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dien_thoai, container, false);
    }

    public static class DienThoaiViewHolder extends RecyclerView.ViewHolder{
        TextView tendt,giadt,ct,solike;
        ImageView anhdt,tim;
        public DienThoaiViewHolder(@NonNull View view) {
            super(view);
            tendt =view.findViewById(R.id.tendt);
            ct =view.findViewById(R.id.chitietdt);
            giadt = view.findViewById(R.id.giadt);
            anhdt = view.findViewById(R.id.anhdt);
            solike = view.findViewById(R.id.sotimqldt);
            tim = view.findViewById(R.id.timdt);

        }
    }
}