package com.shen.somebase.widget.manager_drag_adapter_kt

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import com.shen.somebase.model.SubscribeIndustry

/**
 * created by shen on 2019/10/13
 * at 20:42
 **/
class AdapterManager private constructor() {
    companion object Lazy {
        val instance: AdapterManager by lazy {
            AdapterManager()
        }
    }

    fun subscribeIndustryManager(
        context: Context,
        callback: ItemTouchHelper.Callback,
        subscribeIndustry: SubscribeIndustry
    ): IManagerDrag<SubscribeIndustry> = object : IManagerDrag<SubscribeIndustry> {

        override val data: SubscribeIndustry = subscribeIndustry
        override val context: Context = context
        override val dragItemsId: IntArray? = null
        override val swipeItemsId: IntArray? = null
        override val callback: ItemTouchHelper.Callback = callback
        override val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(callback)

        init {
            Log.d("ItemTouchHelper", itemTouchHelper.toString())
        }

        override fun getLayoutId(viewType: Int): Int = when(viewType) {
            0 -> 0
            else -> 99999
        }

        override fun getItemViewType(position: Int, t: SubscribeIndustry): Int = when(position) {
            0 -> 0
            else -> 99999
        }

        override fun getItemCount(data: SubscribeIndustry): Int = data.like!!.size + data.unlike!!.size + 2

        override fun onBindViewHolder(
            helper: BaseAdapterHelper,
            position: Int,
            data: SubscribeIndustry
        ) {

        }

        override fun onMove(fromPosition: Int, targetPosition: Int): Boolean {
            return false
        }

        override fun onSwipe(position: Int, direction: Int) {

        }
    }
}