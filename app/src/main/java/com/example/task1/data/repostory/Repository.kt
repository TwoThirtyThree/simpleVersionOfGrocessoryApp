

package com.example.task1.data.repostory

import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse
import okhttp3.RequestBody

interface Repository {

    var useApiData: Boolean

    suspend fun getBanners(): Results<List<Banner>>
    suspend fun getItems(): Results<List<Items>>
    suspend fun getServices(body: RequestBody): Results<ServicesListResponse>

}
