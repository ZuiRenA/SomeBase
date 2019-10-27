package com.shen.somebase.task

import com.shen.somebase.util.DebugLog
import project.shen.dessert_life.dessert_task.DessertTask
import project.shen.dessert_life.task.Task

/**
 * created by shen on 2019/10/27
 * at 21:40
 **/
class TaskTwo : DessertTask() {
    override fun run() {
        val intArray = intArrayOf(10)
        for (i in intArray.indices) {
            intArray[i] = i
        }
        DebugLog.logE("init", "two")
    }
}