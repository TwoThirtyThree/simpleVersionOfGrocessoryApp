
package com.example.task1.utils

import android.content.Context
import android.widget.TextView

import com.example.task1.data.models.Items
import com.example.task1.ui.adapters.ItemsAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CartManagerTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var cartQuantityTextView: TextView

    @Mock
    private lateinit var itemsAdapter: ItemsAdapter

    private lateinit var cartManager: CartManager
    private lateinit var itemsList: MutableList<Items>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        itemsList = mutableListOf(
            Items( tenantUid = "tenant1",
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
                discount = 0),
        Items( tenantUid = "tenant1",
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
            maxQty = 0,
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
            discount = 0  )
        )

        Mockito.`when`(itemsAdapter.productList).thenReturn(itemsList)

        cartManager = CartManager(context, itemsAdapter, cartQuantityTextView)
    }

    @Test
    fun testOnCartClick_Success() {
        cartManager.onCartClick(0)

        assertEquals(4, itemsList[0].maxQty)
        assertEquals(1, cartManager.totalQuantityInCart)

        Mockito.verify(itemsAdapter).notifyItemChanged(0)
        Mockito.verify(cartQuantityTextView).text = "1"
    }

    @Test(expected = IllegalStateException::class)
    fun testOnCartClick_OutOfStock() {
        cartManager.onCartClick(1)

        assertEquals(0, itemsList[1].maxQty)
        assertEquals(0, cartManager.totalQuantityInCart)
        assertEquals("Product is out of stock","Product is out of stock")
        }


    @Test
    fun testOnQuantityIncrease_Success() {
        cartManager.onQuantityIncrease(0)

        assertEquals(4, itemsList[0].maxQty)
        assertEquals(1, cartManager.totalQuantityInCart)

        Mockito.verify(itemsAdapter).notifyItemChanged(0)
        Mockito.verify(cartQuantityTextView).text = "1"
    }

    @Test(expected = IllegalStateException::class)
    fun testOnQuantityIncrease_NoMoreStock() {

        cartManager.onQuantityIncrease(1)

        assertEquals(0, itemsList[1].maxQty) // maxQty should still be 0
        assertEquals(0, cartManager.totalQuantityInCart)
        assertEquals("No more stock available","No more stock available")


    }

    @Test
    fun testOnQuantityDecrease_Success() {
        cartManager.totalQuantityInCart = 1
        itemsList[0].maxQty = 4

        cartManager.onQuantityDecrease(0)

        assertEquals(5, itemsList[0].maxQty)
        assertEquals(0, cartManager.totalQuantityInCart)


    }

        @Test(expected = IllegalStateException::class)
        fun testOnQuantityDecrease_NoEffectIfZero() {
            cartManager.totalQuantityInCart = 0
            itemsList[0].maxQty = 5
            cartManager.onQuantityDecrease(0)
        assertEquals("Cannot decrease quantity further","Cannot decrease quantity further")
        assertEquals(5, itemsList[0].maxQty)
        assertEquals(0, cartManager.totalQuantityInCart)


    }
}
