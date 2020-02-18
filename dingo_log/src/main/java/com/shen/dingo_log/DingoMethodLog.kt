package com.shen.dingo_log

import android.text.TextUtils.lastIndexOf
import android.util.Log
import com.shen.dingo_log.ILog.Companion.VERTICAL_LINE
import com.shen.dingo_log.ILog.Companion.cacheName
import com.shen.dingo_log.ILog.Companion.methodName

/**
 *  created at 2020.2020/2/17.14:27
 *  @author shen
 */
class DingoMethodLog private constructor() : ILog {
    companion object {
        internal val getInstance by lazy { DingoMethodLog() }
    }


    override fun v(value: String) {
        if (cacheName == null) {
            LogLevel.V.autoLogLineStart()
        }

        if (cacheName != null && !methodName.contains(cacheName.toString())) {
            LogLevel.V.autoLogLineEnd()
        }

        cacheName = methodName
        Log.v("$VERTICAL_LINE $cacheName" , "$value $VERTICAL_LINE")
    }

    override fun d(value: String) {
        if (cacheName == null) {
            LogLevel.D.autoLogLineStart()
        }

        if (cacheName != null && !methodName.contains(cacheName.toString())) {
            LogLevel.D.autoLogLineEnd()
        }

        cacheName = methodName
        Log.d("$VERTICAL_LINE $cacheName" , "$value $VERTICAL_LINE")
    }

    override fun i(value: String) {
        if (cacheName == null) {
            LogLevel.I.autoLogLineStart()
        }

        if (cacheName != null && !methodName.contains(cacheName.toString())) {
            LogLevel.I.autoLogLineEnd()
        }

        cacheName = methodName
        Log.i("$VERTICAL_LINE $cacheName" , "$value $VERTICAL_LINE")
    }

    override fun w(value: String) {
        if (cacheName == null) {
            LogLevel.W.autoLogLineStart()
        }

        if (cacheName != null && !methodName.contains(cacheName.toString())) {
            LogLevel.W.autoLogLineEnd()
        }

        cacheName = methodName
        Log.w("$VERTICAL_LINE $cacheName" , "$value $VERTICAL_LINE")
    }

    override fun e(value: String) {
        if (cacheName == null) {
            LogLevel.E.autoLogLineStart()
        }

        if (cacheName != null && !methodName.contains(cacheName.toString())) {
            LogLevel.E.autoLogLineEnd()
        }

        cacheName = methodName
        Log.e("$VERTICAL_LINE $cacheName" , "$value $VERTICAL_LINE")
    }

    override fun a(value: String) {
        if (cacheName == null) {
            LogLevel.A.autoLogLineStart()
        }

        if (cacheName != null && !methodName.contains(cacheName.toString())) {
            LogLevel.A.autoLogLineEnd()
        }

        cacheName = methodName
        Log.wtf("$VERTICAL_LINE $cacheName" , "$value $VERTICAL_LINE")
    }
}