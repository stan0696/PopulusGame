package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.myapplication2.Tools.DownloadActivity;
import com.example.myapplication2.Tools.NotificationAdapter;
import com.example.myapplication2.Tools.SearchActivity;
import com.example.myapplication2.View.RankingfragAdapter;

import java.util.ArrayList;
import java.util.List;

public class Rankingfrag_2 extends Fragment{
    private ListView rankingfrag_list;
    private List<String[]> list = new ArrayList<>();
    private RankingfragAdapter adapter;
    private  View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rankingfrag_2, container, false);
        setViews();// 控件初始化
        initData();// 初始化数据
        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
        adapter = new RankingfragAdapter(list,getActivity());
        rankingfrag_list.setAdapter(adapter);
        setData();// 给listView设置adapter
        setListeners();// 设置监听
        return rootView;

    }
    private void setData() {

    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */


    /**
     * 简单的list集合添加一些测试数据
     */
    private void initData() {
        String[] list2 = new String[4];
        list2[0]="1";  //排名
        list2[1]="https://img.tapimg.com/market/lcs/2ea1a63c45fe294d36e29e348d441ea3_360.png?imageMogr2/auto-orient/strip"; //图标
        list2[2]="这是简介";  //
        list2[3]="纪念碑谷";
        List<String[]> newlist = new ArrayList<>();
        newlist.add(list2);
        list=newlist;
    }


    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);
    }

    protected void setItemClick(final List<String[]> filter_lists) {
        rankingfrag_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
                Intent intent = new Intent(Rankingfrag_2.this.getContext(), GameinfoActivity.class);
                intent.putExtra("gamename",filter_lists.get(position)[3]);
                startActivity(intent);
            }
        });
    }
    /**
     * 控件初始化
     */
    private void setViews() {
        rankingfrag_list = (ListView)rootView.findViewById(R.id.rankingfrag_list2);// ListView控件
    }

}