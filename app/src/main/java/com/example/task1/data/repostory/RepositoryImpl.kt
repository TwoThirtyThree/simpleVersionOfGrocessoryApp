import android.content.Context
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RepositoryImpl(private val apiService: ApiService, private val context: Context) :
    Repository {

    override var useApiData: Boolean = true

    override suspend fun getBanners(
        headers: Map<String, String>,
        requestBody: Any
    ): Results<List<Banner>> {
        return withContext(Dispatchers.IO) {
            if (useApiData) {
                try {
                    // Create the request with dynamic headers and body
                    val response = apiService.getServices(headers, requestBody)
                    Results.Success(response.banners)
                } catch (e: IOException) {
                    Results.Failure(
                        "Network Error",
                        "Unable to fetch banners due to network issues."
                    )
                } catch (e: HttpException) {
                    Results.Failure(
                        "HTTP Error",
                        "Failed to fetch banners. Please try again later."
                    )
                } catch (e: Exception) {
                    Results.Failure("General Error", "An unexpected error occurred.")
                }
            } else {
                val localData = getLocalData()
                localData?.banners?.let {
                    Results.Success(it)
                } ?: Results.Failure("Data Error", "Failed to fetch banners from local data.")
            }
        }
    }

    override suspend fun getItems(
        headers: Map<String, String>,
        requestBody: Any
    ): Results<List<Items>> {
        return withContext(Dispatchers.IO) {
            if (useApiData) {
                try {
                    // Create the request with dynamic headers and body
                    val response = apiService.getServices(headers, requestBody)
                    Results.Success(response.items)
                } catch (e: IOException) {
                    Results.Failure("Network Error", "Unable to fetch items due to network issues.")
                } catch (e: HttpException) {
                    Results.Failure("HTTP Error", "Failed to fetch items. Please try again later.")
                } catch (e: Exception) {
                    Results.Failure("General Error", "An unexpected error occurred.")
                }
            } else {
                val localData = getLocalData()
                localData?.items?.let {
                    Results.Success(it)
                } ?: Results.Failure("Data Error", "Failed to fetch items from local data.")
            }
        }
    }

    private fun getLocalData(): ServicesListResponse? {
        val json = getJsonDataFromAsset("home.json", context)
        return if (json.isNotEmpty()) {
            Gson().fromJson(json, ServicesListResponse::class.java)
        } else {
            null
        }
    }

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