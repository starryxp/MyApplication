package com.example.xiaopeng.myapplication.api.rxjava;


import com.example.xiaopeng.myapplication.response.ResponseData;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaopeng on 2018/4/23
 */
public class RxObservable {

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<ActivityEvent>自动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity,RxAppCompatActivity,RxFragmentActivity
     */
    public static <T> Observable getObservable(@NonNull Observable<ResponseData<T>> apiObservable, @NonNull LifecycleProvider lifecycle) {
        return apiObservable
                .compose(lifecycle.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<ActivityEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity,RxAppCompatActivity,RxFragmentActivity
     */
    public static <T> Observable getObservable(@NonNull Observable<ResponseData<T>> apiObservable, @NonNull LifecycleProvider<ActivityEvent> lifecycle, @NonNull ActivityEvent event) {
        return apiObservable
                .compose(lifecycle.bindUntilEvent(event))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * View有data返回值调用
     * 与View的生命周期绑定，在DETACHED的时候取消
     * 循环访问
     */
//    public static <T> Observable getIntervalObservable(@NonNull Observable<ResponseData<T>> apiObservable, @NonNull RxLinearLayout view, @NonNull long delayTime, @NonNull long periodTime) {
//        return Observable.interval(delayTime, periodTime, TimeUnit.MILLISECONDS)
//                .flatMap((Function<Long, Observable<ResponseData<T>>>) aLong -> apiObservable)
//                .compose(view.bindUntilEvent(RxLinearLayout.ViewEvent.DETACHED))
//                .onErrorResumeNext(new HttpResultFunction<>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    /**
     * View有data返回值调用
     * 与View的生命周期绑定，在DETACHED的时候取消
     */
//    public static <T> Observable getObservable(@NonNull Observable<ResponseData<T>> apiObservable, @NonNull RxLinearLayout view) {
//        //随生命周期自动管理.eg:onCreate(start)->onStop(end)
//        return apiObservable
//                .compose(view.bindUntilEvent(RxLinearLayout.ViewEvent.DETACHED))
//                .onErrorResumeNext(new HttpResultFunction<>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<FragmentEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxFragment,RxDialogFragment
     */
    public static <T> Observable getObservable(@NonNull Observable<ResponseData<T>> apiObservable, @NonNull LifecycleProvider<FragmentEvent> lifecycle, @NonNull FragmentEvent event) {
        return apiObservable
                .compose(lifecycle.bindUntilEvent(event))//需要在这个位置添加
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
