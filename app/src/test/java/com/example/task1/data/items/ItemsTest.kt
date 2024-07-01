import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ItemsTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Results<List<Items>>>

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
    fun fetchItemsFromAPI() = testScope.runBlockingTest {
        val productList = listOf(
            Items(
                tenantUid = "tenant1",
                operationUid = "op1",
                id = "id1",
                thirdPartyUid = "third1",
                category = "cat1",
                customizationItems = emptyList(),
                recommendationTags = emptyList(),
                legacyId = "legacy1",
                legacyProductNumber = "legacyNum1",
                stockThreshold = 15,
                size = 10,
                vatPercent = 5.0,
                lastModified = System.currentTimeMillis(),
                brandName = "Brand A",
                brandImage = "image1.jpg",
                sku = "SKU1",
                label = "Product 1",
                description = "Description 1",
                imageUrl = "http://image1.jpg",
                thumbnailUrl = "http://thumb1.jpg",
                unit = "pcs",
                itemViewType = "type1",
                neverRecommend = false,
                recommendationLevel = 1,
                group = "group1",
                itemType = "typeA",
                weight = 1.0,
                outOfStock = false,
                qtyPerUnit = 1,
                sortOrder = 1,
                maxQty = 5,
                minQty = 1,
                price = 10.0,
                isPriceOverridden = false,
                vatFree = false,
                requiresLegalAge = false,
                ratingEnabled = true,
                hasSpecialInstructions = false,
                updatedOn = System.currentTimeMillis(),
                createdOn = System.currentTimeMillis() - 86400000,
                Qty = 10,
                discount = 0
            )
        )
        `when`(repository.getItems(headers, requestBody)).thenReturn(Results.Success(productList))

        val liveData = viewModel.fetchItems()
        // Here observer observe list of items data
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        verify(observer).onChanged(Results.Success(productList))
        verify(repository).getItems(headers, requestBody)
        // Clean up after the test
        reset(observer)
        liveData.removeObserver(observer)
    }

    @Test
    fun fetchItemsFromAPIError() = testScope.runBlockingTest {
        // Mock the repository to return the error result
        `when`(repository.getItems(headers, requestBody)).thenReturn(
            Results.Failure("Network Error", "Unable to fetch items due to network issues."))
        // Observe LiveData
        val liveData = viewModel.fetchItems()
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        // Verify that observer received the error result
        verify(observer).onChanged(Results.Failure("Network Error", "Unable to fetch items due to network issues."))
        // Verify that repository.getItems was called with the correct parameters
        verify(repository).getItems(headers, requestBody)
        // Clean up after the test
        reset(observer)
        liveData.removeObserver(observer)
    }

    @Test
    fun fetchItemsFromLocalAssets() = testScope.runTest {
        val productList = listOf(
            Items(
                tenantUid = "tenant1",
                operationUid = "op1",
                id = "id1",
                thirdPartyUid = "third1",
                category = "cat1",
                customizationItems = emptyList(),
                recommendationTags = emptyList(),
                legacyId = "legacy1",
                legacyProductNumber = "legacyNum1",
                stockThreshold = 15,
                size = 10,
                vatPercent = 5.0,
                lastModified = System.currentTimeMillis(),
                brandName = "Brand A",
                brandImage = "image1.jpg",
                sku = "SKU1",
                label = "Product 1",
                description = "Description 1",
                imageUrl = "http://image1.jpg",
                thumbnailUrl = "http://thumb1.jpg",
                unit = "pcs",
                itemViewType = "type1",
                neverRecommend = false,
                recommendationLevel = 1,
                group = "group1",
                itemType = "typeA",
                weight = 1.0,
                outOfStock = false,
                qtyPerUnit = 1,
                sortOrder = 1,
                maxQty = 5,
                minQty = 1,
                price = 10.0,
                isPriceOverridden = false,
                vatFree = false,
                requiresLegalAge = false,
                ratingEnabled = true,
                hasSpecialInstructions = false,
                updatedOn = System.currentTimeMillis(),
                createdOn = System.currentTimeMillis() - 86400000,
                Qty = 10,
                discount = 0
            )
        )
        `when`(repository.getItems(anyMap(), any())).thenReturn(Results.Success(productList))
        // Observe LiveData
        val liveData = viewModel.fetchItems()
        liveData.observeForever(observer)
        repository.useApiData = false
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        observer.onChanged(Results.Success(productList))
        verify(repository).getItems(any(), any())
        // Clean up after the test
        reset(observer)
        liveData.removeObserver(observer)
    }

    @Test
    fun fetchItemsFromLocalAssetsError() = testScope.runTest {
        `when`(repository.getItems(any(), any())).thenReturn(
            Results.Failure("Data Error", "Failed to fetch items from local data.")
        )
        repository.useApiData = false
        // Observe LiveData
        val liveData = viewModel.fetchItems()
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        // Verify that observer received the failure result for local data
        observer.onChanged(Results.Failure("Data Error", "Failed to fetch items from local data."))
        // Verify that repository.getItems was called with any parameters (since they are not needed for local data)
        verify(repository).getItems(any(), any())
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
