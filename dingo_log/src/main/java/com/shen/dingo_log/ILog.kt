package com.shen.dingo_log

import android.util.Log
import androidx.annotation.StringDef

/**
 *  created at 2020.2020/2/17.14:29
 *  @author shen
 */
interface ILog {
    companion object {
        internal const val VERTICAL_LINE = "|"
        internal const val HORIZONTAL_LINE = "—"
        internal const val LONG_HORIZONTAL_LINE = "——————————————————————————————————————————————————————————————————————————"

        internal val methodName: String
            get() = Thread.currentThread().stackTrace.run {
                this[4].methodName
            }
        internal val className: String
            get() = Thread.currentThread().stackTrace.run {
                this[4].className
            }

        internal var cacheName: String? = null
    }

    fun v(value: String)

    fun d(value: String)

    fun i(value: String)

    fun w(value: String)

    fun e(value: String)

    fun a(value: String)

    fun String.autoLogLineStart() {
        when(this) {
            LogLevel.V -> Log.v("${cacheName ?: "Unknown"} Start", LONG_HORIZONTAL_LINE)
            LogLevel.D -> Log.d("${cacheName ?: "Unknown"} Start", LONG_HORIZONTAL_LINE)
            LogLevel.I -> Log.i("${cacheName ?: "Unknown"} Start", LONG_HORIZONTAL_LINE)
            LogLevel.W -> Log.w("${cacheName ?: "Unknown"} Start", LONG_HORIZONTAL_LINE)
            LogLevel.E -> Log.e("${cacheName ?: "Unknown"} Start", LONG_HORIZONTAL_LINE)
            LogLevel.A -> Log.e("${cacheName ?: "Unknown"} Start", LONG_HORIZONTAL_LINE)
        }
    }

    fun String.autoLogLineEnd() {
        when(this) {
            LogLevel.V -> Log.v("${cacheName ?: "Unknown"} End", LONG_HORIZONTAL_LINE)
            LogLevel.D -> Log.d("${cacheName ?: "Unknown"} End", LONG_HORIZONTAL_LINE)
            LogLevel.I -> Log.i("${cacheName ?: "Unknown"} End", LONG_HORIZONTAL_LINE)
            LogLevel.W -> Log.w("${cacheName ?: "Unknown"} End", LONG_HORIZONTAL_LINE)
            LogLevel.E -> Log.e("${cacheName ?: "Unknown"} End", LONG_HORIZONTAL_LINE)
            LogLevel.A -> Log.e("${cacheName ?: "Unknown"} End", LONG_HORIZONTAL_LINE)
        }
    }
}

@StringDef(value = [LogLevel.V, LogLevel.D, LogLevel.I, LogLevel.W, LogLevel.E, LogLevel.A])
@Retention(AnnotationRetention.SOURCE)
annotation class LogLevel {
    companion object {
        const val V = "verbose"
        const val D = "debug"
        const val I = "info"
        const val W = "warn"
        const val E = "error"
        const val A = "assert"
    }
}

