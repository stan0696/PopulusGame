package com.example.myapplication2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ImageView back_btn = (ImageView)findViewById(R.id.userinfo_back);
        back_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        Button btn1 = (Button)findViewById(R.id.user_button1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                    Intent intent = new Intent(UserInfoActivity.this, PersonalInfoChangeActivity.class);
                    startActivity(intent);
                }
        });
    }
}
