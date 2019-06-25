package com.example.myapplication2.Tools;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;

import java.io.File;
import java.util.ArrayList;

public class DownloadController {
    private Context mContext;
    private ArrayList<DownloadManagerUtil> alldownload;
    public DownloadController(Context context) {
        mContext = context;
        alldownload =new ArrayList<>();
    }



    public ArrayList<DownloadManagerUtil> getAlldownload() {
        return alldownload;
    }
    public void creatdownload(String url, String title, String desc) {
        DownloadManagerUtil a =new DownloadManagerUtil(mContext,url,title,desc);
        this.alldownload.add(a)  ;
    }
    public void setAlldownload(ArrayList<DownloadManagerUtil> alldownload) {
        this.alldownload = alldownload;
    }
    public void addAlldownload(DownloadManagerUtil adownload) {
        this.alldownload.add(adownload)  ;
    }
    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


}
