package com.example.task1.data

import DashboardViewModel
import Repository
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.data.models.ServicesListResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // To run LiveData synchronously

    private lateinit var viewModel: DashboardViewModel

    @Mock
    private lateinit var repository: Repository
@Mock
private lateinit var context : Context
    @Mock
    private lateinit var observer: Observer<Results<ServicesListResponse>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined) // Set the main dispatcher for coroutines
        viewModel = DashboardViewModel(context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher
    }

    @Test
    fun fetchAllDataSuccessFromApi() = runTest {
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
        val banners = listOf(Banner("1", "https://test.com/banner.jpg"))
        val items = listOf(
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
            )
        )

        val response = ServicesListResponse(banners, items)

        Mockito.`when`(repository.fetchAllData(mockHeaders, mockRequestBody))
            .thenReturn(Results.Success(response))

        // When
        viewModel.fetchAllData().observeForever(observer)

        // Then
        observer.onChanged(Results.Success(response))
        assertEquals(1, response.banners.size)
        assertEquals(1, response.items.size)
    }
    @Test
    fun fetchAllDataFailureFromAPI() = runTest {
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

        val errorMessage = "Failed to fetch data. Please try again later."

        Mockito.`when`(repository.fetchAllData(mockHeaders, mockRequestBody))
            .thenReturn(Results.Failure("HTTP Error", errorMessage))

        // When
        viewModel.fetchAllData().observeForever(observer)

        // Then
      observer.onChanged(Results.Failure("HTTP Error", errorMessage))
    }

    @Test
    fun fetchAllDataFromLocalAssetsSuccess() = runTest {
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
        val banners = listOf(Banner("1", "https://test.com/banner.jpg"))
        val items = listOf( Items(
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
        )
        )
        val response = ServicesListResponse(banners, items)

        Mockito.`when`(repository.fetchAllData(mockHeaders, mockRequestBody))
            .thenReturn(Results.Success(response))

        // When
        viewModel.fetchAllData().observeForever(observer)

        // Then
        observer.onChanged(Results.Success(response))
        assertEquals(1, response.banners.size)
        assertEquals(1, response.items.size)
    }
    @Test
    fun fetchAllDataFromLocalAssetsFailure() = runTest {
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
        val errorMessage = "Failed to fetch data from local storage."

        Mockito.`when`(repository.fetchAllData(mockHeaders, mockRequestBody))
            .thenReturn(Results.Failure("Data Error", errorMessage))

        // When
        viewModel.fetchAllData().observeForever(observer)

        // Then
      observer.onChanged(Results.Failure("Data Error", errorMessage))
    }

}
