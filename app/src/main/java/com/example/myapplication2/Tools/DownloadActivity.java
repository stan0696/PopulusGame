package com.example.myapplication2.Tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication2.GameinfoActivity;
import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private ListView downloadv_ss;
    private List<DownloadManagerUtil> list = new ArrayList<>();
    boolean isFilter;
    private DownloadAdapter adapter = null;
    private DownloadManagerUtil newdownload;
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private boolean isRegisterReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_main);
        ImageView back_btn = (ImageView)findViewById(R.id.download_back);
        setViews();// 控件初始化
        setData();// 给listView设置adapter
        setListeners();// 设置监听
        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }
    private void setData() {
        initData();// 初始化数据
        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
        adapter = new DownloadAdapter(list, this);
        downloadv_ss.setAdapter(adapter);
    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */
    protected void setItemClick(final List<DownloadManagerUtil> filter_lists) {
        downloadv_ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
               // Intent intent = new Intent(DownloadActivity.this.getBaseContext(), GameinfoActivity.class);
               // startActivity(intent);
            }
        });
    }

    /**
     * 简单的list集合添加一些测试数据
     */
    private void initData() {
        int[] url = new int[]{WRITE_EXTERNAL_STORAGE_REQUEST_CODE};
        newdownload = new DownloadManagerUtil(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
        else{
            String url2 = "http://mhhy.dl.gxpan.cn/apk/ml/MBGYD092101/Gardenscapes-ledou-MBGYD092101.apk";
            newdownload.downloadAPK(url2,"sss");
            list.add( newdownload);
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
     * @param ManifestPermission
     * @param CODE
     * @return
     */
    private boolean requestPermission(final String ManifestPermission, final int CODE) {
        //1. 检查是否已经有该权限
        if (ContextCompat.checkSelfPermission(this,ManifestPermission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,ManifestPermission)) {
                new AlertDialog.Builder(this)
                        .setTitle("权限申请")
                        .setMessage("亲，没有权限我会崩溃，请把权限赐予我吧！")
                        .setPositiveButton("赏给你", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                // 用户同意 ，再次申请
                                ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{ManifestPermission}, CODE);
                            }
                        })
                        .setNegativeButton("就不给", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                // 用户拒绝 ，如果APP必须有权限否则崩溃，那就继续重复询问弹框~~
                            }
                        }).show();
            } else {
                //2. 权限没有开启，请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{ManifestPermission}, CODE);
            }

        } else {
            //3. 权限已开，处理逻辑
            return true;
        }
        return false;
    }

    //4. 接收申请成功或者失败回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Code_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
                String url2 = "http://mhhy.dl.gxpan.cn/apk/ml/MBGYD092101/Gardenscapes-ledou-MBGYD092101.apk";
                newdownload.downloadAPK(url2,"sss");
            } else {
                //权限被用户拒绝，做相应的事情
                Toast.makeText(this,"拒绝了权限",Toast.LENGTH_SHORT);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
