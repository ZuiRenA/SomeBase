package project.shen.dessert_life.dessert_task.annotation

import android.os.Process
import androidx.annotation.IntRange
import project.shen.dessert_life.dessert_task.DessertTask
import kotlin.reflect.KClass

/**
 *  created by shen
 *  at 2019.2019/12/30.16:01
 *  @author shen
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Task

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskConfig(
    /**
     * Task priority, if invoke in MainThread don't modify
     */
    @IntRange(from = Process.THREAD_PRIORITY_FOREGROUND.toLong(), to = Process.THREAD_PRIORITY_LOWEST.toLong())
    val priority: Int = Process.THREAD_PRIORITY_BACKGROUND,

    val needRunAsSoon: Boolean = false,

    /**
     * ThreadExecutor default in "io" or "cpu" see [Executors]
     */
    @Executors
    val runOnExecute: String = Executors.IO,

    val needWait: Boolean = false,

    /**
     * Depend function name or class name, target must @Annotation [Task] or @implement [DessertTask]
     */
    val dependOn: Array<String> = [],

    val runOnMainThread: Boolean = false,

    val needCall: Boolean = false,

    /**
     * Is used in MainProcess
     */
    val onlyInMainProcess: Boolean = true,

    /**
     * Target callback
     */
    val targetCallback: String = "",

    /**
     * Target TailRunnable
     */
    val tailRunnable: String = ""
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class TaskCallback(
    val name: String
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class TaskTailRunnable(
    val name: String
)

typealias UnitFunction = () -> Unit

