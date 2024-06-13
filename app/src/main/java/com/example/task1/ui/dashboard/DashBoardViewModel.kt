// DashBoardViewModel.kt
package com.example.task1.ui.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.repostory.Repository
import com.example.task1.data.repostory.RepositoryImpl
import com.example.task1.data.retrofit.ApiService

import kotlinx.coroutines.Dispatchers

class DashBoardViewModel(context: Context) : ViewModel() {
    var repository: Repository

    init {
        val apiService = RetrofitClient.getClient().create(ApiService::class.java)
        repository = RepositoryImpl(apiService, context.applicationContext)
    }

    fun fetchBanners(): LiveData<List<Banner>> = liveData(Dispatchers.IO) {
        try {

            val banners = repository.getBanners()
            emit(banners)
        } catch (e: Exception) {
            emit(emptyList<Banner>())
            Log.e("DashBoardViewModel for banners", "Error fetching banners: ${e.message}")
        }
    }

    fun fetchItems(): LiveData<List<Items>> = liveData(Dispatchers.IO) {
        try {
            val items = repository.getItems()
            emit(items)
        } catch (e: Exception) {
            emit(emptyList<Items>())
            Log.e("DashBoardViewModel", "Error fetching products: ${e.message}")
        }
    }

    // Rename the custom setter method
    fun updateRepository(repository: Repository) {
        this.repository = repository
    }
}
