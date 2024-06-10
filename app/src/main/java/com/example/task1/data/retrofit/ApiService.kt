package com.example.task1.data.retrofit

import com.example.task1.data.models.BannerListResponse
import com.example.task1.data.models.ProductListResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {



    @GET("api/market/app/services")
    suspend fun getServices(): ServicesListResponse




}
