package com.example.xiaopeng.myapplication.api.rxjava;


import com.example.xiaopeng.myapplication.response.ResponseData;

import io.reactivex.functions.Function;

/**
 * Created by xiaopeng on 2018/4/23
 */
public class ServerResultFunction<T> implements Function<ResponseData<T>, ResponseData<T>> {


    @Override
    public ResponseData<T> apply(ResponseData<T> response) {
        return response;
    }


}