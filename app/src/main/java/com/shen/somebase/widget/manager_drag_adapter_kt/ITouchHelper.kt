package com.shen.somebase.widget.manager_drag_adapter_kt

import androidx.recyclerview.widget.RecyclerView

/**
 * created by shen on 2019/10/13
 * at 16:59
 **/
interface ITouchHelper<T : RecyclerView.Adapter<*>> {
    val isLongDragEnable: Boolean
    val isSwipeEnable: Boolean
    val onSwiped: OnSwiped?
    val onMove: OnMove?
    val onSelectChange: OnSelectChange?
    val onClearView: OnClearView?
}