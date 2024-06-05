import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.example.task1.data.models.Product
import com.example.task1.ui.adapters.ProductAdapter
import com.example.task1.ui.dashboard.DashBoardViewModel
import com.example.task1.ui.dashboard.DashboardActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class QT{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockListener: ProductAdapter.OnCartClickListener

    @Mock
    lateinit var mockViewHolder: ProductAdapter.ProductViewHolder

    lateinit var productAdapter: ProductAdapter

    private val testDispatcher = TestCoroutineDispatcher()

    private val mockProducts = listOf(
        Product(1, "Product 1", "Description 1", 10.0, "", 5, 10),
        Product(2, "Product 2", "Description 2", 20.0, "", 0, 0)
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        productAdapter = ProductAdapter(mockProducts, mockListener)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun addToCartWithQuantityGreaterThanZero() = runBlockingTest {
        `when`(mockViewHolder.adapterPosition).thenReturn(0)
        `when`(mockProducts[0].quantity).thenReturn(5)

        productAdapter.listener.CartClick(mockViewHolder.adapterPosition)

        assertEquals(1, productAdapter.totalQuantityInCart)
    }

    @Test
    fun plusButtonWithQuantityGreaterThanZero() = runBlockingTest {
        `when`(mockViewHolder.adapterPosition).thenReturn(0)
        `when`(mockProducts[0].quantity).thenReturn(5)

        productAdapter.listener.QuantityIncrease(mockViewHolder.adapterPosition)

        assertEquals(1, productAdapter.totalQuantityInCart)
    }

    @Test
    fun minusButtonWithTotalQuantityGreaterThanZero() = runBlockingTest {
        productAdapter.totalQuantityInCart = 1

        productAdapter.listener.QuantityDecrease(mockViewHolder.adapterPosition)

        assertEquals(0, productAdapter.totalQuantityInCart)
    }

    @Test
    fun addToCartWithQuantityZero() = runBlockingTest {
        `when`(mockViewHolder.adapterPosition).thenReturn(1)
        `when`(mockProducts[1].quantity).thenReturn(0)

        productAdapter.listener.CartClick(mockViewHolder.adapterPosition)

        assertEquals(0, productAdapter.totalQuantityInCart)
    }

    @Test
    fun plusButtonWithQuantityZero() = runBlockingTest {
        `when`(mockViewHolder.adapterPosition).thenReturn(1)
        `when`(mockProducts[1].quantity).thenReturn(0)

        productAdapter.listener.QuantityIncrease(mockViewHolder.adapterPosition)

        assertEquals(0, productAdapter.totalQuantityInCart)
    }

    @Test
    fun minusButtonWithTotalQuantityZero() = runBlockingTest {
        productAdapter.totalQuantityInCart = 0

        productAdapter.listener.QuantityDecrease(mockViewHolder.adapterPosition)

        assertEquals(0, productAdapter.totalQuantityInCart)
    }
}
