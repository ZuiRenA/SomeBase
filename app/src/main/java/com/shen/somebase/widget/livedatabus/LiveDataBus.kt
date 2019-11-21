package com.shen.somebase.widget.livedatabus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer



/**
 *  created by shen at 2019/11/12
 */
class LiveDataBus private constructor() {

    private val bus by lazy { hashMapOf<String, BusMutableLiveData<*>>() }

    fun <T> with(key: String, type: Class<T>): BusMutableLiveData<T> {
        if (!bus.containsKey(key))
            bus[key] = BusMutableLiveData<T>()
        return bus[key] as BusMutableLiveData<T>
    }

    fun with(key: String): BusMutableLiveData<Any> = with(key, Any::class.java)

    companion object {
        @JvmStatic
        var debug: Boolean = false

        val get by lazy { LiveDataBus() }

        private class ObserverWrapper<T>(private val observer: Observer<T>? = null) : Observer<T> {

            override fun onChanged(t: T) {
                observer?.let {
                    if (isCallOnObserve) {
                        return
                    }

                    observer.onChanged(t)
                }
            }

            private val isCallOnObserve: Boolean
                get() =  Thread.currentThread().stackTrace.run {
                if (isNotEmpty()) {
                    forEach {
                        if ("androidx.lifecycle.LiveData" == it.className && "observeForever" == it.methodName) {
                            return@run true
                        }
                    }
                }

                false
            }
        }

        class BusMutableLiveData<T> : MutableLiveData<T>() {
            private val observerMap = hashMapOf<Observer<in T>, Observer<in T>>()

            override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
                super.observe(owner, observer)
                try {
                    hook(observer)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun observeForever(observer: Observer<in T>) {
                if (!observerMap.containsKey(observer)) {
                    observerMap[observer] = ObserverWrapper(observer)
                }
                super.observeForever(observer)
            }

            override fun removeObserver(observer: Observer<in T>) {
                val realObserver = if (observerMap.containsKey(observer)) observerMap.remove(observer)
                else observer
                super.removeObserver(realObserver!!)
            }

            private fun hook(observer: Observer<in T>) {
                val fieldObservers = LiveData::class.java.getDeclaredField("mObservers").apply { isAccessible = true }
                val objObservers = fieldObservers.get(this)
                val classObservers = objObservers.javaClass
                val methodGet = classObservers.getDeclaredMethod("get", Any::class.java).apply { isAccessible = true }
                val objWrapperEntry = methodGet.invoke(objObservers, observer)

                val objWrapper = if (objWrapperEntry is Map.Entry<*, *>) {
                    objWrapperEntry.value
                } else null

                require(objWrapper != null) { "Wrapper can not be bull!" }
                val fieldLastVersion = objWrapper.javaClass.superclass?.getDeclaredField("mLastVersion")?.apply { isAccessible = true }
                val fieldVersion = LiveData::class.java.getDeclaredField("mVersion").apply { isAccessible = true }
                fieldLastVersion?.set(objWrapper, fieldVersion.get(this))
            }
        }
    }
}