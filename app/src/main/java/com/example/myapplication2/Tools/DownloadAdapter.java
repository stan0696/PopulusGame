package com.example.myapplication2.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;



public class DownloadAdapter extends BaseAdapter {

    private List<DownloadManagerUtil> list = new ArrayList<>();
    private Context context;
    public DownloadAdapter (List<DownloadManagerUtil> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.download_item, parent, false);
            holder.downloadtv_ss = (TextView) convertView.findViewById(R.id.downloadtv_ss);
            convertView.setTag(holder);
        }
            holder = (ViewHolder) convertView.getTag();
            //holder.downloadtv_ss.setText(list.get(position));
        return convertView;
    }



    /**
     * 控件缓存类
     *
     * @author 邹奇
     *
     */
    class ViewHolder {
        TextView downloadtv_ss;
    }

}