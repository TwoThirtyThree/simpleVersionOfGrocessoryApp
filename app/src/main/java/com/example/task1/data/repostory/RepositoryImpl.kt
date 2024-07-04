
import android.content.Context
import android.util.Log
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

class RepositoryImpl(private val context: Context) : Repository {

    override var useApiData: Boolean = false
    private var apiService: ApiService

    init {
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
                    handleNetworkError(e)
                } catch (e: HttpException) {
                    handleHttpError(e)
                } catch (e: Exception) {
                    handleGeneralError(e)
                }
            } else {
                try {
                    val localData = getLocalData("home.json") // Intentionally using the correct file name
                    localData?.let {
                        Results.Success(it)
                    } ?: Results.Failure("Data Error", "Failed to fetch data from local storage.")
                } catch (e: IOException) {
                    handleNetworkError(e)
                } catch (e: JsonSyntaxException) {
                    handleGeneralError(e)
                } catch (e: Exception) {
                    handleGeneralError(e)
                }
            }
        }
    }

    private fun handleNetworkError(e: IOException): Results.Failure {
        val errorMessage = "${e.message}"
        logError(errorMessage)
        return Results.Failure("Network Error", errorMessage)
    }

    private fun handleHttpError(e: HttpException): Results.Failure {
        val errorBody = e.response()?.errorBody()?.string()
        val errorMessage = parseErrorResponse(errorBody)
        logError(errorMessage)
        return Results.Failure("HTTP Error", errorMessage)
    }

    private fun handleGeneralError(e: Exception): Results.Failure {
        val errorMessage = "General Error: An unexpected error occurred: ${e.message}"
        logError(errorMessage)
        return Results.Failure("General Error", errorMessage)
    }

    private fun logError(errorMessage: String) {
        Log.d("errorAPIReading", "Error occurred: $errorMessage")

    }

    private fun getLocalData(fileName: String): ServicesListResponse? {
        val json = getJsonDataFromAsset(fileName, context)
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
            "" // Return an empty string to indicate the failure
        }
    }

    private fun parseErrorResponse(errorBody: String?): String {
        if (errorBody.isNullOrEmpty()) {
            return "No error message available."
        }

        return try {
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.error ?: "Unknown error occurred."
        } catch (e: Exception) {
            "Error parsing response: ${e.message}"
        }
    }
}

data class ErrorResponse(val error: String?)

