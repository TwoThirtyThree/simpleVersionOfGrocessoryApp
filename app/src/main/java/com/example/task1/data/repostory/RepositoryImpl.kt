package com.example.task1.data.repostory

import android.content.Context
import com.example.task1.data.models.Banner
import com.example.task1.data.models.BannerListResponse
import com.example.task1.data.models.Product
import com.example.task1.data.models.ProductListResponse
import com.example.task1.data.retrofit.ApiService
import com.google.gson.Gson
import java.io.IOException

class RepositoryImpl(private val apiService: ApiService, private val context: Context) :
    Repository {
    override var useApiData: Boolean = false

    override suspend fun getBanners(): List<Banner> {
        return if (useApiData) {
            apiService.getBanners().banners
        } else {
            val json = getJsonDataFromAsset("home.json", context)
            val bannersResponse = Gson().fromJson(json, BannerListResponse::class.java)
            bannersResponse.banners
        }
    }

    override suspend fun getProducts(): List<Product> {
        return if (useApiData) {
            apiService.getProducts().products
        } else {
            val json = getJsonDataFromAsset("home.json", context)
            val productsResponse = Gson().fromJson(json, ProductListResponse::class.java)
            productsResponse.products
        }
    }

    private fun getJsonDataFromAsset(fileName: String, context: Context): String {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}
