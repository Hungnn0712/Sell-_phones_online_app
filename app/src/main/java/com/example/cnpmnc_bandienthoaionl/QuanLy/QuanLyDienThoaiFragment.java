package com.example.cnpmnc_bandienthoaionl.QuanLy;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnpmnc_bandienthoaionl.R;
import com.example.cnpmnc_bandienthoaionl.entity.DienThoai;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLyDienThoaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLyDienThoaiFragment extends Fragment {

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DienThoai dienThoai;
    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextView tao;
    EditText  nhapiddt,nhaptendt,nhapgiadt,nhapchitiet;
    Button regdt, huy,sua,xoa;
    ImageView themanh;
    RecyclerView recyclerView;
    QuanLyDienThoaiFragment adapter1;
    private Uri imageUri;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuanLyDienThoaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuanLyDienThoaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuanLyDienThoaiFragment newInstance(String param1, String param2) {
        QuanLyDienThoaiFragment fragment = new QuanLyDienThoaiFragment();
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
        dsls.clear();

        recyclerView = view.findViewById(R.id.rvqldt);
        sua = view.findViewById(R.id.suadt);
        xoa = view.findViewById(R.id.xoadt);
        FloatingActionButton button = view.findViewById(R.id.addqldt);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogAdd();
            }
        });
    }

    private void openDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = ((Activity)getActivity()).getLayoutInflater();
        View v1 = layoutInflater.inflate(R.layout.dialog_adddienthoai,null);
        builder.setView(v1);
        AlertDialog dialog = builder.create();
        dialog.show();
        //khai bao
        tao = v1.findViewById(R.id.tvadd);
        nhaptendt = v1.findViewById(R.id.nhaptendt);
        nhapgiadt = v1.findViewById(R.id.nhapgiadt);
        nhapchitiet = v1.findViewById(R.id.nhapctdt);
        regdt = v1.findViewById(R.id.adddt);
        themanh = v1.findViewById(R.id.themanh);
        huy = v1.findViewById(R.id.huyadddt);

        themanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                Intent pho=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent chosser=Intent.createChooser(pick, "Lựa Chọn");
                chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{pho});
                startActivityForResult(chosser, 999);
            }
        });
        //xử lý click
        regdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("DienThoai");
                String uid = databaseReference.push().getKey();
                String tendt = nhaptendt.getText().toString();
                int giadt = Integer.parseInt(nhapgiadt.getText().toString());
                String chitiet = nhapchitiet.getText().toString();
                byte[] anh=ImageView_To_Byte(themanh);
                String chuoianh = Base64.getEncoder().encodeToString(anh);
                int DaBan = 0;
                float SoLike = 0;
                DienThoai dienThoai = new DienThoai(uid,tendt,chitiet,giadt,chuoianh,DaBan,SoLike);

                databaseReference.child(uid).setValue(dienThoai);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(getContext(), "Thêm Điện Thoại Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onStart() {
        FirebaseRecyclerOptions<DienThoai> options =
                new FirebaseRecyclerOptions.Builder<DienThoai>().
                        setQuery(FirebaseDatabase.getInstance().getReference().child("DienThoai")
                        ,DienThoai.class).build();
        FirebaseRecyclerAdapter<DienThoai, QuanLyDienThoaiFragment.QLDienThoaiViewHolder> adapter =
                new FirebaseRecyclerAdapter<DienThoai, QLDienThoaiViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull QLDienThoaiViewHolder holder, @SuppressLint("RecyclerView") int i, @NonNull DienThoai dienThoai) {
                        holder.tendt.setText(""+ dienThoai.getTen()) ;
                        holder.giadt.setText(""+dienThoai.getGiaTien());
                        holder.ct.setText(""+dienThoai.getChiTiet());
                        holder.solike.setText(""+dienThoai.getSoLike());
//                        byte[] manghinh = Base64.getDecoder().decode(dienThoai.getLinkAnh());
//                        Bitmap bm = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
//                        holder.anhdt.setImageBitmap(bm);
                        Picasso.get().load(dienThoai.getLinkAnh()).into(holder.anhdt);
                        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()==true){
                                    sua.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            LayoutInflater layoutInflater = ((Activity)getContext()).getLayoutInflater();
                                            View v1 = layoutInflater.inflate(R.layout.dialog_updatedienthoai,null);
                                            builder.setView(v1);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                            //Anhs xạ
                                            EditText nhapten,nhapgia,nhapct,nhapdaban,nhaplike;
                                            ImageView nhapanh;
                                            Button sua,huy;
                                            nhapten = v1.findViewById(R.id.nhaptendt);
                                            nhapgia = v1.findViewById(R.id.nhapgiamoi);
                                            nhapct = v1.findViewById(R.id.nhapchitiet);
                                            nhapdaban = v1.findViewById(R.id.nhapdaban);
                                            nhaplike = v1.findViewById(R.id.nhapsolike);
                                            nhapanh = v1.findViewById(R.id.nhapanh);
                                            sua = v1.findViewById(R.id.capnhatdt);
                                            huy = v1.findViewById(R.id.huydt);
                                            nhapten.setText(dienThoai.getTen());
                                            nhapgia.setText(""+dienThoai.getGiaTien());
                                            nhapct.setText(""+dienThoai.getChiTiet());
                                            nhapdaban.setText(""+dienThoai.getDaBan());
                                            nhaplike.setText(""+dienThoai.getSoLike());
                                            byte[] manghinh = Base64.getDecoder().decode(dienThoai.getLinkAnh());
                                            Bitmap bm = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                                            nhapanh.setImageBitmap(bm);

                                            nhapanh.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
                                                    pick.setType("image/*");
                                                    Intent pho=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                    Intent chosser=Intent.createChooser(pick, "Lựa Chọn");
                                                    chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{pho});
                                                    startActivityForResult(chosser, 999);
                                                }
                                            });
                                            sua.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    String ten = nhapten.getText().toString();
                                                    int gia = Integer.parseInt(nhapgia.getText().toString());
                                                    String ct = nhapct.getText().toString();
                                                    int daban = Integer.parseInt(nhapdaban.getText().toString());
                                                    float like = Float.parseFloat(nhaplike.getText().toString());
                                                    byte[] anh=ImageView_To_Byte(nhapanh);
                                                    String suaanh = Base64.getEncoder().encodeToString(anh);
                                                    DienThoai thoai = new DienThoai(ten,ct,gia,suaanh,daban,like);
                                                    databaseReference = FirebaseDatabase.getInstance().getReference();
                                                    databaseReference.child("DienThoai").child(getRef(i).getKey()).updateChildren(thoai.toMap(), new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                            Toast.makeText(getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                        }
                                                    });

                                                }
                                            });
                                            huy.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                }
                                            });
                                        }
                                    });
                                    xoa.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            databaseReference = FirebaseDatabase.getInstance().getReference();
                                            databaseReference.child("DienThoai").child(getRef(i).getKey()).removeValue(new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                    Toast.makeText(getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public QLDienThoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qldienthoai, parent, false);
                        return new QLDienThoaiViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        super.onStart();
    }

    private byte[] ImageView_To_Byte(ImageView themanh) {

        BitmapDrawable drawable = (BitmapDrawable) themanh.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {

            if (data.getExtras() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                themanh.setImageBitmap(imageBitmap);
            } else {
                Uri uri = data.getData();
                themanh.setImageURI(uri);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_dien_thoai, container, false);
    }
    class QLDienThoaiViewHolder extends RecyclerView.ViewHolder{
        TextView tendt,giadt,ct,solike;
        ImageView anhdt,tim;
        CheckBox box;
        public QLDienThoaiViewHolder(@NonNull View view) {
            super(view);
            tendt =view.findViewById(R.id.tenqldt);
            ct =view.findViewById(R.id.chitietqldt);
            giadt = view.findViewById(R.id.giaqldt);
            anhdt = view.findViewById(R.id.anhqldt);
            solike = view.findViewById(R.id.sotimqldt);
            tim = view.findViewById(R.id.timdt);
            box = view.findViewById(R.id.checkqldt);
        }
    }
}