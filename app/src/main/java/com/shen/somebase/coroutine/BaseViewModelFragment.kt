package com.shen.somebase.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.shen.somebase.util.DebugLog
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

abstract class BaseViewModelFragment<T: ViewModel>: Fragment() {

    lateinit var viewModel: T
    private var contentView: View? = null

    //反射获取 T 真实类型
    private fun getType(): KClass<T>? = sequence<List<KType>> {
        var thisClass: KClass<*> = this@BaseViewModelFragment::class
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater.inflate(getLayoutId(), container, false)
        getType()?.java?.let {
            viewModel = ViewModelProviders.of(this)[it]
        }
        return contentView
    }


    @LayoutRes
    abstract fun getLayoutId(): Int
}