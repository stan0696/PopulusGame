package com.example.myapplication2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication2.R;

public class UserLoginActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText user_password;
    private Button login;
    private Button registe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

    }

    /**
     * 控件初始化
     */
    private void view_init(){
        user_name = (EditText) findViewById(R.id.user_name);
        user_password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_button);
        registe = (Button) findViewById(R.id.registe_button);

    }



}
