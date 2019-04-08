package com.example.baitaplon.struct_class;

public class MonAn{
    private int id;
    private String tenmon;
    private int gia;

    public MonAn(int id, String tenmon, int gia) {
        this.id = id;
        this.tenmon = tenmon;
        this.gia = gia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}
