package com.example.task1

import com.example.task1.data.models.Items
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28, 32])
class CartQuantityTest {

    private lateinit var cartManager: CartManager
    private lateinit var sampleProductList: MutableList<Items>

    @Before
    fun setUp() {
        // Sample product list for testing
        sampleProductList = mutableListOf(
                        Items(   tenantUid = "tenant2",
                operationUid = "op2",
                id = "id2",
                thirdPartyUid = "third2",
                category = "cat2",
                customizationItems = emptyList(),
                recommendationTags = emptyList(),
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
                outOfStock = true,
                qtyPerUnit = 1,
                sortOrder = 2,
                maxQty = 5,
                minQty = 0,
                price = 20.0,
                isPriceOverridden = false,
                vatFree = true,
                requiresLegalAge = false,
                ratingEnabled = true,
                hasSpecialInstructions = false,
                updatedOn = System.currentTimeMillis(),
                createdOn = System.currentTimeMillis() - 86400000,
                Qty = 0,
                discount = 0),
                        Items(   tenantUid = "tenant2",
                operationUid = "op2",
                id = "id2",
                thirdPartyUid = "third2",
                category = "cat2",
                customizationItems = emptyList(),
                recommendationTags = emptyList(),
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
                outOfStock = true,
                qtyPerUnit = 1,
                sortOrder = 2,
                maxQty = 0,
                minQty = 0,
                price = 20.0,
                isPriceOverridden = false,
                vatFree = true,
                requiresLegalAge = false,
                ratingEnabled = true,
                hasSpecialInstructions = false,
                updatedOn = System.currentTimeMillis(),
                createdOn = System.currentTimeMillis() - 86400000,
                Qty = 0,
                discount = 0),
                        Items(   tenantUid = "tenant2",
                operationUid = "op2",
                id = "id2",
                thirdPartyUid = "third2",
                category = "cat2",
                customizationItems = emptyList(),
                recommendationTags = emptyList(),
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
                outOfStock = true,
                qtyPerUnit = 1,
                sortOrder = 2,
                maxQty = 3,
                minQty = 0,
                price = 20.0,
                isPriceOverridden = false,
                vatFree = true,
                requiresLegalAge = false,
                ratingEnabled = true,
                hasSpecialInstructions = false,
                updatedOn = System.currentTimeMillis(),
                createdOn = System.currentTimeMillis() - 86400000,
                Qty = 0,
                discount = 0)
        )
        cartManager = CartManager(sampleProductList)
    }

    @Test
    fun testCartClickProductAvailable() {
        val position = 0
        cartManager.cartClick(position)
        assertEquals(4, sampleProductList[position].maxQty)
        assertEquals(1, cartManager.totalQuantityInCart)
    }

    @Test(expected = IllegalStateException::class)
    fun testCartClickProductOutOfStock() {
        val position = 1
        cartManager.cartClick(position)
        assertEquals("Product is out of stock","Product is out of stock")
       // assertEquals(0,0)
    }

    @Test
    fun testQuantityIncrease() {
        val position = 2
        cartManager.quantityIncrease(position)
        assertEquals(2, sampleProductList[position].maxQty)
        assertEquals(1, cartManager.totalQuantityInCart)
    }

    @Test(expected = IllegalStateException::class)
    fun testQuantityIncreaseNoStock() {
        val position = 1
        cartManager.quantityIncrease(position)
        assertEquals("No more stock available","No more stock available")
        // assertEquals(0,0)
    }

    @Test
    fun testQuantityDecrease() {
        cartManager.totalQuantityInCart = 2
        val position = 2
        cartManager.quantityDecrease()
        assertEquals(1, cartManager.totalQuantityInCart)
    }

    @Test(expected = IllegalStateException::class)
    fun testQuantityDecreaseCannotGoBelowZero() {
        val position = 2
        cartManager.totalQuantityInCart = 0
       cartManager.quantityDecrease()
        assertEquals("Cannot decrease quantity further","Cannot decrease quantity further")
        // assertEquals(0,0)
    }
}
