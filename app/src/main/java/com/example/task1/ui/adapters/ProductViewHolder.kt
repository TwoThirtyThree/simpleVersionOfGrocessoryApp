package com.example.task1.ui.adapters

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.data.models.Items
import com.example.task1.ui.items.ItemsDetailActivity

class ProductViewHolder(itemView: View, val listener: ItemsAdapter.OnCartClickListener) : RecyclerView.ViewHolder(itemView) {

    private val productName: TextView = itemView.findViewById(R.id.product_name_textView)
    private val productDescription: TextView = itemView.findViewById(R.id.product_description_textView)
    private val productPrice: TextView = itemView.findViewById(R.id.product_price_textView)
    private val productImage: ImageView = itemView.findViewById(R.id.product_imageview)
    private val discountBadge: TextView = itemView.findViewById(R.id.discount_badge_textView)
    private val addToCartButton: Button = itemView.findViewById(R.id.add_to_cart_button)
    private val plusButton: Button = itemView.findViewById(R.id.add_to_cart_plus_button)
    private val minusButton: Button = itemView.findViewById(R.id.sub_to_cart_minus_button)
    private val totalQuantityInTextView: TextView = itemView.findViewById(R.id.quantityInNumbers)
    private val myLinearLayout: LinearLayout = itemView.findViewById(R.id.hide_and_visible_layout)

    fun bind(product: Items) {
        productName.text = product.brandName
        productDescription.text = product.description
        totalQuantityInTextView.text = product.maxQty.toString()
        productPrice.text = "$${product.price}" // Assuming price is in dollars, modify as needed
        discountBadge.text = "${product.discount}% OFF"
        if (product.discount > 0) {
            discountBadge.visibility = View.VISIBLE
        } else {
            discountBadge.visibility = View.GONE
        }

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

        productImage.setOnClickListener {   val context = itemView.context
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

        // Handle stock availability
        if (product.maxQty == 0) {
            addToCartButton.text = "Out of stock"
            plusButton.isEnabled = false
            minusButton.isEnabled = false
        } else {
            addToCartButton.text = "Add to cart"
            plusButton.isEnabled = true
            minusButton.isEnabled = true
        }

        // Disable minus button if quantity is 0 or less
        minusButton.isEnabled = product.maxQty > 0
    }
}
