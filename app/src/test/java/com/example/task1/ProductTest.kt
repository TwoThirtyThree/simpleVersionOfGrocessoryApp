package com.example.task1

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Product
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<List<Product>>

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

    fun `fetchProducts should return a list of product from API`() = testScope.runTest {
        // Given
        val productList = listOf(Product(id = 1, imageUrl = "url1",name = "fahad", description = "xyz", price = 56.0, quantity = 3, discount = 67))
        Mockito.`when`(repository.getProducts()).thenReturn(productList as List<Product>?)

        // Observe LiveData
        val liveData = viewModel.fetchProducts()
        liveData.observeForever(observer)

        // When
        advanceUntilIdle()

        // Then
        Mockito.verify(observer).onChanged(productList)
        Mockito.verify(repository).getBanners()
    }

    @Test

    fun `fetchProducts should handle API error`() = testScope.runTest {
        // Given
        Mockito.`when`(repository.getProducts()).thenThrow(RuntimeException("Network Error"))

        // Observe LiveData
        val liveData = viewModel.fetchProducts()
        val observer = mock<Observer<List<Product>>>()
        liveData.observeForever(observer)

        // When
        advanceUntilIdle()

        // Then
        // verify(observer).onChanged(emptyList())
        Mockito.verify(repository).getProducts()
    }


    @Test

    fun `fetchProducts should return a list of products from local assets`() = testScope.runTest {
        // Given
        val productList = listOf(Product(id = 1, imageUrl = "url1",name = "fahad", description = "xyz", price = 56.0, quantity = 3, discount = 67))
        Mockito.`when`(repository.getProducts()).thenReturn(productList)

        // Observe LiveData
        val liveData = viewModel.fetchProducts()
        liveData.observeForever(observer)

        // When
        repository.useApiData = false
        advanceUntilIdle()

        // Then
        Mockito.verify(observer).onChanged(productList)
        Mockito.verify(repository).getProducts()
    }

    @Test

    fun `fetchProducts should handle local assets error`() = testScope.runTest {
        // Given
        Mockito.`when`(repository.getProducts()).thenThrow(RuntimeException("Error reading local assets"))

        // Observe LiveData
        val liveData = viewModel.fetchProducts()
        liveData.observeForever(observer)

        // When
        repository.useApiData = false
        advanceUntilIdle()

        // Then
        // verify(observer).onChanged(emptyList())
        Mockito.verify(repository).getProducts()
    }
}

