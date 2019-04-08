package com.example.baitaplon.struct_class;

public class DatBan {
    private int id;
    private String ten_mon;
    private int gia;
    private int soluong;

    public DatBan(int id, String ten_mon, int gia, int soluong) {
        this.id = id;
        this.ten_mon = ten_mon;
        this.gia = gia;
        this.soluong = soluong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_mon() {
        return ten_mon;
    }

    public void setTen_mon(String ten_mon) {
        this.ten_mon = ten_mon;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
