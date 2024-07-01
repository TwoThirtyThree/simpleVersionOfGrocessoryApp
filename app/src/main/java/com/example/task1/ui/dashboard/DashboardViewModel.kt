package com.example.task1.ui.dashboard

import ApiService
import Repository
import RepositoryImpl
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import kotlinx.coroutines.Dispatchers

class DashboardViewModel(context: Context) : ViewModel() {

    private var repository: Repository = RepositoryImpl(RetrofitClient.getClient().create(ApiService::class.java), context)

    // Common method to prepare headers
    private fun getHeaders(): Map<String, String> {
        return mapOf(
            "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
            "Content-Type" to "application/json"
        )
    }

    // Common method to prepare the request body
    private fun getRequestBody(): Map<String, Any> {
        return mapOf(
            "locality" to mapOf(
                "coordinates" to mapOf(
                    "lat" to 33.8868890356106,
                    "lon" to 35.5191703140736
                )
            ),
            "isLazy" to false,
            "locale" to "",
            "appIdentifier" to "f368bb3bf8323985",
            "appName" to "noknok",
            "country" to "Pakistan",
            "operationUid" to "eee"
        )
    }

    // Generic method to fetch data (banners or items)
    private fun <T> fetchData(
        fetchFunction: suspend (Map<String, String>, Map<String, Any>) -> Results<List<T>>,
        successLog: String,
        errorLog: String
    ): LiveData<Results<List<T>>> = liveData(Dispatchers.IO) {
        val headers = getHeaders()
        val requestBody = getRequestBody()

        val result = fetchFunction(headers, requestBody)
        emit(result)

        when (result) {
            is Results.Success -> Log.d("DashboardViewModel", successLog)
            is Results.Failure -> Log.e("DashboardViewModel", "$errorLog: ${result.message}")
        }
    }

    // Specific methods to fetch banners and items using the generic fetchData method
    fun fetchBanners(): LiveData<Results<List<Banner>>> {
        return fetchData(
            fetchFunction = { headers, requestBody -> repository.getBanners(headers, requestBody) },
            successLog = "Fetched banners successfully.",
            errorLog = "Error fetching banners"
        )
    }

    fun fetchItems(): LiveData<Results<List<Items>>> {
        return fetchData(
            fetchFunction = { headers, requestBody -> repository.getItems(headers, requestBody) },
            successLog = "Fetched items successfully.",
            errorLog = "Error fetching items"
        )
    }

    // Method to update the repository instance
    fun updateRepository(repository: Repository) {
        this.repository = repository
    }
}
