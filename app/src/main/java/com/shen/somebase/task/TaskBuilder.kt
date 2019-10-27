package com.shen.somebase.task

import com.shen.runtime.ActivityBuilder
import com.shen.somebase.util.DebugLog
import project.shen.dessert_life.dessert_task.DessertTask
import project.shen.dessert_life.task.Task

/**
 * created by shen on 2019/10/27
 * at 21:39
 **/
class TaskBuilder : DessertTask() {

    override val dependOn: List<Class<out DessertTask>>? = listOf(TaskThree::class.java)

    override fun run() {
        ActivityBuilder.INSTANCE.init(context)
        DebugLog.logE("init", "ActivityBuilder")
    }
}