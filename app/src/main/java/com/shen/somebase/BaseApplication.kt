package com.shen.somebase

import android.app.Application
import com.shen.runtime.ActivityBuilder
import com.shen.somebase.task.*
import com.shen.somebase.util.DebugLog
import com.shen.somebase.widget.ToastWidget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.shen.dessert_life.dessert_task.DessertDispatcher
import project.shen.dessert_life.dessert_task.DessertTask
import project.shen.dessert_life.task.Task
import project.shen.dessert_life.task.TaskDispatcher

/**
 * Created by hongzhang on 2019/7/24.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
//        ActivityBuilder.INSTANCE.init(instance)
//        initOne()
//        initTwo()
//        initThree()

        DessertDispatcher.init(instance)
        DessertDispatcher.getInstance()
            .addTask(TaskBuilder())
            .addTask(TaskOne())
            .addTask(TaskTwo())
            .addTask(TaskThree())
            .addTask(TaskFourJava())
            .start()
    }

    companion object {
        var instance: BaseApplication? = null
            private set
    }
}
