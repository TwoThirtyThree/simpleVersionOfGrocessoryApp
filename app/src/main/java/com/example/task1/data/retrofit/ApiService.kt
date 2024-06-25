


import com.example.task1.data.models.ServicesListResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/market/app/services")
    suspend fun getServices(@Body body: RequestBody): ServicesListResponse
}



