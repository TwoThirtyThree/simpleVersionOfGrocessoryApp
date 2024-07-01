import com.example.task1.data.models.ServicesListResponse
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiService {
    @POST("api/market/app/services")
    suspend fun getServices(
        @HeaderMap headers: Map<String, String>,
        @Body requestBody: Any
    ): ServicesListResponse
}
