package com.shen.somebase.task

import android.util.Log
import com.shen.somebase.util.DebugLog
import project.shen.dessert_life.dessert_task.DessertTask

/**
 * created by shen on 2019/10/27
 * at 21:40
 **/
class TaskOne : DessertTask() {

    override val needCall: Boolean = true

    override var callback: (() -> Unit)? = {
        DebugLog.logE("callback", "one")
    }

    override fun run() {
        val intArray = intArrayOf(10)
        for (i in intArray.indices) {
            intArray[i] = i
            DebugLog.logE("one", i.toString())
        }
        DebugLog.logE("init", "one")
    }
}