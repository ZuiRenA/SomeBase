package com.shen.somebase.widget.manager_drag_adapter_kt

import androidx.recyclerview.widget.RecyclerView

/**
 * created by shen on 2019/10/13
 * at 17:55
 **/
class ItemTouchCallback {
    companion object {
        @JvmStatic
        fun <T: RecyclerView.Adapter<*>> builder() = ItemTouchCallbackBuilder<T>()
    }
}
