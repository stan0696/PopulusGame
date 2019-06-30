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
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;

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
        Message m;
        Bundle b;
        name = user_name.getText().toString().trim();
        password = user_password.getText().toString().trim();
        m=handler.obtainMessage();//获取事件
        b=new Bundle();
        b.putString("name", name);
        b.putString("pass", password);//以键值对形式放进 Bundle中
        m.setData(b);
        switch (view.getId()){
            case R.id.login_button:
                m.what=0;
                handler.sendMessage(m);//把信息放到通道中
                break;
            case R.id.registe_button:
                m.what=1;
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
                    String name, pass, password;
                    DBUserService dbUserService = DBUserService.getDbUserService();
                    Bundle b = m.getData();//得到与信息对比用的Bundle
                    switch(m.what){
                        case 0:
                            name = b.getString("name");//根据键取值
                            pass = b.getString("pass");
                            dbUserService = DBUserService.getDbUserService();
                            password = dbUserService.findUser(name);//读取的密码
                            if (pass.equals("")){
                                Toast.makeText(UserLoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            if (password == null){
                                Toast.makeText(UserLoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            if (password.equals(pass))//为true，页面跳转，登陆成功
                            {
                                startActivity(new Intent(UserLoginActivity.this, MainActivity.class));
                                Toast.makeText(UserLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();//显示提示框
                                return;
                            }
                            Toast.makeText(UserLoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            name = b.getString("name");//根据键取值
                            pass = b.getString("pass");
                            dbUserService = DBUserService.getDbUserService();
                            password = dbUserService.findUser(name);//读取的密码，密码为空说明用户不存在，可新建用户
                            if (pass.equals("")||name.equals("")){
                                Toast.makeText(UserLoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            //返还密码为空，用户不存在，可创建用户
                            if (password == null){
                                User user = new User(name, pass);
                                dbUserService.userInsert(user);
                                Toast.makeText(UserLoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            Toast.makeText(UserLoginActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
            Looper.loop();//Looper循环，通道中有数据执行，无数据堵塞
        }
    }
}