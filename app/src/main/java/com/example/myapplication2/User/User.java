package com.example.myapplication2.User;

public class User {
    //表名
    public static final String TABLE="user";

    //表的各域名
    public static final String KEY_ID="id";
    public static final String KEY_Password="password";

    //属性
    public int user_ID;
    public String password;

    public User(int id, String password){
        this.user_ID = id;
        this.password = password;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}