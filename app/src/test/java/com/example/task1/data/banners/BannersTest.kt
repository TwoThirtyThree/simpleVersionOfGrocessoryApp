
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*

import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any


@RunWith(MockitoJUnitRunner::class)
class BannersTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository


    @Mock
    private lateinit var observer: Observer<Results<List<Banner>>>

    @Mock
    private lateinit var context: Context

    private lateinit var viewModel: DashboardViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    private val headers = mapOf(
        "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
        "Content-Type" to "application/json"
    )

    private val requestBody = mapOf(
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
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        // Ensure the application context is mocked
        `when`(context.applicationContext).thenReturn(context)
        // Initialize the ViewModel
        viewModel = DashboardViewModel(context)
        // Set the mock repository
        viewModel.updateRepository(repository)
    }



    @Test
    fun fetchBannersFromAPI() = testScope.runBlockingTest {
        val bannerList = listOf(Banner(id = "1", imageUrl = "url1"), Banner(id = "2", imageUrl = "url2"))
        `when`(repository.getBanners(headers, requestBody)).thenReturn(Results.Success(bannerList))
        val liveData = viewModel.fetchBanners()
        //here observer observe list of banners data
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        verify(observer).onChanged(Results.Success(bannerList))
        verify(repository).getBanners(headers, requestBody)
        // Clean up after the test
        reset(observer)
        liveData.removeObserver(observer)
    }

    @Test
    fun fetchBannersFromAPIError() = testScope.runBlockingTest {
        // Mock the repository to return the error result
        `when`(repository.getBanners(headers, requestBody)).thenReturn(Results.Failure("Network Error", "Unable to fetch banners due to network issues."))
        // Observe LiveData
        val liveData = viewModel.fetchBanners()
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        // Verify that observer received the error result
        verify(observer).onChanged(Results.Failure("Network Error","Unable to fetch banners due to network issues."))
        // Verify that repository.getBanners was called with the correct parameters
        verify(repository).getBanners(headers, requestBody)
        // Clean up after the test
        reset(observer)
        liveData.removeObserver(observer)
    }


    @Test
    fun fetchBannersFromLocalAssets() = testScope.runTest {
        val bannerList = listOf(Banner(id = "1", imageUrl = "url1"), Banner(id = "2", imageUrl = "url2"))
        `when`(repository.getBanners(anyMap(), any())).thenReturn(Results.Success(bannerList))
        // Observe LiveData
        val liveData = viewModel.fetchBanners()
        liveData.observeForever(observer)
        repository.useApiData = false
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        observer.onChanged(Results.Success(bannerList))
        verify(repository).getBanners(any(), any())
        // Clean up after the test
        reset(observer)
        liveData.removeObserver(observer)  }



    @Test
    fun fetchBannersFromLocalAssetsError() = testScope.runTest {
        `when`(repository.getBanners(any(), any())).thenReturn(Results.Failure("Data Error", "Failed to fetch banners from local data."))
        repository.useApiData = false
       advanceUntilIdle()
        // Observe LiveData
        val liveData = viewModel.fetchBanners()
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        observer.onChanged(Results.Failure("Data Error","Failed to fetch banners from local data."))
        // Verify that repository.getBanners was called with any parameters (since they are not needed for local data)
        verify(repository).getBanners(any(), any())
        // Clean up after the test
        reset(observer)
        liveData.removeObserver(observer)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }
}



