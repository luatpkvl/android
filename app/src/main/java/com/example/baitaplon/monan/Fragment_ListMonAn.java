package com.example.baitaplon.monan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baitaplon.R;
import com.example.baitaplon.adapter.ListMonAnAdapter;
import com.example.baitaplon.struct_class.MonAn;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ListMonAn extends Fragment {
    private ListMonAnAdapter adapter;
    private List<MonAn> monAnList = new ArrayList<>();
    private SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_listmonan,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvlistmonan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListMonAnAdapter(getActivity(),monAnList);
        recyclerView.setAdapter(adapter);
        load_data();
    }

    private void load_data() {
        database = getActivity().openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
        monAnList.clear();
        String query = "SELECT * FROM monan";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String tenmonan = cursor.getString(1);
            int gia = cursor.getInt(2);
            MonAn monAn = new MonAn(id,tenmonan,gia);
            monAnList.add(monAn);
            cursor.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }
}
