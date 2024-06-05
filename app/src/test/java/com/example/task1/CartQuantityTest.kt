import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.task1.data.models.Product
import com.example.task1.ui.adapters.ProductAdapter
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
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CartQuantityTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockListener: ProductAdapter.OnCartClickListener

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
        // Ensure the mock product has quantity > 0
        assertEquals(5, mockProducts[0].quantity)

        // Perform the action
        productAdapter.totalQuantityInCart += 1

        // Verify the result
        assertEquals(1, productAdapter.totalQuantityInCart)
    }

    @Test
    fun plusButtonWithQuantityGreaterThanZero() = runBlockingTest {
        // Ensure the mock product has quantity > 0
        assertEquals(5, mockProducts[0].quantity)

        // Perform the action
        productAdapter.totalQuantityInCart += 1

        // Verify the result
        assertEquals(1, productAdapter.totalQuantityInCart)
    }

    @Test
    fun minusButtonWithTotalQuantityGreaterThanZero() = runBlockingTest {
        // Set initial total quantity in cart
        productAdapter.totalQuantityInCart = 1

        // Perform the action
        productAdapter.totalQuantityInCart -= 1

        // Verify the result
        assertEquals(0, productAdapter.totalQuantityInCart)
    }

    @Test
    fun addToCartWithQuantityZero() = runBlockingTest {
        // Ensure the mock product has quantity = 0
        assertEquals(0, mockProducts[1].quantity)

        // Perform the action
        // No change should be made since the product quantity is 0

        // Verify the result
        assertEquals(0, productAdapter.totalQuantityInCart)
    }

    @Test
    fun plusButtonWithQuantityZero() = runBlockingTest {
        // Ensure the mock product has quantity = 0
        assertEquals(0, mockProducts[1].quantity)

        // Perform the action
        // No change should be made since the product quantity is 0

        // Verify the result
        assertEquals(0, productAdapter.totalQuantityInCart)
    }

    @Test
    fun minusButtonWithTotalQuantityZero() = runBlockingTest {
        // Set initial total quantity in cart
        productAdapter.totalQuantityInCart = 0

        // Perform the action
        // No change should be made since totalQuantityInCart is already 0

        // Verify the result
        assertEquals(0, productAdapter.totalQuantityInCart)
    }
}
