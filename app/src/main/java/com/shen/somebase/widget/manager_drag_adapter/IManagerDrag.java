package com.shen.somebase.widget.manager_drag_adapter;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;

/*
 * created by shen at 2019/10/11 15:31
 */
/// 支持指定id拖拽和滑动
/// 需要把callback的
/// 拖拽：ItemTouchHelper.Callback isLongPressDragEnabled() 方法重写为false
/// 滑动: ItemTouchHelper.Callback isItemViewSwipeEnabled() 方法重写为false
public interface IManagerDrag<T> {
    @LayoutRes
    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

    T getData();

    int getItemCount(T data);

    Context getContext();

    void onBindViewHolder(BaseAdapterHelper holder, int position, T data);

    void onMove(int fromPosition, int targetPosition);

    @IdRes
    @Nullable
    int[] getDragItemsId();

    @IdRes
    @Nullable
    int[] getSwipeItemsId();

    ItemTouchHelper.Callback getCallback();

    ItemTouchHelper getItemTouchHelper();
}
