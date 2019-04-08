package com.example.baitaplon.chitietbanan;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.Interface.AttackFragment;
import com.example.baitaplon.Interface.FragmentListener;
import com.example.baitaplon.PagerAdapter;
import com.example.baitaplon.R;
import com.example.baitaplon.adapter.DatThemAdapter;
import com.example.baitaplon.struct_class.ThongKe;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class Main_Each_BanAn_ChiTiet extends AppCompatActivity {
    TextView chitiet_tenban;
    ViewPager viewPager;
    TabLayout tabLayout;
    private SQLiteDatabase database;
    List<ThongKe> thongKeList = new ArrayList<>();
    TextView btn_thanhtoan;
    private AttackFragment attackFragment;
    Fragment_DatThem_Mon fragment_datThem = new Fragment_DatThem_Mon();
    Fragment_DaDat fragment_daDat = new Fragment_DaDat();
    public void setAttackFragment(AttackFragment attackFragment) {
        this.attackFragment = attackFragment;
    }

    int id =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban);
        chitiet_tenban = findViewById(R.id.chitiet_tenban);
        viewPager = findViewById(R.id.pager_chitietban);
        tabLayout = findViewById(R.id.tab_chitietban);
        btn_thanhtoan = findViewById(R.id.thanhtoan);
        final int ten_ban = (Integer) getIntent().getIntExtra("tenban",0);
        final int id_ban = getIntent().getIntExtra("id",0);
        id = id_ban;
        check_thanhtoan(id_ban);
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhtoan(id_ban,ten_ban);
            }
        });
        chitiet_tenban.setText("Bàn "+(ten_ban+1));
        //bundle
        Bundle bundle = new Bundle();
        bundle.putInt("id",id_ban);
        fragment_datThem.setArguments(bundle);
        fragment_daDat.setArguments(bundle);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.Add_Fragment(fragment_daDat,"Danh sách món đặt");
        adapter.Add_Fragment(fragment_datThem,"Đặt thêm");
        fragment_datThem.setFragmentListeners(new FragmentListener(){
            @Override
            public void onAction(String action, int data) {
                if(data ==0){
                    btn_thanhtoan.setVisibility(View.GONE);
                }else{
                    btn_thanhtoan.setVisibility(View.VISIBLE);
                }
            }
        });
        fragment_daDat.setFragmentListener(new FragmentListener() {
            @Override
            public void onAction(String action, int data) {
                if(data ==0){
                    btn_thanhtoan.setVisibility(View.GONE);
                }else{
                    btn_thanhtoan.setVisibility(View.VISIBLE);
                }
            }
        });
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        auto_reload();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        check_thanhtoan(id);
    }

    private void check_thanhtoan(int id_ban) {
        database = openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
        String query = "SELECT * FROM dathang WHERE id_ban="+id_ban;
        Cursor cursor1 = database.rawQuery(query,null);
        cursor1.moveToFirst();
        if(cursor1.getCount() == 0){
            btn_thanhtoan.setVisibility(View.GONE);
        }else{
            btn_thanhtoan.setVisibility(View.VISIBLE);
        }
    }

    private void thanhtoan(final int id_ban, int ten_ban) {
        String query = "SELECT dathang.*,monan.gia FROM dathang INNER JOIN monan ON dathang.id_mon = monan.id where dathang.id_ban="+id_ban;
        Cursor cursor1 = database.rawQuery(query,null);
        if(cursor1.getCount()!=0){
            cursor1.moveToFirst();
            int soluong = 0;
            int dongia = 0;
            int tongtien = 0;
            while(!cursor1.isAfterLast()){
                int thongke_id_mon = cursor1.getInt(2);
                int thongke_soluong =cursor1.getInt(3);
                ThongKe thongKe = new ThongKe(0,thongke_id_mon,thongke_soluong,0);
                thongKeList.add(thongKe);
                tongtien += cursor1.getInt(3)*cursor1.getInt(4);
                cursor1.moveToNext();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("THANH TOÁN BÀN ĂN "+(ten_ban+1));
            builder.setMessage("Tổng số tiền phải thanh toán là: "+tongtien+" Vnđ");
            builder.setNegativeButton("THANH TOÁN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    btn_thanhtoan.setVisibility(View.GONE);
                    fragment_daDat.reload_thanhtoan();
                    for(int i =0; i< thongKeList.size(); i++){
                        ThongKe thongKe = thongKeList.get(i);
                        int thongke_id_mon = thongKe.getId_monan();
                        int thongke_soluong = thongKe.getSoluong();
                        int thongke_thoigian = thongKe.getThoigian();
                        String sql_check = "SELECT * FROM thongke WHERE id_mon="+thongke_id_mon;
                        Cursor cursor_test = database.rawQuery(sql_check,null);
                        if(cursor_test.getCount() != 0){
                            cursor_test.moveToFirst();
                            int soluong_truoc = 0;
                            while (!cursor_test.isAfterLast()){
                                soluong_truoc += cursor_test.getInt(2);
                                cursor_test.moveToNext();
                            }
                            int soluong_sau = thongke_soluong+soluong_truoc;
                            String sql = "UPDATE thongke SET soluong='"+soluong_sau+"' WHERE id_mon="+thongke_id_mon;
                            database.execSQL(sql);
                            String sql1 = "DELETE FROM dathang WHERE id_ban="+id_ban;
                            database.execSQL(sql1);
                            Toast.makeText(Main_Each_BanAn_ChiTiet.this,"Thanh toán thành công", LENGTH_SHORT).show();
                        }else{
                            String sql = "INSERT INTO thongke(id_mon,soluong,thoigian) VALUES ('"+thongke_id_mon+"','"+thongke_soluong+"','"+thongke_thoigian+"')";
                            database.execSQL(sql);
                            String sql1 = "DELETE FROM dathang WHERE id_ban="+id_ban;
                            database.execSQL(sql1);
                            Toast.makeText(Main_Each_BanAn_ChiTiet.this,"Thanh toán thành công", LENGTH_SHORT).show();
                        }
                    }
                }
            });
            builder.setPositiveButton("HỦY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            Dialog dialog = builder.create();
            dialog.show();
        }
    }

    private void auto_reload() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
