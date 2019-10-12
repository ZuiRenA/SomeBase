package com.shen.somebase.widget.manager_drag_adapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

/*
 * created by shen at 2019/10/11 15:29
 */
public class ManagerDragAdapter<T> extends RecyclerView.Adapter<BaseAdapterHelper> {

    private final IManagerDrag<T> managerImpl;

    public ManagerDragAdapter(IManagerDrag<T> managerDrag) {
        this.managerImpl = managerDrag;
    }

    @NonNull
    @Override
    public BaseAdapterHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(managerImpl.getContext())
                .inflate(managerImpl.getLayoutId(viewType), parent, false);
        return new BaseAdapterHelper(view);
    }

    @Override
    public int getItemViewType(int position) {
        return managerImpl.getItemViewType(position, managerImpl.getData());
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseAdapterHelper holder, int position) {
        managerImpl.onBindViewHolder(holder, position, managerImpl.getData());
        if (managerImpl.getDragItemsId() != null && managerImpl.getDragItemsId().length > 0) {
            for (int id : managerImpl.getDragItemsId()) {
                View view = holder.getView(id);
                if (view != null) {
                    holder.getView(id).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (MotionEventCompat.getActionMasked(motionEvent) ==
                                    MotionEvent.ACTION_DOWN) {
                                managerImpl.getItemTouchHelper().startDrag(holder);
                            }
                            return false;
                        }
                    });
                }
            }
        }
        if (managerImpl.getSwipeItemsId() != null && managerImpl.getSwipeItemsId().length > 0) {
            for (int id : managerImpl.getSwipeItemsId()) {
                View view = holder.getView(id);
                if (view != null) {
                    holder.getView(id).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (MotionEventCompat.getActionMasked(motionEvent) ==
                                    MotionEvent.ACTION_DOWN) {
                                managerImpl.getItemTouchHelper().startSwipe(holder);
                            }
                            return false;
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return managerImpl.getItemCount(managerImpl.getData());
    }

    public void onMove(int fromPosition, int targetPosition) {
        managerImpl.onMove(fromPosition, targetPosition);
    }
}
