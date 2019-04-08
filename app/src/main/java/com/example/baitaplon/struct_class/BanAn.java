package com.example.baitaplon.struct_class;

public class BanAn{
    private int id;
    private int tenban;
    private int trangthai;

    public BanAn(int id, int tenban, int trangthai) {
        this.id = id;
        this.tenban = tenban;
        this.trangthai = trangthai;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenban() {
        return tenban;
    }

    public void setTenban(int tenban) {
        this.tenban = tenban;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
