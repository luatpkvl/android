package com.example.baitaplon;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.banan.Fragment_Main_BanAn;
import com.example.baitaplon.index.Fragment_index;
import com.example.baitaplon.monan.Fragment_Main_MonAn;
import com.example.baitaplon.thongke.Fragment_Main_thongke;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SharedPreferences sharedPreferences = this.getSharedPreferences("myLogin",MODE_PRIVATE);
        TextView tv_name_header = findViewById(R.id.textView_Name_header);
//        tv_name_header.setText("hahaha");
        Init_database();
        nav_option(toolbar);
        final SessionClass sessionClass = new SessionClass(this);
        sessionClass.checkLogin();
        load_fragment(new Fragment_index());
    }
    private void Init_database() {
        database = openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
        String sql1 = "CREATE TABLE IF NOT EXISTS monan(id integer primary key autoincrement,tenmon text,gia integer)";
        String sql2 = "CREATE TABLE IF NOT EXISTS dathang(id integer primary key autoincrement,id_ban integer,id_mon integer,soluong integer)";
        String sql3 = "CREATE TABLE IF NOT EXISTS banan(id integer primary key autoincrement,tenban text)";
        String sql4 = "CREATE TABLE IF NOT EXISTS thongke(id integer primary key autoincrement,id_mon integer,soluong integer,thoigian integer)";
        String sql5 = "CREATE TABLE IF NOT EXISTS user(id integer primary key autoincrement,username text,password text,level integer)";
        database.execSQL(sql1);
        database.execSQL(sql2);
        database.execSQL(sql3);
        database.execSQL(sql4);
        database.execSQL(sql5);
        String sql6 = "SELECT * FROM user where username='admin'";
        String sql7 = "SELECT * FROM user where username='nhanvien'";
        Cursor cursor = database.rawQuery(sql6,null);
        if(cursor.getCount()==0){
            String sql8 = "INSERT INTO user(username,password,level) VALUES ('admin','123',1)";
            database.execSQL(sql8);
        }
        Cursor cursor1 = database.rawQuery(sql7,null);
        if(cursor1.getCount()==0){
            String sql9 = "INSERT INTO user(username,password,level) VALUES ('nhanvien','123',2)";
            database.execSQL(sql9);
        }

    }

    private void nav_option(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tv_header = headerView.findViewById(R.id.textView_Name_header);
        Menu nav_menu = navigationView.getMenu();
        SessionClass sessionClass = new SessionClass(this);
        int level = Integer.parseInt(sessionClass.detailUser().get("LEVEL"));
        if(level == 1){
            tv_header.setText("Quản lý");
            nav_menu.findItem(R.id.nav_monan).setVisible(true);
            nav_menu.findItem(R.id.nav_banan).setVisible(true);
            nav_menu.findItem(R.id.nav_doanhthu).setVisible(true);
        }else{
            tv_header.setText("Nhân Viên");
            nav_menu.findItem(R.id.nav_banan).setVisible(true);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_monan) {
            load_fragment(new Fragment_Main_MonAn());
        } else if (id == R.id.nav_banan) {
            load_fragment(new Fragment_Main_BanAn());
        }else if (id == R.id.nav_doanhthu) {
            load_fragment(new Fragment_Main_thongke());
        }else if (id == R.id.nav_login) {
//            load_fragment(new Fragment_login());
        }
        else if (id == R.id.nav_logout) {
            SessionClass sessionClass = new SessionClass(this);
            sessionClass.Logout();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void load_fragment(Fragment fm) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.Fragment_main,fm).
                commit();
    }
}
