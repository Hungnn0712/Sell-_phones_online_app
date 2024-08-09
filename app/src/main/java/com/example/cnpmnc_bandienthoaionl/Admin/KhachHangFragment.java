package com.example.cnpmnc_bandienthoaionl.Admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cnpmnc_bandienthoaionl.Adapter.KhachHangAdapter;
import com.example.cnpmnc_bandienthoaionl.Adapter.NhanVienAdapter;
import com.example.cnpmnc_bandienthoaionl.Login;
import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.Register;
import com.example.cnpmnc_bandienthoaionl.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KhachHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KhachHangFragment extends Fragment {
    RecyclerView recyclerView;
    List<User> userList;
    KhachHangAdapter adapter;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    ImageView imgAdd;
    Dialog dialog;
    Button btn_save;
    User item;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String phanquyen;
    EditText ed_ten, ed_email, ed_matkhau,ed_rematkhau;
    public static final String PHAN_QUYEN = "KH";

    public KhachHangFragment() {
        // Required empty public constructor
    }

    public static KhachHangFragment newInstance(String param1, String param2) {
        KhachHangFragment fragment = new KhachHangFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);
        imgAdd = view.findViewById(R.id.imgThemKH);
        recyclerView = view.findViewById(R.id.recKhachHang);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        userList = new ArrayList<>();
        adapter = new KhachHangAdapter(getContext(),this,userList);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        String phanquyen = itemSnapshot.child("phanQuyen").getValue(String.class);
                        Log.d("MyApp", "Giá trị phanquyen: " + phanquyen);
                        if ("KH".equals(phanquyen)) {
                            User user = itemSnapshot.getValue(User.class);
                            userList.add(user);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(0);
            }
        });
        return view;
    }

    private void openDialog(int i) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialogregister);
        Window window = dialog.getWindow();
        //window.getAttributes().windowAnimations = R.style.DialogAnimation;
        btn_save = dialog.findViewById(R.id.bt_rgsave);
        ed_ten = dialog.findViewById(R.id.rg_ten);
        ed_email = dialog.findViewById(R.id.rg_email);
        ed_matkhau = dialog.findViewById(R.id.rg_mk);
        ed_rematkhau = dialog.findViewById(R.id.rg_remk);
        if (i != 0){
            ed_email.setText(item.taiKhoan);
            ed_email.setEnabled(false);
            ed_ten.setText(item.hoTen);
            ed_matkhau.setText(item.matKhau);
            ed_rematkhau.setText(item.matKhau);
            btn_save.setText("Cập Nhật");
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_ten.getText().toString().trim();
                String Email = ed_email.getText().toString().trim();
                String password = ed_matkhau.getText().toString().trim();
                item = new User();
                item.hoTen = name;
                item.phanQuyen = PHAN_QUYEN;
                item.taiKhoan = Email;
                item.matKhau = password;

                if(validate()>0){
                    if(i==0){
//                        UploadData();
                        UploadData1();
                        Toast.makeText(getContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        UploadData1();
                        Toast.makeText(getContext(), "Cập nhật tài khoản thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.show();
    }

    private void UploadData() {
        String name = ed_ten.getText().toString().trim();
        String Email = ed_email.getText().toString().trim();
        String password = ed_matkhau.getText().toString().trim();
        item = new User();
        item.hoTen = name;
        item.phanQuyen = PHAN_QUYEN;
        item.taiKhoan = Email;
        item.matKhau = password;
        FirebaseDatabase.getInstance().getReference("User").child(item.hoTen);
        if(validate()>0){

        }
    }
    private void UploadData1(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String tk = ed_email.getText().toString();
        String mk = ed_matkhau.getText().toString();
        String hoten = ed_ten.getText().toString();
        String quyen = PHAN_QUYEN;
        User user = new User(hoten, tk, mk, quyen,"","");
        if(validate()>0){
            databaseReference.child("User").child(user.hoTen).setValue(user);
            mAuth.createUserWithEmailAndPassword(tk, mk)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Đăng Ký Thành Công, Vui Lòng Đăng Nhập", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private int validate() {
        int check = 1 ;
        if (ed_ten.getText().length() == 0 || ed_email.getText().length() == 0|| ed_matkhau.getText().length() == 0 || ed_rematkhau.getText().length() == 0){
            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin !",Toast.LENGTH_LONG).show();
            check = -1;
        }
//        else{
//            String sdt = ed_phone_number.getText().toString();
//            String regexSDT = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
//            if (sdt.matches(regexSDT) == false){
//                Toast.makeText(getContext(),"Số điện thoại không hợp lệ",Toast.LENGTH_LONG).show();
//                check = -1;
//            }
//        }
        String pass = ed_matkhau.getText().toString();
        String rePass = ed_rematkhau.getText().toString();
        if (!pass.equals(rePass)) {
            Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_LONG).show();
            check = -1;
        }
        return check;
    }
}