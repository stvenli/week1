package bwie.com.zhoukao01.net;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

    private static HttpUtils httpUtils;
    private final OkHttpClient client;
    public Handler handler = new Handler();
    private static OkHttpClient okHttpClient;

    //网络请求+拦截器
    private Interceptor getInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.e("++++++++++", "拦截前");
                Response proceed = chain.proceed(request);
                Log.e("++++++++++", "拦截后");
                return proceed;
            }
        };
        return interceptor;
    }

    public HttpUtils() {
        File file = new File(Environment.getExternalStorageDirectory(), "call");
        client = new OkHttpClient().newBuilder()
                //设置读取超时时间
                .readTimeout(300, TimeUnit.SECONDS)
                //设置连接的超时时间
                .connectTimeout(300, TimeUnit.SECONDS)
                //Application拦截器
                .addInterceptor(getInterceptor())
                .cache(new Cache(file, 10 * 1024))
                .build();
    }

    //单例模式
    public static HttpUtils getUtils() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    //get网络请求+解析gson
    public void doget(String url, final Class aClass, final NetCallBack netCallBack) {
        Request build = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netCallBack.NO(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(result, aClass);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netCallBack.OK(o);
                    }
                });
            }
        });
    }

    //post请求方式
    public static void getHttpPost(String url, String name, String pswd, Callback callback) {
        okHttpClient = new OkHttpClient();
        FormBody build = new FormBody.Builder()
                .add("phone", name)
                .add("pwd", pswd)
                .build();
        //创建request
        Request request = new Request.Builder()
                .url(url)
                .post(build)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //接口
    public interface NetCallBack {
        void OK(Object ok);

        void NO(Exception no);
    }
}
