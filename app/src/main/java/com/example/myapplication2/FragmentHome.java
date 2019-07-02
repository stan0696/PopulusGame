package com.example.myapplication2;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class FragmentHome extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setViews();// 控件初始化
        setData();// 给listView设置adapter
        setListeners();// 设置监听
        return rootView;
    }


    private void setData() {
        initData();// 初始化数据

    }


    /**
     * 简单的list集合添加一些测试数据
     */
    private void initData() {


    }


    private void setListeners() {

    }


    private void setViews() {

    }
}