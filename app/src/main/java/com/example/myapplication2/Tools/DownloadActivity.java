package com.example.myapplication2.Tools;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.example.myapplication2.GameinfoActivity;
import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private ListView downloadv_ss;
    private List<DownloadController> list = new ArrayList<>();
    boolean isFilter;
    private DownloadAdapter adapter = null;
    private DownloadController newdownload;
    private boolean isRegisterReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_main);
        ImageView back_btn = (ImageView)findViewById(R.id.download_back);
        setViews();// 控件初始化
        setData();// 给listView设置adapter
        setListeners();// 设置监听
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }
    private void setData() {
        initData();// 初始化数据
        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
        adapter = new DownloadAdapter(list, this);
        downloadv_ss.setAdapter(adapter);
    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */
    protected void setItemClick(final List<DownloadController> filter_lists) {
        downloadv_ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
                Intent intent = new Intent(DownloadActivity.this.getBaseContext(), GameinfoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 简单的list集合添加一些测试数据
     */
    private void initData() {
        newdownload = new DownloadController(this);
        newdownload.creatdownload("https://d.taptap.com/latest?app_id=32854","sss","sss");
        list.add( newdownload);
    }


    /**
     * 注册下载成功的广播监听
     */
    private void setReceiver() {
        if (!isRegisterReceiver) {
            DownloadReceiver receiver = new DownloadReceiver();
            IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            this.registerReceiver(receiver, intentFilter);
            isRegisterReceiver = true;
        }
    }



    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);
    }

    /**
     * 控件初始化
     */
    private void setViews() {
        downloadv_ss = (ListView)findViewById(R.id.downloadv_ss);// ListView控件
    }
}
