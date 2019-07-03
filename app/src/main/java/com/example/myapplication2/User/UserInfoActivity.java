package com.example.myapplication2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication2.DBClass.DBUserService;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private Handler handler;
    private TextView user_info_name, user_info_intro;
    private Button changeuserinfo, changepass, changesecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        view_init();
        UserInfo userInfoTD = new UserInfo();
        userInfoTD.start();
    }

    /**
     * 控件初始化
     */
    private void view_init(){
        context = UserInfoActivity.this;
        user_info_name = (TextView) findViewById(R.id.userinfo_name);
        user_info_intro = (TextView) findViewById(R.id.userinfo_intro);
        changeuserinfo = (Button) findViewById(R.id.user_button1);
        changepass = (Button) findViewById(R.id.user_button2);
        changesecret = (Button) findViewById(R.id.user_button3);
        changeuserinfo.setOnClickListener(this);
        changepass.setOnClickListener(this);
        changesecret.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Message m;
        m=handler.obtainMessage();//获取事件
        switch (view.getId()){
            case R.id.user_button1:
                m.what=1;
                handler.sendMessage(m);//把信息放到通道中
                break;
            case R.id.user_button2:
                m.what=2;
                handler.sendMessage(m);//把信息放到通道中
                break;
            case R.id.user_button3:
                m.what=3;
                handler.sendMessage(m);//把信息放到通道中
                break;
        }
    }

    class UserInfo extends Thread{
        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            user_info_name.setText(MainActivity.Username);
            DBUserService dbUserService = DBUserService.getDbUserService();
            User user = dbUserService.getUserByName(MainActivity.Username);
            user_info_intro.setText(user.getUsernote());
            user_info_intro.setFocusable(false);
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message m) {
                    switch (m.what){
                        case 1:
                            Intent intentinfo = new Intent(UserInfoActivity.this, UserInfoChangeActivity.class);
                            finish();
                            startActivity(intentinfo);
                            break;
                        case 2:
                            Intent intentpass = new Intent(UserInfoActivity.this, PasswordChangeActivity.class);
                            finish();
                            startActivity(intentpass);
                            break;
                        case 3:
                            Intent intentsecret = new Intent(UserInfoActivity.this, UserSecretActivity.class);
                            finish();
                            startActivity(intentsecret);
                            break;
                    }
                }
            };
            Looper.loop();
        }
    }

}
