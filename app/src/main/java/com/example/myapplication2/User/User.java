package com.example.myapplication2.User;

import android.provider.ContactsContract;

import java.sql.Date;

public class User {
    //表名
    public static final String TABLE="user";

    //表的各域名
    public static final String KEY_NAME="name";
    public static final String KEY_Password="password";

    //属性
    public String user_Name;
    public String password;
    private String usernote;
    private String userbirth;
    private String usersex;
    private String usercity;
    private String userphone;

    /**
     * 用户构造函数
     * @param name 用户名
     * @param password 用户密码
     * @param usernote 个人简介
     * @param userbirth 生日
     * @param usersex 性别
     * @param usercity 城市
     * @param userphone 电话
     */
    public User(String  name, String password, String usernote, String userbirth,
                String usersex, String usercity, String userphone){
        this.user_Name = name;
        this.password = password;
        this.usernote = usernote;
        this.userbirth = userbirth;
        this.usersex = usersex;
        this.usercity = usercity;
        this.userphone = userphone;
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

    public String getUsernote() {
        return usernote;
    }

    public void setUsernote(String usernote) {
        this.usernote = usernote;
    }

    public String getUserbirth() {
        return userbirth;
    }

    public void setUserbirth(String userbirth) {
        this.userbirth = userbirth;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getUsercity() {
        return usercity;
    }

    public void setUsercity(String usercity) {
        this.usercity = usercity;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }
}