package com.example.xiaopeng.myapplication.app

import android.support.multidex.MultiDexApplication
import com.example.xiaopeng.myapplication.utils.AppUtils
import com.example.xiaopeng.myapplication.utils.GlideUtil
import com.example.xiaopeng.myapplication.utils.LogUtils
import com.squareup.leakcanary.LeakCanary

/**
 *
 * @author xiaopeng
 * @date 2018/9/18
 * 突破64K方法数限制
 */
class MyApplication : MultiDexApplication() {

    override
    fun onCreate() {
        super.onCreate()
        //初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        AppUtils.getInstance().appContext = this

//        EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()

        LogUtils.d(this, "onCreate")

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // 程序在内存清理的时候执行
        GlideUtil.trimMemory(level, applicationContext)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // 低内存的时候执行
        GlideUtil.clearAllMemoryCaches(applicationContext)
    }

}