//package com.example.task1.ui.dashboard
//
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.liveData
//import com.example.task1.data.repostory.Repository
//import com.example.task1.data.repostory.RepositoryImpl
//import com.example.task1.data.models.Banner
//import com.example.task1.data.models.Product
//import com.example.task1.data.retrofit.ApiService
//import com.example.task1.data.retrofit.RetrofitClient
//import kotlinx.coroutines.Dispatchers
//
// class DashBoardViewModel(context: Context) : ViewModel() {
//     var repository: Repository
//// phly resp val thi but tsting m var suggest hui
//     // again modifications
//
//    init {
//        val apiService = RetrofitClient.getClient(context).create(ApiService::class.java)
//        repository = RepositoryImpl(apiService, context.applicationContext)
//    }
//
//    fun fetchBanners(): LiveData<List<Banner>> = liveData(Dispatchers.IO) {
//        try {
//            val banners = repository.getBanners()
//            emit(banners)
//        } catch (e: Exception) {
//            emit(emptyList<Banner>())
//            Log.e("DashBoardViewModel", "Error fetching banners: ${e.message}")
//        }
//    }
//
//    fun fetchProducts(): LiveData<List<Product>> = liveData(Dispatchers.IO) {
//        try {
//            val products = repository.getProducts()
//            emit(products)
//        } catch (e: Exception) {
//            emit(emptyList<Product>())
//            Log.e("DashBoardViewModel", "Error fetching products: ${e.message}")
//        }
//    }
//}
package com.example.task1.ui.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Product
import com.example.task1.data.repostory.Repository
import com.example.task1.data.repostory.RepositoryImpl
import com.example.task1.data.retrofit.ApiService
import com.example.task1.data.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers




class DashBoardViewModel(context: Context) : ViewModel() {
    public var repository: Repository

    init {
        val apiService = RetrofitClient.getClient(context).create(ApiService::class.java)
        repository = RepositoryImpl(apiService, context.applicationContext)
    }

    fun fetchBanners(): LiveData<List<Banner>> = liveData(Dispatchers.IO) {
        try {
            val banners = repository.getBanners()
            emit(banners)
        } catch (e: Exception) {
            emit(emptyList<Banner>())
            Log.e("DashBoardViewModel", "Error fetching banners: ${e.message}")
        }
    }

    fun fetchProducts(): LiveData<List<Product>> = liveData(Dispatchers.IO) {
        try {
            val products = repository.getProducts()
            emit(products)
        } catch (e: Exception) {
            emit(emptyList<Product>())
            Log.e("DashBoardViewModel", "Error fetching products: ${e.message}")
        }
    }

    // Rename the custom setter method
    fun updateRepository(repository: Repository) {
        this.repository = repository
    }
}

