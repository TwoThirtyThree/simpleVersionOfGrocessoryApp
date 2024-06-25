package com.example.task1.ui.dashboard

import ApiService
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.data.repository.RepositoryImpl
import com.example.task1.data.repostory.Repository
import kotlinx.coroutines.Dispatchers






class DashboardViewModel(context: Context) : ViewModel() {

    private val repository: Repository

    init {
        // Define headers here
        val headers = mapOf(
            "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
            "Content-Type" to "application/json"
        )

        val apiService = RetrofitClient.getClient(headers).create(ApiService::class.java)
        repository = RepositoryImpl(apiService, context.applicationContext)
    }

    fun fetchBanners(): LiveData<Results<List<Banner>>> = liveData(Dispatchers.IO) {
        val result = repository.getBanners()
        emit(result)
        when (result) {
            is Results.Success -> Log.d("DashboardViewModel", "Fetched banners successfully.")
            is Results.Failure -> Log.e("DashboardViewModel", "Error fetching banners: ${result.message}")
        }
    }

    fun fetchItems(): LiveData<Results<List<Items>>> = liveData(Dispatchers.IO) {
        val result = repository.getItems()
        emit(result)
        when (result) {
            is Results.Success -> Log.d("DashboardViewModel", "Fetched items successfully.")
            is Results.Failure -> Log.e("DashboardViewModel", "Error fetching items: ${result.message}")
        }
    }
}

