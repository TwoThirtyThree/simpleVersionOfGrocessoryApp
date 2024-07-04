
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse
import kotlinx.coroutines.Dispatchers

class DashboardViewModel(context: Context) : ViewModel() {

    private var repository: Repository = RepositoryImpl(context)

    private fun getHeaders(): Map<String, String> {
        return mapOf(

            "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
            "Content-Type" to "application/json"
        )
    }

    private fun getRequestBody(): Map<String, Any> {
        return mapOf(
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
        ),
        )
    }

    fun fetchAllData(): LiveData<Results<ServicesListResponse>> = liveData(Dispatchers.IO) {
        val headers = getHeaders()
        val requestBody = getRequestBody()
        val result = repository.fetchAllData(headers, requestBody)
        emit(result)

        when (result) {
            is Results.Success -> Log.d("DashboardViewModel", "Fetched all data successfully.")
            is Results.Failure -> Log.e("DashboardViewModel", "Error fetching data: ${result.message}")
        }
    }

    fun updateRepository(repository: Repository) {
        this.repository = repository
    }
}
