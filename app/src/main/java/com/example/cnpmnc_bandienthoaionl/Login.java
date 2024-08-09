package com.example.cnpmnc_bandienthoaionl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextView email,matkhau;
    Button btnLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.lgemail);
        matkhau = findViewById(R.id.lgmatkhau);
        btnLogin = findViewById(R.id.lglogin);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getInstance().signOut();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhap();
            }
        });
    }

    private void DangNhap() {
        String tk = email.getText().toString();
        String mk = matkhau.getText().toString();
        SharedPreferences sharedPref = getSharedPreferences("ThongTin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", tk);
        editor.putString("pass", mk);
        editor.apply();

        mAuth.signInWithEmailAndPassword(tk, mk)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Thông Tin Không Đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}