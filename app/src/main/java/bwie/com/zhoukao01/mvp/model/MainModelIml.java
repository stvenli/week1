package bwie.com.zhoukao01.mvp.model;

import java.io.IOException;

import bwie.com.zhoukao01.net.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainModelIml implements MainModel {

    @Override
    public void login(final String url, final String phone, final String pwd, final ILoginCallBack loginCallback) {
        new Runnable(){
            @Override
            public void run() {
                HttpUtils.getHttpPost(url, phone, pwd, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        loginCallback.success(response.body().string());
                    }
                });
            }
        }.run();
    }

    @Override
    public void zhu(final String url, final String phone, final String pwd, final iZhuCallback zhuCallback) {
        new Runnable(){
            @Override
            public void run() {
                HttpUtils.getHttpPost(url, phone, pwd, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        zhuCallback.success(response.body().string());
                    }
                });
            }
        }.run();
    }
}
