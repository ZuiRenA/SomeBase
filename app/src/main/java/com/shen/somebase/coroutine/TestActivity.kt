package com.shen.somebase.coroutine

import androidx.lifecycle.Observer
import com.shen.somebase.R
import com.shen.somebase.widget.ToastWidget
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity: BaseViewModelActivity<TestViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_test

    override fun init() {

        //观察者模式，观察viewModel内部LiveData的变化
        viewModel.testResult.observe(this, Observer {
            ToastWidget.show("wow")
            testTv.text = "after"
        })

        //发起网络请求，传入错误处理
        viewModel.show2("") {
            onErrorInMain(it)
        }

        parentTest.setOnClickListener {
            finish()
        }
    }
}
