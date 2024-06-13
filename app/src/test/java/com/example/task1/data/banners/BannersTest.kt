package com.example.task1.data.banners

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.task1.data.models.Banner
import com.example.task1.data.repostory.Repository
import com.example.task1.ui.dashboard.DashBoardViewModel
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
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BannersTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<List<Banner>>

    @Mock
    private lateinit var context: Context

    private lateinit var viewModel: DashBoardViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Ensure the application context is mocked
        `when`(context.applicationContext).thenReturn(context)

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
    fun fetchBannersFromAPI() = testScope.runTest {
        val bannerList = listOf(Banner(id = "1", imageUrl = "url1"), Banner(id = "2", imageUrl = "url2"))
        `when`(repository.getBanners()).thenReturn(bannerList)
        // Observe LiveData
        val liveData = viewModel.fetchBanners()
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        verify(observer).onChanged(bannerList)
        verify(repository).getBanners()
    }

    @Test
    fun fetchBannersFromAPIError() = testScope.runTest {
        `when`(repository.getBanners()).thenThrow(RuntimeException("Network Error"))
        // Observe LiveData
        val liveData = viewModel.fetchBanners()
        val observer = mock<Observer<List<Banner>>>()
        liveData.observeForever(observer)
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        verify(observer).onChanged(emptyList())
        verify(repository).getBanners()
    }


    @Test
    fun fetchBannersFromLocalAssets() = testScope.runTest {
        val bannerList = listOf(Banner(id = "1", imageUrl = "url1"), Banner(id = "2", imageUrl = "url2"))
        `when`(repository.getBanners()).thenReturn(bannerList)
        // Observe LiveData
        val liveData = viewModel.fetchBanners()
        liveData.observeForever(observer)
        repository.useApiData = false
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        verify(observer).onChanged(bannerList)
        verify(repository).getBanners()
    }

    @Test
    fun fetchBannersFromLocalAssetsError() = testScope.runTest {
        `when`(repository.getBanners()).thenThrow(RuntimeException("Error reading local assets"))
        // Observe LiveData
        val liveData = viewModel.fetchBanners()
        liveData.observeForever(observer)
        repository.useApiData = false
        // Ensure all coroutines and tasks are completed
        advanceUntilIdle()
        verify(observer).onChanged(emptyList())
        verify(repository).getBanners()
    }
}
