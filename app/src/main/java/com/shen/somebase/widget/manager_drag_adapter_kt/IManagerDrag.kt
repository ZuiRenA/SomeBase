package com.shen.somebase.widget.manager_drag_adapter_kt

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ItemTouchHelper

/**
 * created by shen on 2019/10/13
 * at 20:21
 **/
interface IManagerDrag<T> {

    val data: T

    val context: Context

    val dragItemsId: IntArray?

    val swipeItemsId: IntArray?

    val callback: ItemTouchHelper.Callback

    val itemTouchHelper: ItemTouchHelper

    @LayoutRes
    fun getLayoutId(viewType: Int): Int

    fun getItemViewType(position: Int, t: T): Int

    fun getItemCount(data: T): Int

    fun onBindViewHolder(helper: BaseAdapterHelper, position: Int, data: T)

    fun onMove(fromPosition: Int, targetPosition: Int): Boolean

    fun onSwipe(position: Int, direction: Int)
}