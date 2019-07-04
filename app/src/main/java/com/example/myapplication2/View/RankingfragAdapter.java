package com.example.myapplication2.View;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication2.DBClass.DBGameService;
import com.example.myapplication2.FragmentExplore;
import com.example.myapplication2.GameinfoActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.Rankingfrag_2;
import com.example.myapplication2.SQLiteDbHelper;
import com.example.myapplication2.Tools.DownloadActivity;
import com.example.myapplication2.Tools.NotificationAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.myapplication2.View.MyImageView.GET_DATA_SUCCESS;

public class RankingfragAdapter extends BaseAdapter {
    private List<Rankbymark> list = new ArrayList<>();
    private Context context;
    private String[] downloadgameinfo = new String[3];
    private Rankbymark[] temprank = new Rankbymark[100];
    public RankingfragAdapter (List<Rankbymark> list, Context context) {
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
        System.out.println(list+"list");
        if (convertView == null)
        {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.rankingfrag_item ,parent, false);
            holder.Ranktext1_1 = (TextView) convertView.findViewById(R.id.Ranktext1_1);


            holder.Rankname1_1 = convertView.findViewById(R.id.Rankname1_1);
            holder.Rankimage1_1 = convertView.findViewById(R.id.Rankimage1_1);
            holder.Rankintro1_1 = convertView.findViewById(R.id.Rankintro1_1);
            convertView.setTag(holder);
            if(list.size()==1)
            {
                list.clear();
                rankThread rt=new rankThread();
                rt.start();
            }

        }

if(list.size()!=0)
{
    holder = (ViewHolder) convertView.getTag();
    holder.Ranktext1_1.setText(String.valueOf(position+1));

    holder.Rankimage1_1.setTag(null);
    Glide.with(context).load(list.get(position).getIcon()).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(holder.Rankimage1_1);
    holder.Rankintro1_1.setText(String.valueOf(list.get(position).getMark()));
    holder.Rankname1_1 .setText(list.get(position).getGamename());
    holder.Rankname1_1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GameinfoActivity.class);
            intent.putExtra("gamename",list.get(position).getGamename());
            context.startActivity(intent);

        }
    });
    holder.Rankimage1_1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GameinfoActivity.class);
            intent.putExtra("gamename",list.get(position).getGamename());
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
        TextView Ranktext1_1;
        TextView Rankname1_1;
        MyImageView Rankimage1_1;
        TextView  Rankintro1_1;
        Button  Rankbtn1_1;

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                  for(int k=0;k<100;k++)
                      if(temprank[k]!=null)
                      {
                          list.add(temprank[k]);
                          Collections.sort(list);//根据评分排序
                          notifyDataSetChanged();
                      }


                    break;
            }
        }
    };
    class rankThread extends Thread{

        @SuppressLint("HandlerLeak")
        @Override
        public  void run()
        {

            System.out.println("开启线程");

            DBGameService dbGameService=new DBGameService();
            String [][]temp=dbGameService.getGameMarkSort();
            SQLiteDbHelper helper = new SQLiteDbHelper(context);

            SQLiteDatabase database = helper.getWritableDatabase();
            Cursor cursor = database.query("game", null, null, null, null, null, null);
            int k=0;
            while(cursor.moveToNext()&&k<100)
            {
                temprank[k]=new Rankbymark(1,1);

                temprank[k].setGamename(cursor.getString(0));
                for(int m=0;m<100&&temp[m][0]!=null;m++)
                {


                    if(cursor.getString(0).equals(temp[m][0]))
                    {
                        System.out.println(77777777);
                        System.out.println(temprank[k].getGamename());
                        temprank[k].setMark(new Float(temp[m][1]));
                        break;
                    }

                    temprank[k].setMark(0);
                }

                temprank[k].setIcon(cursor.getString(2));

                k++;

            }



            Message msg = Message.obtain();
            msg.what = GET_DATA_SUCCESS;
            handler.sendMessage(msg);


        }
    }


}
