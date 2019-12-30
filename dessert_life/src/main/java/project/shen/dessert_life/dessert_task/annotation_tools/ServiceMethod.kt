package project.shen.dessert_life.dessert_task.annotation_tools

import java.lang.reflect.Method

/**
 *  created by shen
 *  at 2019.2019/12/30.17:22
 *  @author shen
 */
abstract class ServiceMethod <T> {
    companion object {

        fun <T> parseAnnotations(convert: AnnotationConvertTools, method: Method, args: Array<Any>): ServiceMethod<T> {
            val taskFactory = TaskFactory.parseAnnotations(convert, method, args)
        }
    }
}