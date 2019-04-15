package bwie.com.zhoukao01.mvp.presenter;

import android.util.Log;

import bwie.com.zhoukao01.MainActivity;
import bwie.com.zhoukao01.ShowActivity;
import bwie.com.zhoukao01.ZhuceActivity;
import bwie.com.zhoukao01.mvp.model.MainModel;
import bwie.com.zhoukao01.mvp.model.MainModelIml;
import bwie.com.zhoukao01.mvp.view.MainView;

public class MainPresenterIml implements MainPresenter{

    //实例化界面
    MainActivity mainActivity;
    ZhuceActivity zhuceActivity;
    ShowActivity showActivity;
    //实例化Model实现类
     MainModelIml mainModelIml;



    public MainPresenterIml(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mainModelIml = new MainModelIml();
    }

    public MainPresenterIml(ZhuceActivity zhuceActivity) {
        this.zhuceActivity = zhuceActivity;
        mainModelIml = new MainModelIml();
    }

    public MainPresenterIml(ShowActivity showActivity) {
        this.showActivity = showActivity;
        mainModelIml = new MainModelIml();
    }

    @Override
    public void getLoginPresenterData(String phone, String pwd) {
        mainModelIml.login("http://172.17.8.100/small/user/v1/login",
                phone, pwd, new MainModel.ILoginCallBack() {
                    @Override
                    public void success(String data) {
                        Log.i("2022", "" + data);
                        mainActivity.getViewData(data);
                    }

                    @Override
                    public void fail(String error) {

                    }
                });

    }

    @Override
    public void doZhucePresenterData(String phone, String pwd) {
        mainModelIml.zhu("http://172.17.8.100/small/user/v1/register",
                phone, pwd, new MainModel.iZhuCallback() {
                    @Override
                    public void success(String data) {
                        Log.i("1011", "" + data);
                        zhuceActivity.getZhuViewData(data);
                    }

                    @Override
                    public void fail(String error) {

                    }
                });
    }
}
