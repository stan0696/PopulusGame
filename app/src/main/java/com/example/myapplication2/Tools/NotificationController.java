package com.example.myapplication2.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication2.SQLiteDbHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationController {
    private Context mcontext;
    public  NotificationController(Context mcontext){
        this.mcontext=mcontext;
    }
    public void updata(String name, int id, String iconurl, String message){
        SQLiteDbHelper helper = new SQLiteDbHelper(mcontext);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cValue = new ContentValues();
        cValue.put("name",name);
        cValue.put("id", id);
        cValue.put("icon",iconurl);
        cValue.put("notification",message);
        if (database.query("notificationlist",null, "id=?",new String[]{String.valueOf(id)}, null, null, null).getCount()==0){
            database.insert(SQLiteDbHelper.TABLE_NOTIFICATION, null,cValue);
        }
        database.close();
    }


}

