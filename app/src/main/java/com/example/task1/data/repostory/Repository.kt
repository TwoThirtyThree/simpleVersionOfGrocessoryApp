// Repository.kt
package com.example.task1.data.repostory

import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items

interface Repository {


    var useApiData: Boolean

    suspend fun getBanners(): List<Banner>
   suspend fun getItems(): List<Items>




}
