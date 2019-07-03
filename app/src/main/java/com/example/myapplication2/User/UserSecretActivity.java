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
import android.widget.Toast;

import com.example.myapplication2.DBClass.DBUserService;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

public class UserSecretActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private Handler handler;
    private EditText secretedittext;
    private Button secretbutton;
    private String usersecretprotect, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersecret);
        Intent intent=getIntent();
        name = intent.getStringExtra("name");

        view_init();
        UserSecret userSecret = new UserSecret();
        userSecret.start();
    }

    /**
     * 控件初始化
     */
    private void view_init() {
        context = UserSecretActivity.this;
        secretedittext = (EditText) findViewById(R.id.secret_edittext);
        secretbutton = (Button) findViewById(R.id.secret_button);
        secretbutton.setOnClickListener(this);

    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        Message m;
        Bundle b;
        usersecretprotect = secretedittext.getText().toString().trim();
        m=handler.obtainMessage();//获取事件
        b=new Bundle();
        b.putString("usersecret", usersecretprotect);
        m.setData(b);
        if (view.getId()==R.id.secret_button){
                m.what=1;
                handler.sendMessage(m);//把信息放到通道中
        }
    }

    class UserSecret extends Thread{
        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message m) {
                    String usersecret;
                    DBUserService dbUserService = DBUserService.getDbUserService();
                    Bundle b = m.getData();
                    switch (m.what){
                        case 1:
                            usersecret = b.getString("usersecret");
                            if (MainActivity.passState==false){
                                String usersecretprotect = dbUserService.findUserProtect(name);
                                if((usersecretprotect==null)||(usersecretprotect.isEmpty())){
                                    Toast.makeText(UserSecretActivity.this, "用户不存在或用户未设置密保", Toast.LENGTH_SHORT).show();
                                    break;
                                }else if(!usersecret.equals(usersecretprotect)){
                                    Toast.makeText(UserSecretActivity.this, "密保问题回答错误", Toast.LENGTH_SHORT).show();
                                    break;
                                }else{
                                    //TODO调到修改密码界面
                                    break;
                                }
                            }
                            if (usersecret.isEmpty()){
                                Toast.makeText(UserSecretActivity.this, "请输入密保信息", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            int result = dbUserService.changeProtect(MainActivity.Username, usersecret);
                            if(result==1){
                                Toast.makeText(UserSecretActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                                break;
                            }
                    }
                }
            };
            Looper.loop();
        }
    }

}
