package com.example.xiaopeng.myapplication.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;

/**
 * @author xiaopeng
 * @date 2018/9/19
 */
public class AppUtils {

    private Context mAppContext;

    private AppUtils() {
    }


    /**
     * 得到App的单例对象
     *
     * @return App的单例对象
     */
    public static AppUtils getInstance() {
        return Singleton.INSTANCE;
    }

    public Context getAppContext() {
        return mAppContext;
    }

    public AppUtils setAppContext(Context appContext) {
        this.mAppContext = appContext;
        return AppUtils.this;
    }

    /**
     * 得到全局资源类
     *
     * @return Resource
     */

    public Resources getResources() {
        return getAppContext().getResources();
    }

    /**
     * 得到布局填充器
     *
     * @return LayoutInflater
     */
    public LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 得到String
     *
     * @param stringId stringId
     * @return String
     */
    public String getString(@StringRes int stringId) {
        return getResources().getString(stringId);
    }

    private static final class Singleton {
        private static final AppUtils INSTANCE = new AppUtils();
    }
}