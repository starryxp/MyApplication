package com.example.xiaopeng.myapplication.api.retrofit;

import android.annotation.SuppressLint;

import com.example.xiaopeng.myapplication.api.retrofit.persistentcookiejar.ClearableCookieJar;
import com.example.xiaopeng.myapplication.api.retrofit.persistentcookiejar.PersistentCookieJar;
import com.example.xiaopeng.myapplication.api.retrofit.persistentcookiejar.cache.SetCookieCache;
import com.example.xiaopeng.myapplication.api.retrofit.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.example.xiaopeng.myapplication.app.MyApplication;
import com.example.xiaopeng.myapplication.utils.AppUtils;
import com.example.xiaopeng.myapplication.utils.LogUtils;
import com.example.xiaopeng.myapplication.utils.NetWorkUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 配置Retrofit（配置网络缓存cache、配置持久cookie免登录）
 * Created by xiaopeng on 2017/11/29.
 */

public class BaseApiRetrofit {

    private final okhttp3.OkHttpClient OkHttpClient;
    private final static String TAG = BaseApiRetrofit.class.getSimpleName();
    private static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    private static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    private static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒

    public okhttp3.OkHttpClient getClient() {
        return OkHttpClient;
    }

    public BaseApiRetrofit() {
        /*================== common ==================*/

        // Log信息拦截器
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);//这里可以选择拦截级别

        //cache
        File httpCacheDir = new File(AppUtils.getInstance().getAppContext().getCacheDir(), "response");
        int cacheSize = 10 * 1024 * 1024;// 10 MiB
        Cache cache = new Cache(httpCacheDir, cacheSize);

        //cookie
        ClearableCookieJar cookieJar = new PersistentCookieJar(
                new SetCookieCache(),
                new SharedPrefsCookiePersistor(AppUtils.getInstance().getAppContext()));

        //header配置
        Interceptor REWRITE_HEADER_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
//                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .addHeader("Accept-Encoding", "gzip, deflate")
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Accept", "*/*")
//                .addHeader("Cookie", "add cookies here")
                        .build();
                return chain.proceed(request);
            }
        };

        //cache配置
        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //通过 CacheControl 控制缓存数据
                CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
                cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
                CacheControl cacheControl = cacheBuilder.build();

                //设置拦截器
                Request request = chain.request();
                if (!NetWorkUtils.Companion.isNetworkAvailable(AppUtils.getInstance().getAppContext())) {
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                Response originalResponse = chain.proceed(request);
                if (NetWorkUtils.Companion.isNetworkAvailable(AppUtils.getInstance().getAppContext())) {
                    int maxAge = 0;//read from cache
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public ,max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28;//tolerate 4-weeks stale
                    return originalResponse.newBuilder()
                            .removeHeader("Prama")
                            .header("Cache-Control", "poublic, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };

        //OkHttpClient
        OkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(REWRITE_HEADER_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(new LoggingInterceptor())
//                .addInterceptor(loggingInterceptor)//设置 Debug Log 模式
                .cache(cache)
                .cookieJar(cookieJar)
                .build();
    }

    class LoggingInterceptor implements Interceptor {
        @SuppressLint("DefaultLocale")
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            LogUtils.d(this, String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            LogUtils.d(this, String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));
            return response;
        }
    }

}
