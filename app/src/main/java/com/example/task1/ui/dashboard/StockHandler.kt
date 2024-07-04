package com.example.task1.ui.dashboard


import android.widget.Button
import android.widget.Toast

import com.example.task1.data.models.Items

class StockHandler(
    private val addToCartButton: Button,
    private val plusButton: Button,
    private val minusButton: Button
) {

    fun handleStockStatus(product: Items) {
        if (product.outOfStock || product.maxQty == 0) {

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
