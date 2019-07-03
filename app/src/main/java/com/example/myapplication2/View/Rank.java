package com.example.myapplication2.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Rank implements Comparable<Rank>{


  protected int dowmloadnum; //
    protected float mark; //
    protected String gamename;
    protected int ranking;
    protected String icon;




    public Rank(int dowmloadnum, float mark)
    {
    this.dowmloadnum = dowmloadnum;
    this.mark = mark;

    }

// getter && setter

@Override
    public String toString()
    {
    return "User [dowmloadnum=" + dowmloadnum + ", mark=" + mark + "]";
    }
    @Override
    public int compareTo(Rank user)
    {           //重写Comparable接口的compareTo方法，

        int k=0;

        if (this.mark-user.mark>=0)
            k=1;
        else k=-1;


        return k;// 根据年龄升序排列，降序修改相减顺序即可
    }

    public int getDowmloadnum() {
        return dowmloadnum;
    }

    public void setDowmloadnum(int dowmloadnum) {
        this.dowmloadnum = dowmloadnum;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }


}


