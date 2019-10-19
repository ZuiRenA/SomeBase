package project.shen.dessert_life.factor_dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.IdRes

/**
 * created by shen on 2019/10/17
 * at 21:07
 **/

@FactorDialogMaker
class FactorDialogBuilder {
    lateinit var context: Context

    var style: Int = 0

    @IdRes
    var layoutIdRes: Int = 0

    var layoutGravity: Int = Gravity.CENTER

    var paramsWidth: Int = WindowManager.LayoutParams.WRAP_CONTENT

    var paramsHeight: Int = WindowManager.LayoutParams.WRAP_CONTENT

    var init: ((Dialog) -> Unit)? = null

    private val elementHolderList = arrayListOf<ElementHolder>()

    fun element(init: ElementHolder.() -> Unit) {
        val section = ElementHolder()
        section.init()
        elementHolderList.add(section)
    }

    fun build(): FactorDialogEntity {
        val elements = elementHolderList.map { it.convertToElement() }
        return FactorDialog(
            context = context,
            id = layoutIdRes,
            themeResId = style,
            layoutGravity = layoutGravity,
            paramsWidth = paramsWidth,
            paramsHeight = paramsHeight,
            init = init,
            elements = elements
        ).show()
    }

    @FactorDialogMaker
    class ElementHolder {

        lateinit var view: (Dialog) -> View

        var params: ((Dialog) -> ViewGroup.LayoutParams)? = null

        var func: ((View) -> Unit)? = null

        internal fun convertToElement(): FactorDialog.Element = FactorDialog.Element(
            view = view,
            params = params,
            func = func
        )
    }

}

fun factorBuilder(init: FactorDialogBuilder.() -> Unit): FactorDialogBuilder {
    val factor = FactorDialogBuilder()
    factor.init()
    return factor
}

fun factorDialog(init: FactorDialogBuilder.() -> Unit): FactorDialogEntity = factorBuilder(init).build()


@DslMarker
internal annotation class FactorDialogMaker

