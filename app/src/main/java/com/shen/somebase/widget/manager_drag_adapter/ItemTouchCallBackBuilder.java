package com.shen.somebase.widget.manager_drag_adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/*
 * created by shen at 2019/10/11 16:13
 */
public class ItemTouchCallBackBuilder<T extends RecyclerView.Adapter> {
    private boolean isLongDragEnable = true;
    private boolean isSwipeEnable = false;
    private BaseItemTouchHelpCallBack.OnItemTouchCallbackListener listener = null;
    private BaseItemTouchHelpCallBack.ItemStatusChange listenerStatus = null;

    public BaseItemTouchHelpCallBack<T> commit() {
        ITouchHelper<T> touchHelper = new ITouchHelper<T>() {

            @Override
            public boolean isLongDragEnable() {
                return isLongDragEnable;
            }

            @Override
            public boolean isSwipeEnable() {
                return isSwipeEnable;
            }

            @Override
            public BaseItemTouchHelpCallBack.OnItemTouchCallbackListener<T> listener() {
                return listener;
            }

            @Nullable
            @Override
            public BaseItemTouchHelpCallBack.ItemStatusChange listenerStatus() {
                return listenerStatus;
            }
        };

        return new BaseItemTouchHelpCallBack<T>(touchHelper);
    }

    public ItemTouchCallBackBuilder swipeEnable(boolean swipeEnable) {
        isSwipeEnable = swipeEnable;
        return this;
    }

    public ItemTouchCallBackBuilder longDragEnable(boolean longDragEnable) {
        isLongDragEnable = longDragEnable;
        return this;
    }

    public ItemTouchCallBackBuilder swipeDragListener(BaseItemTouchHelpCallBack.OnItemTouchCallbackListener listener) {
        this.listener = listener;
        return this;
    }

    public ItemTouchCallBackBuilder selectClearListener(BaseItemTouchHelpCallBack.ItemStatusChange listenerStatus) {
        this.listenerStatus = listenerStatus;
        return this;
    }
}
