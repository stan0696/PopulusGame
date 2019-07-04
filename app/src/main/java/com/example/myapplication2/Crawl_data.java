package com.example.myapplication2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.myapplication2.Tools.FindGame;
import com.example.myapplication2.View.Game;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;

public class Crawl_data {
    private  String url;
    private  FindGame []newgametag ;
    private  FindGame []newgametj ;
    private  ArrayList<Game>  newgame;
    public Crawl_data(String url){
        this.url=url;
    }

    public void run(Context context){
        newgame =new ArrayList<>();
        SQLiteDbHelper helper = new SQLiteDbHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cValue = new ContentValues();
        try {
            Document document = Jsoup.connect(url).timeout(10000).get();
            this.newgametag = new FindGame[document.select(".tag").size()];
            for(int i=0;i<document.select(".tag").size();i++){
                Element span=document.select(".tag").get(i);
                String relHref=document.select(".tag").select("a").get(i).attr("href");
                String text=span.text();
                URI uri = URI.create(relHref);
                newgametag[i] = new FindGame(uri,text);

                // System.out.println(newgametag[i].getTag());
                // System.out.println(newgametag[i].getUrl());
            }
            this.newgametj = new FindGame[17];
            for (int k=0;k<17;k++){
                URI uritj =URI.create("https://www.taptap.com/category/recommend?sort=hits&page="+k);
                newgametj[k]=new FindGame(uritj,"推荐");
            }
            for (FindGame findGame:newgametj){
                try{
                    Document gamedoc = Jsoup.connect(findGame.getUrl().toString()).timeout(10000).get();
                    for (int i=0;i<gamedoc.select("h4[class=flex-text]").size();i++){
                        Element span=gamedoc.select("h4[class=flex-text]").get(i);
                        String text=span.text();
                        String relHref=gamedoc.select("a[style]").get(i).attr("href");/*获取游戏自身的url*/
                        URI uri = URI.create(relHref);
                        Game nowgame =new Game(text);
                        nowgame.setUrl(uri);
                        setgameinfo(nowgame,context);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (FindGame findGame:newgametag
            ) {
                try {
                    Document gamedoc = Jsoup.connect(findGame.getUrl().toString()).timeout(10000).get();
                    for(int i=0;i<gamedoc.select(".card-app-title").size();i++)
                    {
                        Element span=gamedoc.select(".card-app-title").get(i);
                        String text=span.text();
                        String relHref=gamedoc.select(".card-right-title").get(i).attr("href");/*获取游戏自身的url*/
                        URI uri = URI.create(relHref);
                        Game nowgame =new Game(text);
                        nowgame.setUrl(uri);

                        int tagnum=0;
                        for (Element tagelement:gamedoc.select(".card-tags").get(i).select("a"))        /*获取游戏的tag*/

                        {

                            nowgame.setTag(tagnum%3+1,tagelement.text());

                            tagnum++;



                        }

                        Document thisgamedoc = Jsoup.connect(nowgame.getUrl().toString()).timeout(10000).get();//获取具体某款游戏页面内的内容

                        String introduction ;
                        Element intro = thisgamedoc.select(".body-description-paragraph").first();
                        String icon=thisgamedoc.select(".header-icon-body").select("img").attr("src");
                        String id=thisgamedoc.select("button").attr("data-taptap-app-id");
                        String[] tittleimg =new String[5];
                        if (thisgamedoc.select("a[data-lightbox=screenshots]").size()>=5){
                                    tittleimg = new String[]{thisgamedoc.select("a[data-lightbox=screenshots]").get(0).attr("href"),
                                    thisgamedoc.select("a[data-lightbox=screenshots]").get(1).attr("href"),
                                    thisgamedoc.select("a[data-lightbox=screenshots]").get(2).attr("href"),
                                    thisgamedoc.select("a[data-lightbox=screenshots]").get(3).attr("href"),
                                    thisgamedoc.select("a[data-lightbox=screenshots]").get(4).attr("href")
                            };//获取主题图片
                        }

                        if(id.isEmpty())
                        {
                            id=thisgamedoc.select(".taptap-button-download").attr("data-app-id");/*付费游戏的id获取方式不同*/
                        }
                        if(id.isEmpty())
                        {   Random ra =new Random();
                            id="10000"+ ra.nextInt(1000)+1;
                        }

                        String downloadget = new String("https://www.9game.cn/search/?keyword="+java.net.URLEncoder.encode(nowgame.getName(), "UTF-8")+"&uc_gd_adm=%E6%90%9C%E7%B4%A2");
                        Document downloaddoc = Jsoup.connect(downloadget).timeout(10000).get();
                        Element info =downloaddoc.select(".sr-poker").first();
                        if (nowgame.getName().indexOf(info.select("img").attr("alt"))>-1){
                            nowgame.setDownloadUrl(URI.create(info.select("a[class=down android]").attr("href")));
                        }
                        else {
                            nowgame.setDownloadUrl(URI.create("no_this_game"));
                        }
                        if(id!=null)
                        {
                            int Id=new Integer(id);
                            nowgame.setID(Id);
                            /*将获取的id传入nowgame里*/
                        }

                        if(intro!=null)
                        {
                            introduction = intro.text();
                            nowgame.setIntroduction(introduction);/*将获取的简介传入nowgame里*/

                        }

                        if(icon!=null)
                        {
                            nowgame.setIcon(icon);/*将获取的图标url传入nowgame里*/
                        }

                        if(tittleimg!=null){
                            nowgame.setTitleImage(tittleimg);/*将获取的主题图片url传入nowgame里*/
                        }


                        cValue.put("name",nowgame.getName());
                        cValue.put("gameid",nowgame.getID());
                        cValue.put("icon",nowgame.getIcon());
                        cValue.put("introduction",nowgame.getIntroduction());
                        cValue.put("downloadurl",nowgame.getDownloadUrl().toString());
                        cValue.put("titleImage",nowgame.getTitleImage()[0]);
                        cValue.put("titleImage1",nowgame.getTitleImage()[1]);
                        cValue.put("titleImage2",nowgame.getTitleImage()[2]);
                        cValue.put("titleImage3",nowgame.getTitleImage()[3]);
                        cValue.put("titleImage4",nowgame.getTitleImage()[4]);
                        if(nowgame.getTag(1)!=null)
                        {
                            cValue.put("tag1",nowgame.getTag(1));
                        }

                        if(nowgame.getTag(2)!=null)
                        {
                            cValue.put("tag2",nowgame.getTag(2));
                        }

                        if(nowgame.getTag(3)!=null)
                        {
                            cValue.put("tag3",nowgame.getTag(3));
                        }

                        database.replace(SQLiteDbHelper.TABLE_GAME, null,cValue);

                        newgame.add(nowgame);/*传入newgame类*/
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        database.close();
    }

    public FindGame[] getNewgametag() {
        return newgametag;
    }

    public void setNewgametag(FindGame[] newgametag) {
        this.newgametag = newgametag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Game> getNewgame() {
        return newgame;
    }

    public void setNewgame(ArrayList<Game> newgame) {
        this.newgame = newgame;
    }

    private void setgameinfo(Game nowgame,Context context) {

        SQLiteDbHelper helper = new SQLiteDbHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cValue = new ContentValues();
        try {
            Document thisgamedoc = Jsoup.connect(nowgame.getUrl().toString()).timeout(10000).get();//获取具体某款游戏页面内的内容

            String introduction;

            for (int tagnum=0;tagnum<3;tagnum++)        /*获取游戏的tag*/
            {
                nowgame.setTag(tagnum%3+1,thisgamedoc.select("ul[id=appTag]").select("a").get(tagnum).text());
            }
            Element intro = thisgamedoc.select(".body-description-paragraph").first();
            String icon = thisgamedoc.select(".header-icon-body").select("img").attr("src");
            String id = thisgamedoc.select("button").attr("data-taptap-app-id");
            String[] tittleimg = new String[5];
            if (thisgamedoc.select("a[data-lightbox=screenshots]").size() >= 5) {
                tittleimg = new String[]{thisgamedoc.select("a[data-lightbox=screenshots]").get(0).attr("href"),
                        thisgamedoc.select("a[data-lightbox=screenshots]").get(1).attr("href"),
                        thisgamedoc.select("a[data-lightbox=screenshots]").get(2).attr("href"),
                        thisgamedoc.select("a[data-lightbox=screenshots]").get(3).attr("href"),
                        thisgamedoc.select("a[data-lightbox=screenshots]").get(4).attr("href")
                };//获取主题图片
            }

            if (id.isEmpty()) {
                id = thisgamedoc.select(".taptap-button-download").attr("data-app-id");/*付费游戏的id获取方式不同*/
            }
            if (id.isEmpty()) {
                id = "0" ;
            }

            String downloadget = new String("https://www.9game.cn/search/?keyword=" + java.net.URLEncoder.encode(nowgame.getName(), "UTF-8") + "&uc_gd_adm=%E6%90%9C%E7%B4%A2");
            Document downloaddoc = Jsoup.connect(downloadget).timeout(10000).get();
            Element info = downloaddoc.select(".sr-poker").first();
            if (nowgame.getName().indexOf(info.select("img").attr("alt")) > -1) {
                nowgame.setDownloadUrl(URI.create(info.select("a[class=down android]").attr("href")));
            } else {
                nowgame.setDownloadUrl(URI.create("no_this_game"));
            }
            if (id != null) {
                int Id = new Integer(id);
                nowgame.setID(Id);
                /*将获取的id传入nowgame里*/
            }

            if (intro != null) {
                introduction = intro.text();
                nowgame.setIntroduction(introduction);/*将获取的简介传入nowgame里*/

            }

            if (icon != null) {
                nowgame.setIcon(icon);/*将获取的图标url传入nowgame里*/
            }

            if (tittleimg != null) {
                nowgame.setTitleImage(tittleimg);/*将获取的主题图片url传入nowgame里*/
            }


            cValue.put("name", nowgame.getName());
            cValue.put("gameid", nowgame.getID());
            cValue.put("icon", nowgame.getIcon());
            cValue.put("introduction", nowgame.getIntroduction());
            cValue.put("downloadurl", nowgame.getDownloadUrl().toString());
            cValue.put("titleImage", nowgame.getTitleImage()[0]);
            cValue.put("titleImage1", nowgame.getTitleImage()[1]);
            cValue.put("titleImage2", nowgame.getTitleImage()[2]);
            cValue.put("titleImage3", nowgame.getTitleImage()[3]);
            cValue.put("titleImage4", nowgame.getTitleImage()[4]);
            if (nowgame.getTag(1) != null) {
                cValue.put("tag1", nowgame.getTag(1));
            }

            if (nowgame.getTag(2) != null) {
                cValue.put("tag2", nowgame.getTag(2));
            }

            if (nowgame.getTag(3) != null) {
                cValue.put("tag3", nowgame.getTag(3));
            }

            database.replace(SQLiteDbHelper.TABLE_GAME, null, cValue);
            database.close();
            newgame.add(nowgame);/*传入newgame类*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

