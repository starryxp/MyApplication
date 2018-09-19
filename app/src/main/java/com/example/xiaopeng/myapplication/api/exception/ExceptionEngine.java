package com.example.xiaopeng.myapplication.api.exception;

import android.net.ParseException;

import com.example.xiaopeng.myapplication.R;
import com.example.xiaopeng.myapplication.app.MyApplication;
import com.example.xiaopeng.myapplication.utils.AppUtils;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by xiaopeng on 2018/4/23
 */
public class ExceptionEngine {
    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int ANALYTIC_CLIENT_DATA_ERROR = 1002;//解析(客户端)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时
    public static final int OPERATE_FAIL = 10005;//操作失败

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            //HTTP错误
            HttpException httpExc = (HttpException) e;
            ex = new ApiException(e, httpExc.code());
            //均视为网络错误
            ex.setMsg(AppUtils.getInstance().getString(R.string.resmsg_net_error));
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof MalformedJsonException) {
            //解析数据错误
            ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR);
            ex.setMsg(AppUtils.getInstance().getString(R.string.resmsg_json_error));
            return ex;
        } else if (e instanceof ConnectException) {
            //连接网络错误
            ex = new ApiException(e, CONNECT_ERROR);
            ex.setMsg(AppUtils.getInstance().getString(R.string.resmsg_connect_error));
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            //网络超时
            ex = new ApiException(e, TIME_OUT_ERROR);
            ex.setMsg(AppUtils.getInstance().getString(R.string.resmsg_time_out_error));
            return ex;
        } else {  //未知错误
            ex = new ApiException(e, UN_KNOWN_ERROR);
            ex.setMsg(AppUtils.getInstance().getString(R.string.resmsg_net_error));
            return ex;
        }
    }
}
