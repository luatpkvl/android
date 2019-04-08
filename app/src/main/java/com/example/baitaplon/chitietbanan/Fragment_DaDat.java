package com.example.baitaplon.chitietbanan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.Interface.OnItemRVListener;
import com.example.baitaplon.R;
import com.example.baitaplon.Interface.FragmentListener;
import com.example.baitaplon.struct_class.DatBan;
import com.example.baitaplon.adapter.DatBanAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment_DaDat extends Fragment {
    private SQLiteDatabase database;
    private RecyclerView recyclerView;
    private List<DatBan> datBanList = new ArrayList<>();
    private TextView tvstatus;
    DatBanAdapter adapter;

    private FragmentListener fragmentListener;

    private int tableId = 0;

    public void setFragmentListener(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chitietban_dadat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.rvchitietban);
        Bundle bundle = getArguments();
        tvstatus = view.findViewById(R.id.tvstatus_ban);
        tableId = bundle.getInt("id");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DatBanAdapter(getActivity(), datBanList);
        adapter.setOnItemRVListener(new OnItemRVListener() {
            @Override
            public void onClick(int position, String action) {
                showPopupConfirmDelete(position);
            }
        });
        recyclerView.setAdapter(adapter);
        loadData();

    }
    public void reload_thanhtoan(){
        tvstatus.setVisibility(View.VISIBLE);
        datBanList.clear();
        adapter.notifyDataSetChanged();
    }

    private void showPopupConfirmDelete(int pos) {
        final DatBan datBan = datBanList.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa món ăn khỏi bàn");
        builder.setMessage("Bạn có chắc chắn xóa chứ?");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database = getContext().openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE, null);
                String sql = "DELETE FROM dathang WHERE id=" + datBan.getId();
                database.execSQL(sql);
                Toast.makeText(getContext(), "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                loadData();
            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        database = getActivity().openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE, null);
        datBanList.clear();
        String query = "SELECT * FROM dathang WHERE id_ban=" + tableId;
        Cursor cursor1 = database.rawQuery(query, null);
        cursor1.moveToFirst();
        if (cursor1.getCount() == 0) {
            tvstatus.setVisibility(View.VISIBLE);
            if(fragmentListener!=null){
                fragmentListener.onAction("send_status",0);
            }
        }else{
            if(fragmentListener!= null){
                fragmentListener.onAction("send_status",1);
            }
            tvstatus.setVisibility(View.GONE);
            String query1 = "SELECT monan.gia, monan.tenmon, dathang.* FROM dathang INNER JOIN monan ON monan.id = dathang.id_mon where dathang.id_ban =" + tableId;
            Cursor cursor = database.rawQuery(query1, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id_don = cursor.getInt(2);
                String tenmonan = cursor.getString(1);
                int gia = cursor.getInt(0);
                int soluong = cursor.getInt(5);
                DatBan datBan = new DatBan(id_don, tenmonan, gia, soluong);
                datBanList.add(datBan);
                cursor.moveToNext();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void notifyData() {
        if(fragmentListener!= null){
            fragmentListener.onAction("CHANGE_ACTIVITY",tableId);
        }
    }
}
