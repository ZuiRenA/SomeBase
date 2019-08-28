package com.shen.somebase.mvp

import android.os.Bundle
import com.shen.somebase.api.Api
import com.shen.somebase.api.RetrofitHelper

abstract class BasePresenterKt<out V: IMVP.IView<BasePresenterKt<V>>>: IMVP.IPresenter<V>{

    override lateinit var view: @UnsafeVariance V

    //by lazy表示使用时再进行初始化，by 用到了委托，把初始化委托给了 lazy 去初始化
    protected val apiService: Api by lazy {
        RetrofitHelper.instance.apiService
    }

    override fun onCreate(savedInstanceState: Bundle?) {}
    override fun onSaveInstanceState(outState: Bundle) {}
    override fun onViewStateRestored(savedInstanceState: Bundle?) {}
    override fun onDestroy() {}
    override fun onStart() {}
    override fun onStop() {}
    override fun onResume() {}
    override fun onPause() {}
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {}
}