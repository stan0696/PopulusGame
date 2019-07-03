package com.example.myapplication2.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication2.DBClass.DBGameService;
import com.example.myapplication2.DBClass.DBUserService;
import com.example.myapplication2.GameinfoActivity;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.MyGameActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.SQLiteDbHelper;
import com.example.myapplication2.Tools.DownloadActivity;
import com.example.myapplication2.Tools.NotificationAdapter;
import com.example.myapplication2.View.MyImageView;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication2.View.MyImageView.GET_DATA_SUCCESS;

public class MyGameActivityAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private List<String> iconlist = new ArrayList<>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.mygame_item, parent, false);
            holder.mygamename_iv = convertView.findViewById(R.id.mygamename_iv);
            holder.mygameicon_iv= convertView.findViewById(R.id.mygameicon_iv);
            convertView.setTag(holder);
            if(list.size()==1)
            {
                list.clear();
                gameThread gt=new gameThread();
                gt.start();
            }

        }
            if(list.size()!=0)
            {
            holder = (ViewHolder) convertView.getTag();
            holder.mygamename_iv.setText(list.get(position));

            holder.mygameicon_iv.setTag(null);
            Glide.with(context).load(iconlist.get(position)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(holder.mygameicon_iv);
            holder.mygamename_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GameinfoActivity.class);
                    intent.putExtra("gamename",list.get(position));
                    context.startActivity(intent);

                }
            });
            holder.mygameicon_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GameinfoActivity.class);
                    intent.putExtra("gamename",list.get(position));
                    context.startActivity(intent);

                }
            });
            }


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
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    notifyDataSetChanged();

                    break;
            }
        }
    };
    class gameThread extends Thread{

        @SuppressLint("HandlerLeak")
        @Override
        public  void run()
        {
            SQLiteDbHelper helper = new SQLiteDbHelper(context);
            SQLiteDatabase database = helper.getWritableDatabase();
            String[]tempname=new String[100];
            String[]tempicon=new String[100];
            System.out.println("开启线程");
            DBUserService dbUserService=DBUserService.getDbUserService();
            tempname=dbUserService.findUserGame(MainActivity.Username);
            for(int k=0;k<tempname.length&&tempname[k]!=null;k++)
            {
                Cursor cursor = database.query("game", null, "name=?",
                        new String[]{tempname[k]}, null, null, null);
                if (cursor.moveToFirst())
                {
                    tempicon[k]=cursor.getString(2);
                    list.add(tempname[k]);
                    iconlist.add(tempicon[k]);
                }


            }

            System.out.println("读取成功");
            Message msg = Message.obtain();

            msg.what = GET_DATA_SUCCESS;
            handler.sendMessage(msg);

        }
    }


}
