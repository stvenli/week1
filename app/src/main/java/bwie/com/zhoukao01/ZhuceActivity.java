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
import bwie.com.zhoukao01.bean.ZhuceBean;
import bwie.com.zhoukao01.mvp.presenter.MainPresenterIml;
import bwie.com.zhoukao01.mvp.view.MainView;

public class ZhuceActivity extends AppCompatActivity implements MainView {
    private EditText zhucename;
    private EditText zhucepass;
    private TextView zhuce_login;
    private Button zhuce;
    private MainPresenterIml mainPresenterIml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        zhucename = findViewById(R.id.zhu_name);
        zhucepass = findViewById(R.id.zhu_pass);
        zhuce_login = findViewById(R.id.zhu_login);
        zhuce = findViewById(R.id.zhuce);
        mainPresenterIml = new MainPresenterIml(this);


        zhuce_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.zhu_login:
                        Intent intent = new Intent(ZhuceActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = zhucename.getText().toString().trim();
                String pwd = zhucepass.getText().toString().trim();
                if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(pwd)){
                    Toast.makeText(ZhuceActivity.this, "注册时,账号或者密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    mainPresenterIml.doZhucePresenterData(phone,pwd);
                }
            }
        });


    }

    @Override
    public void getViewData(String ViewData) {

    }

    @Override
    public void getZhuViewData(final String mView) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        ZhuceBean zhuceBean = gson.fromJson(mView, ZhuceBean.class);
                        String status = zhuceBean.getStatus();
                        if (status.equals("0000")) {
                            startActivity(new Intent(ZhuceActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ZhuceActivity.this, "账号密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }.start();

    }
}
