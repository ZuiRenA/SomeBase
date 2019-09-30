package com.shen.somebase

import android.app.Application
import com.shen.runtime.ActivityBuilder

/**
 * Created by hongzhang on 2019/7/24.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        ActivityBuilder.INSTANCE.init(this)
    }

    companion object {
        var instance: BaseApplication? = null
            private set
    }
}
