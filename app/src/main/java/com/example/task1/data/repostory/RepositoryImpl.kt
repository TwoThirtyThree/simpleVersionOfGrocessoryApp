// RepositoryImpl.kt
package com.example.task1.data.repostory

import android.content.Context
import android.util.Log
import com.example.task1.data.models.Banner
import com.example.task1.data.models.BannerListResponse
import com.example.task1.data.models.Product
import com.example.task1.data.models.ProductListResponse
import com.example.task1.data.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class RepositoryImpl(private val apiService: ApiService, private val context: Context) : Repository {
    override var useApiData: Boolean = true

    override suspend fun getBanners(): List<Banner> {
        return if (useApiData) {
            try {
                val response = apiService.getServices()
                response.banners
            } catch (e: IOException) {
                Log.e("RepositoryImpl", "Network error: ${e.message}")
                emptyList()
            } catch (e: HttpException) {
                Log.e("RepositoryImpl", "HTTP error: ${e.message}")
                emptyList()
            } catch (e: Exception) {
                Log.e("RepositoryImpl", "Error: ${e.message}")
                emptyList()
            }
        } else {
            getLocalBanners()
        }
    }

    override suspend fun getProducts(): List<Product> {
        return if (useApiData) {
            try {
                val response = apiService.getServices()
                response.products
            } catch (e: IOException) {
                Log.e("RepositoryImpl", "Network error: ${e.message}")
                emptyList()
            } catch (e: HttpException) {
                Log.e("RepositoryImpl", "HTTP error: ${e.message}")
                emptyList()
            } catch (e: Exception) {
                Log.e("RepositoryImpl", "Error: ${e.message}")
                emptyList()
            }
        } else {
            getLocalProducts()
        }
    }

    private fun getLocalBanners(): List<Banner> {
        val json = getJsonDataFromAsset("banners.json", context)
        val bannersResponse = Gson().fromJson(json, BannerListResponse::class.java)
        return bannersResponse.banners
    }

    private fun getLocalProducts(): List<Product> {
        val json = getJsonDataFromAsset("products.json", context)
        val productsResponse = Gson().fromJson(json, ProductListResponse::class.java)
        return productsResponse.products
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
            Log.e("RepositoryImpl", "Error reading asset: ${e.message}")
            ""
        }
    }
}
