package com.example.xiaopeng.myapplication.main

import com.example.xiaopeng.myapplication.mvp.IPresenter
import com.example.xiaopeng.myapplication.mvp.IView

/**
 *
 * @author xiaopeng
 * @date 2018/9/18
 *
 */
interface MainContract {

    interface View : IView<Presenter> {

        fun setText(string: String)

    }

    interface Presenter : IPresenter {

        fun test()

    }

}