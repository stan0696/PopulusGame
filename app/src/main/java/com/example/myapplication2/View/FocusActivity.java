package com.example.myapplication2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import com.example.myapplication2.R;
import com.example.myapplication2.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FocusActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    Fragment focus_Game;
    Fragment focus_User;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_main);
        ImageView back_btn = (ImageView)findViewById(R.id.focus_back);
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        mViewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.mytab);
        initFragments();
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("游戏");
        tabLayout.getTabAt(1).setText("用户");
    }
    private void initFragments(){
        focus_Game = new FocusGameActivity();
        focus_User = new FocusUserActivity();
        fragmentList.add(focus_Game);
        fragmentList.add(focus_User);
        mViewPager.setAdapter(new ViewPagerAdapter(fragmentList,getSupportFragmentManager()));
    }
}
