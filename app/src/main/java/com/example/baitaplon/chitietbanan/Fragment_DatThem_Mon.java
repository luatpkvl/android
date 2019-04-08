package com.example.baitaplon.chitietbanan;

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

import com.example.baitaplon.Interface.AttackFragment;
import com.example.baitaplon.Interface.FragmentListener;
import com.example.baitaplon.R;
import com.example.baitaplon.adapter.DatThemAdapter;
import com.example.baitaplon.struct_class.MonAn;

import java.util.ArrayList;
import java.util.List;

public class Fragment_DatThem_Mon extends Fragment {
    RecyclerView rv_chitiet_datthem;
    List<MonAn> monAnList = new ArrayList<>();
    private SQLiteDatabase database;
    DatThemAdapter adapter;

    FragmentListener fragmentListener;
    public void setFragmentListeners(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chitietban_dsmon,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_chitiet_datthem = view.findViewById(R.id.rvdsmon_toadd);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv_chitiet_datthem.setLayoutManager(layoutManager);
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        adapter = new DatThemAdapter(getActivity(),monAnList,id);
        adapter.setFragmentListener(new FragmentListener() {
            @Override
            public void onAction(String action, int data) {
                fragmentListener.onAction("set_Staus",data);
            }
        });
        rv_chitiet_datthem.setAdapter(adapter);
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
