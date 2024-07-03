import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse


interface Repository {
    var useApiData: Boolean

    suspend fun fetchAllData(headers: Map<String, String>, requestBody: Any): Results<ServicesListResponse>
}
