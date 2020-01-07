package com.shen.somebase

import android.app.Application
import com.shen.somebase.task.ITaskObj
import com.shen.somebase.task.TaskObjImpl
import com.shen.somebase.task.TaskOne
import project.shen.dessert_life.dessert_task.DessertDispatcher


/**
 * Created by hongzhang on 2019/7/24.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        DessertDispatcher.init(instance)
//        DessertDispatcher.init(instance)
//        DessertDispatcher.getInstance()
//            .addTask(TaskBuilder())
//            .addTask(TaskOne())
//            .addTask(TaskTwo())
//            .addTask(TaskThree())
//            .addTask(TaskFourJava())
//            .start()

        DessertDispatcher
            .getInstance()
            .create(ITaskObj::class.java, TaskObjImpl())
        DessertDispatcher.getInstance().start()
    }

    companion object {
        var instance: BaseApplication? = null
            private set
    }
}
