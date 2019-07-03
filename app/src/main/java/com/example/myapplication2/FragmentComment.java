package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

import com.example.myapplication2.DBClass.DBGameService;
import com.example.myapplication2.Tools.DownloadActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication2.View.MyImageView.GET_DATA_SUCCESS;

public class FragmentComment extends Fragment {
    private ScrollView scrollView;
    private ListView commentlist;
    private List<String[]> list = new ArrayList<>();
    private View Rootview;
    private String name;
    private FloatingActionButton fab;
    private CommentAdapter adapter;
    public FragmentComment(String name) {

        this.name = name;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);
        Rootview = rootView;
        setViews();

        initData();
        adapter = new CommentAdapter(list, getActivity(),name);
        commentlist.setAdapter(adapter);
        scrollView=Rootview.findViewById(R.id.comment_scrollview);

        adapter.notifyDataSetChanged();
        gameThread gt=new gameThread();
        gt.start();
        scrollView.requestDisallowInterceptTouchEvent(false);

        return rootView;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("gamename", name);
                startActivity(intent);


            }
        });


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


            System.out.println("运行");
            String[] comment = new String[3];
            comment[0] ="hyf";
            comment[1] ="10";
            comment[2] ="10";
            list.add(comment);

    }



    /**
     * 控件初始化
     */
    private void setViews() {
        commentlist = (ListView) Rootview.findViewById(R.id.comment_list);// ListView控件
    }




    class gameThread extends Thread{

        @SuppressLint("HandlerLeak")
        @Override
        public  void run()
        {
           Looper.prepare();


           Looper.loop();


        }
    }



}

