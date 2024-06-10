package com.example.task1.data.retrofit

import com.example.task1.data.models.Banner
import com.example.task1.data.models.Product

data class ServicesListResponse(
    val banners: List<Banner>,
    val products: List<Product>

)