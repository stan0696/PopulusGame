package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.DBClass.DBGameService;
import com.bumptech.glide.Glide;
import com.example.myapplication2.DBClass.DBUserService;
import com.example.myapplication2.Tools.DownloadActivity;
import com.example.myapplication2.Tools.FindGame;
import com.example.myapplication2.Tools.SearchActivity;
import com.example.myapplication2.View.Game;
import com.example.myapplication2.View.MyImageView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import java.net.URI;
import java.util.ArrayList;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication2.DBClass.DBGameService.dbgameservice;

public class GameinfoActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private Handler handler;
    private MyImageView iconview;

    private TextView nametext;
    private ImageView imageView_introduce;

    private TextView marktext;
    private Button tag1text;
    private Button  tag2text;
    private Button  tag3text;
    private Button  button_focus;
    private Button  button_download;
    private String name;
    private String iconurl;
    private String[] downloadgameinfo = new String[3];
    private String introduction;
    private String[] tittleimg = new String[5];
    private String tag1;
    private String tag2;
    private String tag3;
    private int state;
    private float mark;
    private boolean isdownload;
    private TabLayout tabLayout;
    private WrapContentHeightViewPager mViewPager;
    List<Fragment> fragmentList = new ArrayList<>();
    Fragment fragmentDetails;
    Fragment fragmentComment;
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mark=9.9f;
        setContentView(R.layout.activity_gameinfo);
        Intent intent = getIntent();
        name = intent.getStringExtra("gamename");

        mViewPager = findViewById(R.id.viewPager_gameinfo);
        tabLayout = findViewById(R.id.mytab);
        getdata();
        initFragments();


        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("详情");
        tabLayout.getTabAt(1).setText("评论");
        gameThread gt = new gameThread();
        gt.start();
        setlistener();


    }

    private void initFragments(){
        fragmentDetails = new FragmentDetails(introduction,tittleimg);
        fragmentComment = new FragmentComment(name);
        fragmentList.add(fragmentDetails);
        fragmentList.add(fragmentComment);
        mViewPager.setAdapter(new ViewPagerAdapter(fragmentList,getSupportFragmentManager()));


    }

    private void setlistener()
    {
        button_download=findViewById(R.id.button_download);
        if (downloadgameinfo[0].equals("no_this_game")){
            button_download.setText("暂无下载");
        }else{

            button_download.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Message m;
                    m=handler.obtainMessage();//获取事件
                    m.what=1;
                    handler.sendMessage(m);


                }


            });
        }


        button_focus=findViewById(R.id.button_focus);
        button_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.Username!=null)
                {
                    Message M;
                    M=handler.obtainMessage();
                    M.arg1=1;
                    handler.sendMessage(M);
                }
                else
                {
                    Toast.makeText(GameinfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }




    private void getdata() {

        SQLiteDbHelper helper = new SQLiteDbHelper(getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cValue = new ContentValues();
        String [] names=new String[1];
        names[0]=this.name;
        Cursor cursor = database.query("game", null, "name=?", new String[]{this.name}, null, null, null);//修改
        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            this.name= cursor.getString(0);
            this.iconurl=cursor.getString(2);
            this.introduction=cursor.getString(3);
            this.downloadgameinfo[0]=cursor.getString(4);
            this.downloadgameinfo[1]=name;
            this.downloadgameinfo[2]=iconurl;
            this.tag1=cursor.getString(6);
            this.tag2=cursor.getString(7);
            this.tag3=cursor.getString(8);
            this.tittleimg[0]=cursor.getString(5);
            this.tittleimg[1]=cursor.getString(10);
            this.tittleimg[2]=cursor.getString(11);
            this.tittleimg[3]=cursor.getString(12);
            this.tittleimg[4]=cursor.getString(13);
            iconview=(MyImageView)findViewById(R.id.imageView_game);
            nametext=findViewById(R.id.game_textname);

            iconview.setImageURL(iconurl);
            imageView_introduce=findViewById(R.id.imageView_introduce);


            iconview.setTag(null);
            Glide.with(this.getBaseContext()).load(iconurl).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(iconview);

            //iconview.setImageURL(iconurl);
            imageView_introduce.setTag(null);
            Glide.with(this.getBaseContext()).load(tittleimg[0]).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(imageView_introduce);

            nametext.setText(name);

            if(tag1!=null)
            {
                tag1text=findViewById(R.id.gametag1);
                tag1text.setText(tag1);
            }

            if(tag2!=null)
            {
                tag2text=findViewById(R.id.gametag2);
                tag2text.setText(tag2);
            }

            if(tag3!=null)
            {
                tag3text=findViewById(R.id.gametag3);
                tag3text.setText(tag3);
            }
        }
        database.close();
    }



    class gameThread extends Thread{

            @SuppressLint("HandlerLeak")
            @Override
            public  void run()
            {
                final DBGameService dbgameservice=new DBGameService();
                mark= dbgameservice.getAvgMark(name);//从云端获取平均分
                final DBUserService dbUserService=DBUserService.getDbUserService();
                state=dbUserService.findGameState(MainActivity.Username,name);
                button_focus=findViewById(R.id.button_focus);
                if(state==1)
                {
                    button_focus.setText("已关注");
                }

                marktext=findViewById(R.id.game_text_mark);
                marktext.setText(String.valueOf(mark));
                Looper.prepare();
                handler = new Handler(){
                    @Override
                    public void handleMessage(Message m)
                    {


                        if(m.what==1)
                        {
                            DBGameService dbgameservice=DBGameService.getDbGameService();
                            int downloadnum = dbgameservice.getGameDL(name);
                            downloadnum++;
                            dbgameservice.updateGameDL(name,downloadnum);
                            Intent intent = new Intent(GameinfoActivity.this.getBaseContext(), DownloadActivity.class);
                            intent.putExtra("downloadurl",downloadgameinfo);startActivity(intent);
                        }
                        if(m.arg1==1)
                        {

                            DBUserService dbUserService1=DBUserService.getDbUserService();
                            dbUserService.focusGame(MainActivity.Username,name,state);
                            state=dbUserService.findGameState(MainActivity.Username,name);

                            button_focus=findViewById(R.id.button_focus);



                            switch(state)
                            {
                                case 0:button_focus.setText("关注");break;

                                case 1:button_focus.setText("已关注");break;

                                case 2:button_focus.setText("关注");break;




                            }



                        }

                    }


                };
                Looper.loop();
            }
    }

}

