package com.shen.somebase.video_about

/**
 * created by shen on 2019/9/25
 * at 23:09
 **/
interface IDecoderStateListener {
    fun decoderPrepare(decoderJob: BaseDecoder?)
    fun decoderReady(decoderJob: BaseDecoder?)
    fun decoderRunning(decoderJob: BaseDecoder?)
    fun decoderPause(decoderJob: BaseDecoder?)
    fun decoderOneFrame(decoderJob: BaseDecoder?)
    fun decoderFinish(decoderJob: BaseDecoder?)
    fun decoderDestory(decoderJob: BaseDecoder?)
    fun decoderError(decoderJob: BaseDecoder?, msg: String)
}