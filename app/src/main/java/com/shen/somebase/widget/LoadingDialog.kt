package com.shen.somebase.widget

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View

import com.shen.somebase.R


class LoadingDialog : Dialog {

    /**
     * @param context
     */
    constructor(context: Context) : super(context, R.style.loading_dialog) {
        setCustomView()
    }// 自定义style主要去掉标题，标题将在setCustomView中自定义设置

    constructor(
        context: Context, cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener
    ) : super(context, R.style.loading_dialog) {
        this.setOnCancelListener(cancelListener)
        setCustomView()
    }

    constructor(context: Context, theme: Int) : super(context, R.style.loading_dialog) {
        setCustomView()
    }

    /**
     * 设置整个弹出框的视图
     */
    @SuppressLint("InflateParams")
    private fun setCustomView() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        this.setCanceledOnTouchOutside(false)
        super.setContentView(view)
    }

    override fun setContentView(view: View) {
        // 重写本方法，使外部调用时不可破坏控件的视图。
        // 也可以使用本方法改变CustomDialog的内容部分视图，比如让用户把内容视图变成复选框列表，图片等。这需要获取mView视图里的其它控件
    }

    override fun dismiss() {
        super.dismiss()
    }
}