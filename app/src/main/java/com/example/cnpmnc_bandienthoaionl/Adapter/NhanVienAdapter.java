package com.example.cnpmnc_bandienthoaionl.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cnpmnc_bandienthoaionl.Admin.NhanVienFragment;
import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.Register;
import com.example.cnpmnc_bandienthoaionl.entity.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<User> userlist;
    NhanVienFragment fragment;
    public NhanVienAdapter(Context context,NhanVienFragment fragment, List<User> userlist) {
        this.context = context;
        this.userlist = userlist;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhanvien,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvHoTen.setText("Họ và Tên: "+userlist.get(position).getHoTen());
        holder.TvEmail.setText("Tài Khoản: "+userlist.get(position).getTaiKhoan());
        holder.TvMatKhau.setText("*******");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, dialog.getClass());
//                intent.putExtra("Name",userlist.get(holder.getAdapterPosition()).getHoTen());
//                intent.putExtra("Email",userlist.get(holder.getAdapterPosition()).getTaiKhoan());
//                intent.putExtra("Password",userlist.get(holder.getAdapterPosition()).getMatKhau());
//                context.startActivity(intent);

            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String Hoten = userlist.get(holder.getAdapterPosition()).getHoTen();
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Xóa Chủ Sân");
                builder.setMessage("Bạn có muốn xóa Không ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int clickedPosition = holder.getAdapterPosition();
                        // Xử lý sự kiện long click trên mục tiêu cụ thể (ví dụ: xóa)
                        xoaItem(clickedPosition);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("User");
                        reference.child(String.valueOf(Hoten)).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(context, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton(
                        "Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );
                AlertDialog alertDialog = builder.create();
                builder.show();
                return true;
            }
        });
    }

    private void xoaItem(int clickedPosition) {
        if (clickedPosition >= 0 && clickedPosition < userlist.size()) {
            userlist.remove(clickedPosition); // Xóa mục tiêu cụ thể khỏi danh sách
            notifyItemRemoved(clickedPosition); // Thông báo cho adapter là mục tiêu đã bị xóa
        }
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView TvHoTen,TvEmail,TvMatKhau;
    CardView cardView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        TvHoTen = itemView.findViewById(R.id.tvTenNV);
        TvEmail = itemView.findViewById(R.id.tvEmailNV);
        TvMatKhau = itemView.findViewById(R.id.tvMatKhauNV);
        cardView = itemView.findViewById(R.id.tvCardNV);
    }
}