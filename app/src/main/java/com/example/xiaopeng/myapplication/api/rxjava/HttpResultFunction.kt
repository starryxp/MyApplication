package com.example.xiaopeng.myapplication.api.rxjava

import com.example.xiaopeng.myapplication.api.exception.ExceptionEngine
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 *
 * @author xiaopeng
 * @date 2018/9/18
 *
 */
class HttpResultFunction<T> : Function<Throwable, Observable<T>> {

    override fun apply(t: Throwable): Observable<T> {
        return Observable.error(ExceptionEngine.handleException(t))
    }
}