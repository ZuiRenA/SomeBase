package com.shen.somebase.view

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.feheadline.news.coroutine_demo.BaseViewModelActivity
import com.shen.somebase.R
import com.shen.somebase.model.SubscribeIndustry
import com.shen.somebase.widget.manager_drag_adapter_kt.AdapterManager
import com.shen.somebase.widget.manager_drag_adapter_kt.DragSwipeAdapter
import com.shen.somebase.widget.manager_drag_adapter_kt.ItemTouchCallback
import kotlinx.android.synthetic.main.activity_drag_item_test.*

class DragItemTestActivity : BaseViewModelActivity<DragItemTestViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_drag_item_test

    private lateinit var subscribeIndustry: SubscribeIndustry

    override fun init() {
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

        val itemTouchHelper = ItemTouchHelper(callback)

        val adapter = DragSwipeAdapter(AdapterManager.instance.subscribeIndustryManager(
            this,
            callback,
            subscribeIndustry,
            itemTouchHelper
        ))

        rvDragTest.apply {
            layoutManager = lm
            this.adapter = adapter
            itemTouchHelper.attachToRecyclerView(this)
        }
    }
}
