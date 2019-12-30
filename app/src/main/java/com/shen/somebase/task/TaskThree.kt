package com.shen.somebase.task

import com.shen.somebase.util.DebugLog
import project.shen.dessert_life.dessert_task.DessertTask

/**
 * created by shen on 2019/10/27
 * at 21:41
 **/
class TaskThree : DessertTask() {
    override fun run() {
        val intArray = intArrayOf(10)
        for (i in intArray.indices) {
            intArray[i] = i
        }
        DebugLog.logE("init", "three")
    }
}