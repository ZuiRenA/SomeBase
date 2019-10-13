package com.shen.somebase.widget.manager_drag_adapter_kt

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * created by shen on 2019/10/13
 * at 17:03
 **/
class BaseItemTouchHelpCallback<T : RecyclerView.Adapter<*>>(private val helper: ITouchHelper<T>) : ItemTouchHelper.Callback(){

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int = if (recyclerView.layoutManager is GridLayoutManager) {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val swipeFlags = 0
        makeMovementFlags(dragFlags, swipeFlags)
    } else {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = 0
        makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun isLongPressDragEnabled(): Boolean = helper.isLongDragEnable

    override fun isItemViewSwipeEnabled(): Boolean = helper.isSwipeEnable

    @Suppress("UNCHECKED_CAST")
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = helper.onMove?.invoke(viewHolder.adapterPosition, target.adapterPosition, recyclerView.adapter as T) ?: false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        helper.onSwiped?.invoke(viewHolder.adapterPosition, direction)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        helper.onSelectChange?.invoke(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        helper.onClearView?.invoke(recyclerView, viewHolder)
    }
}

typealias OnSwiped = (position: Int, direction: Int) -> Unit
typealias OnMove = (fromPosition: Int, targetPosition: Int, adapter: RecyclerView.Adapter<*>) -> Boolean
typealias OnSelectChange = (viewHolder: RecyclerView.ViewHolder?, actionState: Int) -> Unit
typealias OnClearView = (recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) -> Unit
