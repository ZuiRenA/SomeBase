package project.shen.dessert_life.dessert_task.annotation_tools

import project.shen.dessert_life.dessert_task.DelayDessertDispatcher
import project.shen.dessert_life.dessert_task.DessertDispatcher
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 *  created by shen
 *  at 2019.2019/12/30.17:07
 *  @author shen
 */

class AnnotationConvertTools private constructor () {

    companion object {
        val instance by lazy { AnnotationConvertTools() }

        fun <T> validateServiceInterface(service: Class<T>) {
            require(service.isInterface) { "API declarations must be interfaces." }
            require(service.interfaces.isEmpty()) { "API interfaces must not extend other interfaces." }
        }
    }

    private val serviceMethodCache: MutableMap<Method, DessertMethod<*>> = ConcurrentHashMap()
    private var dispatcherNormal: DessertDispatcher? = null
    private var dispatcherDelay: DelayDessertDispatcher? = null

    @Suppress("UNCHECKED_CAST")
    fun <T> create(service: Class<T>): T {
        validateServiceInterface(service)
        return Proxy.newProxyInstance(service.classLoader, arrayOf(service)) { _, method, args ->
            if (method.declaringClass == Objects::class.java) {
                return@newProxyInstance method.invoke(this, args)
            }

            loadServiceMethod(method, args ?: emptyArray())
        } as T
    }

    fun last() {
        val cacheMethods = serviceMethodCache.values.toList()

        if (cacheMethods.isEmpty()) {
            return
        }

        serviceMethodCache.values.forEach {
            if (it.taskFactory.type == TaskFactory.Companion.Builder.FactoryType.TASK) {
                it.addDependOn(cacheMethods)
                it.addTailRunnable(cacheMethods)
                it.addCallback(cacheMethods)

                dispatcherNormal?.addTask(it.taskFactory.task)
                dispatcherDelay?.addTask(it.taskFactory.task ?: throw IllegalArgumentException("Can't find task by $it"))
            }
        }
    }

    fun dispatcher(dispatcher: DessertDispatcher): AnnotationConvertTools {
        dispatcherNormal = dispatcher
        return this
    }

    fun dispatcher(dispatcher: DelayDessertDispatcher): AnnotationConvertTools {
        dispatcherDelay = dispatcher
        return this
    }

    private fun loadServiceMethod(method: Method, args: Array<Any>): DessertMethod<*>? {
        var serviceMethod = serviceMethodCache[method]

        serviceMethod?.let { return it }

        synchronized(serviceMethodCache) {
            serviceMethod = serviceMethodCache[method] ?: DessertMethod.parseAnnotations<Any>(this, method, args).also {
                serviceMethodCache[method] = it
            }
        }

        return serviceMethod
    }
}