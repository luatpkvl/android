package com.example.baitaplon.thongke;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baitaplon.R;

public class Fragment_thongke_monan extends Fragment {
    private SQLiteDatabase database;
    private TextView tv_dem_tongsl;
    private TextView max_soluong_monan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongke_monan,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_dem_tongsl = view.findViewById(R.id.tv_tongsomonan);
        max_soluong_monan = view.findViewById(R.id.tv_max_soluong_monan);
        load_data();
    }

    private void load_data() {
        database = getActivity().openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
        String Sql_monan = "SELECT * FROM monan";
        Cursor cursor = database.rawQuery(Sql_monan,null);
        if(cursor.getCount() == 0){
            tv_dem_tongsl.setText("Chưa có món nào được đặt");
            tv_dem_tongsl.setTextColor(Color.parseColor("#ff0000"));
        }else{
            cursor.moveToFirst();
            int soluong_mon = 0;
            while (!cursor.isAfterLast()){
                soluong_mon ++;
                cursor.moveToNext();
            }
            tv_dem_tongsl.setText("Tổng số món ăn của cửa hàng: "+soluong_mon);
        }
        String sql_top_mon = "SELECT monan.tenmon FROM thongke INNER JOIN monan ON thongke.id_mon = monan.id WHERE thongke.soluong = (SELECT MAX(thongke.soluong) FROM thongke)";
        Cursor cursor1 = database.rawQuery(sql_top_mon,null);
        if(cursor1.getCount()==0){
            max_soluong_monan.setText("Chưa có món nào được thống kê");
        }else{
            cursor1.moveToFirst();
            while (!cursor1.isAfterLast()){
                String top_monan = cursor1.getString(0);
                cursor1.moveToNext();
                max_soluong_monan.setText("Món được dùng nhiều nhất: "+top_monan);
            }
        }
    }
}
