package com.example.task1.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.data.models.Product
import com.example.task1.ui.products.ProductDetailActivity


class ProductAdapter(val productList: List<Product>, private val listener: OnCartClickListener) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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


        fun bind(product: Product) {
            productName.text = product.name
            productDescription.text = product.description
            totalQuantityInTextView.text = product.quantity.toString()
            productPrice.text =
                "$${product.price}" // Assuming price is in dollars, modify as needed
            discountBadge.text = "${product.discount}% OFF"
            if (product.discount > 0) {
                discountBadge.visibility = View.VISIBLE
            } else {
                discountBadge.visibility = View.VISIBLE
            }

            Glide.with(itemView.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.cart)
                .error(androidx.core.R.drawable.ic_call_decline)
                .into(productImage)

            addToCartButton.setOnClickListener {
                listener.CartClick(adapterPosition)
                addToCartButton.visibility = View.GONE
                myLinearLayout.visibility = View.VISIBLE

            }
            productImage.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra("name", product.name)
                    putExtra("description", product.description)
                    putExtra("price", "$${product.price}")
                    putExtra("imageUrl", product.imageUrl)
                    putExtra("quantity", product.quantity)
                    putExtra("discount", product.discount)
                }
                context.startActivity(intent)
            }

            plusButton.setOnClickListener {
                listener.QuantityIncrease(adapterPosition)
            }

            minusButton.setOnClickListener {
                listener.QuantityDecrease(adapterPosition)
            }

            // If quantity becomes zero, show "Out of stock" and disable buttons
            if (product.quantity == 0) {
                addToCartButton.text = "Out of stock"
                plusButton.isEnabled = false
                minusButton.isEnabled = false
            } else {
                addToCartButton.text = "Add to cart"
                plusButton.isEnabled = true
                minusButton.isEnabled = true
            }

            // Disable minus button if quantity is 1 or less
            minusButton.isEnabled = product.quantity > 0
        }


    }

    interface OnCartClickListener {
        fun CartClick(position: Int)
        fun QuantityIncrease(position: Int)
        fun QuantityDecrease(position: Int)
    }
}






