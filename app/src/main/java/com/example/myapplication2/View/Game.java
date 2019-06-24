package com.example.myapplication2.View;

import android.media.Image;

import java.net.URI;
import java.net.URL;
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
    private  String[] tag;









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

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
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
}
