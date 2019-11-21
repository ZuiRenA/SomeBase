package com.shen.somebase.widget.livedatabus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 *  created by shen at 2019/11/12
 */
@LiveDataBusMaker
class DslLiveDataBus<T> {
    lateinit var tag: String
    lateinit var type: Class<T>
    lateinit var action: T.() -> Unit

    fun build(lifecycleOwner: LifecycleOwner) = LiveDataBus.get
        .with(tag, type)
        .observe(lifecycleOwner, Observer { action.invoke(it) })
}

fun <T> LifecycleOwner.liveDataBus(init: DslLiveDataBus<T>.() -> Unit) = DslLiveDataBus<T>().apply {
    init()
    build(this@liveDataBus)
}

@DslMarker
internal annotation class LiveDataBusMaker