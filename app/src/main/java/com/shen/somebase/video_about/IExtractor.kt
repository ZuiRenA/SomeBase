package com.shen.somebase.video_about

import android.media.MediaFormat
import java.nio.ByteBuffer

/**
 * created by shen on 2019/9/25
 * at 23:29
 **/
interface IExtractor {
    fun getFormat(): MediaFormat

    ///读取音视频数据
    fun readBuffer(byteBuffer: ByteBuffer): Int

    ///获取当前帧时间
    fun getCurrentTimestamp(): Long

    fun getSampleFlag(): Int

    ///快进到指定时间，并返回实际帧的时间戳
    fun seek(position: Long): Long

    fun setStartPosition(position: Long)

    ///停止读取数据
    fun stop()
}