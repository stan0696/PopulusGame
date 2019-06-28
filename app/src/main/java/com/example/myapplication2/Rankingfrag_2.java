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

import com.example.myapplication2.Tools.NotificationAdapter;
import com.example.myapplication2.View.RankingfragAdapter;

import java.util.ArrayList;
import java.util.List;

public class Rankingfrag_2 extends Fragment {
    private ListView rankingfrag_list;
    private List<String> list = new ArrayList<>();
    private RankingfragAdapter adapter;
    private  View rootView;
    private static int i= 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rankingfrag_2, container, false);
        setViews();// 控件初始化
        initData();// 初始化数据
        System.out.println(i);
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
        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list=list2;
        i++;
    }


    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听

    }

    /**
     * 控件初始化
     */
    private void setViews() {
        rankingfrag_list = (ListView)rootView.findViewById(R.id.rankingfrag_list2);// ListView控件
    }

}