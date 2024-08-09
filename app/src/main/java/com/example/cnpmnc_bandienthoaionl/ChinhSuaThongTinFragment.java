package com.example.cnpmnc_bandienthoaionl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cnpmnc_bandienthoaionl.Fragment.TaiKhoanFragment;
import com.example.cnpmnc_bandienthoaionl.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChinhSuaThongTinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChinhSuaThongTinFragment extends Fragment {

    EditText emailmoi , sdtmoi,tenndmoi , diachimoi;
    Button suaa ,xoaa;
    ImageView themanh;
    User dangKy;
    DatabaseReference databaseReference;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChinhSuaThongTinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChinhSuaThongTinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChinhSuaThongTinFragment newInstance(String param1, String param2) {
        ChinhSuaThongTinFragment fragment = new ChinhSuaThongTinFragment();
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
        emailmoi =view.findViewById(R.id.doiemailmoi);
        sdtmoi = view.findViewById(R.id.doisdtmoi);
        tenndmoi = view.findViewById(R.id.doitennd);
        diachimoi = view.findViewById(R.id.doiiachi);
        suaa = view.findViewById(R.id.capnhatls);
        xoaa = view.findViewById(R.id.huyls);
        themanh = view.findViewById(R.id.udanhnd);
        Bundle bundle = this.getArguments();
        String email = bundle.getString("emailndne");
        String sdt = bundle.getString("sdtnd");
        String ten = bundle.getString("hotennd");
        String diachi = bundle.getString("diachind");
        String anh = bundle.getString("anhnd");
        String id = bundle.getString("idnd");
        String pass = bundle.getString("passnd");
        String quyen = bundle.getString("quyennd");
        emailmoi.setText(email);
        sdtmoi.setText(""+sdt);
        tenndmoi.setText(ten);
        diachimoi.setText(diachi);

        themanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                Intent pho=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent chosser=Intent.createChooser(pick, "Lựa Chọn");
                chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{pho});
                startActivityForResult(chosser, 999);
            }
        });

        suaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int sdtmoi = Integer.parseInt(dtmoi.getText().toString());
                String sdtmoi1 = sdtmoi.getText().toString();
                String tenmoi = tenndmoi.getText().toString();
                String diachimoine = diachimoi.getText().toString();
                byte[] anh1=ImageView_To_Byte(themanh);
                String anhmoi = Base64.getEncoder().encodeToString(anh1);
                String layemail = emailmoi.getText().toString();

                User user = new User(tenmoi,layemail,pass,quyen,diachimoine,sdtmoi1);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("User").child(tenmoi).setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(layemail);
                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new TaiKhoanFragment();
                        FragmentManager fmgr = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fmgr.beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });
            }
        });
        xoaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TaiKhoanFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
    public byte[] ImageView_To_Byte(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chinh_sua_thong_tin, container, false);
    }
}