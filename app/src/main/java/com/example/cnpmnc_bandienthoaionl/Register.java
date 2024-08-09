package com.example.cnpmnc_bandienthoaionl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnpmnc_bandienthoaionl.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextView HovaTen,Email,Matkhau,reMatKhau;
    DatabaseReference databaseReference;
    Button btnRg;
    FirebaseAuth mAuth;
    User item;
    String PHAN_QUYEN = "KH";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        HovaTen =findViewById(R.id.rgHovaTen);
        Email =findViewById(R.id.rgemail);
        Matkhau =findViewById(R.id.rgmk);
        reMatKhau =findViewById(R.id.rgremk);
        btnRg = findViewById(R.id.rgregister);
        btnRg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()>0){
                    DangKy();
                }

            }
        });
    }

    private void DangKy() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String tk = Email.getText().toString();
        String mk = Matkhau.getText().toString();
        String hoten = HovaTen.getText().toString();
        String quyen = PHAN_QUYEN;
        User user = new User(hoten, tk, mk, quyen,"","");
        if(validate()>0){
            databaseReference.child("User").child(user.hoTen).setValue(user);
            mAuth.createUserWithEmailAndPassword(tk, mk)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Đăng Ký Thành Công, Vui Lòng Đăng Nhập", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this, Login.class));
                            }
                        }
                    });
        }else{
            Toast.makeText(Register.this, "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
        }
    }
    public int validate(){
        int check = 1 ;
        if (Email.getText().length() == 0 || Matkhau.getText().length() == 0 || HovaTen.getText().length() == 0 || reMatKhau.getText().length() == 0){
            Toast.makeText(Register.this,"Bạn phải nhập đầy đủ thông tin !",Toast.LENGTH_LONG).show();
            check = -1;
        }
        String pass = Matkhau.getText().toString();
        String rePass = reMatKhau.getText().toString();
        if (!pass.equals(rePass)) {
            Toast.makeText(Register.this, "Mật khẩu không trùng khớp", Toast.LENGTH_LONG).show();
            check = -1;
        }
        return check;
    }
    private void UploadData() {
        String name = HovaTen.getText().toString().trim();
        String Emailc = Email.getText().toString().trim();
        String password = Matkhau.getText().toString().trim();
        item = new User();
        item.hoTen = name;
        item.phanQuyen = PHAN_QUYEN;
        item.taiKhoan = Emailc;
        item.matKhau = password;
        FirebaseDatabase.getInstance().getReference("User").child(item.hoTen)
                .setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}