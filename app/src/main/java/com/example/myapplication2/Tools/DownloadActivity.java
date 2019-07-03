package com.example.myapplication2.Tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.GameinfoActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.SQLiteDbHelper;
import com.example.myapplication2.View.MyImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private ListView downloadv_ss;
    private List<String[]> list = new ArrayList<>();
    private DownloadManager downloadManager;
    private DownloadAdapter adapter = null;
    private DownloadManagerUtil newdownload;
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private  String[] downloadgameinfo ;
    private  List<String[]> alreadydownloadgameinfo = new ArrayList<>();
    private boolean isRegisterReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_main);
        ImageView back_btn = (ImageView)findViewById(R.id.downloadcenter_back);
        setViews();// 控件初始化
        SetData();// 给listView设置adapter
        setListeners();// 设置监听
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    private void SetData() {
        initData();// 初始化数据
        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
        adapter = new DownloadAdapter(list, this);
        downloadv_ss.setAdapter(adapter);

    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */
    protected void setItemClick(final List<String[]> filter_lists) {
        downloadv_ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        newdownload = new DownloadManagerUtil(this);
        Intent intent = getIntent();
        downloadgameinfo = intent.getStringArrayExtra("downloadurl");
        if (downloadgameinfo!=null){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                newdownload.downloadAPK(downloadgameinfo[0],downloadgameinfo[1]);
                downloadManager = (DownloadManager)this.getSystemService(Context.DOWNLOAD_SERVICE);
                setdata(newdownload);
                getdata();
                list=alreadydownloadgameinfo;
            }
            else
            {
                newdownload.downloadAPK(downloadgameinfo[0],downloadgameinfo[1]);
                downloadManager = (DownloadManager)this.getSystemService(Context.DOWNLOAD_SERVICE);
                setdata(newdownload);
                getdata();
                list=alreadydownloadgameinfo;
            }
        }
        else {
            getdata();
            list=alreadydownloadgameinfo;
        }

    }




    /**
     * 注册下载成功的广播监听
     */
    private void setReceiver() {
        if (!isRegisterReceiver) {
            DownloadReceiver receiver = new DownloadReceiver();
            IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            this.registerReceiver(receiver, intentFilter);
            isRegisterReceiver = true;
        }
    }



    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);
    }

    /**
     * 控件初始化
     */
    private void setViews() {
        downloadv_ss = (ListView)findViewById(R.id.downloadv_ss);// ListView控件
    }

    /**
     * 请求权限
     */
    int Code_PERMISSION = 0;
    /**
     * 权限申请
     * @return
     */

    //4. 接收申请成功或者失败回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Code_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
                newdownload.downloadAPK(downloadgameinfo[0],downloadgameinfo[1]);
                downloadManager = (DownloadManager)this.getSystemService(Context.DOWNLOAD_SERVICE);
                setdata(newdownload);
                getdata();
                list=alreadydownloadgameinfo;
            } else {
                //权限被用户拒绝，做相应的事情
                Toast.makeText(this,"拒绝了权限",Toast.LENGTH_SHORT);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void setdata(DownloadManagerUtil download){
        SQLiteDbHelper helper = new SQLiteDbHelper(getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cValue = new ContentValues();
        cValue.put("name",downloadgameinfo[1]);
        cValue.put("icon",downloadgameinfo[2]);
        cValue.put("id",download.getDownloadId());
        database.insert(SQLiteDbHelper.TABLE_DOWNLOAD, null,cValue);
        database.close();
    }
    private void getdata(){
        SQLiteDbHelper helper = new SQLiteDbHelper(getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("downloadgame", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            alreadydownloadgameinfo.add(new String[]{cursor.getString(1),
                    cursor.getString(0),
                    cursor.getString(2)});

            while (cursor.moveToNext()) {
                alreadydownloadgameinfo.add(
                        new String[]{cursor.getString(1),
                                cursor.getString(0),
                                cursor.getString(2)});
            }
        }
        database.close();
    }
    public void updateView(int itemIndex) {
        //得到第一个可显示控件的位置，
        int fvisiblePosition = downloadv_ss.getFirstVisiblePosition();

        //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
        if (itemIndex >= fvisiblePosition ) {
            //得到要更新的item的view
            View view = downloadv_ss.getChildAt(itemIndex - fvisiblePosition);
            //从view中取得holder
            ProgressBar item;
            item =view.findViewById(R.id.downloadprogressBar);
            TextView   tvitem;
            tvitem = view.findViewById(R.id.downloadgameprogress);
            //获取到具体的控件，
            DownloadManagerUtil nowdownload = new DownloadManagerUtil(getBaseContext());
            nowdownload.setDownloadId(Long.parseLong(list.get(itemIndex - fvisiblePosition)[0]));
            item.setProgress(nowdownload.getDownloadSoFar());
            tvitem.setText(nowdownload.getDownloadedSoFar()+"/"+nowdownload.getTotalSize());
        }
    }


}
