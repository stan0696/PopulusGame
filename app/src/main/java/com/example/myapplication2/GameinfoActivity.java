package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication2.View.MyImageView;

public class GameinfoActivity extends AppCompatActivity {

    private MyImageView iconview;
    private TextView introductiontext;
    private TextView nametext;
    private TextView tag1text;
    private TextView tag2text;
    private TextView tag3text;
    private String name;
    private String iconurl;
    private String introduction;
    private String tag1;
    private String tag2;
    private String tag3;
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
            this.tag1=cursor.getString(6);
            this.tag2=cursor.getString(7);
            this.tag3=cursor.getString(8);
            System.out.println(iconurl);
            System.out.println(123);
            iconview=(MyImageView)findViewById(R.id.imageView_game);
            introductiontext=findViewById(R.id.game_textIntroduce);
            nametext=findViewById(R.id.game_textname);


            iconview.setImageURL(iconurl);
            introductiontext.setText(introduction);
            nametext.setText(name);
            if(tag1!=null)
            {
                tag1text=findViewById(R.id.gametag1);
                tag1text.setText(tag1);
            }

            if(tag2!=null)
            {
                tag2text=findViewById(R.id.gametag2);
                tag2text.setText(tag2);
            }

            if(tag3!=null)
            {
                tag3text=findViewById(R.id.gametag3);
                tag3text.setText(tag3);
            }
        }
    }

}

