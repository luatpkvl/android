package com.example.baitaplon.thongke;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.baitaplon.R;

public class Fragment_thongke_doanhthu extends Fragment {
    TextView tv_doanhthu;
    TableLayout tbl_doanhthu;
    private SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongke_doanhthu,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_doanhthu = view.findViewById(R.id.tv_doanhthu);
        tbl_doanhthu = view.findViewById(R.id.tbl_doanhthu);
        TableRow rowHeader = new TableRow(getActivity());
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"Tên Món","Số lượng","Thành Tiền"};
        for (String c:headerText){
            TextView tv = new TextView(getActivity());
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5,5,5,5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tbl_doanhthu.addView(rowHeader);
        load_data();

    }

    private void load_data() {
        database = getActivity().openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
        String sql = "SELECT thongke.*,monan.tenmon,monan.gia,SUM(thongke.soluong) FROM thongke INNER JOIN monan ON monan.id = thongke.id_mon GROUP BY thongke.id_mon";
        Cursor cursor = database.rawQuery(sql,null);
        cursor.moveToFirst();
        int tien =0;
        int thanhtien =0;
        int sum_soluong =0;
        while (!cursor.isAfterLast()){
            int soluong = cursor.getInt(6);
            int gia = cursor.getInt(5);
            sum_soluong += soluong;
            String tenmon = cursor.getString(4);
            tien += soluong*gia;
            thanhtien = soluong*gia;
            cursor.moveToNext();
            TableRow row = new TableRow(getActivity());
//            row.setBackgroundColor(Color.parseColor("#FFFFFF"));
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
            String[] Content = {tenmon,soluong+"",thanhtien+""};
            for (String c:Content){
                TextView textView = new TextView(getActivity());
                textView.setTextSize(18);
                textView.setGravity(Gravity.CENTER);
                textView.setText(c);
                row.addView(textView);
            }
            tbl_doanhthu.addView(row);
        }
        TableRow footerRow = new TableRow(getActivity());
        footerRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        String[] footerContent = {"Tổng",sum_soluong+"",tien+" VNĐ"};
        for(String c:footerContent){
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setText(c);
            footerRow.addView(textView);
        }
        tbl_doanhthu.addView(footerRow);
        tv_doanhthu.setText("Doanh thu: "+tien+" VNĐ");

    }
}
