package com.example.cnpmnc_bandienthoaionl;

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

import com.example.cnpmnc_bandienthoaionl.Adapter.HangDienThoaiAdapter;
import com.example.cnpmnc_bandienthoaionl.entity.DienThoai;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HangDienThoaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HangDienThoaiFragment extends Fragment {
    HangDienThoaiAdapter adapter;
    ArrayList<DienThoai> list = new ArrayList<DienThoai>();
    DatabaseReference databaseReference;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HangDienThoaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HangDienThoaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HangDienThoaiFragment newInstance(String param1, String param2) {
        HangDienThoaiFragment fragment = new HangDienThoaiFragment();
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
        RecyclerView recyclerView = view.findViewById(R.id.rechangdt);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Bundle bundle = this.getArguments();
        String key = bundle.getString("keyword");
        getList(key);
        adapter = new HangDienThoaiAdapter(getContext(), list, new HangDienThoaiAdapter.chuyen() {
            @Override
            public void ChuyenFragment(DienThoai dienThoai) {
                Fragment fragment = new ChiTietDienThoaiFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("name", dienThoai.getTen());
                bundle.putInt("gia", dienThoai.getGiaTien());
                bundle.putString("chitiet", dienThoai.getChiTiet());
                bundle.putString("anh", dienThoai.getLinkAnh());
                bundle.putString("keydt",dienThoai.getId());
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        recyclerView.setAdapter(adapter);
    }
    public void getList(String keyword){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DienThoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DienThoai dienThoai = dataSnapshot.getValue(DienThoai.class);
                    if(dienThoai.getTen().toLowerCase().contains(keyword.toLowerCase())){
                        list.add(dienThoai);
                    }
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
        return inflater.inflate(R.layout.fragment_hang_dien_thoai, container, false);
    }
}