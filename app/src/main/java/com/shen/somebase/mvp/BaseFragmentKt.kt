package com.shen.somebase.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseFragmentKt<out P: BasePresenterKt<BaseFragmentKt<P>>> : Fragment(), IMVP.IView<P> {
    final override val presenter: P
    private var contentView: View? = null

    init {
        presenter = createPresenterJava()
        presenter.view = this
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater.inflate(getLayoutId(), container, false)
        return contentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun createPresenterJava(): P {
        sequence {
            var thisClass: Class<*> = this@BaseFragmentKt::class.java
            while (true) {
                yield(thisClass.genericSuperclass)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType
        }.flatMap {
            (it as ParameterizedType).actualTypeArguments.asSequence()
        }.first {
            it is Class<*> && IMVP.IPresenter::class.java.isAssignableFrom(it)
        }.let {
            return (it as Class<P>).newInstance()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        presenter.onRestoreInstanceState(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
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
}