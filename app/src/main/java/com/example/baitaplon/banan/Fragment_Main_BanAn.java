package com.example.baitaplon.banan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.baitaplon.R;
import com.example.baitaplon.SessionClass;
import com.example.baitaplon.adapter.ListBanAnAdapter;
import com.example.baitaplon.struct_class.BanAn;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class Fragment_Main_BanAn extends Fragment {
    private SQLiteDatabase database;
    private List<BanAn> banAnList = new ArrayList<>();
    private ListBanAnAdapter adapter;
    private Button btn_themban;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banan,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!= null){
            load_data();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_themban = view.findViewById(R.id.btnthembanan);
        database = getActivity().openOrCreateDatabase("quanlynhahang.db",Context.MODE_PRIVATE,null);
        RecyclerView recyclerView = view.findViewById(R.id.rvbanan);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListBanAnAdapter(getActivity(),banAnList);
        recyclerView.setAdapter(adapter);
        load_data();
        SessionClass sessionClass = new SessionClass(getContext());
        int level = Integer.parseInt(sessionClass.detailUser().get("LEVEL"));
        if(level != 1){
            btn_themban.setVisibility(View.GONE);
        }
       btn_themban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               showDialog(v);
               showDialert();
               adapter.notifyDataSetChanged();

            }
        });
    }

    private void showDialert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thêm Bàn Ăn");
        builder.setMessage("Bạn có muốn thêm bàn mới?");
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "INSERT INTO banan(tenban) VALUES ('1')";
                database.execSQL(query);
                load_data();
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void load_data() {
        String sql = "SELECT * FROM banan";
        Cursor cursor = database.rawQuery(sql,null);
        banAnList.clear();
        cursor.moveToFirst();
        int number = 0;
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            int trangthai = 0;
            String query = "SELECT * FROM dathang WHERE id_ban="+id;
            Cursor cursor1 = database.rawQuery(query,null);
            number ++;
            cursor1.moveToFirst();
            if(cursor1.getCount() == 0){
                trangthai = 0;

            }else{
                trangthai = 1;
            }
            BanAn banAn = new BanAn(id,number,trangthai);
            banAnList.add(banAn);
            cursor.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }

//    private void showDialog(View v) {
//        final Dialog dialog = new Dialog(getContext());
//        dialog.setTitle("THÊM BÀN ĂN");
//        dialog.setContentView(R.layout.dialog_thembanan);
//        dialog.setCancelable(false);
//        dialog.show();
//        dialog.findViewById(R.id.btnok_add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                    String query = "INSERT INTO banan(tenban) VALUES ('1')";
//                    database.execSQL(query);
//                    load_data();
//                    dialog.dismiss();
//            }
//        });
//        dialog.findViewById(R.id.btn_refuse).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//    }
}
