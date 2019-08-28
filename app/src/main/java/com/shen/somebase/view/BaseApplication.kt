package com.shen.somebase.view

import android.app.Application
import android.content.Context

/**
 * Created by hongzhang on 2019/7/24.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    companion object {
        var instance: BaseApplication? = null
            private set
    }
}
