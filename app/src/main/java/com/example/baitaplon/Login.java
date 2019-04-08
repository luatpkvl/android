package com.example.baitaplon;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btn_sign_in;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_sign_in = findViewById(R.id.sign_button);
        final SessionClass sessionClass = new SessionClass(Login.this);
//        sharedPreferences = getActivity().getSharedPreferences("myLogin",getActivity().MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedPreferences.edit();
        database = openOrCreateDatabase("quanlynhahang.db", Context.MODE_PRIVATE,null);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().length() ==0 || password.getText().toString().length() ==0){
                    Toast.makeText(Login.this,"Bạn không được bỏ trống!",Toast.LENGTH_SHORT).show();
                }else{
                    String name = username.getText().toString();
                    String pass = password.getText().toString();
                    String sql = "SELECT * FROM user WHERE username='"+name+"' AND password = '"+pass+"'";
                    Cursor cursor = database.rawQuery(sql,null);
                    if(cursor.getCount()==0){
                        Toast.makeText(Login.this,"Bạn đã nhập sai tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                        cursor.moveToFirst();
                        int level = 0;
                        while (!cursor.isAfterLast()){
                            String username = cursor.getString(1);
                            level = cursor.getInt(3);
                            cursor.moveToNext();
                        }
                        sessionClass.CreateSession(name,level,true);
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}
