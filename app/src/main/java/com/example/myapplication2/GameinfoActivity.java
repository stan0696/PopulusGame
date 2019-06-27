package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication2.View.MyImageView;

public class GameinfoActivity extends AppCompatActivity {
    private MyImageView iconview;
    private String name;
    private String iconurl;
    private String introduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameinfo);
        Intent intent = getIntent();
        name = intent.getStringExtra("gamename");
        getdata();
    }

    private void getdata() {
        SQLiteDbHelper helper = new SQLiteDbHelper(getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cValue = new ContentValues();
        String [] names=new String[1];
        names[0]=this.name;
        Cursor cursor = database.query("game", null, "name=?", new String[]{this.name}, null, null, null);//修改
        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            this.name= cursor.getString(0);
            this.iconurl=cursor.getString(2);
            this.introduction=cursor.getString(3);
            System.out.println(iconurl);
            System.out.println(123);
            iconview=(MyImageView)findViewById(R.id.imageView_game);
            iconview.setImageURL(iconurl);

        }
    }

}
