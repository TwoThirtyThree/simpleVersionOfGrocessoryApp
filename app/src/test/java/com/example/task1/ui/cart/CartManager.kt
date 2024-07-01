package com.example.task1

import com.example.task1.data.models.Items

class CartManager(private val productList: List<Items>) {
    var totalQuantityInCart: Int = 0

    fun cartClick(position: Int) {
        val product = productList[position]
        if (product.maxQty > 0) {
            product.maxQty -= 1
            totalQuantityInCart++
        } else {
            throw IllegalStateException("Product is out of stock")
        }
    }

    fun quantityIncrease(position: Int) {
        val product = productList[position]
        if (product.maxQty > 0) {
            product.maxQty -= 1
            totalQuantityInCart++
        } else {
            throw IllegalStateException("No more stock available")
        }
    }

    fun quantityDecrease() {
        if (totalQuantityInCart > 0) {
            totalQuantityInCart--
        } else {
            throw IllegalStateException("Cannot decrease quantity further")
        }
    }
}
