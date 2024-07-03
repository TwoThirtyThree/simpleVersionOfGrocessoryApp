//package com.example.task1.data
//
//import DashboardViewModel
//import Repository
//import android.content.Context
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.example.task1.data.models.Banner
//import com.example.task1.data.models.Items
//import com.example.task1.data.models.Results
//import com.example.task1.data.models.ServicesListResponse
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.*
//import org.junit.rules.TestRule
//import org.mockito.ArgumentMatchers.any
//import org.mockito.ArgumentMatchers.anyMap
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.verify
//import org.mockito.kotlin.whenever
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class DashboardViewModelTest {
//
//    @get:Rule
//    val instantExecutorRule: TestRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: DashboardViewModel
//    private lateinit var mockRepository: Repository
//    private lateinit var context: Context
//
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(Dispatchers.Unconfined)
//        mockRepository = mock()
//        context = mock() // Mock the context
//        viewModel = DashboardViewModel(context)
//        viewModel.updateRepository(mockRepository) // Set the mock repository to ViewModel
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `test fetchAllData success`() = runTest {
//        val mockBanners = listOf(
//            Banner(id = "1", imageUrl = "http://example.com/banner1.png"),
//            Banner(id = "2", imageUrl = "http://example.com/banner2.png")
//        )
//
//        val mockItems = listOf(
//            Items(
//                tenantUid = "tenant1",
//                operationUid = "operation1",
//                id = "item1",
//                thirdPartyUid = "thirdParty1",
//                category = "category1",
//                customizationItems = emptyList(),
//                recommendationTags = emptyList(),
//                legacyId = "legacy1",
//                legacyProductNumber = "legacyProduct1",
//                stockThreshold = 10,
//                size = 1,
//                vatPercent = 5.0,
//                lastModified = System.currentTimeMillis(),
//                brandName = "Brand1",
//                brandImage = "http://example.com/brand1.png",
//                sku = "SKU1",
//                label = "Item 1",
//                description = "Description 1",
//                imageUrl = "http://example.com/item1.png",
//                thumbnailUrl = "http://example.com/thumb1.png",
//                unit = "unit1",
//                itemViewType = "viewType1",
//                neverRecommend = false,
//                recommendationLevel = 1,
//                group = "group1",
//                itemType = "type1",
//                weight = 1.0,
//                outOfStock = false,
//                qtyPerUnit = 1,
//                sortOrder = 1,
//                maxQty = 5,
//                minQty = 1,
//                price = 10.0,
//                isPriceOverridden = false,
//                vatFree = false,
//                requiresLegalAge = false,
//                ratingEnabled = true,
//                hasSpecialInstructions = false,
//                updatedOn = System.currentTimeMillis(),
//                createdOn = System.currentTimeMillis(),
//                Qty = 1,
//                discount = 0
//            ),
//            Items(
//                tenantUid = "tenant2",
//                operationUid = "operation2",
//                id = "item2",
//                thirdPartyUid = "thirdParty2",
//                category = "category2",
//                customizationItems = emptyList(),
//                recommendationTags = emptyList(),
//                legacyId = "legacy2",
//                legacyProductNumber = "legacyProduct2",
//                stockThreshold = 5,
//                size = 2,
//                vatPercent = 10.0,
//                lastModified = System.currentTimeMillis(),
//                brandName = "Brand2",
//                brandImage = "http://example.com/brand2.png",
//                sku = "SKU2",
//                label = "Item 2",
//                description = "Description 2",
//                imageUrl = "http://example.com/item2.png",
//                thumbnailUrl = "http://example.com/thumb2.png",
//                unit = "unit2",
//                itemViewType = "viewType2",
//                neverRecommend = true,
//                recommendationLevel = 2,
//                group = "group2",
//                itemType = "type2",
//                weight = 2.0,
//                outOfStock = true,
//                qtyPerUnit = 2,
//                sortOrder = 2,
//                maxQty = 0,
//                minQty = 1,
//                price = 20.0,
//                isPriceOverridden = true,
//                vatFree = true,
//                requiresLegalAge = true,
//                ratingEnabled = false,
//                hasSpecialInstructions = true,
//                updatedOn = System.currentTimeMillis(),
//                createdOn = System.currentTimeMillis(),
//                Qty = 2,
//                discount = 5
//            )
//        )
//
//        // Mock ServicesListResponse combining banners and items
//        val mockServicesListResponse = ServicesListResponse(
//            banners = mockBanners,
//            items = mockItems
//        )
//
//        // Mocking success response with headers and request body
//        val mockHeaders = mapOf(
//            "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
//            "Content-Type" to "application/json"
//        )
//        val mockRequestBody = mapOf(
//            "locality" to mapOf(
//                "coordinates" to mapOf(
//                    "lat" to 33.8868890356106,
//                    "lon" to 35.5191703140736
//                )
//            ),
//            "isLazy" to false,
//            "locale" to "",
//            "appIdentifier" to "f368bb3bf8323985",
//            "appName" to "noknok",
//            "country" to "Pakistan",
//            "operationUid" to "eee"
//        )
//
//        val mockSuccessResponse = Results.Success(mockServicesListResponse)
//        whenever(mockRepository.fetchAllData(mockHeaders, mockRequestBody)).thenReturn(mockSuccessResponse)
//
//        // Observer to verify the response
//        val observer: Observer<Results<ServicesListResponse>> = mock()
//        viewModel.fetchAllData().observeForever(observer)
//
//        // Verify that the repository's fetchAllData method was called with specific headers and request body
//        verify(mockRepository).fetchAllData(mockHeaders, mockRequestBody)
//
//        // Verify that the observer received the success result
//        verify(observer).onChanged(mockSuccessResponse)
//    }
//
//    @Test
//    fun `test fetchAllData failure`() = runTest {
//        // Mocking failure response with headers and request body
//        val mockHeaders = mapOf(
//            "Authorization" to "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y",
//            "Content-Type" to "application/json"
//        )
//        val mockRequestBody = mapOf(
//            "locality" to mapOf(
//                "coordinates" to mapOf(
//                    "lat" to 33.8868890356106,
//                    "lon" to 35.5191703140736
//                )
//            ),
//            "isLazy" to false,
//            "locale" to "",
//            "appIdentifier" to "f368bb3bf8323985",
//            "appName" to "noknok",
//            "country" to "Pakistan",
//            "operationUid" to "eee"
//        )
//
//        val mockFailureResponse = Results.Failure("Failed to fetch data", "Network Error")
//        whenever(mockRepository.fetchAllData(mockHeaders, mockRequestBody)).thenReturn(mockFailureResponse)
//
//        // Observer to verify the response
//        val observer: Observer<Results<ServicesListResponse>> = mock()
//        viewModel.fetchAllData().observeForever(observer)
//
//        // Verify that the repository's fetchAllData method was called with specific headers and request body
//        verify(mockRepository).fetchAllData(mockHeaders, mockRequestBody)
//
//        // Verify that the observer received the failure result
//        verify(observer).onChanged(mockFailureResponse)
//    }
//}
//
import android.content.Context
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RepositoryImplTest {

    private lateinit var repository: RepositoryImpl
    private lateinit var mockApiService: ApiService
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockApiService = mock()
        mockContext = mock()

        // Create an actual instance of RepositoryImpl with mocked context and then spy on it
        repository = spy(RepositoryImpl(mockContext)).apply {
            apiService = mockApiService
        }
    }

    @Test
    fun `fetchAllData returns success from API`() = runBlocking {
        // Given
        // Mocking success response with headers and request body
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


        // When
        `when`(mockApiService.fetchAllData(mockHeaders, mockRequestBody)).thenReturn(
            mockServicesListResponse
        )

        // Act
        val result = repository.fetchAllData(mockHeaders, mockRequestBody)

        // Then
        assertTrue(result is Results.Success)
        assertEquals(mockServicesListResponse, (result as Results.Success).data)
    }

    @Test
    fun `fetchAllData returns failure on network error`() = runBlocking {
        // Given
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

        // When
        whenever(mockApiService.fetchAllData(mockHeaders, mockRequestBody)).thenAnswer {
            throw IOException("Simulated network error")
        }

        // Act
        val result = repository.fetchAllData(mockHeaders, mockRequestBody)

        // Then
        assertTrue(result is Results.Failure)
        assertEquals(
            "Unable to fetch data due to network issues.",
            (result as Results.Failure).title
        )
    }

    @Test
    fun `fetchAllData returns failure on HTTP error`() = runBlocking {
        // Given
        // Mocking success response with headers and request body
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

        // When
        val mockHttpException =
            HttpException(Response.error<Any>(500, ResponseBody.create(null, "Server Error")))
        `when`(mockApiService.fetchAllData(mockHeaders, mockRequestBody)).thenThrow(
            mockHttpException
        )

        // Act
        val result = repository.fetchAllData(mockHeaders, mockRequestBody)

        // Then
        assertTrue(result is Results.Failure)
        assertEquals("An unexpected error occurred.", (result as Results.Failure).title)
    }

    @Test
    fun `fetchAllData returns success from local data`() = runBlocking {
        // Given
        repository.useApiData = false
        val mockJson = """{"banners":[], "items":[]}""" // Your mock JSON data
        val mockResponse = Gson().fromJson(mockJson, ServicesListResponse::class.java)

        // Mock the getJsonDataFromAsset method to return the mockJson
        doReturn(mockJson).`when`(repository).getJsonDataFromAsset("home.json", mockContext)

        // Act
        val result = repository.fetchAllData(emptyMap(), Any())

        // Then
        assertTrue(result is Results.Success)
        assertEquals(mockResponse, (result as Results.Success).data)
    }


    @Test
    fun `fetchAllData returns failure on local data error`() = runBlocking {
        // Given
        repository.useApiData = false

        // Mock the getJsonDataFromAsset method to return an empty string
        doReturn("").`when`(repository).getJsonDataFromAsset("home.json", mockContext)

        // Act
        val result = repository.fetchAllData(emptyMap(), Any())

        // Then
        assertTrue(result is Results.Failure)
        assertEquals("Failed to fetch data from local storage.", (result as Results.Failure).title)
    }

}