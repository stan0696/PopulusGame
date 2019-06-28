package com.example.myapplication2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.Tools.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class RankingfragAdapter extends BaseAdapter {

    private List<String> list = new ArrayList<>();
    private Context context;
    public RankingfragAdapter (List<String> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.rankingfrag_item ,parent, false);
            holder.Ranktext1_1 = (TextView) convertView.findViewById(R.id.Ranktext1_1);
            holder.Rankname1_1 = convertView.findViewById(R.id.Rankname1_1);
            holder.Rankimage1_1 = convertView.findViewById(R.id.Rankimage1_1);
            holder.Rankintro1_1 = convertView.findViewById(R.id.Rankintro1_1);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.Ranktext1_1.setText(list.get(position));
        holder.Rankimage1_1.setImageURL("https://img.tapimg.com/market/lcs/2ea1a63c45fe294d36e29e348d441ea3_360.png?imageMogr2/auto-orient/strip");
        holder.Rankintro1_1.setText("这是简介");
        return convertView;
    }



    /**
     * 控件缓存类
     *
     * @author 邹奇
     *
     */
    class ViewHolder {
        TextView Ranktext1_1;
        TextView Rankname1_1;
        MyImageView Rankimage1_1;
        TextView  Rankintro1_1;
    }

}
