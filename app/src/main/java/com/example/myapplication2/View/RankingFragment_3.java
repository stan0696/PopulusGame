package com.example.myapplication2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.myapplication2.R;
import com.example.myapplication2.Tools.DownloadActivity;
import com.example.myapplication2.Tools.NotificationAdapter;
import com.example.myapplication2.Tools.SearchActivity;
import com.example.myapplication2.View.Rank;
import com.example.myapplication2.View.Rankbydl;
import com.example.myapplication2.View.Rankbymark;
import com.example.myapplication2.View.RankingfragAdapter;

import java.util.ArrayList;
import java.util.List;

public class RankingFragment_3 extends Fragment{
    private ListView rankingfrag_list;
    private List<Rank> list = new ArrayList<>();
    private List<Rankbydl> list1 = new ArrayList<>();

    private RankingfragAdapter3 adapter;
    private  View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ranking_fragment_3, container, false);
        setViews();// 控件初始化
        initData();// 初始化数据
        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
        adapter = new RankingfragAdapter3(list1,getActivity());
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
        Rankbydl rk=new Rankbydl(1,9.9f);
        list1.add(rk);


    }


    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);
    }

    protected void setItemClick(final List<Rank> filter_lists) {
        rankingfrag_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容

            }
        });
    }
    /**
     * 控件初始化
     */
    private void setViews() {
        rankingfrag_list = (ListView)rootView.findViewById(R.id.rankingfrag_list3);// ListView控件
    }

}