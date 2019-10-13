package com.shen.somebase.widget.manager_drag_adapter_kt

import androidx.recyclerview.widget.RecyclerView

/**
 * created by shen on 2019/10/13
 * at 17:42
 **/
class ItemTouchCallbackBuilder <T : RecyclerView.Adapter<*>> {
    private var isLongDragEnable = false
    private var isSwipeEnable = false
    private var onSwiped: OnSwiped? = null
    private var onMove: OnMove? = null
    private var onSelectChange: OnSelectChange? = null
    private var onClearView: OnClearView? = null

    fun build() = BaseItemTouchHelpCallback(object : ITouchHelper<T> {
        override val isLongDragEnable: Boolean
            get() = this@ItemTouchCallbackBuilder.isLongDragEnable
        override val isSwipeEnable: Boolean
            get() = this@ItemTouchCallbackBuilder.isSwipeEnable
        override val onSwiped: OnSwiped?
            get() = this@ItemTouchCallbackBuilder.onSwiped
        override val onMove: OnMove?
            get() = this@ItemTouchCallbackBuilder.onMove
        override val onSelectChange: OnSelectChange?
            get() = this@ItemTouchCallbackBuilder.onSelectChange
        override val onClearView: OnClearView?
            get() = this@ItemTouchCallbackBuilder.onClearView
    })

    fun isLongDragEnable(isLongDragEnable: Boolean): ItemTouchCallbackBuilder<T> {
        this.isLongDragEnable = isLongDragEnable
        return this
    }

    fun isSwipeEnable(isSwipeEnable: Boolean): ItemTouchCallbackBuilder<T> {
        this.isSwipeEnable = isSwipeEnable
        return this
    }

    fun onSwiped(onSwiped: OnSwiped): ItemTouchCallbackBuilder<T> {
        this.onSwiped = onSwiped
        return this
    }

    fun onMove(onMove: OnMove): ItemTouchCallbackBuilder<T> {
        this.onMove = onMove
        return this
    }

    fun onSelectChange(onSelectChange: OnSelectChange): ItemTouchCallbackBuilder<T> {
        this.onSelectChange = onSelectChange
        return this
    }

    fun onClearView(onClearView: OnClearView): ItemTouchCallbackBuilder<T> {
        this.onClearView = onClearView
        return this
    }
}