package com.example.shopapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapplication.R
import com.example.shopapplication.domain.model.Grade

/**
 * We are using DIFF to update only changed items in the list; with this approach,
 * we avoid reloading the entire list when only a few items have changed.
 * */
class GradeAdapter : ListAdapter<Grade, GradeVH>(DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_product, parent, false)
        return GradeVH(view)
    }

    override fun onBindViewHolder(holder: GradeVH, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Grade>() {
            // Compare only productId for changes
            override fun areItemsTheSame(o: Grade, n: Grade) = o.productId == n.productId
            // If the productId is changed, the display will be automatically changed too
            override fun areContentsTheSame(o: Grade, n: Grade) = o.display == n.display
        }
    }
}

class GradeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.title)
    fun bind(m: Grade) {
        title.text = m.display
    }
}