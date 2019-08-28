package com.shen.somebase.mvp

import android.os.Bundle

interface Ilifeycycle {
    fun onCreate(savedInstanceState: Bundle?)

    fun onSaveInstanceState(outState: Bundle)

    fun onViewStateRestored(savedInstanceState: Bundle?)

    fun onDestroy()

    fun onStart()

    fun onStop()

    fun onResume()

    fun onPause()

    fun onRestoreInstanceState(savedInstanceState: Bundle?)
}

interface BaseView {
    /**
     * 显示加载中
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 数据获取失败
     * @param throwable
     */
    fun onError(throwable: Throwable)

    /**
     * 数据获取失败
     * @param throwable
     */
    fun onRequestError(throwable: Throwable, index: Int)
}