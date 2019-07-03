package com.example.myapplication2.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.SQLiteDbHelper;
import com.example.myapplication2.View.MyImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends BaseAdapter {
    private List<String[]> list = new ArrayList<>();
    private Context context;
    public NotificationAdapter (List<String[]> list, Context context) {
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
        MyListener myListener=new MyListener(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
            holder.notification_tv = (TextView) convertView.findViewById(R.id.notification_tv);
            holder.gamename_iv = convertView.findViewById(R.id.gamename_iv);
            holder.delete_iv = convertView.findViewById(R.id.notification_delete);
            holder.gameicon_iv = convertView.findViewById(R.id.gameicon_iv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.gamename_iv.setText(list.get(position)[0]);
        holder.gameicon_iv.setImageURL(list.get(position)[2]);
        holder.notification_tv.setText(list.get(position)[3]);
        holder.delete_iv.setOnClickListener(myListener);
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
        ImageView delete_iv;
    }
    private class MyListener implements View.OnClickListener {
        int mPosition;

        public MyListener(int inPosition) {
            mPosition = inPosition;
        }

        @Override
        public void onClick(View v) {
            SQLiteDbHelper helper = new SQLiteDbHelper(context);
            SQLiteDatabase database = helper.getWritableDatabase();
            database.delete("notificationlist","id=?",new String[]{list.get(mPosition)[1]});
            list.remove(mPosition);
            notifyDataSetChanged();
        }
    }
}

