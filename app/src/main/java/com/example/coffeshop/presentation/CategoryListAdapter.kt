package com.example.coffeshop.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.coffeshop.R

class CategoryListAdapter(
    private val categories: List<String>,
    private val onCategoryClick: (Int) -> Unit
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryName.text = categories[position]
        holder.itemView.setOnClickListener { onCategoryClick(position) }
    }

    class CategoryViewHolder(itemView: View) : ViewHolder(itemView) {
        val categoryName = itemView.findViewById<TextView>(R.id.category_name)
    }
}