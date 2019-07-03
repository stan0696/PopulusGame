package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.ListView;


import com.example.myapplication2.Tools.DownloadManagerUtil;
import com.example.myapplication2.Tools.NotificationAdapter;
import com.example.myapplication2.Tools.NotificationController;

import org.jsoup.select.Evaluator;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private ListView Notification_ss;
    private List<String[]> list = new ArrayList<>();
    private  NotificationAdapter adapter;
    private List<String[]> alreadylist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);
        ImageView back_btn = (ImageView)findViewById(R.id.notification_back);
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        setViews();// 控件初始化
        setData();// 给listView设置adapter
        setListeners();// 设置监听
    }
    private void setData() {
        initData();// 初始化数据
        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
        adapter = new NotificationAdapter(list,this);
        Notification_ss.setAdapter(adapter);
    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */
    protected void setItemClick(final List<String[]> filter_lists) {
        Notification_ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
                Intent intent = new Intent(NotificationActivity.this.getBaseContext(), GameinfoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 简单的list集合添加一些测试数据
     */
    private void initData() {
        NotificationController notificationController=new NotificationController(getApplicationContext());
        notificationController.updata("populusgame",001,"https://img.tapimg.com/market/lcs/2ea1a63c45fe294d36e29e348d441ea3_360.png?imageMogr2/auto-orient/strip","欢迎使用populusgame");
        getdata();
        list=alreadylist;

    }


    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);
    }

    /**
     * 控件初始化
     */
    private void setViews() {
        Notification_ss = (ListView)findViewById(R.id.Notification_ss);// ListView控件
    }


    private void getdata(){
        SQLiteDbHelper helper = new SQLiteDbHelper(getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("notificationlist", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            alreadylist.add(new String[]{cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)});

            while (cursor.moveToNext()) {
                alreadylist.add(
                        new String[]{cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3)});
            }
        }
        database.close();
    }
}
