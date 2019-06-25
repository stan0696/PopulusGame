package com.example.myapplication2;


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

public class Crawl_data {
    private  String url;
    private  FindGame []newgametag ;
    private  ArrayList<Game>  newgame;
    public Crawl_data(String url){
        this.url=url;
    }

    public void run(){
         newgame =new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
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
            for (FindGame findGame:newgametag
                 ) {
                try {
                    Document gamedoc = Jsoup.connect(findGame.getUrl().toString()).get();

                    for(int i=0;i<gamedoc.select(".card-app-title").size();i++){

                        Element span=gamedoc.select(".card-app-title").get(i);
                        String text=span.text();
                        //System.out.println(text);

                        Game nowgame =new Game(text);
                        newgame.add(nowgame);
                        for (Element tagelement:gamedoc.select(".card-tags").get(i).select("a")
                        ) {
                            //System.out.println(tagelement.text());
                            nowgame.addTag(tagelement.text());
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            //System.out.println(newgame.get(1).getName());

        } catch (
                IOException e) {
            e.printStackTrace();
        }

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
}
