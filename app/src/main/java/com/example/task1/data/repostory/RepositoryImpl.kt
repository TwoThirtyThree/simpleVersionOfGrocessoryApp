import android.content.Context
import android.net.http.HttpException
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.io.IOException



class RepositoryImpl(private val  context: Context) : Repository {

    override var useApiData: Boolean = true

    var apiService: ApiService

    init {
        // Use RetrofitClient to get the Retrofit instance
        val retrofit: Retrofit = RetrofitClient.getClient()
        apiService = retrofit.create(ApiService::class.java)
    }

    override suspend fun fetchAllData(
        headers: Map<String, String>,
        requestBody: Any
    ): Results<ServicesListResponse> {
        return withContext(Dispatchers.IO) {
            if (useApiData) {
                try {
                    val response = apiService.fetchAllData(headers, requestBody)
                    Results.Success(response)
                } catch (e: IOException) {
                    Results.Failure("Network Error", "Unable to fetch data due to network issues.")
                } catch (e: HttpException) {
                    Results.Failure("HTTP Error", "Failed to fetch data. Please try again later.")
                } catch (e: Exception) {
                    Results.Failure("General Error", "An unexpected error occurred.")
                }
            } else {
                val localData = getLocalData()
                localData?.let {
                    Results.Success(it)
                } ?: Results.Failure("Data Error", "Failed to fetch data from local storage.")
            }
        }
    }

     fun getLocalData(): ServicesListResponse? {
        val json = getJsonDataFromAsset("home.json", context )
        return if (json.isNotEmpty()) {
            Gson().fromJson(json, ServicesListResponse::class.java)
        } else {
            null
        }
    }

    fun getJsonDataFromAsset(fileName: String, context: Context): String {
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

