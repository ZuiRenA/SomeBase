package project.shen.dessert_life.factor_dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import project.shen.dessert_life.R

/**
 * created by shen on 2019/10/17
 * at 21:07
 **/
@FactorDialogMaker
class FactorDialogBuilder {
    lateinit var context: Context

    var style: Int = 0

    @LayoutRes var layoutIdRes: Int = 0

    @LayoutRes var itemShowLayoutId: Int = 0

    var layoutGravity: Int = Gravity.CENTER

    var paramsWidth: Int = WindowManager.LayoutParams.WRAP_CONTENT

    var paramsHeight: Int = WindowManager.LayoutParams.WRAP_CONTENT

    var init: ((Dialog) -> Unit)? = null

    private val elementCreatorList = arrayListOf<AbstractElementCreator<*>>()

    fun build(): FactorDialogDirection {
        val elements = elementCreatorList.map { it.convertToElement() }
        return FactorDialogDirection(
            context = context,
            layoutIdRes = layoutIdRes,
            style = style,
            itemShowLayoutId = itemShowLayoutId,
            layoutGravity = layoutGravity,
            paramsHeight = paramsHeight,
            paramsWidth = paramsWidth,
            elements = elements
        )
    }

    fun elementCreator(init: AbstractElementCreator<String>.() -> Unit) {
        val elementGlideElement = GlideIconElementCreator()
        elementGlideElement.init()
        elementCreatorList.add(element = elementGlideElement)
    }


    @FactorDialogMaker
    class DrawableIconElementCreator : AbstractElementCreator<Drawable>() {
        override lateinit var title: String
        override var icon: Drawable? = null
        override var iconLoader: ((Drawable, ImageView) -> Unit)? = null
        override var funcClick: ((View, Int) -> Unit)? = null

        override fun convertToElement(): Element<Drawable> = DrawableElement(
            title = title, icon = icon, iconLoader = iconLoader, funcClick = funcClick
        )
    }

    @FactorDialogMaker
    class GlideIconElementCreator : AbstractElementCreator<String>() {
        override lateinit var title: String
        override var icon: String? = null
        override var iconLoader: ((String, ImageView) -> Unit)? = null
        override var funcClick: ((View, Int) -> Unit)? = null

        override fun convertToElement(): Element<String> = GlideElement(
            title = title, icon = icon, iconLoader = iconLoader, funcClick = funcClick
        )
    }

    @FactorDialogMaker
    class NullAbleIconElementCreator : AbstractElementCreator<Int>() {

        override lateinit var title: String
        override var icon: Int? = null
        override var iconLoader: ((Int, ImageView) -> Unit)? = null
        override var funcClick: ((View, Int) -> Unit)? = null

        override fun convertToElement(): Element<Int> = DefaultElement(
            title = title, icon = icon, iconLoader = iconLoader, funcClick = funcClick
        )
    }

    @FactorDialogMaker
    abstract class AbstractElementCreator<T: Any> {
        abstract var title: String

        abstract var icon: T?

        abstract var iconLoader: ((icon: T, imageView: ImageView) -> Unit)?

        abstract var funcClick: ((view: View, position: Int) -> Unit)?

        internal abstract fun convertToElement(): Element<T>
    }
}

fun factorBuilder(init: FactorDialogBuilder.() -> Unit): FactorDialogBuilder {
    val factor = FactorDialogBuilder()
    factor.init()
    return factor
}

fun factorDialog(init: FactorDialogBuilder.() -> Unit): FactorDialogDirection = factorBuilder(init).build().init()


@DslMarker
internal annotation class FactorDialogMaker

