package project.shen.dessert_life.dessert_task.annotation_tools

import project.shen.dessert_life.dessert_task.DessertTask
import project.shen.dessert_life.dessert_task.annotation.*
import project.shen.dessert_life.dessert_task.easyTask
import project.shen.dessert_life.dessert_task.getCPUExecute
import project.shen.dessert_life.dessert_task.getIOExecute
import java.lang.reflect.Method
import java.util.concurrent.ExecutorService

/**
 *  created by shen
 *  at 2019.2019/12/30.17:42
 *  @author shen
 */
class TaskFactory(private val builder: Builder) {

    companion object {
        @JvmStatic
        fun parseAnnotations(convert: AnnotationConvertTools, method: Method, args: Array<Any>): TaskFactory = Builder(convert, method, args).build()


        class Builder(private val convert: AnnotationConvertTools, private val method: Method, private val args: Array<Any>) {
            private val methodAnnotations: Array<Annotation> = method.annotations

            private var methodTask: DessertTask? = null
            private var methodTailRunnable: Runnable? = null
            private var methodCallback: (() -> Unit)? = null

            fun build(): TaskFactory {
                methodAnnotations.forEach {
                    parseMethodAnnotation(it)
                }

                return TaskFactory(this)
            }

            private fun parseMethodAnnotation(annotation: Annotation) {
                when(annotation) {
                    is Task -> {
                        parseTaskAnnotation()
                    }

                    is TaskCallback -> {

                    }

                    is TaskTailRunnable -> {

                    }
                }
            }

            private fun parseTaskAnnotation() {
                var taskConfig: TaskConfig? = null
                method.annotations.forEach {
                    if (it is TaskConfig) {
                        taskConfig = it
                        return@forEach
                    }
                }

                val dessertTask = if (taskConfig == null) {
                    easyTask { method.invoke(this, args) }
                } else {
                    initConfig(taskConfig!!)
                }

                methodTask = dessertTask
            }

            private fun initConfig(taskConfig: TaskConfig) = object : DessertTask() {

                override fun needRunAsSoon(): Boolean = taskConfig.needRunAsSoon

                override fun priority(): Int = taskConfig.priority

                override val runOn: ExecutorService = if (taskConfig.runOnExecute == Executors.IO) getIOExecute() else getCPUExecute()

                override val needWait: Boolean = taskConfig.needWait

                override val runOnMainThread: Boolean = taskConfig.runOnMainThread

                override val needCall: Boolean = taskConfig.needCall

                override val onlyInMainProcess: Boolean = taskConfig.onlyInMainProcess

                override fun run() {
                    method.invoke(this, args)
                }
            }
        }
    }
}