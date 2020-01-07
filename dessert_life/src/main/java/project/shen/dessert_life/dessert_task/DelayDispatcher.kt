package project.shen.dessert_life.dessert_task

import android.os.Looper
import android.os.MessageQueue
import project.shen.dessert_life.dessert_task.annotation_tools.AnnotationConvertTools
import java.util.*

/**
 * created by shen on 2019/10/27
 * at 21:21
 **/
class DelayDessertDispatcher {
    private val delayTasks: Queue<DessertTask> by lazy { LinkedList<DessertTask>() }

    var interfaceCreate = false

    private val idleHandler = MessageQueue.IdleHandler {
        if (delayTasks.size > 0) {
            val task = delayTasks.poll()
            task?.let {
                DessertDispatchRunnable(task)
            }
        }

        !delayTasks.isEmpty()
    }

    fun addTask(task: DessertTask): DelayDessertDispatcher {
        delayTasks.add(task)
        return this
    }

    inline fun <reified T> create(interfaceObj : Class<T>, interfaceImpl: T, autoInvoke: Boolean = true) = AnnotationConvertTools.instance
        .dispatcher(this)
        .create(interfaceObj, interfaceImpl).also {
            this.interfaceCreate = true
            if (autoInvoke) {
                it.autoInvoke()
            }
        }

    inline fun <reified T> T.autoInvoke() {
        T::class.java.methods.forEach {
            it.invoke(this)
        }
    }

    fun start() {
        if (interfaceCreate) {
            AnnotationConvertTools.instance.autoInvoke()
        }

        Looper.myQueue().addIdleHandler(idleHandler)
    }
}