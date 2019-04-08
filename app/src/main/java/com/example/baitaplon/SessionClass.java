package com.example.baitaplon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

public class SessionClass {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public Context context;
    int MODE_PRIVATE = 0;
    private static final String PRE_NAME = "myLogin";
    private static final String USERNAME = "USERNAME";
    private static final String LEVEL = "LEVEL";
    private static final String CHECK = "CHECKED";
    public SessionClass(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PRE_NAME,this.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }
    public void CreateSession(String username,int level,Boolean Checked){
        editor.putString(USERNAME,username);
        editor.putInt(LEVEL,level);
        editor.putBoolean(CHECK,Checked);
        editor.apply();
    }
    public boolean isLogin(){
        return sharedPreferences.getBoolean(CHECK,false);
    }
    public void checkLogin(){
        if(!isLogin()){
            Intent intent = new Intent(context,Login.class);
            context.startActivity(intent);
        }
    }
    public HashMap<String, String> detailUser(){
        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME,sharedPreferences.getString(USERNAME,null));
        user.put(LEVEL, String.valueOf((sharedPreferences.getInt(LEVEL,0))));
        return user;
    }
    public void Logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context,Login.class);
        context.startActivity(intent);
    }
}
