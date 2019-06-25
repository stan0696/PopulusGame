package com.example.myapplication2.View;


import android.media.Image;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

public class Game {
    private String name;
    private String[] introduction;
    private URI downloadUrl;
    private Image titleImage;
    private Image icon;
    private Boolean isfocused;
    private int  mark;
    private int  downloadnum;
    private Date publishTime;
    private ArrayList<String> tag;
    private int ID;

    public Game(String name){
        this.name=name;
        this.tag=new ArrayList<>();
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

    public String[] getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String[] introduction) {
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

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
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

    public ArrayList<String> getTag() {
        return tag;
    }

    public void addTag(String tag) {
        this.tag.add(tag)  ;
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
}
