package project.shen.dessert_life.dessert_task

import android.os.Looper
import android.os.MessageQueue
import project.shen.dessert_life.task.Task
import java.util.*

/**
 * created by shen on 2019/10/27
 * at 21:21
 **/
class DelayDessertDispatcher {
    private val delayTasks: Queue<DessertTask> by lazy { LinkedList<DessertTask>() }

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

    fun start() {
        Looper.myQueue().addIdleHandler(idleHandler)
    }
}