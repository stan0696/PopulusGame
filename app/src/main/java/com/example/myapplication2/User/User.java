package com.example.myapplication2.User;

public class User {
    //表名
    public static final String TABLE="user";

    //表的各域名
    public static final String KEY_NAME="name";
    public static final String KEY_Password="password";

    //属性
    public String user_Name;
    public String password;

    public User(String  name, String password){
        this.user_Name = name;
        this.password = password;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}