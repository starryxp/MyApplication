package com.example.xiaopeng.myapplication.api.rxjava;

import com.example.xiaopeng.myapplication.R;
import com.example.xiaopeng.myapplication.api.exception.ApiException;
import com.example.xiaopeng.myapplication.api.exception.ExceptionEngine;
import com.example.xiaopeng.myapplication.mvp.BaseView;
import com.example.xiaopeng.myapplication.moudel.response.ResponseData;
import com.example.xiaopeng.myapplication.utils.AppUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiaopeng on 2018/4/23
 */
public abstract class RxObserver<T> implements Observer<ResponseData<T>> {

    private BaseView view;

    public RxObserver(BaseView view) {
        this.view = view;
    }

    @Override
    public void onError(Throwable e) {
        if (view != null) {
            view.hideLoadingDialog();
        }
        onError(ExceptionEngine.handleException(e));
    }

    @Override
    public void onComplete() {
    }


    @Override
    public void onNext(ResponseData<T> responseData) {
        if (view != null) {
            view.hideLoadingDialog();
        }
        try {
            if (responseData.getErrCode() != 0) {
                ApiException e = new ApiException(responseData.getErrCode(), responseData.getErrMsg());
                if (responseData.getData() != null) {
                    onError(e, responseData.getData());
                } else {
                    onError(e);
                }
                return;
            }
            onSuccess(responseData.getData());
        } catch (Exception e) {
            e.printStackTrace();
            ApiException apiException = new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR);
            apiException.setMsg(AppUtils.getInstance().getString(R.string.resmsg_net_error));
            onError(apiException);
        }
    }


    //处理单点登录
//    private boolean handleLoginOut(ApiException apiException) {
//        if (apiException.getCode() == ResponseData.LOGIN_OUT_CODE || apiException.getCode() == ResponseData.NO_LOGIN_CODE) {
//            if (UserManager.Companion.getInstance().isLogin()) {
//                ToastUtil.info(apiException.getMsg());
//                EventBus.getDefault().post(new LoginMsgEvent(MsgEventConfig.MSG_EVENT_EXTRUDE_LOGIN_SUCCESS));
//            }
//            return true;
//        }
//        return false;
//    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        if (view != null) {
            view.showLoadingDialog();
        }
        onStart(disposable);
    }

    protected abstract void onStart(Disposable disposable);

    /**
     * 成功回调
     */
    protected abstract void onSuccess(T response);

    /**
     * 错误/异常回调
     */
    protected abstract void onError(ApiException e);

    protected void onError(ApiException e, T data) {

        onError(e);
    }

}
