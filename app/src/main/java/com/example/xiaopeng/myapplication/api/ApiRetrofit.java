package com.example.xiaopeng.myapplication.api;

import com.example.xiaopeng.myapplication.api.retrofit.BaseApiRetrofit;
import com.example.xiaopeng.myapplication.response.LoginResponse;
import com.example.xiaopeng.myapplication.response.ResponseData;
import com.example.xiaopeng.myapplication.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiaopeng on 2018/4/20
 */
public class ApiRetrofit extends BaseApiRetrofit {

    private final String TAG = ApiRetrofit.class.getSimpleName();
    private ApiService mApi;
    private static volatile ApiRetrofit mInstance;

    private ApiRetrofit() {
        super();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create();

        //在构造方法中完成对Retrofit接口的初始化
        mApi = new Retrofit.Builder()
                .baseUrl("https://wawa-api.vchangyi.com/")
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    public static ApiRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (ApiRetrofit.class) {
                if (mInstance == null) {
                    mInstance = new ApiRetrofit();
                }
            }
        }
        return mInstance;
    }

    public static void updateInstance() {
        mInstance = new ApiRetrofit();
    }

    private RequestBody getRequestBody(Object obj) {
        String route = new Gson().toJson(obj);
        return getRequestBody(route);
    }

    private RequestBody getRequestBody(String json) {
        LogUtils.json(this, json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }

    //手机号登陆
    public Observable<ResponseData<LoginResponse>> Login(String name, String pass) {
        return mApi.LoginByRx(name, pass);
    }
}

