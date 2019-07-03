package com.example.myapplication2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication2.DBClass.DBUserService;
import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoChangeActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private Handler handler;
    private EditText user_name, user_note, user_birth, user_sex, user_city, user_phone;
    private Button userInfoChange;
    private String name, note, birth, sex, city, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinfo);
        view_init();
        InfoChangeThread infochangeth = new InfoChangeThread();
        infochangeth.start();
    }

    /**
     * 控件初始化
     */
    private void view_init(){
        context = UserInfoChangeActivity.this;
        user_name = (EditText) findViewById(R.id.user_name_info);
        user_note = (EditText) findViewById(R.id.user_note_info);
        user_birth = (EditText) findViewById(R.id.user_birth_info);
        user_sex = (EditText) findViewById(R.id.user_sex_info);
        user_city = (EditText) findViewById(R.id.user_city_info);
        user_phone = (EditText) findViewById(R.id.user_phone_info);
        user_phone.setInputType(InputType.TYPE_CLASS_PHONE);
        userInfoChange = (Button) findViewById(R.id.user_info_change_button);
        userInfoChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Message m;
        Bundle b;
        name = MainActivity.Username;
        note = user_note.getText().toString().trim();
        birth = user_birth.getText().toString().trim();
        sex = user_sex.getText().toString().trim();
        city = user_city.getText().toString().trim();
        phone = user_phone.getText().toString().trim();
        m=handler.obtainMessage();//获取事件
        b=new Bundle();
        b.putString("name", name);//以键值对形式放进 Bundle中
        b.putString("note", note);
        b.putString("birth", birth);
        b.putString("sex", sex);
        b.putString("city", city);
        b.putString("phone", phone);
        m.setData(b);
        switch (view.getId()){
            case R.id.user_info_change_button:
                m.what = 1;
                handler.sendMessage(m);//把信息放到通道中
                break;
        }
    }

    /**
     * 判断是否为手机号码
     * @param inputText 输入
     * @return 结果
     */
    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

    /**
     * 判断是否为时间格式
     * @param userdate 输入
     * @return 结果
     */
    public static boolean isDate(String userdate){
        Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))");
        Matcher m = p.matcher(userdate);
        return m.matches();
    }


    /**
     * 用户信息修改线程
     */
    class InfoChangeThread extends Thread{
        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            user_name.setText(MainActivity.Username);
            user_name.setFocusable(false);
            String userbirth, usernote, usersex, usercity, userphone;
            DBUserService dbUserService = DBUserService.getDbUserService();
            name = MainActivity.Username;
            final User user = dbUserService.getUserByName(MainActivity.Username);
            usernote = user.getUsernote();
            userbirth = user.getUserbirth();
            usersex = user.getUsersex();
            usercity = user.getUsercity();
            userphone = user.getUserphone();
            user_note.setText(usernote);
            user_birth.setText(userbirth);
            user_sex.setText(usersex);
            user_city.setText(usercity);
            user_phone.setText(userphone);

            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message m) {
                    String birth, note, sex, city, phone;
                    DBUserService dbUserService = DBUserService.getDbUserService();
                    Bundle b = m.getData();//得到与信息对比用的Bundle
                    switch (m.what){
                        case 1:
                            note = b.getString("note");
                            sex = b.getString("sex");
                            city = b.getString("city");
                            phone = b.getString("phone");
                            birth = b.getString("birth");
                            if((!birth.isEmpty())&&(!isDate(birth))){
                                Toast.makeText(UserInfoChangeActivity.this, "请输入正确的日期", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            if((!sex.isEmpty())&&(!sex.equals("男"))&&(!sex.equals("女"))){
                                Toast.makeText(UserInfoChangeActivity.this, "请输入正确的性别", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            if((!phone.isEmpty())&&(!isPhone(phone))){
                                Toast.makeText(UserInfoChangeActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            user.setUsernote(note);
                            user.setUsersex(sex);
                            user.setUsercity(city);
                            user.setUserphone(phone);
                            user.setUserbirth(birth);
                            int result = dbUserService.changeUserinfo(user);
                            if(result==1){
                                Toast.makeText(UserInfoChangeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                break;
                            }else if(result==-1){
                                Toast.makeText(UserInfoChangeActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                break;
                            }
                    }

                }
            };
            Looper.loop();
        }
    }
}
