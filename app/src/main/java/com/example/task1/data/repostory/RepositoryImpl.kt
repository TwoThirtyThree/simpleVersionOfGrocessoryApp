package com.example.task1.data.repository


import ApiService

import android.content.Context
import android.net.http.HttpException
import android.os.Build

import androidx.annotation.RequiresExtension
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse
import com.example.task1.data.repostory.Repository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException



class RepositoryImpl(private val apiService: ApiService, private val context: Context) : Repository {
    override var useApiData: Boolean = true

    // Fetch services, banners, and items from the API
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getServices(body: RequestBody): Results<ServicesListResponse> {
        return try {
            val response = apiService.getServices(body)
            Results.Success(response)
        } catch (e: IOException) {
            Results.Failure("Network Error", "Unable to fetch services due to network issues.")
        } catch (e: HttpException) {
            Results.Failure("HTTP Error", "Failed to fetch services. Please try again later.")
        } catch (e: Exception) {
            Results.Failure("General Error", "An unexpected error occurred.")
        }
    }

    // Fetch banners, can use either API or local data
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getBanners(): Results<List<Banner>> {
        return if (useApiData) {
            try {
                val response = apiService.getServices(createRequestBody())
                Results.Success(response.banners)
            } catch (e: IOException) {
                Results.Failure("Network Error", "Unable to fetch banners due to network issues.")
            } catch (e: HttpException) {
                Results.Failure("HTTP Error", "Failed to fetch banners. Please try again later.")
            } catch (e: Exception) {
                Results.Failure("General Error", "An unexpected error occurred.")
            }
        } else {
            // Fetch local data
            val localData = getLocalData()
            localData?.banners?.let {
                Results.Success(it)
            } ?: Results.Failure("Data Error", "Failed to fetch banners from local data.")
        }
    }

    // Fetch items, can use either API or local data
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getItems(): Results<List<Items>> {
        return if (useApiData) {
            try {
                val response = apiService.getServices(createRequestBody())
                Results.Success(response.items)
            } catch (e: IOException) {
                Results.Failure("Network Error", "Unable to fetch items due to network issues.")
            } catch (e: HttpException) {
                Results.Failure("HTTP Error", "Failed to fetch items. Please try again later.")
            } catch (e: Exception) {
                Results.Failure("General Error", "An unexpected error occurred.")
            }
        } else {
            // Fetch local data
            val localData = getLocalData()
            localData?.items?.let {
                Results.Success(it)
            } ?: Results.Failure("Data Error", "Failed to fetch items from local data.")
        }
    }

    // Create the request body from a predefined JSON string
    private fun createRequestBody(): RequestBody {
        val jsonBody = """
            {
                "locality": {
                    "coordinates": {
                        "lat": 33.8868890356106,
                        "lon": 35.5191703140736
                    }
                },
                "isLazy": false,
                "locale": "",
                "appIdentifier": "f368bb3bf8323985",
                "appName": "noknok",
                "country": "Pakistan",
                "operationUid": "eee"
            }
        """.trimIndent()

        return jsonBody.toRequestBody("application/json".toMediaTypeOrNull())
    }

    // Read local data from the assets folder
    private fun getLocalData(): ServicesListResponse? {
        val json = getJsonDataFromAsset("home.json", context)
        return if (json.isNotEmpty()) {
            Gson().fromJson(json, ServicesListResponse::class.java)
        } else {
            null
        }
    }

    // Helper function to read JSON data from assets
    private fun getJsonDataFromAsset(fileName: String, context: Context): String {
        return try {
            val assetManager = context.assets
            val inputStream = assetManager.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            ""
        }
    }
}




