package bwie.com.zhoukao01.mvp.model;

public interface MainModel {

    //登录
    public void login(String url,String phone,String pwd,ILoginCallBack loginCallback);
    interface ILoginCallBack{
        void success(String data);
        void fail(String error);
    }
    //注册
    public void zhu(String url,String phone,String pwd,iZhuCallback zhuCallback);
    interface iZhuCallback{
        void success(String data);
        void fail(String error);
    }



}
