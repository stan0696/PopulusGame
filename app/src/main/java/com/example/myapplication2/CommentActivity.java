package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.myapplication2.DBClass.DBGameService;
import com.example.myapplication2.User.UserLoginActivity;

public class CommentActivity extends AppCompatActivity {
    private String gamename;
    private String comment;
    private float mark;
    private Button publish;
    private RatingBar markbar;
    private EditText commenttext;
    private  Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_main);
        ImageView close_btn = (ImageView)findViewById(R.id.close_imageView);

        close_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        Intent intent=getIntent();
        gamename = intent.getStringExtra("gamename");
        init();
        gameThread gt=new gameThread();
        gt.start();


    }

    public void init()
    {
        publish=findViewById(R.id.button);
        commenttext=findViewById(R.id.comment_text);
        markbar=findViewById(R.id.ratingBar_mycomment);
        publish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.Username!=null)
                {
                    comment=commenttext.getText().toString().trim();
                    mark=markbar.getRating()*2;

                    Message m;
                    m=handler.obtainMessage();//获取事件
                    m.what=1;
                    handler.sendMessage(m);

                    Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    finish();

                }
               else
                {
                    Toast.makeText(CommentActivity.this, "请先登录", Toast.LENGTH_SHORT).show();


                }



            }
        });




    }
    class gameThread extends Thread{

        @SuppressLint("HandlerLeak")
        @Override
        public  void run()
        {
            Looper.prepare();
            handler = new Handler(){
                @Override
                public void handleMessage(Message m)
                {

                    if(m.what==1)
                    {
                        int k=0;
                        int status=0;
                        DBGameService dbgameservice=DBGameService.getDbGameService();
                        String [][] a =dbgameservice.getGameComment(gamename);
                        System.out.println(a[0][0]);//用户名
                        System.out.println(a[0][1]);//评分
                        System.out.println(a[0][2]);//评论
                       while(a[k][0]!=null  )
                       {
                           if(a[k][0].equals(MainActivity.Username))
                           {
                               status=1;
                               break;
                           }

                           k++;
                       }


                        dbgameservice.setGameComment(MainActivity.Username,gamename,mark,comment,status);




                    }


                }


            };
            Looper.loop();
        }
    }
}
