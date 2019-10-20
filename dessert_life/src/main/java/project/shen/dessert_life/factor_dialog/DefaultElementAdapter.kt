package project.shen.dessert_life.factor_dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.lruCache
import androidx.recyclerview.widget.RecyclerView
import project.shen.dessert_life.R

/**
 * created by shen on 2019/10/20
 * at 16:47
 **/
class DefaultElementAdapter(
    private val id: Int,
    private val context: Context,
    private val elements: List<Element<Any>>
) : RecyclerView.Adapter<BaseAdapterHelper>() {
    var customClickEvent: ((BaseAdapterHelper, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapterHelper =
        BaseAdapterHelper(LayoutInflater.from(context).inflate(id, parent, false))

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: BaseAdapterHelper, position: Int) {
        if (id == R.layout.item_factor_element) {
            holder.getTextView(R.id.tvItemTitle).text = elements[position].title

            elements[position].icon?.let { elements[position].iconLoader?.invoke(it, holder.getImageView(R.id.ivItemIcon)) }
            holder.itemView.setOnClickListener {
                elements[position].funcClick?.invoke(it, position)
            }
        } else {
            holder.itemView.setOnClickListener {
                customClickEvent?.invoke(holder, position)
            }
        }
    }

    fun event(customClickEvent: (BaseAdapterHelper, Int) -> Unit) {
        this.customClickEvent = customClickEvent
    }
}

