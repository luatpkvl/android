package com.example.baitaplon.monan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplon.R;

public class Fragment_ThemMonAn extends Fragment {
    EditText edttenmon;
    EditText edtgia;
    private SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_themmonan,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edttenmon = view.findViewById(R.id.edttenmonan);
        edtgia = view.findViewById(R.id.edtgiamonan);
        Init_database();
        view.findViewById(R.id.btnThemMonAn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_monan(v);
            }
        });
    }

    private void Add_monan(View v){
        if(edttenmon.getText().toString().length() == 0 || edtgia.getText().toString().length()== 0){
            Toast.makeText(getActivity(),"Bạn phải nhập đầy đủ!",Toast.LENGTH_SHORT).show();
        }else{
            String tenmon = edttenmon.getText().toString();
            int gia = Integer.parseInt(edtgia.getText().toString());
            String sql = "INSERT INTO monan(tenmon,gia) VALUES('"+tenmon+"','"+gia+"')";
            database.execSQL(sql);
            edttenmon.setText("");
            edtgia.setText("");
            edttenmon.requestFocus();
            Toast.makeText(getActivity(),"Đã thêm thành công!",Toast.LENGTH_SHORT).show();
        }

    }
    private void Init_database() {
        database = getActivity().openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
        String sql = "CREATE TABLE IF NOT EXISTS monan(id integer primary key autoincrement,tenmon text,gia integer)";
        database.execSQL(sql);
    }
}
