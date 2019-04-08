package com.example.baitaplon.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.R;
import com.example.baitaplon.struct_class.MonAn;

import java.util.List;

public class ListMonAnAdapter extends RecyclerView.Adapter<ListMonAnAdapter.ViewHolder>{
    private SQLiteDatabase database;
    private Context context;
    private List<MonAn> monAnList;
    public ListMonAnAdapter(Context context, List<MonAn> monAnList) {
        this.context = context;
        this.monAnList = monAnList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_monan,viewGroup,false);
        database = context.openOrCreateDatabase("quanlynhahang.db",Context.MODE_PRIVATE,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final MonAn monAn = monAnList.get(position);
        final int id_monan = monAn.getId();
        viewHolder.tvnamemonan.setText(monAn.getTenmon());
        viewHolder.tvgia.setText(monAn.getGia()+" VNĐ");
        viewHolder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final Dialog dialog = new Dialog(context);
                dialog.setTitle("Sửa món ăn");
                dialog.setContentView(R.layout.dialog_edit_monan);
                final EditText edt_tenmonan = dialog.findViewById(R.id.edttenmonan_sua);
                final EditText edt_gia = dialog.findViewById(R.id.edtgiamonan_sua);
                edt_tenmonan.setText(monAn.getTenmon());
                edt_gia.setText(monAn.getGia()+"");
                dialog.show();
                dialog.findViewById(R.id.btnHuySua).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnSuaMonAn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edt_tenmonan.getText().toString().length()==0 || edt_gia.getText().toString().length()==0){
                            Toast.makeText(context,"Bạn phải nhập đầy đủ!",Toast.LENGTH_SHORT).show();
                        }else{
                            String sql = "UPDATE monan SET tenmon = '"+edt_tenmonan.getText()+"' , gia="+edt_gia.getText()+" WHERE id="+id_monan;
                            database.execSQL(sql);
                            Toast.makeText(context,"Đã sửa thành công!",Toast.LENGTH_SHORT).show();
                            load_data();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        viewHolder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa món ăn");
                builder.setMessage("Bạn có chắc chắn xóa món ăn?");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String query = "DELETE FROM monan where id ="+id_monan;
                        database.execSQL(query);
                        load_data();
                        Toast.makeText(context,"Đã xóa thành công! ",Toast.LENGTH_SHORT).show();
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
        });
    }

    private void load_data() {
        database = context.openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
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
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return monAnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvnamemonan;
        TextView tvgia;
        ImageView img_edit;
        ImageView img_del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnamemonan = itemView.findViewById(R.id.tvnamemonan);
            tvgia = itemView.findViewById(R.id.tvgiamonan_item);
            img_edit = itemView.findViewById(R.id.btn_edit_monan);
            img_del = itemView.findViewById(R.id.btn_del_monan);
        }
    }
}
