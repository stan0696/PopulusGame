package com.example.myapplication2.Tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication2.FilterListener;
import com.example.myapplication2.GameinfoActivity;
import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private ListView downloadv_ss;
    private List<String> list = new ArrayList<String>();
    boolean isFilter;
    private DownloadAdapter adapter = null;
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
        adapter = new DownloadAdapter(list, this, new FilterListener() {
            // 回调方法获取过滤后的数据
            public void getFilterData(List<String> list) {
                // 这里可以拿到过滤后数据，所以在这里可以对搜索后的数据进行操作
                Log.e("TAG", "接口回调成功");
                Log.e("TAG", list.toString());
                setItemClick(list);
            }
        });
        downloadv_ss.setAdapter(adapter);
    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */
    protected void setItemClick(final List<String> filter_lists) {
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
        list.add("下载项目1");
        list.add("下载项目2");
        list.add("下载项目3");
        list.add("下载项目1");
        list.add("下载项目2");
        list.add("下载项目3");
        list.add("下载项目1");
        list.add("下载项目2");
        list.add("下载项目3");

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
