// Repository.kt
package com.example.task1.data.repostory

import com.example.task1.data.models.Banner
import com.example.task1.data.models.Product

interface Repository {
    suspend fun getBanners(): List<Banner>
    suspend fun getProducts(): List<Product>
}
