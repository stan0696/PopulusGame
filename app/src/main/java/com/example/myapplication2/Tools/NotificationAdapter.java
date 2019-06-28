package com.example.myapplication2.Tools;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.View.MyImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context context;
    public NotificationAdapter (List<String> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
            holder.notification_tv = (TextView) convertView.findViewById(R.id.notification_tv);
            holder.gamename_iv = convertView.findViewById(R.id.gamename_iv);
            holder.delete_iv = convertView.findViewById(R.id.delete_iv);
            holder.gameicon_iv = convertView.findViewById(R.id.gameicon_iv);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        holder.notification_tv.setText(list.get(position));
        holder.gameicon_iv.setImageURL("https://img.tapimg.com/market/lcs/2ea1a63c45fe294d36e29e348d441ea3_360.png?imageMogr2/auto-orient/strip");
        return convertView;
    }



    /**
     * 控件缓存类
     *
     * @author 邹奇
     *
     */
    class ViewHolder {
        TextView notification_tv;
        TextView gamename_iv;
        MyImageView gameicon_iv;
        ImageView  delete_iv;
    }

}

