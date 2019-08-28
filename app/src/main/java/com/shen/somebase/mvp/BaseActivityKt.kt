package com.shen.somebase.mvp

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializer
import com.shen.somebase.widget.LoadingDialog
import com.shen.somebase.widget.ToastWidget
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.io.NotSerializableException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

//kotlin 中泛型没有了 ? extend xxx, ? super xxx, 使用了 out in
//out 表示生产者，in 表示消费者, 即 out T 相当于 T extend xxx, in T 相当于 T super xxx
abstract class BaseActivityKt<out P: BasePresenterKt<BaseActivityKt<P>>>:  AppCompatActivity(), IMVP.IView<P> {

    //重写presenter
    final override val presenter: P

    //延迟初始化，等同于于java的 private String errorMessage
    private lateinit var errMessage: String

    private var mLoadingDialog: LoadingDialog? = null

    //BaseActivity初始化时，init块会初始化
    init {
        presenter = createPresenterJava()
        presenter.view = this
    }

    //反射获取Presenter真实实例，并且初始化
    private fun createPresenterJava(): P {
        //排序化，内部是有迭代器的
        sequence {
            //获取BaseActivity的class
            var thisClass: Class<*> = this@BaseActivityKt::class.java
            while (true) {
                //yield为正在构建的迭代器产生一个值
                yield(thisClass.genericSuperclass)
                //如果thisClass的父类是null就break跳出循环
                //aaa ?: bbb 表示如果前面的aaa为空就做bbb
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            //挑出type是 ParameterizedType
            it is ParameterizedType
        }.flatMap {
            //把下面的这个东西铺平，flatMap就是铺平的高阶函数
            (it as ParameterizedType).actualTypeArguments.asSequence()
        }.first {
            //挑出第一个，符合下面条件的type
            it is Class<*> && IMVP.IPresenter::class.java.isAssignableFrom(it)
        }.let {
            //返回它的实例，newInstance() 只能对无参构造器进行初始化
            return (it as Class<P>).newInstance()
        }
    }


    //kotlin 里 when 相当于java的switch，而且when是具有返回值的，所有可以errMessage = when(xxx)
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

    //同理when，if也是有返回值的
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

    override fun showLoading() {
        showInLoading()
    }

    override fun hideLoading() {
        dismissLoading()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
        setContentView(getContentLayoutId())
        initView()
        initData()
        initListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        presenter.onViewStateRestored(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.onRestoreInstanceState(savedInstanceState)
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

    @LayoutRes
    abstract fun getContentLayoutId(): Int

    //open 关键词表示可以被重写， 如果不加open，那么这个方法在子类里无法进行重写
    //同理 类 如果不加open，无法被继承
    open fun initView() {}

    open fun initData() {}

    open fun initListener() {}
}
