package project.shen.dessert_life.factor_dialog

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView

/**
 * created by shen on 2019/10/20
 * at 15:23
 **/

data class DefaultElement(
    override val title: String,
    override val icon: Int?,
    override val iconLoader: ((Int, ImageView) -> Unit)?,
    override val funcClick: ((View, Int) -> Unit)?
): Element<Int>

data class DrawableElement(
    override val title: String,
    override val icon: Drawable?,
    override val iconLoader: ((Drawable, ImageView) -> Unit)?,
    override val funcClick: ((View, Int) -> Unit)?
): Element<Drawable>

data class GlideElement(
    override val title: String,
    override val icon: String?,
    override val iconLoader: ((String, ImageView) -> Unit)?,
    override val funcClick: ((View, Int) -> Unit)?
): Element<String>

interface Element<T> {
    val title: String

    val icon: T?

    val iconLoader: ((T, ImageView) -> Unit)?

    val funcClick: ((View, Int) -> Unit)?
}



