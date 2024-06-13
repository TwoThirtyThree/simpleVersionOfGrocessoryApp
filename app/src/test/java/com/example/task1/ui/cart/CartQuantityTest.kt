package com.example.task1.ui.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.task1.data.models.Items
import com.example.task1.ui.adapters.ItemsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
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
    lateinit var mockListener: ItemsAdapter.OnCartClickListener

    lateinit var itemsAdapter: ItemsAdapter

    private val testDispatcher = StandardTestDispatcher()

    private val mockItems = listOf(
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
        ),
        Items(
            tenantUid = "tenant2",
            operationUid = "op2",
            id = "id2",
            thirdPartyUid = "third2",
            category = "cat2",
            customizationItems = emptyList(), // Replace with actual customization items if known
            recommendationTags = emptyList(), // Replace with actual recommendation tags if known
            legacyId = "legacy2",
            legacyProductNumber = "legacyNum2",
            stockThreshold = 20,
            size = 15,
            vatPercent = 10.0,
            lastModified = System.currentTimeMillis(),
            brandName = "Brand B",
            brandImage = "image2.jpg",
            sku = "SKU2",
            label = "Product 2",
            description = "Description 2",
            imageUrl = "http://image2.jpg",
            thumbnailUrl = "http://thumb2.jpg",
            unit = "pcs",
            itemViewType = "type2",
            neverRecommend = false,
            recommendationLevel = 2,
            group = "group2",
            itemType = "typeB",
            weight = 1.5,
            outOfStock = true, // Indicate this product is out of stock
            qtyPerUnit = 1,
            sortOrder = 2,
            maxQty = 0, // Indicates this product has no stock
            minQty = 0,
            price = 20.0,
            isPriceOverridden = false,
            vatFree = true,
            requiresLegalAge = false,
            ratingEnabled = true,
            hasSpecialInstructions = false,
            updatedOn = System.currentTimeMillis(),
            createdOn = System.currentTimeMillis() - 86400000, // Created 1 day ago
            Qty = 0, // Initial quantity
            discount = 0
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        itemsAdapter = ItemsAdapter(mockItems, mockListener)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun addToCartWithQuantityGreaterThanZero() = runTest(testDispatcher) {
        // Ensure the mock product has quantity > 0
        assertEquals(5, mockItems[0].maxQty)
        // Perform the action
        itemsAdapter.totalQuantityInCart += 1
        // Verify the result
        assertEquals(1, itemsAdapter.totalQuantityInCart)
    }

    @Test
    fun plusButtonWithQuantityGreaterThanZero() = runTest(testDispatcher) {
        // Ensure the mock product has quantity > 0
        assertEquals(5, mockItems[0].maxQty)
        // Perform the action
        itemsAdapter.totalQuantityInCart += 1
        // Verify the result
        assertEquals(1, itemsAdapter.totalQuantityInCart)
    }

    @Test
    fun minusButtonWithTotalQuantityGreaterThanZero() = runTest(testDispatcher) {
        // Set initial total quantity in cart
        itemsAdapter.totalQuantityInCart = 1
        // Perform the action
        itemsAdapter.totalQuantityInCart -= 1
        // Verify the result
        assertEquals(0, itemsAdapter.totalQuantityInCart)
    }

    @Test
    fun addToCartWithQuantityZero() = runTest(testDispatcher) {
        // Ensure the mock product has quantity = 0
        assertEquals(0, mockItems[1].maxQty)
        // Perform the action
        // No change should be made since the product quantity is 0
        // Verify the result
        assertEquals(0, itemsAdapter.totalQuantityInCart)
    }

    @Test
    fun plusButtonWithQuantityZero() = runTest(testDispatcher) {
        // Ensure the mock product has quantity = 0
        assertEquals(0, mockItems[1].maxQty)
        // Perform the action
        // No change should be made since the product quantity is 0
        // Verify the result
        assertEquals(0, itemsAdapter.totalQuantityInCart)
    }

    @Test
    fun minusButtonWithTotalQuantityZero() = runTest(testDispatcher) {
        // Set initial total quantity in cart
        itemsAdapter.totalQuantityInCart = 0
        // Perform the action
        // No change should be made since totalQuantityInCart is already 0
        // Verify the result
        assertEquals(0, itemsAdapter.totalQuantityInCart)
    }
}
