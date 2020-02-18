package com.shen.dingo_log

import android.util.Log
import com.shen.dingo_log.ILog.Companion.LONG_HORIZONTAL_LINE
import com.shen.dingo_log.ILog.Companion.VERTICAL_LINE
import com.shen.dingo_log.ILog.Companion.cacheName
import com.shen.dingo_log.ILog.Companion.className

/**
 *  created at 2020.2020/2/17.14:24
 *  @author shen
 */
class DingoClassLog private constructor() : ILog {
    companion object {
        internal val getInstance by lazy { DingoClassLog() }

    }

    override fun v(value: String) {
        if (cacheName == null) {
            LogLevel.V.autoLogLineStart()
        }

        if (cacheName != null && !className.contains(cacheName.toString())) {
            LogLevel.V.autoLogLineEnd()
        }

        cacheName = className
        Log.v("$VERTICAL_LINE $cacheName" , "$value $VERTICAL_LINE")
    }

    override fun d(value: String) {
        if (cacheName == null) {
            LogLevel.D.autoLogLineStart()
        }

        if (cacheName != null && !className.contains(cacheName.toString())) {
            LogLevel.D.autoLogLineEnd()
        }

        cacheName = className
        Log.d("$VERTICAL_LINE $cacheName", "$value $VERTICAL_LINE")
    }

    override fun i(value: String) {
        if (cacheName == null) {
            LogLevel.I.autoLogLineStart()
        }

        if (cacheName != null && !className.contains(cacheName.toString())) {
            LogLevel.I.autoLogLineEnd()
        }

        cacheName = className
        Log.i("$VERTICAL_LINE $cacheName", "$value $VERTICAL_LINE")
    }

    override fun w(value: String) {
        if (cacheName == null) {
            LogLevel.W.autoLogLineStart()
        }

        if (cacheName != null && !className.contains(cacheName.toString())) {
            LogLevel.W.autoLogLineEnd()
        }

        cacheName = className
        Log.w("$VERTICAL_LINE $cacheName", "$value $VERTICAL_LINE")
    }

    override fun e(value: String) {
        if (cacheName == null) {
            LogLevel.E.autoLogLineStart()
        }

        if (cacheName != null && !className.contains(cacheName.toString())) {
            LogLevel.E.autoLogLineEnd()
        }

        cacheName = className
        Log.e("$VERTICAL_LINE $cacheName", "$value $VERTICAL_LINE")
    }

    override fun a(value: String) {
        if (cacheName == null) {
            LogLevel.A.autoLogLineStart()
        }

        if (cacheName != null && !className.contains(cacheName.toString())) {
            LogLevel.A.autoLogLineEnd()
        }

        cacheName = className
        Log.wtf("$VERTICAL_LINE $cacheName", "$value $VERTICAL_LINE")
    }
}