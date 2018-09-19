package com.example.xiaopeng.myapplication.api.rxjava

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 *
 * @author xiaopeng
 * @date 2018/9/18
 *
 */
open class RxView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr), LifecycleProvider<ViewEvent> {
    private val lifecycleSubject = BehaviorSubject.create<ViewEvent>()
    override fun lifecycle(): Observable<ViewEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: ViewEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent<T, ViewEvent>(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindView(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleSubject.onNext(ViewEvent.ATTACHED)
    }

    override fun onDetachedFromWindow() {
        lifecycleSubject.onNext(ViewEvent.DETACHED)
        super.onDetachedFromWindow()
    }
}