package com.example.baitaplon.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.R;
import com.example.baitaplon.SessionClass;
import com.example.baitaplon.struct_class.BanAn;
import com.example.baitaplon.chitietbanan.Main_Each_BanAn_ChiTiet;

import java.util.List;

public class ListBanAnAdapter extends RecyclerView.Adapter<ListBanAnAdapter.ViewHolder> {
    private Context context;
    private List<BanAn> banAnList;
    private SQLiteDatabase database;

    public ListBanAnAdapter(Context context, List<BanAn> banAnList) {
        this.context = context;
        this.banAnList = banAnList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banan,viewGroup,false);
        database = context.openOrCreateDatabase("quanlynhahang.db",Context.MODE_PRIVATE,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final BanAn banAn = banAnList.get(position);
        final int id = banAn.getId();
        viewHolder.tvtenban.setText(banAn.getTenban()+"");
        if(banAn.getTrangthai()==0){
            viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#A0EE6F"));
        }else{
            viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#FA6574"));
        }
        SessionClass sessionClass = new SessionClass(context);
        int level = Integer.parseInt(sessionClass.detailUser().get("LEVEL"));
        if(level!=1){
            viewHolder.btn_del.setVisibility(View.GONE);
        }
        viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa bàn ăn");
                builder.setMessage("Bạn chắc chắn xóa bàn ăn?");
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql_check = "SELECT * FROM dathang where id_ban="+id;
                        Cursor cursor = database.rawQuery(sql_check,null);
                        cursor.moveToFirst();
                        if(cursor.getCount()==0){
                            String sql = "DELETE FROM banan WHERE id="+id;
                            database.execSQL(sql);
                            load_data();
                        }else{
                            Toast.makeText(context,"Bàn đang có người ngồi nên không thể xóa!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void load_data() {
        String sql = "SELECT * FROM banan";
        Cursor cursor = database.rawQuery(sql,null);
        banAnList.clear();
        cursor.moveToFirst();
        int number = 0;
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            number ++;
            int trangthai = 0;
            String query = "SELECT * FROM dathang WHERE id_ban="+id;
            Cursor cursor1 = database.rawQuery(query,null);
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
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return banAnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtenban;
        TextView trangthai;
        ImageView btn_del;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtenban = itemView.findViewById(R.id.tvtenban);
            btn_del = itemView.findViewById(R.id.btn_del_ban);
            relativeLayout = itemView.findViewById(R.id.background_ban);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BanAn banAn = banAnList.get(getPosition());
                    Intent intent = new Intent(context, Main_Each_BanAn_ChiTiet.class);
                    intent.putExtra("tenban",getPosition());
                    intent.putExtra("id",banAn.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}