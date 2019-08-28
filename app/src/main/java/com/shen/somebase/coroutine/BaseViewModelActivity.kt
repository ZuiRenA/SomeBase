package com.feheadline.news.coroutine_demo

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializer
import com.shen.somebase.mvp.BaseView
import com.shen.somebase.util.DebugLog
import com.shen.somebase.widget.LoadingDialog
import com.shen.somebase.widget.ToastWidget
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.io.NotSerializableException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

abstract class BaseViewModelActivity<T: ViewModel>: AppCompatActivity(), BaseView {

    lateinit var viewModel: T

    private lateinit var errMessage: String

    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        getType()?.java?.let {
//            viewModel = ViewModelProvider.NewInstanceFactory().create(it)
            viewModel = ViewModelProviders.of(this)[it]
        }

        init()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun init()

    //反射获取 T 真实类型
    private fun getType(): KClass<T>? = sequence<List<KType>> {
        var thisClass: KClass<*> = this@BaseViewModelActivity::class
        while (true) {
            yield(thisClass.supertypes)
            thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
        }
    }.flatMap {
        it.flatMap { kType -> kType.arguments }.asSequence()
    }.first {
        it.type?.jvmErasure?.isSubclassOf(ViewModel::class) ?: false
    }.let {
        DebugLog.logE("BaseViewModelActivity", "$it")
        return it.type!!.jvmErasure as? KClass<T>
    }

    override fun onRequestError(throwable: Throwable, index: Int) {
        errMessage = when (throwable) {
            is HttpException -> {
                try {
                    throwable.response().errorBody()?.string() ?: ""
                } catch (e: IOException) {
                    e.printStackTrace()
                    e.message ?: ""
                }
            }
            is SocketTimeoutException -> "网络连接超时，请检查您的网络状态，稍后重试！"
            is ConnectException -> "网络连接异常，请检查您的网络状态，稍后重试！"
            is ConnectTimeoutException -> "网络连接超时，请检查您的网络状态，稍后重试！"
            is UnknownHostException -> "网络连接异常，请检查您的网络状态，稍后重试！"
            is NullPointerException -> "空指针异常"
            is ClassCastException -> "类型转换异常"

            is JsonParseException, is JSONException,
            is JsonSerializer<*>, is NotSerializableException, is ParseException -> "解析错误"

            is IllegalStateException -> throwable.message ?: ""
            else -> "未知错误"
        }

//        mNetworkStateView?.let{
//            showError(index,errMessage)
//        }
    }

    override fun onError(throwable: Throwable) {
        val toastMes = if (throwable is SocketTimeoutException || throwable is ConnectTimeoutException) {
            "网络连接超时，请检查网络状态"
        } else if (throwable is JsonParseException
                || throwable is JSONException
                || throwable is JsonSerializer<*>
                || throwable is NotSerializableException
                || throwable is ParseException) {
            "解析错误"
        } else {
            "网络连接异常，请检查网络状态"
        }

        if ("main" == Thread.currentThread().name) {
            ToastWidget.show(toastMes)
        } else {
            ToastWidget.showToastThread(this, toastMes)
        }
    }

    fun onErrorInMain(throwable: Throwable) {
        val toastMes = if (throwable is SocketTimeoutException || throwable is ConnectTimeoutException) {
            "网络连接超时，请检查网络状态"
        } else if (throwable is JsonParseException
                || throwable is JSONException
                || throwable is JsonSerializer<*>
                || throwable is NotSerializableException
                || throwable is ParseException) {
            "解析错误"
        } else {
            "网络连接异常，请检查网络状态"
        }

        ToastWidget.show(toastMes)
    }

    override fun showLoading() {
        showInLoading()
    }

    override fun hideLoading() {
        dismissLoading()
    }

    private fun showInLoading() {
        if (mLoadingDialog == null)
            mLoadingDialog = LoadingDialog(this)
        if (!this.isFinishing && mLoadingDialog != null && !mLoadingDialog!!.isShowing)
            mLoadingDialog?.show()
    }

    private fun dismissLoading() {
        if (mLoadingDialog != null && !this.isFinishing) {
            mLoadingDialog?.dismiss()
        }
    }
}