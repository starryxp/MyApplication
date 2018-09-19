package com.example.xiaopeng.myapplication.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by xiaopeng on 2018/4/23
 */
public class ActivityStackManager {

    private final static String TAG = ActivityStackManager.class.getSimpleName();
    private volatile static ActivityStackManager instance = null;
    private static Stack<Activity> activityStack;// 栈

    /**
     * 私有构造
     */
    private ActivityStackManager() {
        activityStack = new Stack<Activity>();
    }

    /**
     * 单例实例
     */
    public static ActivityStackManager getManager() {
        if (instance == null) {
            synchronized (ActivityStackManager.class) {
                if (instance == null) {
                    instance = new ActivityStackManager();
                }
            }
        }
        return instance;
    }

    /**
     * 压栈
     */
    public void push(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 出栈
     */
    public Activity pop() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.pop();
    }

    /**
     * 栈顶
     */
    public Activity peek() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.peek();
    }

    /**
     * 用于异地登录或者退出时清除activity
     */
    public void clearActivity() {
        while (!activityStack.isEmpty()) {
            Activity activity = activityStack.pop();
            /*if (activity instanceof LoginActivity) {
            } else {
                activity.finish();
            }*/
        }
    }

    /**
     * 移除
     */
    public void remove(Activity activity) {
        if (activityStack.size() > 0 && activity == activityStack.peek()) {
            activityStack.pop();
        } else {
            activityStack.remove(activity);
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        if (activityStack.size() > 0) {
            Activity activity = activityStack.lastElement();
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
            }
        }
    }


    /**
     * 是否存在栈
     */
    public boolean contains(Activity activity) {
        return activityStack.contains(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        while (!activityStack.isEmpty()) {
            activityStack.pop().finish();
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                activityManager.restartPackage(context.getPackageName());
            }
            //清除通知栏
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.cancelAll();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
//            Logger.t(TAG).e(e, "exitApp is error");
        }
    }

    public boolean isEmpty() {
        if (activityStack == null) {
            return true;
        } else {
            return activityStack.isEmpty();
        }
    }

    public Activity getLastActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            return activityStack.lastElement();
        } else {
            return null;
        }
    }

    public Class getLastActivityClassName() {
        if (activityStack != null && activityStack.size() > 0) {
            return activityStack.lastElement().getClass();
        } else {
            return null;
        }
    }

}
