package com.example.myapplication2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.DBClass.DBUserService;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

public class PasswordChangeActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Handler handler;
    private EditText user_answer, user_new_pass, user_new_pass1;
    private ImageView userinfo_back;
    private Button password_certify;
    private String useranswer, usernewpass, usernewpass1, name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_find);

        Intent intent=getIntent();
        name = intent.getStringExtra("name");

        view_init();
        PassChange passChange = new PassChange();
        passChange.start();

    }

    /**
     * 控件初始化
     */
    private void view_init() {
        context = PasswordChangeActivity.this;
        user_answer = (EditText) findViewById(R.id.useranswer);
        user_new_pass = (EditText) findViewById(R.id.passwordreset0);
        user_new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        user_new_pass1 = (EditText) findViewById(R.id.passwordreset1);
        user_new_pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        password_certify = (Button) findViewById(R.id.password_certify);
        userinfo_back = (ImageView) findViewById(R.id.userinfo_back);
        password_certify.setOnClickListener(this);
        userinfo_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Message m;
        Bundle b;
        useranswer = user_answer.getText().toString().trim();
        usernewpass = user_new_pass.getText().toString().trim();
        usernewpass1 = user_new_pass1.getText().toString().trim();
        m = handler.obtainMessage();//获取事件
        b = new Bundle();
        b.putString("useranswer", useranswer);
        b.putString("usernewpass", usernewpass);
        b.putString("usernewpass1", usernewpass1);
        m.setData(b);
        switch (view.getId()) {
            case R.id.password_certify:
                m.what = 1;
                handler.sendMessage(m);//把信息放到通道中
                break;
            case R.id.userinfo_back:
                this.finish();
                break;
        }
    }

    class PassChange extends Thread{
        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message m) {
                    DBUserService dbUserService = DBUserService.getDbUserService();
                    String useranswer, usernewpass, usernewpass1, username;
                    Bundle b = m.getData();
                    useranswer = b.getString("useranswer");
                    usernewpass = b.getString("usernewpass");
                    usernewpass1 = b.getString("usernewpass1");
                    switch (m.what) {
                        case 1:
                            if (MainActivity.passState==false){
                                username = name;
                            }else{
                                username = MainActivity.Username;
                            }
                            String user_answer = dbUserService.findUserProtect(username);
                            if (!user_answer.equals(useranswer)){
                                Toast.makeText(PasswordChangeActivity.this, "密保错误", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            if((!usernewpass.equals(usernewpass1))||(usernewpass.isEmpty())){
                                Toast.makeText(PasswordChangeActivity.this, "密码不一致或密码为空", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            dbUserService.changePassword(name, usernewpass);
                            Toast.makeText(PasswordChangeActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                            if(MainActivity.passState==false){
                                Intent intentmain = new Intent(PasswordChangeActivity.this, MainActivity.class);
                                finish();
                                startActivity(intentmain);
                                break;
                            }
                            if(MainActivity.passState==true){
                                Intent intentuser = new Intent(PasswordChangeActivity.this, UserInfoActivity.class);
                                finish();
                                startActivity(intentuser);
                                break;
                            }

                    }
                }
            };
            Looper.loop();
        }
    }



}
