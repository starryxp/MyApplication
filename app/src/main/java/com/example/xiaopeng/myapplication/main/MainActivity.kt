package com.example.xiaopeng.myapplication.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.xiaopeng.myapplication.R
import com.example.xiaopeng.myapplication.app.MyApplication
import com.example.xiaopeng.myapplication.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View {

    private var mPresenter: MainContract.Presenter? = null

    override fun onCreateExecute(savedInstanceState: Bundle?) {
        button.setOnClickListener { launch(this) }

        MainPresenter(this, this)

        mPresenter?.test()


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setPresenter(presenter: MainContract.Presenter?) {
        mPresenter = presenter
    }

    override fun setText(string: String) {
        sample_text.text = string
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Example of a call to a native method
//        sample_text.text = stringFromJNI()
//    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    external fun stringFromJNI(): String
//
//    companion object {
//
//        // Used to load the 'native-lib' library on application startup.
//        init {
//            System.loadLibrary("native-lib")
//        }
//    }

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }

}
