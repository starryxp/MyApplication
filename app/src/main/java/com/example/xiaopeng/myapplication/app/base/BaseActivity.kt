package com.example.xiaopeng.myapplication.app.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.widget.Toast
import com.example.xiaopeng.myapplication.mvp.BaseView
import com.example.xiaopeng.myapplication.utils.ActivityStackManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *
 * @author xiaopeng
 * @date 2018/9/18
 *
 */
abstract class BaseActivity : RxAppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityStackManager.getManager().push(this)
//        if (loadBaseStateView()) {
//            setContentView(R.layout.layout_base_view)
//            initView()
//        } else {
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
//        }

        EventBus.getDefault().register(this)

        onCreateExecute(savedInstanceState)
    }

    /**
     * setContentView方法会回调这个方法得到布局id，如果返回的id=0,不会自动设置布局
     *
     * @return 布局id
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun onCreateExecute(savedInstanceState: Bundle?)

    override fun showLoadingDialog() {
        Toast.makeText(applicationContext, "showLoadingDialog", Toast.LENGTH_SHORT).show()
    }

    override fun hideLoadingDialog() {
        Toast.makeText(applicationContext, "hideLoadingDialog", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        ActivityStackManager.getManager().finishCurrentActivity()
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        ActivityStackManager.getManager().remove(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun event(msg: String) {

    }

}