package com.shen.somebase.video_about

import android.media.MediaFormat

/**
 * created by shen on 2019/9/25
 * at 23:04
 **/
interface IDecoder: Runnable {
    ///暂停解码
    fun pause()

    ///继续解码
    fun goOn()

    ///跳转到指定位置，并返回实际帧的时间
    fun seekTo(position: Long): Long

    ///跳转到指定位置，并播放，并返回实际帧的时间
    fun seekAndPlay(position: Long): Long

    ///停止解码
    fun stop()

    ///是否正在解码
    fun isDecoder(): Boolean

    ///是否正在快进
    fun isSeeking(): Boolean

    ///是否停止解码
    fun isStop(): Boolean

    ///设置监听器
    fun setStateListener(l: IDecoderStateListener)

    ///获取视频高
    fun getHeight(): Int

    ///获取视频宽
    fun getWidth(): Int

    ///获取视频时长
    fun getDuration(): Long

    ///当前帧时间，单位：ms
    fun getCurTimeStamp(): Long


    ///获取视频旋转角度
    fun getRotationAngle(): Int

    ///获取音视频对应的参数格式
    fun getMediaFormat(): MediaFormat?

    ///获取音视频对应的媒体轨道
    fun getTrack(): Int

    ///获取解码的文件的路径
    fun getFilePath(): String

    ///作为合成器解码
    fun asCropper(): IDecoder
}