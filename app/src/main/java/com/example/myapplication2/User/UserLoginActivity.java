package com.example.myapplication2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.DBClass.DBUserService;
import com.example.myapplication2.R;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Handler handler;
    private EditText user_name, user_password;
    private Button login, registe;
    private String name, password ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        view_init();
        LoginThread lt=new LoginThread();
        lt.start();//调动子线程

    }

    /**
     * 控件初始化
     */
    private void view_init(){
        context = UserLoginActivity.this;
        user_name = (EditText) findViewById(R.id.user_name);
        user_password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_button);
        registe = (Button) findViewById(R.id.registe_button);
        login.setOnClickListener(this);
        registe.setOnClickListener(this);
    }

    /**
     * 监听
     * @param view
     */
    @SuppressLint("输入错误")
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_button:
                name = user_name.getText().toString().trim();
                password = user_password.getText().toString().trim();
                Message m=handler.obtainMessage();//获取事件
                Bundle b=new Bundle();
                b.putString("name", name);
                b.putString("pass", password);//以键值对形式放进 Bundle中
                m.setData(b);
                m.what=0;
                handler.sendMessage(m);//把信息放到通道中
                break;
        }
    }

    /**
     * 登录线程
     */
    class LoginThread extends Thread{
        @Override
        public void run(){
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message m) {
                    switch(m.what){
                        case 0:
                            Bundle b = m.getData();//得到与信息对比用的Bundle
                            String name = b.getString("name");//根据键取值
                            String pass = b.getString("pass");
                            DBUserService dbUserService = DBUserService.getDbUserService();
                            String password = dbUserService.findUser(name);
                            if (password.equals(pass))//为true，页面跳转，登陆成功
                            {
                                //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                //Toast.makeText(UserLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();//显示提示框
                                return;
                            }
                            //Toast.makeText(UserLoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
            Looper.loop();//Looper循环，通道中有数据执行，无数据堵塞
        }
    }

}
