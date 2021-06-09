package com.example.thuctaptotnghiep.NhanVien.ObjectNhanVien;

import java.io.Serializable;

public class ThanhVienNV implements Serializable {
    int id;
    String ten;
    String sdt;
    String photo;
    String email;
    String diachi;
    String matkhau;
    int daxoa;

    public ThanhVienNV(int id, String ten, String sdt, String photo, String email, String diachi, String matkhau, int daxoa) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.photo = photo;
        this.email = email;
        this.diachi = diachi;
        this.matkhau = matkhau;
        this.daxoa = daxoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getDaxoa() {
        return daxoa;
    }

    public void setDaxoa(int daxoa) {
        this.daxoa = daxoa;
    }
}
