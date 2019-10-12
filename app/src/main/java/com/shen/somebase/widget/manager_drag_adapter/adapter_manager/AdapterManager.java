package com.shen.somebase.widget.manager_drag_adapter.adapter_manager;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.shen.somebase.model.SubscribeIndustry;
import com.shen.somebase.widget.manager_drag_adapter.BaseAdapterHelper;
import com.shen.somebase.widget.manager_drag_adapter.IManagerDrag;


/*
 * created by shen at 2019/10/11 17:16
 */
public class AdapterManager {
    private AdapterManager() {

    }

    public static class Lazy {
        public static AdapterManager getInstance() {
            return new AdapterManager();
        }
    }

    public IManagerDrag<SubscribeIndustry> subscribeIndustryManager(
            final Context context,
            final ItemTouchHelper.Callback callback,
            final SubscribeIndustry subscribeIndustry,
            final ItemTouchHelper itemTouchHelper
            ) {
        return new IManagerDrag<SubscribeIndustry>() {
            @Override
            public int getLayoutId(int viewType) {
                if (viewType == 0) return 0;
                else if (viewType == 1) return 0;
                else return 0;
            }

            @Override
            public int getItemViewType(int position, SubscribeIndustry subscribeIndustry) {
                int size = subscribeIndustry.getLike().size();
                if (position <= size - 1) return 0;
                else if (position == size) return 1;
                else return 2;
            }

            @Override
            public SubscribeIndustry getData() {
                return subscribeIndustry;
            }

            @Override
            public int getItemCount(SubscribeIndustry data) {
                return data.getLike().size() + 1 + data.getUnlike().size();
            }

            @Override
            public Context getContext() {
                return context;
            }

            @Override
            public void onBindViewHolder(BaseAdapterHelper holder, int position, SubscribeIndustry data) {
                int size = data.getLike().size();
                if (position <= size - 1) {
                    holder.getTextView(0).setText(data.getLike().get(position).getName());
                } else if (position == size) {
                    if (data.getUnlike().isEmpty()) {
                        holder.getTextView(0).setVisibility(View.GONE);
                    }
                    holder.getTextView(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                } else {

                }
            }

            @Override
            public void onMove(int fromPosition, int targetPosition) {

            }

            @Override
            public int[] getDragItemsId() {
                return null;
            }

            @Override
            public int[] getSwipeItemsId() {
                return null;
            }

            @Override
            public ItemTouchHelper.Callback getCallback() {
                return callback;
            }

            @Override
            public ItemTouchHelper getItemTouchHelper() {
                return itemTouchHelper;
            }
        };
    }
}
