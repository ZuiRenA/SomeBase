package com.shen.somebase.task

import android.util.Log
import com.shen.runtime.ActivityBuilder
import project.shen.dessert_life.dessert_task.DessertDispatcher
import project.shen.dessert_life.dessert_task.annotation.Task
import project.shen.dessert_life.dessert_task.annotation.TaskCallback
import project.shen.dessert_life.dessert_task.annotation.TaskConfig

/**
 *  created by shen
 *  at 2020.2020/1/2.14:11
 *  @author shen
 */
interface TaskObj {

    @Task
    fun one() {
        ActivityBuilder.INSTANCE.init(DessertDispatcher.getContext())
    }

    @Task
    @TaskConfig(dependOn = ["one"])
    fun two() {
        Log.d("two", "start")
    }

    @Task
    @TaskConfig(targetCallback = "callback")
    fun three() {
        Log.d("three", "start")
    }

    @TaskCallback("callback")
    fun threeCallback() {
        Log.d("threeCallback", "start")
    }
}