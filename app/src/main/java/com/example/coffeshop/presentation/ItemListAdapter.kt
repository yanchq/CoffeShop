package com.example.coffeshop.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.coffeshop.R
import com.example.coffeshop.domain.entity.Item

class ItemListAdapter(
    val items: List<Any>
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is String) VIEW_TYPE_HEADER
        else VIEW_TYPE_PRODUCT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            HeaderViewHolder(view)
        }
        else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            ProductViewHolder(view)
        }
    }

    override fun getItemCount(): Int = items.size

    fun isHeader(position: Int) = getItemViewType(position) == VIEW_TYPE_HEADER

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            (holder as HeaderViewHolder).headerName.text = item as String
        }
        else {
            (holder as ProductViewHolder).productName.text = (item as Item).name

        }
    }

    class ProductViewHolder(itemView : View): ViewHolder(itemView) {
        val productImage = itemView.findViewById<ImageView>(R.id.product_image)
        val productName = itemView.findViewById<TextView>(R.id.product_name)
    }

    class HeaderViewHolder(itemView: View): ViewHolder(itemView) {
        val headerName = itemView.findViewById<TextView>(R.id.header_name)
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_PRODUCT = 1
    }

}