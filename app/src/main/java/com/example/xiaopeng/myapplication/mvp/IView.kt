package com.example.xiaopeng.myapplication.mvp

/**
 *
 * @author xiaopeng
 * @date 2018/9/18
 *
 */
interface IView<T> : BaseView {

    fun setPresenter(presenter: T?)

}