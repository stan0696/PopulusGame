package com.example.myapplication2;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication2.DBClass.DBGameService;

import static com.example.myapplication2.View.MyImageView.GET_DATA_SUCCESS;


public class FragmentHome extends Fragment{
    private View Rootview;
    private ConstraintLayout cl1;
    private ConstraintLayout cl2;
    private ConstraintLayout cl3;
    private ConstraintLayout cl4;
    private ImageView iconview1;
    private ImageView postview1;
    private TextView nametext1;
    private TextView introuductiontext1;
    private TextView marktext1;
    private ImageView iconview2;
    private ImageView postview2;
    private TextView nametext2;
    private TextView introuductiontext2;
    private TextView marktext2;
    private ImageView iconview3;
    private ImageView postview3;
    private TextView nametext3;
    private TextView introuductiontext3;
    private TextView marktext3;
    private ImageView iconview4;
    private ImageView postview4;
    private TextView nametext4;
    private TextView introuductiontext4;
    private TextView marktext4;
    private float mark1;
    private float mark2;
    private float mark3;
    private float mark4;
    public FragmentHome()
    {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Rootview = inflater.inflate(R.layout.fragment_home, container, false);

        setViews();// 控件初始化
        setData();// 给listView设置adapter
        setListeners();// 设置监听
        gameThread gt=new gameThread();
        gt.start();

        return Rootview;
    }


    private void setData() {
        initData();// 初始化数据

        SQLiteDbHelper helper = new SQLiteDbHelper(Rootview.getContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("game", null, "name=?", new String[]{"明日方舟 CN"}, null, null, null);
        if(cursor.moveToFirst())
        {
            Glide.with(Rootview.getContext()).load(cursor.getString(2)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(iconview1);
            Glide.with(Rootview.getContext()).load(cursor.getString(5)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(postview1);
            introuductiontext1.setText(cursor.getString(3));
            nametext1.setText(cursor.getString(0));


        }

         cursor = database.query("game", null, "name=?", new String[]{"脑裂"}, null, null, null);
        if(cursor.moveToFirst())
        {
            Glide.with(Rootview.getContext()).load(cursor.getString(2)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(iconview2);
            Glide.with(Rootview.getContext()).load(cursor.getString(5)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(postview2);
            introuductiontext2.setText(cursor.getString(3));
            nametext2.setText(cursor.getString(0));


        }
        cursor = database.query("game", null, "name=?", new String[]{"濡沫江湖"}, null, null, null);
        if(cursor.moveToFirst())
        {
            Glide.with(Rootview.getContext()).load(cursor.getString(2)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(iconview3);
            Glide.with(Rootview.getContext()).load(cursor.getString(5)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(postview3);
            introuductiontext3.setText(cursor.getString(3));
            nametext3.setText(cursor.getString(0));


        }
        cursor = database.query("game", null, "name=?", new String[]{"王者荣耀 CN"}, null, null, null);
        if(cursor.moveToFirst())
        {
            Glide.with(Rootview.getContext()).load(cursor.getString(2)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(iconview4);
            Glide.with(Rootview.getContext()).load(cursor.getString(5)).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(postview4);
            introuductiontext4.setText(cursor.getString(3));
            nametext4.setText(cursor.getString(0));


        }







    }


    /**
     * 简单的list集合添加一些测试数据
     */
    private void initData() {
        iconview1=Rootview.findViewById(R.id.imageView_game0);
        nametext1=Rootview.findViewById(R.id.game_name0);
        postview1=Rootview.findViewById(R.id.imageView_introduce0);
        marktext1=Rootview.findViewById(R.id.point0);
        introuductiontext1=Rootview.findViewById(R.id.game_introduce0);

        iconview2=Rootview.findViewById(R.id.imageView_game1);
        nametext2=Rootview.findViewById(R.id.game_name1);
        postview2=Rootview.findViewById(R.id.imageView_introduce1);
        marktext2=Rootview.findViewById(R.id.point1);
        introuductiontext2=Rootview.findViewById(R.id.game_introduce1);

        iconview3=Rootview.findViewById(R.id.imageView_game2);
        nametext3=Rootview.findViewById(R.id.game_name2);
        postview3=Rootview.findViewById(R.id.imageView_introduce2);
        marktext3=Rootview.findViewById(R.id.point2);
        introuductiontext3=Rootview.findViewById(R.id.game_introduce2);

        iconview4=Rootview.findViewById(R.id.imageView_game3);
        nametext4=Rootview.findViewById(R.id.game_name3);
        postview4=Rootview.findViewById(R.id.imageView_introduce3);
        marktext4=Rootview.findViewById(R.id.point3);
        introuductiontext4=Rootview.findViewById(R.id.game_introduce3);

        cl1=Rootview.findViewById(R.id.cl1);
        cl2=Rootview.findViewById(R.id.cl2);
        cl3=Rootview.findViewById(R.id.cl3);
        cl4=Rootview.findViewById(R.id.cl4);

    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    marktext1.setText(String.valueOf(mark1));
                    marktext2.setText(String.valueOf(mark2));
                    marktext3.setText(String.valueOf(mark3));
                    marktext4.setText(String.valueOf(mark4));

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
            mark1=dbGameService.getAvgMark("明日方舟 CN");
            mark2=dbGameService.getAvgMark("脑裂");
            mark3=dbGameService.getAvgMark("濡沫江湖");
            mark4=dbGameService.getAvgMark("王者荣耀 CN");

            Message msg = Message.obtain();

            msg.what = GET_DATA_SUCCESS;
            handler.sendMessage(msg);

        }
    }
    private void setListeners() {
cl1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(FragmentHome.this.getContext(), GameinfoActivity.class);
        intent.putExtra("gamename","明日方舟 CN");
        startActivity(intent);

    }
});
        cl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FragmentHome.this.getContext(), GameinfoActivity.class);
                intent.putExtra("gamename","脑裂");
                startActivity(intent);

            }
        });
        cl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FragmentHome.this.getContext(), GameinfoActivity.class);
                intent.putExtra("gamename","濡沫江湖");
                startActivity(intent);

            }
        });
        cl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FragmentHome.this.getContext(), GameinfoActivity.class);
                intent.putExtra("gamename","王者荣耀 CN");
                startActivity(intent);

            }
        });


    }


    private void setViews() {

    }
}