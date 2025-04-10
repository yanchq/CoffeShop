package com.example.coffeshop.presentation.order

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.coffeshop.R
import com.example.coffeshop.domain.entity.Item

class OrderListAdapter(
    private val items: List<Item>
): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = items[position]
        Log.d("CheckImage", item.image)
        (holder as ItemViewHolder).itemName.text = item.name
        holder.cost.text = item.cost.toString()
        holder.properties.text = getProperties(item)

        Glide.with(holder.icon.context)
            .load(item.image)
            .placeholder(R.drawable.ic_product)
            .into(holder.icon)
    }

    private fun getProperties(item: Item): String {
        return "Size: ${item.size}\n" +
                "Sugar: ${item.sugar}\n" +
                "Syrup: ${item.syrup}"
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(itemView: View): ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val icon: ImageView = itemView.findViewById(R.id.item_image)
        val properties: TextView = itemView.findViewById(R.id.item_properties)
        val cost: TextView = itemView.findViewById(R.id.item_cost)
    }
}