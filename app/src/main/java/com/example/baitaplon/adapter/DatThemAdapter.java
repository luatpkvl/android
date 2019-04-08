package com.example.baitaplon.adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.Interface.FragmentListener;
import com.example.baitaplon.R;
import com.example.baitaplon.struct_class.MonAn;

import java.util.List;

public class DatThemAdapter extends RecyclerView.Adapter<DatThemAdapter.ViewHolder>{
    private Context context;
    private List<MonAn> monAnList;
    private int id;
    private SQLiteDatabase database;
    private FragmentListener fragmentListener;
    public void setFragmentListener(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    public DatThemAdapter(Context context, List<MonAn> monAnList, int id) {

        this.context = context;
        this.monAnList = monAnList;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.item_dsmon_toadd,viewGroup,false);
        database = context.openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MonAn monAn = monAnList.get(position);
        viewHolder.tvchitiettenmon.setText(monAn.getTenmon());
        viewHolder.tvchitietgia.setText(monAn.getGia()+"");
    }

    @Override
    public int getItemCount() {
        return monAnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvchitiettenmon;
        TextView tvchitietgia;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvchitiettenmon = itemView.findViewById(R.id.tvnamemonan_toadd);
            tvchitietgia = itemView.findViewById(R.id.tvgiamonan_item_toadd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MonAn monAn = monAnList.get(getPosition());
                    final Dialog dialog = new Dialog(context);
                    dialog.setTitle("Đặt thêm món: "+monAn.getTenmon());
                    dialog.setContentView(R.layout.dialog_datmon);
                    dialog.show();
                    final EditText edt_soluong = dialog.findViewById(R.id.edt_soluongmondat);
                    dialog.findViewById(R.id.btn_ok_dat).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(edt_soluong.getText().toString().length() == 0){
                                Toast.makeText(context, "Số lượng không được bỏ trống!", Toast.LENGTH_SHORT).show();
                            }else if(Integer.parseInt(edt_soluong.getText().toString())<=0){
                                Toast.makeText(context, "Số lượng không được bằng 0!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(fragmentListener!= null){
                                    fragmentListener.onAction("set_status",1);
                                }
                                int soluong_dat = Integer.parseInt(edt_soluong.getText().toString());
                                String sql_check = "SELECT * FROM dathang WHERE id_mon="+monAn.getId()+" AND id_ban="+id;
                                Cursor cursor = database.rawQuery(sql_check,null);
                                if(cursor.getCount() == 0){
                                    String sql = "INSERT INTO dathang(id_mon,id_ban,soluong) VALUES ('"+monAn.getId()+"','"+id+"','"+soluong_dat+"')";
                                    database.execSQL(sql);
                                    Toast.makeText(context, "Đã đặt thành công!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else{
                                    cursor.moveToFirst();
                                    int soluong = 0;
                                    while (!cursor.isAfterLast()){
                                        soluong = cursor.getInt(3);
                                        cursor.moveToNext();
                                    }
                                    int new_soluong = soluong+Integer.parseInt(edt_soluong.getText().toString());
                                    String sql = "UPDATE dathang SET id_mon = '"+monAn.getId()+"',id_ban ='"+id+"',soluong='"+new_soluong+"' WHERE id_mon="+monAn.getId()+" AND id_ban="+id;
                                    database.execSQL(sql);
                                    Toast.makeText(context, "Đã đặt thành công!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                            }
                        }
                    });
                    dialog.findViewById(R.id.btn_huy_dat).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
    }
}
