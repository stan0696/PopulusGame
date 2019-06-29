package com.example.myapplication2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication2.View.Game;

public class SQLiteDbHelper  extends SQLiteOpenHelper {
    public static final String DB_NAME = "database.db";

    public static final int DB_VERSION = 1;

    public static final String TABLE_GAME= "game";
    public static final String NAME= "name";
    public static final String GAMEID= "gameid";
    public static final String ICON= "icon";
    public static final String INTRODUCTION= "introduction";
    public static final String DOWNLOADURL= "downloadurl";
    public static final String TITLEIMAGE= "titleImage";
    public static final String MARK= "mark";
    public static final String TAG1= "tag1";
    public static final String TAG2= "tag2";
    public static final String TAG3= "tag3";

    public static final String TABLE_DOWNLOAD= "downloadgame";
    public static final String DOWNLOADNAME= "name";
    public static final String DOWNLOADICON= "icon";
    public static final String DOWNLOADID= "id";
    //创建 students 表的 sql 语句
    private static final String GAME_CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_GAME
            +"("+ NAME + " varchar(10), " +
               GAMEID+ " Integer primary key, " +
               ICON+ " varchar(10)," +
             INTRODUCTION + " TEXT ," +
             DOWNLOADURL + " TEXT," +
             TITLEIMAGE + " TEXT," +
             TAG1 +" TEXT," +
             TAG2 +" TEXT," +
             TAG3 +" TEXT," +
             MARK + " Integer)";

    private static final String DOWNLOAD_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_DOWNLOAD
            +"("+ DOWNLOADNAME+ " varchar(10) primary key, " +
            DOWNLOADID+ " Integer , " +
            DOWNLOADICON+ " varchar(10))";


    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        // 创建 students 表
        db.execSQL(GAME_CREATE_TABLE_SQL);
        db.execSQL(DOWNLOAD_TABLE_SQL );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            case 1:
                // do something
                break;

            default:
                break;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys = 1;");

            //或者这样写
            String query = String.format("PRAGMA foreign_keys = %s", "ON");
            db.execSQL(query);
        }
    }

}
