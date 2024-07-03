//import android.content.Context
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.example.task1.data.models.Banner
//import com.example.task1.data.models.Items
//import com.example.task1.data.models.Results
//import com.example.task1.data.models.ServicesListResponse
//import com.example.task1.ui.dashboard.DashboardViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.test.*
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.junit.MockitoJUnitRunner
//
//@RunWith(MockitoJUnitRunner::class)
//class DashboardViewModelTest {
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var repository: Repository
//
//    @Mock
//    private lateinit var observerServices: Observer<Results<ServicesListResponse>>
//
//    @Mock
//    private lateinit var observerItems: Observer<List<Items>>
//
//    @Mock
//    private lateinit var context: Context
//
//    private lateinit var viewModel: DashboardViewModel
//
//    private val testDispatcher = UnconfinedTestDispatcher()
//
//    private val testScope = TestCoroutineScope(testDispatcher)
//
//    private val headers = mapOf(
//        "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
//        "Content-Type" to "application/json"
//    )
//
//    private val requestBody = mapOf(
//        "locality" to mapOf(
//            "coordinates" to mapOf(
//                "lat" to 33.8868890356106,
//                "lon" to 35.5191703140736
//            )
//        ),
//        "isLazy" to false,
//        "locale" to "",
//        "appIdentifier" to "f368bb3bf8323985",
//        "appName" to "noknok",
//        "country" to "Pakistan",
//        "operationUid" to "eee"
//    )
//
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(testDispatcher)
//        `when`(context.applicationContext).thenReturn(context)
//        viewModel = DashboardViewModel(context)
//        viewModel.updateRepository(repository)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testScope.cleanupTestCoroutines()
//    }
//
//    @Test
//    fun fetchAllDataFromAPI() = testScope.runBlockingTest {
//        val servicesListResponse = ServicesListResponse(
//            banners = listOf(Banner(id = "1", imageUrl = "url1"), Banner(id = "2", imageUrl = "url2")),
//            items = listOf(Items(id = "1", name = "Item 1"), Items(id = "2", name = "Item 2"))
//        )
//        `when`(repository.fetchAllData(headers, requestBody)).thenReturn(Results.Success(servicesListResponse))
//
//        val liveData = viewModel.fetchAllData()
//        liveData.observeForever(observerServices)
//
//        advanceUntilIdle()
//
//        verify(observerServices).onChanged(Results.Success(servicesListResponse))
//        verify(repository).fetchAllData(headers, requestBody)
//
//        reset(observerServices)
//        liveData.removeObserver(observerServices)
//    }
//
//    @Test
//    fun fetchAllDataFromAPIError() = testScope.runBlockingTest {
//        `when`(repository.fetchAllData(headers, requestBody)).thenReturn(Results.Failure("Network Error", "Unable to fetch data due to network issues."))
//
//        val liveData = viewModel.fetchAllData()
//        liveData.observeForever(observerServices)
//
//        advanceUntilIdle()
//
//        verify(observerServices).onChanged(Results.Failure("Network Error", "Unable to fetch data due to network issues."))
//        verify(repository).fetchAllData(headers, requestBody)
//
//        reset(observerServices)
//        liveData.removeObserver(observerServices)
//    }
//
//    @Test
//    fun fetchDataFromAssets() = testScope.runBlockingTest {
//        val itemsList = listOf(Items(id = "1", name = "Item 1"), Items(id = "2", name = "Item 2"))
//        `when`(repository.()).thenReturn(itemsList)
//
//        val liveData = viewModel.fetchDataFromAssets()
//        liveData.observeForever(observerItems)
//
//        advanceUntilIdle()
//
//        verify(observerItems).onChanged(itemsList)
//        verify(repository).fetchDataFromAssets()
//
//        reset(observerItems)
//        liveData.removeObserver(observerItems)
//    }
//
//    @Test
//    fun fetchDataFromAssetsEmpty() = testScope.runBlockingTest {
//        `when`(repository.fetchDataFromAssets()).thenReturn(emptyList())
//
//        val liveData = viewModel.fetchDataFromAssets()
//        liveData.observeForever(observerItems)
//
//        advanceUntilIdle()
//
//        verify(observerItems).onChanged(emptyList())
//        verify(repository).fetchDataFromAssets()
//
//        reset(observerItems)
//        liveData.removeObserver(observerItems)
//    }
//}
import android.content.Context
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import retrofit2.HttpException

//class RepositoryImplTest {
//
//    @Mock
//    private lateinit var apiService: ApiService
//
//    @Mock
//    private lateinit var context: Context
//
//    private lateinit var repository: RepositoryImpl
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        repository = RepositoryImpl(context)
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun `fetchAllData returns success when API call is successful`() = runTest {
//        // Arrange
//        val headers = mapOf( "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
//            "Content-Type" to "application/json")
//        val requestBody = mapOf(
//            "coordinates" to mapOf(
//                "lat" to 33.8868890356106,
//                "lon" to 35.5191703140736
//            ),
//
//        "isLazy" to false,
//        "locale" to "",
//        "appIdentifier" to "f368bb3bf8323985",
//        "appName" to "noknok",
//        "country" to "Pakistan",
//        "operationUid" to "eee"
//        )
//        val expectedResponse = ServicesListResponse(listOf(), listOf()) // Mocked response
//
//        `when`(apiService.fetchAllData(headers, requestBody)).thenReturn(expectedResponse)
//
//        // Act
//        repository.useApiData = true
//        val result = repository.fetchAllData(headers, requestBody)
//
//        // Assert
//        assert(result is Results.Success)
//        assert((result as Results.Success).data == expectedResponse)
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun `fetchAllData returns failure when API call fails`() = runTest {
//        // Arrange
//        val headers = mapOf("Authorization" to "Bearer token")
//        val requestBody = mapOf("key" to "value")
//        val exception = HttpException(mock())
//
//        `when`(apiService.fetchAllData(headers, requestBody)).thenThrow(exception)
//
//        // Act
//        repository.useApiData = true
//        val result = repository.fetchAllData(headers, requestBody)
//
//        // Assert
//        assert(result is Results.Failure)
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun `fetchAllData returns success when reading local data`() = runTest {
//        // Arrange
//        val expectedResponse = ServicesListResponse(mapOf(), any()) // Mocked local data
//        `when`(context.assets.open("home.json")).thenReturn(mock())
//        `when`(repository.getLocalData()).thenReturn(expectedResponse)
//
//        // Act
//        repository.useApiData = false
//        val result = repository.fetchAllData(mapOf(), any())
//
//        // Assert
//        assert(result is Results.Success)
//        assert((result as Results.Success).data == expectedResponse)
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun `fetchAllData returns failure when reading local data fails`() = runTest {
//        // Arrange
//        `when`(repository.getLocalData()).thenReturn(null)
//
//        // Act
//        repository.useApiData = false
//        val result = repository.fetchAllData(mapOf(), any())
//
//        // Assert
//        assert(result is Results.Failure)
//    }
//}
