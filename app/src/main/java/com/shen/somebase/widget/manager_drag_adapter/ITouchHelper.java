package com.shen.somebase.widget.manager_drag_adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/*
 * created by shen at 2019/10/11 16:15
 */
interface ITouchHelper <T extends RecyclerView.Adapter> {
    boolean isLongDragEnable();
    boolean isSwipeEnable();

    @Nullable
    BaseItemTouchHelpCallBack.OnItemTouchCallbackListener<T> listener();

    @Nullable
    BaseItemTouchHelpCallBack.ItemStatusChange listenerStatus();
}
