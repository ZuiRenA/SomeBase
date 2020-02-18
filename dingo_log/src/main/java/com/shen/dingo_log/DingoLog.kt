package com.shen.dingo_log

/**
 *  created at 2020.2020/2/17.14:21
 *  @author shen
 */
object DingoLog {
    val logC: DingoClassLog by lazy { DingoClassLog.getInstance }
    val logM: DingoMethodLog by lazy { DingoMethodLog.getInstance }
}