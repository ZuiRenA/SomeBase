package com.shen.somebase.widget.manager_drag_adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/*
 * created by shen at 2019/10/11 16:03
 */
public class BaseItemTouchHelpCallBack<T extends RecyclerView.Adapter> extends ItemTouchHelper.Callback {

    //是否可以长按拖住啊哦
    private boolean isLongDragEnable;

    //是否可以左右滑动
    private boolean isSwipeEnable;

    @Nullable
    private OnItemTouchCallbackListener<T> listener = null;

    @Nullable
    private ItemStatusChange listenerStatus = null;

    BaseItemTouchHelpCallBack(ITouchHelper touchHelper) {
        this.isLongDragEnable = touchHelper.isLongDragEnable();
        this.isSwipeEnable = touchHelper.isSwipeEnable();
        this.listener = touchHelper.listener();
        this.listenerStatus = touchHelper.listenerStatus();
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongDragEnable;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnable;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags,swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (listener != null) {
            return listener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition(), (T) recyclerView.getAdapter());
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (listener != null) {
            listener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (listenerStatus != null) listenerStatus.onSelectChange(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (listenerStatus != null) listenerStatus.onClearView(recyclerView, viewHolder);
    }

    public interface OnItemTouchCallbackListener<T extends RecyclerView.Adapter> {
        void onSwiped(int position);

        boolean onMove(int beforePosition, int targetPosition, T adapter);
    }

    public interface ItemStatusChange {
        void onSelectChange(RecyclerView.ViewHolder viewHolder, int actionState);
        void onClearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
    }
}
