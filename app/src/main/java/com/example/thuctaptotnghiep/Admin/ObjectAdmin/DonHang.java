package com.example.thuctaptotnghiep.Admin.ObjectAdmin;

public class DonHang {
    int id;
    String img;
    String tenSp;
    int slMua;
    String hoTen;
    String sdt;
    String diaChiNhan;
    double tongTien;
    String ngayDat;
    int xacNhan;
    int nvGiaoHang;
    int daNhan;

    public DonHang(int id, String img, String tenSp, int slMua, String hoTen, String sdt, String diaChiNhan, double tongTien, String ngayDat, int xacNhan, int nvGiaoHang, int daNhan) {
        this.id = id;
        this.img = img;
        this.tenSp = tenSp;
        this.slMua = slMua;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.diaChiNhan = diaChiNhan;
        this.tongTien = tongTien;
        this.ngayDat = ngayDat;
        this.xacNhan = xacNhan;
        this.nvGiaoHang = nvGiaoHang;
        this.daNhan = daNhan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getSlMua() {
        return slMua;
    }

    public void setSlMua(int slMua) {
        this.slMua = slMua;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public int getXacNhan() {
        return xacNhan;
    }

    public void setXacNhan(int xacNhan) {
        this.xacNhan = xacNhan;
    }

    public int getNvGiaoHang() {
        return nvGiaoHang;
    }

    public void setNvGiaoHang(int nvGiaoHang) {
        this.nvGiaoHang = nvGiaoHang;
    }

    public int getDaNhan() {
        return daNhan;
    }

    public void setDaNhan(int daNhan) {
        this.daNhan = daNhan;
    }
}
