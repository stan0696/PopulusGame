package com.example.myapplication2.Tools;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.example.myapplication2.R;
import com.example.myapplication2.View.RankingfragAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private ListView fz_downloadv_ss;
    private List<String[]> list = new ArrayList<>();
    private fzDownloadAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        ImageView back_btn = (ImageView)findViewById(R.id.setting_back);
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
        adapter = new fzDownloadAdapter(list,this);
        fz_downloadv_ss.setAdapter(adapter);
    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */
    protected void setItemClick(final List<String[]> filter_lists) {
        fz_downloadv_ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
    }

    /**
     * 简单的list集合添加一些测试数据
     */
    private void initData() {
        String[] list2 = new String[4];
        list2[0]="辅助";
        list2[1]="https://android-artworks.25pp.com/fs08/2019/05/10/2/1_89ad0a1301660d8f8ad7327e97e1051c_con_130x130.png";
        list2[2]="免费加速海外手游";
        list2[3]="biubiu加速器";
        list.add(list2);

    }





    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);
    }

    /**
     * 控件初始化
     */
    private void setViews() {
        fz_downloadv_ss = (ListView)findViewById(R.id.fz_download_list2);// ListView控件
    }
}
