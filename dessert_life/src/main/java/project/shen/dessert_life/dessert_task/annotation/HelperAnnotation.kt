package project.shen.dessert_life.dessert_task.annotation

import androidx.annotation.StringDef

/**
 *  created by shen
 *  at 2019.2019/12/30.16:13
 *  @author shen
 */

@StringDef(value = [Executors.IO, Executors.CPU])
@Retention(AnnotationRetention.SOURCE)
annotation class Executors {
    companion object {
       const val IO: String = "io"
       const val CPU: String = "cpu"
    }
}