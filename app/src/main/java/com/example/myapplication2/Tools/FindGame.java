package com.example.myapplication2.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.UrlQuerySanitizer;

import com.example.myapplication2.SQLiteDbHelper;
import com.example.myapplication2.View.Game;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class FindGame {
    private  String tag;
    private URI url;
    private Game game;
    private ArrayList<Game>  games;
    public  FindGame(URI url,String tag){
        this.tag=tag;
        this.url=url;
        games=new ArrayList<>();
    }
    public Game findgamebyname(String name, Context context)
    {
        Game thegame=new Game(name);

        SQLiteDbHelper helper = new SQLiteDbHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cValue = new ContentValues();
        String [] names=new String[1];
        names[0]=name;
        Cursor cursor = database.query("game", null, "name=?", new String[]{name}, null, null, null);//修改
        if (cursor.moveToFirst()) {

            int id = Integer.parseInt( cursor.getString(1) );
            thegame.setID(id);

            thegame.setIcon(cursor.getString(2));

            thegame.setIntroduction(cursor.getString(3));

            //URI downloaduri=URI.create(cursor.getString(4));
           // thegame.setDownloadUrl(downloaduri);

            thegame.setTag(1,cursor.getString(6));
            thegame.setTag(2,cursor.getString(7));
            thegame.setTag(3,cursor.getString(8));

            //int mark = Integer.parseInt( cursor.getString(9) );
            //thegame.setMark( mark);




        }
        return thegame;
    }
    public ArrayList<Game> findgamebytag(String tag, Context context)
    {

        SQLiteDbHelper helper = new SQLiteDbHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
       // ContentValues cValue = new ContentValues();

        Cursor cursor1 = database.query("game", null, "tag1=? or tag2=? or tag3=?", new String[]{tag,tag,tag}, null, null, null);//修改
        if (cursor1.moveToFirst())
        {
            while(cursor1.moveToNext())
            {


            Game thegame=new Game("name");

            thegame.setName(cursor1.getString(0));

            int id = Integer.parseInt( cursor1.getString(1) );
            thegame.setID(id);

            thegame.setIcon(cursor1.getString(2));

            thegame.setIntroduction(cursor1.getString(3));

                thegame.setTag(1,cursor1.getString(6));
                thegame.setTag(2,cursor1.getString(7));
                thegame.setTag(3,cursor1.getString(8));

            //URI downloaduri=URI.create(cursor1.getString(4));
            //thegame.setDownloadUrl(downloaduri);


            //int mark = Integer.parseInt( cursor1.getString(9) );
           // thegame.setMark( mark);
            games.add(thegame);

            }

        }

        return games;
    }











    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }
}
