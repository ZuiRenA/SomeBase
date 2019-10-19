package project.shen.dessert_life.factor_dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.annotation.UiThread
import project.shen.dessert_life.R


/**
 * created by shen on 2019/10/17
 * at 21:26
 **/

class FactorDialog
constructor(
    private val context: Context,
    @IdRes internal val id: Int = 0,
    @StyleRes internal val themeResId: Int = 0,
    private val layoutGravity: Int,
    private val paramsWidth: Int?,
    private val paramsHeight: Int?,
    private val init: ((Dialog) -> Unit)?,
    private val elements: List<Element>? = null
) {
    private var factorDialog: FactorDialogEntity? = null

    fun show(): FactorDialogEntity {
        defaultInit()
        factorDialog?.init(id, init = init)
        if (elements != null && elements.isNotEmpty()) {
            elements.forEach {
                factorDialog?.addViewDsl(
                    it.view, it.params, it.func
                )
            }
        }
        factorDialog?.show()
        return factorDialog!!
    }

    @UiThread
    fun dismiss() {
        factorDialog?.dismiss()
    }

    data class Element(
        val view: (Dialog) -> View,
        val params: ((Dialog) -> ViewGroup.LayoutParams)?,
        val func: ((View) -> Unit)?
    )

    private fun defaultInit() {
        if (factorDialog == null) {
            val themeResId = resolveDialogStyle()
            factorDialog = FactorDialogEntity(
                context, themeResId, layoutGravity, paramsWidth, paramsHeight
            )
        }
    }

    private fun resolveDialogStyle(): Int = if (themeResId != 0) themeResId else R.style.FactorStyle
}

class FactorDialogEntity(
    internal val context: Context,
    @StyleRes val themeResId: Int,
    private val layoutGravity: Int,
    private val paramsWidth: Int? = null,
    private val paramsHeight: Int? = null
) : Dialog(context, themeResId) {

    private var init: Init? = null
    private val addViews: MutableList<AddView> by lazy { mutableListOf<AddView>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init?.let {
            setContentView(it.id)
            it.init?.invoke(this)
        }

        val layoutParams = window?.attributes
        layoutParams?.apply {
            gravity = layoutGravity
            width = paramsWidth ?: WindowManager.LayoutParams.WRAP_CONTENT
            height = paramsHeight ?: WindowManager.LayoutParams.WRAP_CONTENT
        }

        if (addViews.isNotEmpty()) {
            addViews.forEach {
                val view = it.view.invoke(this)
                if (it.params != null) {
                    addContentView(view, it.params.invoke(this))
                } else{
                    addContentView(view, null)
                }

                if (it.func != null) {
                    view.setOnClickListener { self ->
                        it.func.invoke(self)
                    }
                }
            }
        }
    }

    fun init(
        id: Int,
        init: ((Dialog) -> Unit)? = null
    ) {
       this.init = Init(id, init)
    }

    fun addViewDsl(
        view: (Dialog) -> View,
        params: ((Dialog) -> ViewGroup.LayoutParams)?,
        func: ((View) -> Unit)?
    ) {
        addViews.add(AddView(view, params, func))
    }

    internal data class Init(
        val id: Int,
        val init: (((Dialog) -> Unit))?
    )

    internal data class AddView(
        val view: (Dialog) -> View,
        val params: ((Dialog) -> ViewGroup.LayoutParams?)?,
        val func: ((View) -> Unit)?
    )
}