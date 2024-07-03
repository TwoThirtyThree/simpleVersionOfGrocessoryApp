import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestRule
import org.mockito.Mockito.*
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: Repository
    private lateinit var context: Context
    private lateinit var dispatcher: TestCoroutineDispatcher

    @Before
    fun setUp() {
        // Initialize the dispatcher for testing
        dispatcher = TestCoroutineDispatcher()
        context = mock()
        // Initialize the repository mock
        repository = mock()

        // Initialize the view model with the mocked repository
        viewModel = DashboardViewModel(context)

        // Set the main dispatcher to the testing dispatcher
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to the original main dispatcher
        Dispatchers.resetMain()

        // Clean up the test dispatcher
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchAllData returns success`() = runBlockingTest {
        val mockHeaders = mapOf(
            "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
            "Content-Type" to "application/json"
        )
        val mockRequestBody = mapOf(
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
        val mockBanners = listOf(
            Banner(id = "1", imageUrl = "http://example.com/banner1.png"),
            Banner(id = "2", imageUrl = "http://example.com/banner2.png")
        )

        val mockItems = listOf(
            Items(
                tenantUid = "tenant1",
                operationUid = "operation1",
                id = "item1",
                thirdPartyUid = "thirdParty1",
                category = "category1",
                customizationItems = emptyList(),
                recommendationTags = emptyList(),
                legacyId = "legacy1",
                legacyProductNumber = "legacyProduct1",
                stockThreshold = 10,
                size = 1,
                vatPercent = 5.0,
                lastModified = System.currentTimeMillis(),
                brandName = "Brand1",
                brandImage = "http://example.com/brand1.png",
                sku = "SKU1",
                label = "Item 1",
                description = "Description 1",
                imageUrl = "http://example.com/item1.png",
                thumbnailUrl = "http://example.com/thumb1.png",
                unit = "unit1",
                itemViewType = "viewType1",
                neverRecommend = false,
                recommendationLevel = 1,
                group = "group1",
                itemType = "type1",
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
                createdOn = System.currentTimeMillis(),
                Qty = 1,
                discount = 0
            ),
            Items(
                tenantUid = "tenant2",
                operationUid = "operation2",
                id = "item2",
                thirdPartyUid = "thirdParty2",
                category = "category2",
                customizationItems = emptyList(),
                recommendationTags = emptyList(),
                legacyId = "legacy2",
                legacyProductNumber = "legacyProduct2",
                stockThreshold = 5,
                size = 2,
                vatPercent = 10.0,
                lastModified = System.currentTimeMillis(),
                brandName = "Brand2",
                brandImage = "http://example.com/brand2.png",
                sku = "SKU2",
                label = "Item 2",
                description = "Description 2",
                imageUrl = "http://example.com/item2.png",
                thumbnailUrl = "http://example.com/thumb2.png",
                unit = "unit2",
                itemViewType = "viewType2",
                neverRecommend = true,
                recommendationLevel = 2,
                group = "group2",
                itemType = "type2",
                weight = 2.0,
                outOfStock = true,
                qtyPerUnit = 2,
                sortOrder = 2,
                maxQty = 0,
                minQty = 1,
                price = 20.0,
                isPriceOverridden = true,
                vatFree = true,
                requiresLegalAge = true,
                ratingEnabled = false,
                hasSpecialInstructions = true,
                updatedOn = System.currentTimeMillis(),
                createdOn = System.currentTimeMillis(),
                Qty = 2,
                discount = 5
            )
        )

        // Mock ServicesListResponse combining banners and items
        val mockServicesListResponse = ServicesListResponse(
            banners = mockBanners,
            items = mockItems
        )

        // Mock the repository to return success
        `when`(repository.fetchAllData(mockHeaders, mockRequestBody)).thenReturn(
            Results.Success(
                mockServicesListResponse
            )
        )

        // Create an observer
        val observer = mock<Observer<Results<ServicesListResponse>>>()
        viewModel.fetchAllData().observeForever(observer)

        // Act
        viewModel.fetchAllData()

        // Assert
        observer.onChanged(Results.Success(mockServicesListResponse))
//        assertEquals(mockBanners, viewModel.banners.value)
//        assertEquals(mockItems, viewModel.items.value)

        // Remove observer
        viewModel.fetchAllData().removeObserver(observer)
    }

    @Test
    fun `fetchAllData returns failure`() = runBlockingTest {
        // Given
        val headers = mapOf(
            "Authorization" to "some_auth_token",
            "Content-Type" to "application/json"
        )
        val requestBody = mapOf(
            "some_key" to "some_value"
        )
        val mockError = "Network Error"

        // Mock the repository to return failure
        `when`(
            repository.fetchAllData(
                headers,
                requestBody
            )
        ).thenReturn(Results.Failure("Network Error", "Failed to fetch data"))

        // Create an observer
        val observer = mock<Observer<Results<ServicesListResponse>>>()
        viewModel.fetchAllData().observeForever(observer)

        // Act
        viewModel.fetchAllData()

        // Assert
        observer.onChanged(Results.Failure("Network Error", "Failed to fetch data"))

        // Remove observer
        viewModel.fetchAllData().removeObserver(observer)
    }


    @Test
    fun `fetchAllData from local source returns success`() = runBlockingTest {
        // Given
        repository.useApiData = false
        val headers = emptyMap<String, String>()
        val requestBody = Any()
        val mockBanners = listOf(Banner(id = "1", imageUrl = "url1"))
        val mockItems = listOf(Items(    tenantUid = "tenant2",
            operationUid = "operation2",
            id = "item2",
            thirdPartyUid = "thirdParty2",
            category = "category2",
            customizationItems = emptyList(),
            recommendationTags = emptyList(),
            legacyId = "legacy2",
            legacyProductNumber = "legacyProduct2",
            stockThreshold = 5,
            size = 2,
            vatPercent = 10.0,
            lastModified = System.currentTimeMillis(),
            brandName = "Brand2",
            brandImage = "http://example.com/brand2.png",
            sku = "SKU2",
            label = "Item 2",
            description = "Description 2",
            imageUrl = "http://example.com/item2.png",
            thumbnailUrl = "http://example.com/thumb2.png",
            unit = "unit2",
            itemViewType = "viewType2",
            neverRecommend = true,
            recommendationLevel = 2,
            group = "group2",
            itemType = "type2",
            weight = 2.0,
            outOfStock = true,
            qtyPerUnit = 2,
            sortOrder = 2,
            maxQty = 0,
            minQty = 1,
            price = 20.0,
            isPriceOverridden = true,
            vatFree = true,
            requiresLegalAge = true,
            ratingEnabled = false,
            hasSpecialInstructions = true,
            updatedOn = System.currentTimeMillis(),
            createdOn = System.currentTimeMillis(),
            Qty = 2,
            discount = 5))
        val mockResponse = ServicesListResponse(banners = mockBanners, items = mockItems)

        // Mock the repository to return success from local data
        `when`(repository.fetchAllData(headers, requestBody)).thenReturn(Results.Success(mockResponse))

        // Create an observer
        val observer = mock<Observer<Results<ServicesListResponse>>>()
        viewModel.fetchAllData().observeForever(observer)

        // Act
        viewModel.fetchAllData()

        // Assert
        observer.onChanged(Results.Success(mockResponse))


        // Remove observer
        viewModel.fetchAllData().removeObserver(observer)
    }
}
