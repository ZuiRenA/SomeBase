package com.shen.somebase.coroutine


import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.shen.somebase.util.DebugLog
import kotlinx.coroutines.*
import java.io.Closeable
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext


@SuppressLint("UseSparseArrays")
abstract class BaseViewModel: ViewModel() {

    private val jobMap: HashMap<Int,Job> by lazy {
        HashMap<Int,Job>()
    }

    //扩展函数
    fun Job.addJobToAutoCancelMap(): Job {
        synchronized(jobMap) {
            if (!jobMap.containsValue(this)) {
                jobMap[jobMap.size] = this
            }
        }
        return this
    }

    //生命周期绑定结束，即依附的activity或者fragment被销毁
    //进行协程任务取消，防止内存泄露
    override fun onCleared() {
        DebugLog.logD("ViewModel", "onClear")
        if (jobMap.isNotEmpty()) {
            for ((key, value) in jobMap) {
                DebugLog.logD("ViewModelClear", "$key, $value")
                value.cancel(cause = CancellationException("viewModel cleared"))
            }
        }
        jobMap.clear()
        DebugLog.logD("ViewModelJobMap", "${jobMap.size}")
        super.onCleared()
    }


    //根据谷歌官方的viewModelScope进行修改
    //因为调不到ViewModel内部的getTag 和 setTagIfAbsent，使用反射方法曲线救国
    inline val ViewModel.viewModelScope: CoroutineScope
        get() {
            val clazz = Class.forName("androidx.lifecycle.ViewModel")
            val method = clazz.getDeclaredMethod("getTag", String::class.java)
            method.isAccessible = true

            val scope: CoroutineScope? = method.invoke(this, JOB_KEY) as? CoroutineScope?
            if (scope != null) {
                return scope
            }

            val methodSetTagIfAbsent = clazz.getDeclaredMethod("setTagIfAbsent",
                    String::class.java, Any::class.java)
            methodSetTagIfAbsent.isAccessible = true
            return methodSetTagIfAbsent.invoke(this, JOB_KEY,
                    CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main)) as CoroutineScope
        }

    //viewModelScope的包装方法，多传了个错误已处理的方法
    fun ViewModel.viewModelScopeByErrorCatch(
        errorHandler: ErrorHandler,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                errorHandler(e)
            }
        }
    }

    class CloseableCoroutineScope(context: CoroutineContext): Closeable, CoroutineScope {
        override val coroutineContext: CoroutineContext = context

        override fun close() {
            coroutineContext.cancel()
        }
    }

    companion object {
        const val JOB_KEY = "androidx.lifecycle.ViewModelCoroutineScope.JOB_KEY"
    }


}
