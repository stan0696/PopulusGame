package com.example.myapplication2.View;

public class Rankbymark extends Rank {
        public Rankbymark(int downloadnum,float mark)
        {
            super(downloadnum,mark);

        }


        @Override
        public int compareTo(Rank user)
        {           //重写Comparable接口的compareTo方法，
            int k=0;

            if (super.mark-user.mark>=0)
                k=-1;
            else
                k=1;

            return k;
        }
    }

