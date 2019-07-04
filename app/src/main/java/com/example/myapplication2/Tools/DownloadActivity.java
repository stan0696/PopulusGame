package com.example.myapplication2.Tools;

import androidx.annotation.RequiresApi;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import static com.example.myapplication2.View.MyImageView.GET_DATA_SUCCESS;

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
//                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                int requestCode = 100;
//                requestPermissions(permissions,requestCode);
////                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
////                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                //要申请的权限
                final String[] permission= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

                //要申请权限的回调
                PermissionCallback callbackk=new PermissionCallback() {

                    @Override
                    public void onPermissionGranted() {
                        System.out.println("开始下载");
                        newdownload.downloadAPK(downloadgameinfo[0],downloadgameinfo[1]);
                        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                        setdata(newdownload);
                        getdata();
                        list=alreadydownloadgameinfo;
                }

                    @Override
                    public void shouldShowRational(String[] rationalPermissons, boolean before) {

                        StringBuilder sb=new StringBuilder();
                        sb.append("我们将获取以下权限:\n\n");

                        for(int i=0;i<rationalPermissons.length;i++){
                            sb.append((i+1)+"、");
                            if(Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(rationalPermissons[i])){
                                sb.append("读写设备外部存储空间的权限，将被用于获取用户头像、保存一些文件到项目文件夹中");
                            }else if(Manifest.permission.CAMERA.equals(rationalPermissons[i])){
                                sb.append("使用摄像头的权限将，被用于拍照获取用户头像，直播视频采集");
                            }else if(Manifest.permission.READ_PHONE_STATE.equals(rationalPermissons[i])){
                                sb.append("读取手机状态信息的权限，将被用于登录时账号验证");
                            }

                            sb.append("\n");
                        }

                        PermissionUtil.showDialog(DownloadActivity.this, sb.toString(),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //重新申请权限
                                        //PermissionUtil.requestAgain(DownloadActivity.this,callbackk);
                                    }
                                });
                    }

                    @Override
                    public void onPermissonReject(String[] rejectPermissons) {
                        StringBuilder sb=new StringBuilder();
                        sb.append("我们需要的权限:\n\n");

                        for(int i=0;i<rejectPermissons.length;i++){
                            sb.append((i+1)+"、");
                            if(Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(rejectPermissons[i])){
                                sb.append("读写外部存储空间的权限，将被用于获取用户头像、保存一些文件到项目文件夹中");
                            }else if(Manifest.permission.CAMERA.equals(rejectPermissons[i])){
                                sb.append("使用摄像头的权限，被用于拍照获取用户头像，直播视频采集");
                            }else if(Manifest.permission.READ_PHONE_STATE.equals(rejectPermissons[i])){
                                sb.append("读取手机状态信息的权限，将被用于登录时账号验证");
                            }
                            sb.append("\n");
                        }
                        sb.append("\n被设为禁止,请到设置里开启权限");

                        PermissionUtil.showDialog(DownloadActivity.this, sb.toString(),
                                getString(R.string.下载中心),
                                getString(R.string.下载中心),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //重新申请权限
                                        PermissionUtil.startSettingsActivity(DownloadActivity.this);
                                    }
                                }
                        );
                    }
                };
                        System.out.println("申请权限");
                        PermissionUtil.request(DownloadActivity.this,permission,callbackk);

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
            DownloadManagerUtil nowdownload = new DownloadManagerUtil(getBaseContext());
            nowdownload.setDownloadId(Long.parseLong(cursor.getString(1)));
            String DownloadSoFar=new String();
            DownloadSoFar = String.valueOf(nowdownload.getDownloadSoFar());
            String DownloadSoFarinfo =new String(nowdownload.getDownloadedSoFar()+"/"+nowdownload.getTotalSize());
            alreadydownloadgameinfo.add(new String[]{cursor.getString(1),
                    cursor.getString(0),
                    cursor.getString(2),
                    DownloadSoFar,
                    DownloadSoFarinfo

            });

            while (cursor.moveToNext()) {
                DownloadManagerUtil nowdownload1 = new DownloadManagerUtil(getBaseContext());
                nowdownload.setDownloadId(Long.parseLong(cursor.getString(1)));
                String DownloadSoFar1=new String();
                DownloadSoFar = String.valueOf(nowdownload.getDownloadSoFar());
                String DownloadSoFarinfo1 =new String(nowdownload.getDownloadedSoFar()+"/"+nowdownload.getTotalSize());
                alreadydownloadgameinfo.add(new String[]{cursor.getString(1),
                        cursor.getString(0),
                        cursor.getString(2),
                        DownloadSoFar1,
                        DownloadSoFarinfo1

                });
            }
        }
        database.close();
    }



}
