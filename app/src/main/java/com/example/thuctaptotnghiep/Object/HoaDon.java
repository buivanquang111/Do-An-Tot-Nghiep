package com.example.thuctaptotnghiep.Object;

public class HoaDon {
    int id;
    String tenproduct;
    String img;
    int gia;
    int slmua;
    int xacnhan;
    int danhan;
    int thanhtien;
    String diaChiNhan;
    String ngaydat;

    public HoaDon(int id, String tenproduct, String img, int gia, int slmua, int xacnhan, int danhan, int thanhtien, String diaChiNhan, String ngaydat) {
        this.id = id;
        this.tenproduct = tenproduct;
        this.img = img;
        this.gia = gia;
        this.slmua = slmua;
        this.xacnhan = xacnhan;
        this.danhan = danhan;
        this.thanhtien = thanhtien;
        this.diaChiNhan = diaChiNhan;
        this.ngaydat = ngaydat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenproduct() {
        return tenproduct;
    }

    public void setTenproduct(String tenproduct) {
        this.tenproduct = tenproduct;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSlmua() {
        return slmua;
    }

    public void setSlmua(int slmua) {
        this.slmua = slmua;
    }

    public int getXacnhan() {
        return xacnhan;
    }

    public void setXacnhan(int xacnhan) {
        this.xacnhan = xacnhan;
    }

    public int getDanhan() {
        return danhan;
    }

    public void setDanhan(int danhan) {
        this.danhan = danhan;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }
}
