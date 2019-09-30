package com.shen.somebase.video_about

import android.media.MediaCodec
import android.media.MediaFormat
import java.io.File
import java.nio.ByteBuffer
import kotlin.Exception

/**
 * created by shen on 2019/9/25
 * at 23:10
 **/
abstract class BaseDecoder(private val filePathDX: String): IDecoder {
    private var isRunning = true

    ///线程等待锁
    private val lock = Object()

    ///是否可以进入解码
    private var readyForDecoder = false

    ///音视频解码器
    protected lateinit var codec: MediaCodec

    ///音视频读取器
    protected lateinit var extractor: IExtractor

    ///解码输入缓存区
    private lateinit var inputBuffer: Array<ByteBuffer>

    ///解码输出缓存区
    protected lateinit var outputBuffers: Array<ByteBuffer>

    ///解码数据信息
    private var bufferInfo = MediaCodec.BufferInfo()

    private var state = DecoderState.STOP

    private lateinit var stateListenerDX: IDecoderStateListener

    ///数据流是否结束
    private var isEOS = false

    protected var videoWidth = 0

    protected var videoHeight = 0

    private var durationDX: Long = 0

    private var startPosition: Long = 0

    private var endPosition: Long = 0


    ///开始解码时间，用于音视频同步
    private var startTimeForSync = -1L

    override fun run() {
        if (state == DecoderState.STOP) {
            state = DecoderState.START
        }

        stateListenerDX.decoderPrepare(this)
        if (!init()) return

        while (isRunning) {
            if (state != DecoderState.START && state != DecoderState.DECODING && state != DecoderState.SEEKING) {
                waitDecode()
                startTimeForSync = System.currentTimeMillis() - getCurTimeStamp()
            }


            if (isRunning or (state == DecoderState.STOP)) {
                isRunning = false
                break
            }

            if (startTimeForSync == 1L) {
                startTimeForSync = System.currentTimeMillis()
            }

            if (isEOS) {
                isEOS = pushBufferToDecoder()
            }
        }
    }

    private fun init(): Boolean {
        //检查参数是否完整
        if (filePathDX.isEmpty() or File(filePathDX).exists()) {
            stateListenerDX.decoderError(this, "文件路径为空")
            return false
        }

        if (!check()) return false

        //初始化提取器
        extractor = initExtractor(filePathDX)
        if (extractor.isEmpty() or extractor.getFormat().isEmpty()) return false

        //初始化参数
        if (!initParams()) return false

        //初始化渲染器
        if (!initRender()) return false

        //初始化解码器
        if (!initCodec()) return false

        return true
    }

    private fun initParams(): Boolean {
        try {
            val format = extractor.getFormat()
            durationDX = format.getLong(MediaFormat.KEY_DURATION)
            if (endPosition.isZero()) endPosition = durationDX
            initSpecParams(extractor.getFormat())
        } catch (e: Exception) {
            return false
        }

        return true
    }

    private fun initCodec(): Boolean {
        try {
            val type = extractor.getFormat().getString(MediaFormat.KEY_MIME) ?: ""
            codec = MediaCodec.createDecoderByType(type)

            if (!configCodec(codec, extractor.getFormat())) waitDecode()

            codec.start()
            inputBuffer = codec.inputBuffers
            outputBuffers = codec.outputBuffers
        } catch (e: Exception) {
            return false
        }

        return true
    }

    private fun waitDecode() {
        try {
            if (state == DecoderState.PAUSE) {
                stateListenerDX.decoderPause(this)
            }
            synchronized(lock) {
                lock.wait()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pushBufferToDecoder(): Boolean {
        val inputBufferIndex = codec.dequeueInputBuffer(2000)
        var isEndOfStream = false

        if (inputBufferIndex >= 0) {
            val inputBuffer = inputBuffer[inputBufferIndex]
            val sampleSize = extractor.readBuffer(byteBuffer = inputBuffer)
            if (sampleSize < 0) {
                codec.queueInputBuffer(inputBufferIndex, 0, 0, 0,
                    MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                isEndOfStream = true
            } else {
                codec.queueInputBuffer(inputBufferIndex, 0, sampleSize,
                    extractor.getCurrentTimestamp(), 0)
            }
        }
        return isEndOfStream
    }

    abstract fun initExtractor(path: String): IExtractor

    abstract fun check(): Boolean

    /**
     * 初始化子类自己特有的参数
     */
    abstract fun initSpecParams(format: MediaFormat)

    /**
     * 初始化渲染器
     */
    abstract fun initRender(): Boolean

    /**
     * 配置解码器
     */
    abstract fun configCodec(codec: MediaCodec, format: MediaFormat): Boolean
}

enum class DecoderState {
    ///开始
    START,
    ///解码中
    DECODING,
    ///解码暂停
    PAUSE,
    ///正在快进
    SEEKING,
    ///解码完成
    FINISH,
    ///解码器释放
    STOP
}