package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.myapplication2.Tools.DownloadActivity;
import com.example.myapplication2.Tools.SearchActivity;
import com.example.myapplication2.Tools.SettingActivity;
import com.example.myapplication2.User.UserInfoChangeActivity;
import com.example.myapplication2.User.UserInfoActivity;
import com.example.myapplication2.User.UserLoginActivity;
import com.example.myapplication2.User.UserSecretActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_explore:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_ranking:
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    public static String Username=null;
    private ViewPager mViewPager;
    public static boolean passState=false;
    List<Fragment> fragmentList = new ArrayList<>();
    Fragment fragmentHome;
    Fragment fragmentExplore;
    Fragment fragmentRanking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        if(intent.getStringExtra("Username")!=null)
        {
//            Username=intent.getStringExtra("Username");
//            System.out.println(Username);

        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("             PopulusGame");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView navView = findViewById(R.id.bottom_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        View v = navigationView.getHeaderView(0);
        ImageView img=(ImageView) v.findViewById(R.id.nav_imageView);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Username == null) {
                    Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        mViewPager = findViewById(R.id.viewPager);
        initFragments();

        }

    private void initFragments(){
        fragmentHome = new FragmentHome();
        fragmentExplore = new FragmentExplore();
        fragmentRanking = new FragmentRanking();
        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentExplore);
        fragmentList.add(fragmentRanking);
        mViewPager.setAdapter(new ViewPagerAdapter(fragmentList,getSupportFragmentManager()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
            if (id == R.id.download) {
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);

                startActivity(intent);
            return true;
        }
        if (id == R.id.search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_focus) {

            if(MainActivity.Username!=null)
            {
                Intent intent = new Intent(MainActivity.this, MyGameActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
        else if (id == R.id.nav_game) {
            Intent intent = new Intent(MainActivity.this, MyGameActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_notification) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
