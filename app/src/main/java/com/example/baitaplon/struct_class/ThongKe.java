package com.example.baitaplon.struct_class;

public class ThongKe {
    private int id_thongke;
    private int id_monan;
    private int soluong;
    private int thoigian;

    public ThongKe(int id_thongke, int id_monan, int soluong, int thoigian) {
        this.id_thongke = id_thongke;
        this.id_monan = id_monan;
        this.soluong = soluong;
        this.thoigian = thoigian;
    }

    public int getId_thongke() {
        return id_thongke;
    }

    public void setId_thongke(int id_thongke) {
        this.id_thongke = id_thongke;
    }

    public int getId_monan() {
        return id_monan;
    }

    public void setId_monan(int id_monan) {
        this.id_monan = id_monan;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getThoigian() {
        return thoigian;
    }

    public void setThoigian(int thoigian) {
        this.thoigian = thoigian;
    }
}
