package project.shen.dessert_life.factor_dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.widget_factor_list.*
import project.shen.dessert_life.R

/**
 * created by shen on 2019/10/20
 * at 16:12
 **/
class FactorDialogEntity(
    @LayoutRes private val layoutIdRes: Int,
    @LayoutRes private val itemShowLayoutId: Int,
    @StyleRes private val themeResId: Int,
    internal val context: Context,
    private val layoutGravity: Int,
    private val paramsWidth: Int? = null,
    private val paramsHeight: Int? = null,
    private val elements: List<Element<*>>? = null
) : Dialog(context, themeResId) {
    val adapter: RecyclerView.Adapter<*>?
        get() = if (rvFactorDefault != null) rvFactorDefault.adapter else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutIdRes)
        //set dialog attributes
        val layoutParams = window?.attributes
        layoutParams?.width = paramsWidth
        layoutParams?.height = paramsHeight
        layoutParams?.gravity = layoutGravity
        window?.attributes = layoutParams

        //default recyclerView init
        if (layoutIdRes != R.layout.widget_factor_list) {
            return
        }

        if (elements == null || elements.isEmpty()) {
            return
        }

        val manager = LinearLayoutManager(this.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        val elementsAdapter = DefaultElementAdapter(itemShowLayoutId, this.context, elements as List<Element<Any>>)

        rvFactorDefault.apply {
            layoutManager = manager
            adapter = elementsAdapter
        }
    }
}

class FactorDialogDirection(
    val context: Context,
    @LayoutRes val layoutIdRes: Int,
    @StyleRes val style: Int,
    @LayoutRes val itemShowLayoutId: Int,
    private val layoutGravity: Int,
    private val paramsWidth: Int,
    private val paramsHeight: Int,
    private val elements: List<Element<*>>
) {
    private var dialog: FactorDialogEntity? = null

    private val dialogContext: Context?
        get() = dialog?.context

    internal fun init(): FactorDialogDirection {
        val id = resolveLayoutId(layoutIdRes)
        val styleResolve = resolveStyle(style = style)
        val itemShowLayoutId: Int = resolveItemShowId(this.itemShowLayoutId)
        if (dialog == null) {
             dialog = FactorDialogEntity(
                 layoutIdRes = id,
                 itemShowLayoutId = itemShowLayoutId,
                 themeResId = styleResolve,
                 context = context,
                 layoutGravity = layoutGravity,
                 paramsWidth = paramsWidth,
                 paramsHeight = paramsHeight,
                 elements = elements
            )
        }
        return this
    }

    @UiThread
    fun dismiss() {
        dialog?.dismiss()
    }

    fun show() {
        dialog?.show()
    }

    fun adapterNotifyDataChanged() {
        dialog?.adapter?.notifyDataSetChanged()
    }

    fun customClickEvent(onClick: (BaseAdapterHelper, Int) -> Unit): FactorDialogDirection {
        dialog?.adapter?.let {
            (it as DefaultElementAdapter).event(onClick)
        }
        return this
    }

    private fun resolveLayoutId(layoutIdRes: Int): Int = if (layoutIdRes == 0) R.layout.widget_factor_list else layoutIdRes
    private fun resolveStyle(style: Int): Int = if (style == 0) R.style.FactorStyle else style
    private fun resolveItemShowId(id: Int): Int = if (id == 0) R.layout.item_factor_element else id
}