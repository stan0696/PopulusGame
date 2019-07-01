package com.example.myapplication2.View;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication2.GameinfoActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.Rankingfrag_2;
import com.example.myapplication2.SQLiteDbHelper;
import com.example.myapplication2.Tools.DownloadActivity;
import com.example.myapplication2.Tools.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class RankingfragAdapter extends BaseAdapter {
    private List<String[]> list = new ArrayList<>();
    private Context context;
    private String[] downloadgameinfo = new String[3];
    public RankingfragAdapter (List<String[]> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        MyListener myListener=new MyListener(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.rankingfrag_item ,parent, false);
            holder.Ranktext1_1 = (TextView) convertView.findViewById(R.id.Ranktext1_1);
            holder.Rankname1_1 = convertView.findViewById(R.id.Rankname1_1);
            holder.Rankimage1_1 = convertView.findViewById(R.id.Rankimage1_1);
            holder.Rankintro1_1 = convertView.findViewById(R.id.Rankintro1_1);
            holder.Rankbtn1_1 =convertView.findViewById(R.id.Rankbtn1_1);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.Ranktext1_1.setText(list.get(position)[0]);
        holder.Rankimage1_1.setTag(null);
        Glide.with(context).load(list.get(position)[1]).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(holder.Rankimage1_1);
        holder.Rankintro1_1.setText(list.get(position)[2]);
        holder.Rankname1_1 .setText(list.get(position)[3]);
        holder.Rankbtn1_1.setOnClickListener(myListener);
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
        Button  Rankbtn1_1;

    }
    private class MyListener implements View.OnClickListener {
        int mPosition;
        public MyListener(int inPosition){
            mPosition= inPosition;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DownloadActivity.class);
                SQLiteDbHelper helper = new SQLiteDbHelper(context);
                SQLiteDatabase database = helper.getWritableDatabase();
                System.out.println(list.get(mPosition)[3]);
                Cursor cursor = database.query("game", null, "name=?", new String[]{list.get(mPosition)[3]}, null, null, null);//修改
                if (cursor.moveToFirst()) {
                    downloadgameinfo[0]=cursor.getString(4);
                    downloadgameinfo[1]=cursor.getString(0);
                    downloadgameinfo[2]=cursor.getString(2);
                }
            System.out.println(downloadgameinfo[0]);
                database.close();
            if (downloadgameinfo[0].equals("no_this_game")){
                Toast.makeText(context,"暂无下载",Toast.LENGTH_SHORT).show();
            }else {
                /**此处需修改*/ intent.putExtra("downloadurl",downloadgameinfo);
                context.startActivity(intent);
            }


        }

    }


}
