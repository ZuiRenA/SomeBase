package com.shen.somebase.coroutine

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

fun ViewModel.viewModelScope(
        errorAction: ErrorHandler,
        context: CoroutineContext = Dispatchers.Main,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
): Job = GlobalScope.launch(context, start){
        try {
            block()
        } catch (e: Exception) {
            errorAction(e)
        }
    }


typealias ErrorHandler = (e: Exception) -> Unit