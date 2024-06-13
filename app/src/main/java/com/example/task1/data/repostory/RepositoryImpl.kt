// RepositoryImpl.kt
package com.example.task1.data.repostory

import android.content.Context
import android.util.Log
import com.example.task1.data.models.Banner
import com.example.task1.data.models.BannerListResponse
import com.example.task1.data.models.Items
import com.example.task1.data.models.ItemListResponse
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
                Log.e("RepositoryImpl for banners", "Network error: ${e.message}")
                emptyList()
            } catch (e: HttpException) {
                Log.e("RepositoryImpl  for banners", "HTTP error: ${e.message}")
                emptyList()
            } catch (e: Exception) {
                Log.e("RepositoryImpl  for banners", "Error: ${e.message}")
                emptyList()
            }
        } else {
            getLocalBanners()
        }
    }

    override suspend fun getItems(): List<Items> {
        return if (useApiData) {
            try {
                val response = apiService.getServices()
                Log.d("RepositoryImpl for items", "Fetched items: ${response.items}")

                if (response.items.isNullOrEmpty()) {
                    Log.e("RepositoryImpl  for items", "No items received from the API.")
                }
           response.items
            } catch (e: IOException) {
                Log.e("RepositoryImpl for items", "Network error: ${e.message}")
                emptyList()
            } catch (e: HttpException) {
                Log.e("RepositoryImpl for items", "HTTP error: ${e.message}")
                emptyList()
            } catch (e: Exception) {
                Log.e("RepositoryImpl for items", "Error in: ${e.message}")
                emptyList()
            }
        } else {
          getLocalItems()
        }
    }


    private fun getLocalBanners(): List<Banner> {
        val json = getJsonDataFromAsset("home.json", context)
        val bannersResponse = Gson().fromJson(json, BannerListResponse::class.java)
        return bannersResponse.banners
    }

    private fun getLocalItems(): List<Items> {
        val json = getJsonDataFromAsset("home.json", context)
        val itemsResponse = Gson().fromJson(json, ItemListResponse::class.java)
        return itemsResponse.products
    }

    private fun getJsonDataFromAsset(fileName: String, context: Context): String {
        return try {
            Log.d("RepositoryImpl", "Attempting to read asset: $fileName from context: ${context.packageName}")

            val assetManager = context.assets
            val fileList = assetManager.list("")
            Log.d("RepositoryImpl", "Files in assets: ${fileList?.joinToString()}")

            if (fileList != null && fileName !in fileList) {
                Log.e("RepositoryImpl", "File $fileName not found in assets directory.")
                return ""
            }

            val inputStream = assetManager.open(fileName)
            Log.d("RepositoryImpl", "Asset opened successfully")

            val size = inputStream.available()
            Log.d("RepositoryImpl", "Asset size: $size bytes")

            val buffer = ByteArray(size)
            inputStream.read(buffer)
            Log.d("RepositoryImpl", "Asset read successfully into buffer")

            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            Log.e("RepositoryImpl", "Error reading asset: ${e.message}")
            ""
        }
    }
}