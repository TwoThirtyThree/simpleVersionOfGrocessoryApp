package com.example.task1.data.retrofit

import com.example.task1.data.models.ServicesListResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/market/app/services")
    suspend fun getServices(): ServicesListResponse
}
