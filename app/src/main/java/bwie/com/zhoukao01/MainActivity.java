package bwie.com.zhoukao01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import bwie.com.zhoukao01.bean.LoginBean;
import bwie.com.zhoukao01.mvp.model.MainModelIml;
import bwie.com.zhoukao01.mvp.presenter.MainPresenterIml;
import bwie.com.zhoukao01.mvp.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private EditText username;
    private EditText userpass;
    private TextView login_zhuce;
    private Button login;
    private MainPresenterIml mainPresenterIml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.user_name);
        userpass = findViewById(R.id.user_pass);
        login_zhuce = findViewById(R.id.login_zhuce);
        login = findViewById(R.id.login);
        //实例化P层实现类的对象
        mainPresenterIml = new MainPresenterIml(this);

        login_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login_zhuce:
                        Intent intent = new Intent(MainActivity.this, ZhuceActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });


        //按钮点击事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = username.getText().toString().trim();
                String pwd = userpass.getText().toString().trim();
                if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(pwd)){
                    Toast.makeText(MainActivity.this, "手机号或者密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    mainPresenterIml.getLoginPresenterData(phone,pwd);
                }
            }
        });

    }

    @Override
    public void getViewData(final String ViewData) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(ViewData, LoginBean.class);
                        //获取账号密码
                        String status = loginBean.getStatus();
                        if (status.equals("0000")) {
                            startActivity(new Intent(MainActivity.this, ShowActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "账号密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }.start();

    }

    @Override
    public void getZhuViewData(String mView) {

    }
}