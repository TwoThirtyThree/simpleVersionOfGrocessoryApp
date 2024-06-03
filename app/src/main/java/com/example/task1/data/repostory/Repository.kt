// Repository.kt
package com.example.task1.data.repostory

import com.example.task1.data.models.Banner
import com.example.task1.data.models.Product

interface Repository {


    var useApiData: Boolean

    suspend fun getBanners(): List<Banner>
    suspend fun getProducts(): List<Product>
}
