package com.example.cnpmnc_bandienthoaionl.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private static User instance;
    private List<User> userList;
    public String hoTen,taiKhoan,matKhau,phanQuyen,diaChi,soDienThoai;
    public User() {
    }
    public static User getInstance(){
        if(instance ==null){
            synchronized (User.class){
                if(instance == null){
                    instance = new User();
                }
            }
        }
        return instance;
    }
    public List<User> getUserList() {
        return userList;
    }
    public User(String hoTen, String taiKhoan, String matKhau, String phanQuyen, String diaChi, String soDienThoai) {
        this.hoTen = hoTen;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.phanQuyen = phanQuyen;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("sdt",soDienThoai);
        result.put("hoTen",hoTen);
        result.put("diaChi",diaChi);
//        result.put("anh",a);
        return result;
    }
}
