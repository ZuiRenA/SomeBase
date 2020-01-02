package com.shen.somebase.view

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.shen.somebase.R
import com.shen.somebase.coroutine.BaseViewModelActivity
import com.shen.somebase.model.SubscribeIndustry
import com.shen.somebase.widget.manager_drag_adapter_kt.AdapterManager
import com.shen.somebase.widget.manager_drag_adapter_kt.DragSwipeAdapter
import com.shen.somebase.widget.manager_drag_adapter_kt.ItemTouchCallback
import kotlinx.android.synthetic.main.activity_drag_item_test.*

class DragItemTestActivity : BaseViewModelActivity<DragItemTestViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_drag_item_test

    private lateinit var subscribeIndustry: SubscribeIndustry

    override fun init() {
        subscribeIndustry = SubscribeIndustry()
        initRv()
    }

    private fun initRv() {
        val lm = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val callback = ItemTouchCallback.builder<DragSwipeAdapter<SubscribeIndustry>>()
            .isLongDragEnable(false)
            .onMove { fromPosition, targetPosition, adapter ->
                (adapter as DragSwipeAdapter<*>).onMove(fromPosition, targetPosition)
            }
            .build()

        val manager = AdapterManager.instance.subscribeIndustryManager(
            this,
            callback,
            subscribeIndustry
        )

//        val adapter = DragSwipeAdapter(manager)

        rvDragTest.apply {
            layoutManager = lm
            this.adapter = adapter
            manager.itemTouchHelper.attachToRecyclerView(this)
            Log.d("ItemTouchHelper", manager.itemTouchHelper.toString())
        }
    }
}
