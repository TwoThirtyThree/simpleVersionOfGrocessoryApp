// File: ProductViewHolder.kt
package com.example.task1.ui.adapters

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.data.models.Items
import com.example.task1.ui.items.ItemsDetailActivity
import com.example.task1.ui.dashboard.StockHandler

class ProductViewHolder(itemView: View, private val listener: ItemsAdapter.OnCartClickListener) :
    RecyclerView.ViewHolder(itemView) {

    private val productName: TextView = itemView.findViewById(R.id.product_name_textView)
    private val productDescription: TextView =
        itemView.findViewById(R.id.product_description_textView)
    private val productPrice: TextView = itemView.findViewById(R.id.product_price_textView)
    private val productImage: ImageView = itemView.findViewById(R.id.product_imageview)
    private val discountBadge: TextView = itemView.findViewById(R.id.discount_badge_textView)
    private val addToCartButton: Button = itemView.findViewById(R.id.add_to_cart_button)
    private val plusButton: Button = itemView.findViewById(R.id.add_to_cart_plus_button)
    private val minusButton: Button = itemView.findViewById(R.id.sub_to_cart_minus_button)
    private val totalQuantityInTextView: TextView = itemView.findViewById(R.id.quantityInNumbers)
    private val myLinearLayout: LinearLayout = itemView.findViewById(R.id.hide_and_visible_layout)

    private val stockHandler = StockHandler(addToCartButton, plusButton, minusButton)

    fun bind(product: Items) {
        productName.text = product.brandName
        productDescription.text = product.description
        totalQuantityInTextView.text = product.maxQty.toString()
        productPrice.text = "$${product.price}" // Assuming price is in dollars, modify as needed
        discountBadge.text = "${product.discount}% OFF"
        discountBadge.visibility = if (product.discount > 0) View.VISIBLE else View.GONE

        Glide.with(itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.cart)
            .error(androidx.core.R.drawable.ic_call_decline)
            .into(productImage)

        addToCartButton.setOnClickListener {
            listener.cartClick(adapterPosition)
            addToCartButton.visibility = View.GONE
            myLinearLayout.visibility = View.VISIBLE
        }

        productImage.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, ItemsDetailActivity::class.java).apply {
                putExtra("name", product.brandName)
                putExtra("description", product.description)
                putExtra("price", "$${product.price}")
                putExtra("imageUrl", product.imageUrl)
                putExtra("quantity", product.maxQty)
                putExtra("discount", product.discount)
            }
            context.startActivity(intent)
        }

        plusButton.setOnClickListener {
            listener.quantityIncrease(adapterPosition)
        }

        minusButton.setOnClickListener {
            listener.quantityDecrease(adapterPosition)
        }

        // Use the StockHandler to manage stock status
        stockHandler.handleStockStatus(product)

    }
}