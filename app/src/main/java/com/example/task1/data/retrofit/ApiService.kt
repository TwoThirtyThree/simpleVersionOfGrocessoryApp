package com.example.task1.data.retrofit

import com.example.task1.data.models.BannerListResponse
import com.example.task1.data.models.ProductListResponse
import retrofit2.http.GET

interface ApiService {
    @GET("banners.json")
    suspend fun getBanners(): BannerListResponse

    @GET("products.json")
    suspend fun getProducts(): ProductListResponse
}
