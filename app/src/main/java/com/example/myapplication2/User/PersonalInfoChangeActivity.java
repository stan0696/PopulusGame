package com.example.myapplication2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication2.R;

public class PersonalInfoChangeActivity extends AppCompatActivity {
    private ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinfo);
        ImageView back_btn = (ImageView)findViewById(R.id.personalinfo_back);
        back_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        ImageView img1 = (ImageView) findViewById(R.id.personal_userimage);
        img1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(PersonalInfoChangeActivity.this, UserImageSelect.class);
                startActivity(intent);
            }
        });
    }
}