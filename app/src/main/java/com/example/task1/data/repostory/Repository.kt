import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results



interface Repository {
    var useApiData: Boolean

    suspend fun getBanners(headers: Map<String, String>, requestBody: Any): Results<List<Banner>>
    suspend fun getItems(headers: Map<String, String>, requestBody: Any): Results<List<Items>>
}
