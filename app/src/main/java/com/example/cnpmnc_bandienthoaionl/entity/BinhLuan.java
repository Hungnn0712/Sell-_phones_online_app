package com.example.cnpmnc_bandienthoaionl.entity;

public class BinhLuan {
    private String Id;
    private String Username;
    private String NoiDung;
    private String Ngay;
    private float Sao;

    public BinhLuan() {
    }

    public BinhLuan(String id, String username, String noiDung, String ngay, float sao) {
        Id = id;
        Username = username;
        NoiDung = noiDung;
        Ngay = ngay;
        Sao = sao;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public float getSao() {
        return Sao;
    }

    public void setSao(float sao) {
        Sao = sao;
    }
}
