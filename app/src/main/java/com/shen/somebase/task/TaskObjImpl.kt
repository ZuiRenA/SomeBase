package com.shen.somebase.task

import android.util.Log
import com.shen.runtime.ActivityBuilder
import project.shen.dessert_life.dessert_task.DessertDispatcher

/**
 *  created by shen
 *  at 2020.2020/1/7.14:46
 *  @author shen
 */
class TaskObjImpl : ITaskObj {
    override fun one() {
        Log.d("one", "start")
        ActivityBuilder.INSTANCE.init(DessertDispatcher.getContext())
    }

    override fun two() {
        Log.d("two", "start")
    }

    override fun three() {
        Log.d("three", "start")
    }

    override fun threeCallback() {
        Log.d("threeCallback", "start")
    }
}