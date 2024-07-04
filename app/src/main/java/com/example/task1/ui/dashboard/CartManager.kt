
package com.example.task1.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast

import com.example.task1.ui.adapters.ItemsAdapter

class CartManager(
    private val context: Context,
    private val itemsAdapter: ItemsAdapter,
    private val cartQuantityTextView: TextView
) {

    var totalQuantityInCart: Int = 0

    fun onCartClick(position: Int) {
        val product = itemsAdapter.productList[position]
        if (product.maxQty > 0) {
            product.maxQty -= 1
            totalQuantityInCart++
            updateProductListUI(position)
            updateCartIcon()
        } else {
           Toast.makeText(context,"Product is out of stock",Toast.LENGTH_LONG)
           throw IllegalStateException("Product is out of stock")
        }
    }

    fun onQuantityIncrease(position: Int) {
        val product = itemsAdapter.productList[position]
        if (product.maxQty > 0) {
            product.maxQty -= 1
            totalQuantityInCart++
            updateProductListUI(position)
            updateCartIcon()
        } else {
            Toast.makeText(context,"Product is out of stock",Toast.LENGTH_LONG)
            throw IllegalStateException("No more stock available")
        }
    }

    fun onQuantityDecrease(position: Int) {
        val product = itemsAdapter.productList[position]
        if (totalQuantityInCart > 0) {
            totalQuantityInCart--
            product.maxQty += 1
            updateProductListUI(position)
            updateCartIcon()
        }
        else
        {  throw IllegalStateException("Cannot decrease quantity further")
            Toast.makeText(context,"Cannot decrease quantity further", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCartIcon() {
        cartQuantityTextView.text = totalQuantityInCart.toString()
    }

    private fun updateProductListUI(position: Int) {
        itemsAdapter.notifyItemChanged(position)
    }
}
