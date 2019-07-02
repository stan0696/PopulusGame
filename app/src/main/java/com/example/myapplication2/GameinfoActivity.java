package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

public class GameinfoActivity extends AppCompatActivity {

    private MyImageView iconview;
    private TextView introductiontext;
    private TextView nametext;
    private ImageView imageView_introduce;

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
    private TabLayout tabLayout;
    private WrapContentHeightViewPager mViewPager;
    List<Fragment> fragmentList = new ArrayList<>();
    Fragment fragmentDetails;
    Fragment fragmentComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private void initFragments(){
        fragmentDetails = new FragmentDetails(tittleimg);
        fragmentComment = new FragmentComment();
        fragmentList.add(fragmentDetails);
        fragmentList.add(fragmentComment);
        mViewPager.setAdapter(new ViewPagerAdapter(fragmentList,getSupportFragmentManager()));
        setlistener();
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

                    Intent intent = new Intent(GameinfoActivity.this.getBaseContext(), DownloadActivity.class);
                    /**此处需修改*/ intent.putExtra("downloadurl",downloadgameinfo);
                    startActivity(intent);
                }
            });
        }

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
            introductiontext=findViewById(R.id.game_textIntroduce);
            nametext=findViewById(R.id.game_textname);
            imageView_introduce=findViewById(R.id.imageView_introduce);


            iconview.setTag(null);
            Glide.with(this.getBaseContext()).load(iconurl).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(iconview);
            //iconview.setImageURL(iconurl);
            imageView_introduce.setTag(null);
            Glide.with(this.getBaseContext()).load(tittleimg[0]).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(imageView_introduce);



            introductiontext.setText(introduction);
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

}

