package com.example.myapplication2.View;

public class Rankbydl extends Rank {
    public Rankbydl(int downloadnum,float mark)
    {
        super(downloadnum,mark);

    }


    @Override
    public int compareTo(Rank user)
    {           //重写Comparable接口的compareTo方法，
        return user.dowmloadnum - super.dowmloadnum;// 根据年龄升序排列，降序修改相减顺序即可

    }
}
