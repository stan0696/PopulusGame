package com.example.myapplication2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.myapplication2.R;

public class UserImageSelect extends AppCompatActivity {
    private ImageView image0;
    private ImageView icon1;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iconimage_select);
        LayoutInflater inflate = LayoutInflater.from(UserImageSelect.this);
        view = inflate.inflate(R.layout.personalinfo,null);
        ImageView back_btn = (ImageView)findViewById(R.id.imageicon_back);
        back_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        icon1 = (ImageView)findViewById(R.id.imageicon0);
        image0 = (ImageView) view.findViewById(R.id.personal_userimage);
        image0.setImageResource(R.drawable.agricultural_productivity);
        icon1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                System.out.println(image0);
                image0.setImageResource(R.drawable.agricultural_productivity);
                System.out.println(image0.getImageMatrix());
                finish();
            }
        });
    }
}