package com.example.xiaopeng.myapplication.main

import com.example.xiaopeng.myapplication.api.ApiRetrofit
import com.example.xiaopeng.myapplication.api.rxjava.RxObserver
import com.example.xiaopeng.myapplication.api.exception.ApiException
import com.example.xiaopeng.myapplication.api.rxjava.RxObservable
import com.example.xiaopeng.myapplication.response.LoginResponse
import com.example.xiaopeng.myapplication.response.ResponseData
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.disposables.Disposable

/**
 *
 * @author xiaopeng
 * @date 2018/9/18
 *
 */
class MainPresenter(var view: MainContract.View, private var lifecycle: LifecycleProvider<ActivityEvent>) : MainContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun test() {
        RxObservable.getObservable(ApiRetrofit.getInstance().Login("qq", "11"), lifecycle)
                .subscribe(object : RxObserver<ResponseData<LoginResponse>>(view) {
                    override fun onStart(disposable: Disposable?) {

                    }

                    override fun onSuccess(response: ResponseData<LoginResponse>?) {
                        view.setText("ok")
                    }

                    override fun onError(e: ApiException) {
                        view.setText(e.code.toString() + e.msg)
//                        Toast.makeText(AppUtils.getInstance().appContext, e?.msg, Toast.LENGTH_SHORT).show()
                    }

                })
    }


}