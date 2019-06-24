package com.example.myapplication2;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Crawl_data {
    private  String url;
    public Crawl_data(String url){
        this.url=url;
    }

    public void run(){
        try {
            Document document = Jsoup.connect("https://www.taptap.com").get();
            String str = document.title();
            Log.e("标题",str);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }



}
