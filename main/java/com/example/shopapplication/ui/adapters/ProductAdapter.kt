package com.example.shopapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapplication.R
import com.example.shopapplication.domain.model.Product

/**
 * Same logic as GradeAdapter, but with a click listener.
 * */
class ProductAdapter(private val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductVH>(DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_product, parent, false)
        return ProductVH(view, onClick)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(o: Product, n: Product) = o.id == n.id
            override fun areContentsTheSame(o: Product, n: Product) = o == n
        }
    }
}

class ProductVH(itemView: View, public val onClick: (Product) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.title)
    private var model: Product? = null

    init {
        // Set the click listener for the entire item view
        itemView.setOnClickListener { model?.let(onClick) }
    }

    fun bind(product: Product) {
        model = product
        title.text = itemView.context.getString(R.string.product_title_text, product.name, product.clientCount)
    }
}