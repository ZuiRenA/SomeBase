package com.shen.somebase.widget

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast

import com.shen.somebase.view.BaseApplication

/**
 * 封装Toast，以便每次toast出来的msg能够及时显示
 * 而不是等待之前msg显示后在显示
 *
 * @author Hyl
 */
object ToastWidget {

    private var toast: Toast? = null
    private val mContext = BaseApplication.instance

    fun show(info: String) {
        if (TextUtils.isEmpty(info))
            return
        if (toast == null) {
            toast = Toast.makeText(mContext, info, Toast.LENGTH_SHORT)
            toast?.setGravity(Gravity.CENTER, 0, 0)
        } else {
            toast?.setText(info)
        }
        toast?.show()
    }

    //如果想在子线程中和子线程中都能使用，则调用此方法即可（前提是在Activity中，因为runOnUiThread属于Activity中的方法）
    fun showToastThread(context: Activity, messages: String) {
        if (TextUtils.isEmpty(messages))
            return
        context.runOnUiThread {
            if (toast == null) {
                toast = Toast.makeText(context, messages, Toast.LENGTH_SHORT)
                toast?.setGravity(Gravity.CENTER, 0, 0)
            } else {
                toast?.setText(messages)
            }
            toast?.show()
        }

    }

    fun show(info: Int) {

        if (toast == null) {
            toast = Toast.makeText(mContext, info, Toast.LENGTH_SHORT)
            toast?.setGravity(Gravity.CENTER, 0, 0)
        } else {
            toast?.setText(info)
        }
        toast?.show()

    }

}
