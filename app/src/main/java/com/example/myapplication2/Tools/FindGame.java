package com.example.myapplication2.Tools;

import java.net.URI;
import java.net.URL;

public class FindGame {
    private  String tag;
    private URI url;
    public  FindGame(URI url,String tag){
        this.tag=tag;
        this.url=url;
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
