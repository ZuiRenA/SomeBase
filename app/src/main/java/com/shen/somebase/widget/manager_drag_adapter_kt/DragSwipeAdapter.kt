package com.shen.somebase.widget.manager_drag_adapter_kt

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * created by shen on 2019/10/13
 * at 20:29
 **/
class DragSwipeAdapter <T>(private val managerImpl: IManagerDrag<T>) : RecyclerView.Adapter<BaseAdapterHelper>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapterHelper =
        BaseAdapterHelper(LayoutInflater.from(managerImpl.context).inflate(managerImpl.getLayoutId(viewType),
            parent, false))

    override fun getItemCount(): Int = managerImpl.getItemCount(managerImpl.data)

    override fun getItemViewType(position: Int): Int = managerImpl.getItemViewType(position, managerImpl.data)

    override fun onBindViewHolder(holder: BaseAdapterHelper, position: Int) {
        managerImpl.onBindViewHolder(holder, position, managerImpl.data)

        managerImpl.dragItemsId?.let {
            it.forEach { id ->
                holder.getView<View>(id)?.let { view ->
                    view.setOnTouchListener { _, motionEvent ->
                        if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                            managerImpl.itemTouchHelper.startDrag(holder)
                        }
                        false
                    }
                }
            }
        }

        managerImpl.swipeItemsId?.let {
            it.forEach { id ->
                holder.getView<View>(id)?.let { view ->
                    view.setOnTouchListener { _, motionEvent ->
                        if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                            managerImpl.itemTouchHelper.startSwipe(holder)
                        }
                        false
                    }
                }
            }
        }
    }

    fun onMove(fromPosition: Int, targetPosition: Int): Boolean = managerImpl.onMove(fromPosition, targetPosition)


    fun onSwipe(position: Int, direction: Int) {
        managerImpl.onSwipe(position, direction)
    }
}