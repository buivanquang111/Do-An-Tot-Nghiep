package com.example.thuctaptotnghiep.Admin.ObjectAdmin;

import java.io.Serializable;

public class SanPham implements Serializable {
    int id;
    String title;
    int price;
    String image;
    String description;
    int soluong;
    String thuonghieu;
    String loai;
    int daxoa;

    public SanPham(int id, String title, int price, String image, String description, int soluong, String thuonghieu, String loai, int daxoa) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.description = description;
        this.soluong = soluong;
        this.thuonghieu = thuonghieu;
        this.loai = loai;
        this.daxoa = daxoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getDaxoa() {
        return daxoa;
    }

    public void setDaxoa(int daxoa) {
        this.daxoa = daxoa;
    }
}
