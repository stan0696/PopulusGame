package com.example.myapplication2;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication2.DBClass.DBGameService;
import com.example.myapplication2.GameinfoActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.Rankingfrag_2;
import com.example.myapplication2.SQLiteDbHelper;
import com.example.myapplication2.Tools.DownloadActivity;
import com.example.myapplication2.Tools.NotificationAdapter;
import com.example.myapplication2.View.MyImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication2.View.MyImageView.GET_DATA_SUCCESS;

public class CommentAdapter extends BaseAdapter {
        private List<String[]> list = new ArrayList<>();
        private Context context;
        private String name;
        private String[][] comment = new String[100][3];
        public CommentAdapter (List<String[]> list, Context context,String name) {

            this.list = list;
            this.name=name;
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
        public View getView(final int position, View convertView, final ViewGroup parent) {
            com.example.myapplication2.CommentAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new com.example.myapplication2.CommentAdapter.ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item ,parent, false);
                holder.comment = (TextView) convertView.findViewById(R.id.player_comment1);
                holder.nametext = convertView.findViewById(R.id.player_name);
                holder.rating = convertView.findViewById(R.id.ratingBarplayer);
                if(list.size()==1)
                {
                    list.clear();
                    gameThread gt = new gameThread();
                    gt.start();
                }

                System.out.println("开启线程");
                convertView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                parent.getParent().requestDisallowInterceptTouchEvent(true);break;
                                case MotionEvent.ACTION_MOVE:
                                parent.getParent().requestDisallowInterceptTouchEvent(true);break;
                                case MotionEvent.ACTION_CANCEL:
                                parent.requestDisallowInterceptTouchEvent(false);break;

                        }
                        return true;
                    }

                });
                convertView.setTag(holder);
            }
            if(list.size()!=0)
            {
             holder = (com.example.myapplication2.CommentAdapter.ViewHolder) convertView.getTag();
                holder.comment.setText(list.get(position)[2]);
                holder.nametext.setText(list.get(position)[0]);
                holder.rating.setRating(new Float(list.get(position)[1])/2);
                notifyDataSetChanged();

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
           TextView nametext;
           TextView comment;
           RatingBar rating;

        }
        private class MyListener implements View.OnClickListener {
            int mPosition;
            public MyListener(int inPosition){
                mPosition= inPosition;
            }
            @Override
            public void onClick(View v) {

            }

        }
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case GET_DATA_SUCCESS:
                        for(int k=0;k<100;k++) {
                            if (comment[k][0] != null) {
                                String[] commentlist = new String[3];
                                commentlist[0] = comment[k][0];
                                commentlist[1] = comment[k][1];
                                commentlist[2] = comment[k][2];
                                list.add(commentlist);

                                notifyDataSetChanged();
                                System.out.println(list.get(k)[0]);
                                System.out.println(list.get(k)[1]);
                                System.out.println(list.get(k)[2]);

                            }
                        }
                        break;
                }
            }
        };



        class gameThread extends Thread{

            @SuppressLint("HandlerLeak")
            @Override
            public  void run()
            {
                DBGameService dbGameService=new DBGameService();
                comment = new String[100][3];
                System.out.println(name);
                comment = dbGameService.getGameComment(name);

                Message msg = Message.obtain();
                msg.obj = comment;
                msg.what = GET_DATA_SUCCESS;
                handler.sendMessage(msg);
                System.out.println(comment[0][1]);
                System.out.println(comment[0][2]);
                System.out.println(comment[0][0]);
            }
        }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        CommentAdapter listAdapter = (CommentAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }
    }


