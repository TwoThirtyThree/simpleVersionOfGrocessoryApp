package com.example.task1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.R
import com.example.task1.data.models.Items

class ItemsAdapter(val productList: List<Items>, private val listener: OnCartClickListener) :
    RecyclerView.Adapter<ProductViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface OnCartClickListener {
        fun cartClick(position: Int)
        fun quantityIncrease(position: Int)
        fun quantityDecrease(position: Int)
    }
}
