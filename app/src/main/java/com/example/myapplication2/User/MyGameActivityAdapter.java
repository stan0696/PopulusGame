package com.example.myapplication2.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication2.R;
import com.example.myapplication2.Tools.NotificationAdapter;
import com.example.myapplication2.View.MyImageView;

import java.util.ArrayList;
import java.util.List;

public class MyGameActivityAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context context;
    public  MyGameActivityAdapter  (List<String> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.mygame_item, parent, false);
            holder.mygamename_iv = convertView.findViewById(R.id.mygamename_iv);
            holder.mygameicon_iv= convertView.findViewById(R.id.mygameicon_iv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.mygamename_iv.setText(list.get(position));
        holder.mygameicon_iv.setTag(null);
        Glide.with(context).load("https://img.tapimg.com/market/lcs/2ea1a63c45fe294d36e29e348d441ea3_360.png?imageMogr2/auto-orient/strip").animate(R.anim.item_alpha_in).thumbnail(0.1f).into(holder.mygameicon_iv);
        return convertView;
    }



    /**
     * 控件缓存类
     *
     * @author 邹奇
     *
     */
    class ViewHolder {
        TextView mygamename_iv;
        ImageView mygameicon_iv;
    }

}
