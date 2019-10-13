package com.shen.somebase.widget.manager_drag_adapter_kt

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.util.Linkify
import android.util.SparseArray
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * created by shen on 2019/10/13
 * at 18:00
 **/
class BaseAdapterHelper(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var views: SparseArray<View?> = SparseArray()

    @Suppress("UNCHECKED_CAST")
    private fun <T: View> retrieveView(@IdRes id: Int): T = views.get(id) as T? ?: itemView.addNoViewToSparse(id) as T

    private fun <T: View> View.addNoViewToSparse(@IdRes id: Int): T {
        val view = itemView.findViewById<T>(id)
        views.put(id, view)
        return view
    }

    fun <T: View> getView(@IdRes id: Int): T? = retrieveView(id)

    fun setText(@IdRes id: Int, value: String): BaseAdapterHelper = runBlockReturnSelf {
        retrieveView<TextView>(id).text = value
    }

    fun setImageResource(@IdRes id: Int, imageId: Int) = runBlockReturnSelf {
        retrieveView<ImageView>(id).setImageResource(imageId)
    }

    fun setBackgroundColor(@IdRes id: Int, color: Int) = runBlockReturnSelf {
        retrieveView<View>(id).setBackgroundColor(color)
    }

    fun setBackgroundRes(@IdRes id: Int, backgroundRes: Int) = runBlockReturnSelf {
        retrieveView<View>(id).setBackgroundResource(backgroundRes)
    }

    fun setTextColor(@IdRes id: Int, textColor: Int) = runBlockReturnSelf {
        retrieveView<TextView>(id).setTextColor(textColor)
    }

    fun setTextColorRes(@IdRes id: Int, textColorRes: Int) = runBlockReturnSelf {
        retrieveView<TextView>(id).run { setTextColor(this.resources.getColor(textColorRes)) }
    }

    fun setImageDrawable(@IdRes id: Int, drawable: Drawable) = runBlockReturnSelf {
        retrieveView<ImageView>(id).setImageDrawable(drawable)
    }

    fun setImageBitmap(@IdRes id: Int, bitmap: Bitmap) = runBlockReturnSelf {
        retrieveView<ImageView>(id).setImageBitmap(bitmap)
    }

    fun setAlpha(@IdRes id: Int, alphaValue: Float) = runBlockReturnSelf {
        retrieveView<View>(id).alpha = alphaValue
    }

    fun setVisible(@IdRes id: Int, isVisible: Boolean) = runBlockReturnSelf {
        retrieveView<View>(id).visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun linkify(@IdRes id: Int) = runBlockReturnSelf {
        retrieveView<TextView>(id).run { Linkify.addLinks(this, Linkify.ALL) }
    }

    fun setTypeface(@IdRes id: Int, typeface: Typeface) = runBlockReturnSelf {
        retrieveView<TextView>(id).run {
            setTypeface(typeface)
            paintFlags = this.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
    }

    fun setTypefaces(typeface: Typeface, ids: IntArray) = runBlockReturnSelf {
        ids.forEach {
            retrieveView<TextView>(it).run {
                setTypeface(typeface)
                paintFlags = this.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
            }
        }
    }

    fun setProgress(@IdRes id: Int, progress: Int) = runBlockReturnSelf {
        retrieveView<ProgressBar>(id).progress = progress
    }

    fun setProgress(@IdRes id: Int, progress: Int, max: Int) = runBlockReturnSelf {
        retrieveView<ProgressBar>(id).run {
            this.max = max
            this.progress = progress
        }
    }

    fun setMax(@IdRes id: Int, max: Int) = runBlockReturnSelf {
        retrieveView<ProgressBar>(id).max = max
    }

    fun setRating(@IdRes id: Int, rating: Float) = runBlockReturnSelf {
        retrieveView<RatingBar>(id).rating = rating
    }

    fun setRating(@IdRes id: Int, rating: Float, max: Int) = runBlockReturnSelf {
        retrieveView<RatingBar>(id).run {
            this.max = max
            this.rating = rating
        }
    }

    fun setOnClickListener(@IdRes id: Int, listener: View.OnClickListener) = runBlockReturnSelf {
        retrieveView<View>(id).setOnClickListener(listener)
    }

    fun setOnTouchListener(@IdRes id: Int, listener: View.OnTouchListener) = runBlockReturnSelf {
        retrieveView<View>(id).setOnTouchListener(listener)
    }

    fun setOnLongClickListener(@IdRes id: Int, listener: View.OnLongClickListener) = runBlockReturnSelf {
        retrieveView<View>(id).setOnLongClickListener(listener)
    }

    fun setTag(@IdRes id: Int, tag: Any) = runBlockReturnSelf {
        retrieveView<View>(id).tag = tag
    }

    fun setTag(@IdRes id: Int, key: Int, tag: Any) = runBlockReturnSelf {
        retrieveView<View>(id).setTag(key, tag)
    }

    fun setChecked(@IdRes id: Int, checked: Boolean) = runBlockReturnSelf {
        (retrieveView<View>(id) as Checkable).isChecked = checked
    }

    fun getConvertView(): View = itemView

    fun getTextView(viewId: Int): TextView = retrieveView(viewId)

    fun getLinerLayout(layoutId: Int): LinearLayout = retrieveView(layoutId)

    fun getGridviewLayout(viewID: Int): GridView = retrieveView(viewID)

    fun getRelativeLayout(relative: Int): RelativeLayout = retrieveView(relative)

    fun getFrameLayout(id: Int): FrameLayout = retrieveView(id)

    fun getCheckBox(viewId: Int): CheckBox = retrieveView(viewId)

    fun getButton(viewId: Int): Button = retrieveView(viewId)

    fun getImageView(viewId: Int): ImageView = retrieveView(viewId)

    private fun BaseAdapterHelper.runBlockReturnSelf(block:() -> Unit): BaseAdapterHelper {
        block.invoke()
        return this
    }
}