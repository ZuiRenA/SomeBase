package com.shen.somebase.widget.recyclerview

import android.content.Context
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CheckResult
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

/**
 * created by shen on 2019/11/3
 * at 17:03
 **/
abstract class QuickAdapter<T, Holder: RecyclerView.ViewHolder> : RecyclerView.Adapter<Holder> {

    val data: MutableList<T> by lazy { mutableListOf<T>() }

    var dragListener: ((view: View, event: DragEvent, position: Int, item: T) -> Boolean)? = null

    private var context: Context

    @LayoutRes private var idRes: Int = 0

    private var varietySupport: VarietySupport<T>? = null

    constructor(context: Context, @LayoutRes idRes: Int): this(context, idRes, null)

    constructor(context: Context, @LayoutRes idRes: Int, data: List<T>?) {
        this.context = context
        this.idRes = idRes
        data?.let { this.data.addAll(it)}
    }

    constructor(context: Context, varietySupport: VarietySupport<T>): this(context, varietySupport, null)

    constructor(context: Context, varietySupport: VarietySupport<T>, data: List<T>?) {
        this.context = context
        this.varietySupport = varietySupport
        data?.let { this.data.addAll(it) }
    }

    abstract fun convert(holder: Holder, item: T)

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        reflectHolder(LayoutInflater.from(context).inflate(varietySupport?.getItemId(viewType) ?: idRes, parent, false))


    override fun getItemViewType(position: Int): Int =
        varietySupport?.getItemViewType(position, data[position]) ?: DEFAULT_VIEW_TYPE

    companion object {
        const val DEFAULT_VIEW_TYPE = -114514
    }

    private fun reflectHolder(view: View): Holder =
        sequence<List<KType>> {
            var thisClass: KClass<*> = this@QuickAdapter::class
            while (true) {
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap { kType -> kType.arguments }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(RecyclerView.ViewHolder::class) ?: false
        }.run {
            type!!.jvmErasure as? KClass<Holder>
        }?.run {
            this.javaObjectType
        }?.run {
            getConstructor(View::class.java)
        }?.run {
            newInstance(view)
        }!!

    override fun onBindViewHolder(holder: Holder, position: Int) {
        convert(holder, data[position])
    }
}


interface VarietySupport<T> {
    fun getItemViewType(position: Int, data: T): Int
    fun getItemId(viewType: Int): Int
}