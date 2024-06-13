package com.example.task1.data.items

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.task1.data.models.Items
import com.example.task1.data.repostory.Repository
import com.example.task1.ui.dashboard.DashBoardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ItemsTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<List<Items>>

    @Mock
    private lateinit var context: Context

    private lateinit var viewModel: DashBoardViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Ensure the application context is mocked
        Mockito.`when`(context.applicationContext).thenReturn(context)

        // Initialize the ViewModel
        viewModel = DashBoardViewModel(context)

        // Set the mock repository
        viewModel.updateRepository(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test

    fun fetchProductsFromApi() = testScope.runTest {
        val productList = listOf(
            Items(
                tenantUid = "tenant1",
                operationUid = "op1",
                id = "id1",
                thirdPartyUid = "third1",
                category = "cat1",
                customizationItems = emptyList(), // Replace with actual customization items if known
                recommendationTags = emptyList(), // Replace with actual recommendation tags if known
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
                maxQty = 5, // Match the intended max quantity
                minQty = 1,
                price = 10.0,
                isPriceOverridden = false,
                vatFree = false,
                requiresLegalAge = false,
                ratingEnabled = true,
                hasSpecialInstructions = false,
                updatedOn = System.currentTimeMillis(),
                createdOn = System.currentTimeMillis() - 86400000, // Created 1 day ago
                Qty = 10, // Initial quantity
                discount = 0
            )
        )
        Mockito.`when`(repository.getItems()).thenReturn(productList)

        // Observe LiveData
        val liveData = viewModel.fetchItems()
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        Mockito.verify(observer).onChanged(productList)
        Mockito.verify(repository).getItems()
    }


    @Test

    fun fetchProductsFromAPIError() = testScope.runTest {
        Mockito.`when`(repository.getItems()).thenThrow(RuntimeException("Network Error"))
        // Observe LiveData
        val liveData = viewModel.fetchItems()
        val observer = mock<Observer<List<Items>>>()
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        verify(observer).onChanged((emptyList()))
        Mockito.verify(repository).getItems()
    }


    @Test

    fun fetchProductsFromLocalAssets() = testScope.runTest {
        val productList = listOf(
            Items(
                tenantUid = "tenant1",
                operationUid = "op1",
                id = "id1",
                thirdPartyUid = "third1",
                category = "cat1",
                customizationItems = emptyList(), // Replace with actual customization items if known
                recommendationTags = emptyList(), // Replace with actual recommendation tags if known
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
                maxQty = 5, // Match the intended max quantity
                minQty = 1,
                price = 10.0,
                isPriceOverridden = false,
                vatFree = false,
                requiresLegalAge = false,
                ratingEnabled = true,
                hasSpecialInstructions = false,
                updatedOn = System.currentTimeMillis(),
                createdOn = System.currentTimeMillis() - 86400000, // Created 1 day ago
                Qty = 10, // Initial quantity
                discount = 0
            )
        )

        Mockito.`when`(repository.getItems()).thenReturn(productList)
        // Observe LiveData
        val liveData = viewModel.fetchItems()
        liveData.observeForever(observer)
        repository.useApiData = false
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        Mockito.verify(observer).onChanged(productList)
        Mockito.verify(repository).getItems()
    }

    @Test

    fun fetchProductsFromLocalAssetsError() = testScope.runTest {
        Mockito.`when`(repository.getItems()).thenThrow(RuntimeException("Error reading local assets"))
        // Observe LiveData
        val liveData = viewModel.fetchItems()
        liveData.observeForever(observer)
        repository.useApiData = false
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        verify(observer).onChanged(emptyList())
        Mockito.verify(repository).getItems()
    }
}

