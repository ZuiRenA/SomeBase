package com.shen.somebase

import android.app.Application
import android.util.Log
import com.shen.runtime.ActivityBuilder
import com.shen.somebase.api.Api
import com.shen.somebase.api.RetrofitHelper
import com.shen.somebase.task.TaskObj
import project.shen.dessert_life.dessert_task.DessertDispatcher
import project.shen.dessert_life.dessert_task.annotation.Task
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


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

//        DessertDispatcher.init(instance)
//        DessertDispatcher.getInstance()
//            .addTask(TaskBuilder())
//            .addTask(TaskOne())
//            .addTask(TaskTwo())
//            .addTask(TaskThree())
//            .addTask(TaskFourJava())
//            .start()
        DessertDispatcher.init(instance)
        val tasks = DessertDispatcher.getInstance().create(TaskObj::class.java)

        tasks.one()
        tasks.two()
        tasks.three()
        DessertDispatcher.getInstance().start()
    }

    companion object {
        var instance: BaseApplication? = null
            private set
    }
}
