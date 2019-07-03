package com.example.myapplication2.Tools;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.SQLiteDbHelper;
import com.example.myapplication2.View.MyImageView;

import java.util.ArrayList;
import java.util.List;



public class DownloadAdapter extends BaseAdapter {
    private List<String[]> list = new ArrayList<>();
    private Context context;
    private DownloadManagerUtil newdownload;
    public DownloadAdapter (List<String[]> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.download_item, parent, false);
            holder.downloadtv_ss = (TextView) convertView.findViewById(R.id.downloadtv_ss);
            holder.downloadgameicon = convertView.findViewById(R.id.downloadgameicon);
            holder.downloadgameprogress=convertView.findViewById(R.id.downloadgameprogress);
            holder.download_delete = convertView.findViewById(R.id.download_delete);
            holder.downloadprogressBar = convertView.findViewById(R.id.downloadprogressBar);
            convertView.setTag(holder);
        }
            holder = (ViewHolder) convertView.getTag();
            holder.downloadtv_ss.setText(list.get(position)[1]);
            holder.downloadgameicon.setImageURL(list.get(position)[2]);
            holder.download_delete.setOnClickListener(myListener);
            newdownload = new DownloadManagerUtil(context);
            newdownload.setDownloadId(Long.parseLong(list.get(position)[0]));
            holder.downloadprogressBar.setProgress(newdownload.getDownloadSoFar());
            holder.downloadgameprogress.setText(newdownload.getDownloadedSoFar()+"/"+newdownload.getTotalSize());
            notifyDataSetChanged();
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
        MyImageView downloadgameicon;
        TextView downloadgameprogress;
        ImageView download_delete;
        ProgressBar downloadprogressBar;
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
            database.delete("downloadgame","id=?",new String[]{list.get(mPosition)[0]});
            DownloadManagerUtil deletedownload = new DownloadManagerUtil(context);
            deletedownload.setDownloadId(Long.parseLong(list.get(mPosition)[0]));
            deletedownload.deletedownloadbyid();
            list.remove(mPosition);
            notifyDataSetChanged();
        }
    }
}