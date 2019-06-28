package com.example.myapplication2.View;

import android.media.Image;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class Game {
    private String name;
    private String introduction;
    private URI downloadUrl;
    private Image titleImage;
    private String icon;
    private Boolean isfocused;
    private int  mark;
    private int  downloadnum;
    private Date publishTime;
    private String tag1;
    private String tag2;
    private String tag3;
    private int ID;
    private URI url;

    public Game(String name){
        this.name=name;

    }






    public Game getgamebyname(String name){
        return this;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public URI getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(URI downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Image getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(Image titleImage) {
        this.titleImage = titleImage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getIsfocused() {
        return isfocused;
    }

    public void setIsfocused(Boolean isfocused) {
        this.isfocused = isfocused;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getDownloadnum() {
        return downloadnum;
    }

    public void setDownloadnum(int downloadnum) {
        this.downloadnum = downloadnum;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getTag(int i) {

        switch (i)
        {
            case 1:return tag1;
            case 2:return tag2;
            case 3:return tag3;
            default:return null;
        }



    }

    public void setTag(int i,String tag)

    {

        switch (i)
        {
            case 1:this.tag1 = tag;
            case 2:this.tag2 = tag;
            case 3:this.tag3 = tag;
        }


    }


}
