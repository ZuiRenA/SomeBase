package com.shen.somebase.util

import android.util.Log

/**
 * Created by hongzhang on 2018/4/10.
 */

object DebugLog {
    var isDebug = true

    fun logE(tag: String, value: String) {
        if (!isDebug) {
            return
        } else {
            Log.e(tag, value)
        }

    }

    fun logD(tag: String, value: String) {
        if (!isDebug) {
            return

        } else {
            Log.d(tag, value)
        }

    }

    fun logI(tag: String, value: String) {
        if (!isDebug) {
            return
        } else {
            Log.i(tag, value)
        }

    }

    fun logW(tag: String, value: String) {
        if (!isDebug) {
            return
        } else {
            Log.w(tag, value)
        }

    }
}
