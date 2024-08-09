package com.example.cnpmnc_bandienthoaionl.QuanLy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cnpmnc_bandienthoaionl.Adapter.QuanLyDonHangAdapter;
import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.entity.ThongTinDonHang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLyDonHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLyDonHangFragment extends Fragment {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    ArrayList<ThongTinDonHang> dsdh = new ArrayList<ThongTinDonHang>();
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;
    DatabaseReference databaseReference;
    TextView tao;
    EditText nhaptendh,nhapgiadh,nhaptrangthai;
    Button regdt, huy,sua,xoa;
    RecyclerView recyclerView;
    QuanLyDonHangAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuanLyDonHangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuanLyDonHangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuanLyDonHangFragment newInstance(String param1, String param2) {
        QuanLyDonHangFragment fragment = new QuanLyDonHangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvqldh);

        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        dsdh.clear();
        getDuLieu();
        adapter = new QuanLyDonHangAdapter(getContext(),dsdh);
        recyclerView.setAdapter(adapter);
    }

    private void getDuLieu() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ThongTinDonHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren() ){
                    for (DataSnapshot snapshot1:dataSnapshot.getChildren()){
                        ThongTinDonHang donHang = snapshot1.getValue(ThongTinDonHang.class);
                        dsdh.add(donHang);
                    }
                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_don_hang, container, false);
    }
}