package project.shen.dessert_life.dessert_task.annotation_tools

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
    }

    private val serviceMethodCache: MutableMap<Method, ServiceMethod<*>> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    fun <T> create(service: Class<T>) = Proxy.newProxyInstance(service.classLoader, arrayOf(service)) { _, method, args ->
        if (method.declaringClass == Objects::class.java) {
            return@newProxyInstance method.invoke(this, args)
        }

        loadServiceMethod(method, args)
    } as T

    private fun loadServiceMethod(method: Method, args: Array<Any>): ServiceMethod<*>? {
        var serviceMethod = serviceMethodCache[method]

        serviceMethod?.let { return it }

        synchronized(serviceMethodCache) {
            serviceMethod = serviceMethodCache[method] ?: ServiceMethod.parseAnnotations<Any>(this, method, args).also {
                serviceMethodCache[method] = it
            }
        }

        return serviceMethod
    }
}