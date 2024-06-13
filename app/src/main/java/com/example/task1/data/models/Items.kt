package com.example.task1.data.models

data class Items
    (val tenantUid: String,
     val operationUid: String,
     val id: String,
     val thirdPartyUid: String,
     val category: String,
     val customizationItems: List<Any>, // You can replace Any with the actual type if known
     val recommendationTags: List<Any>, // You can replace Any with the actual type if known
     val legacyId: String,
     val legacyProductNumber: String,
     val stockThreshold: Int,
     val size: Int,
     val vatPercent: Double,
     val lastModified: Long,
     val brandName: String,
     val brandImage: String,
     val sku: String,
     val label: String,
     val description: String,
     val imageUrl: String,
     val thumbnailUrl: String,
     val unit: String,
     val itemViewType: String,
     val neverRecommend: Boolean,
     val recommendationLevel: Int,
     val group: String,
     val itemType: String,
     val weight: Double,
     val outOfStock: Boolean,
     var qtyPerUnit: Int,
     val sortOrder: Int,
     var maxQty: Int,
     val minQty: Int,
     val price: Double,
     val isPriceOverridden: Boolean,
     val vatFree: Boolean,
     val requiresLegalAge: Boolean,
     val ratingEnabled: Boolean,
     val hasSpecialInstructions: Boolean,
     val updatedOn: Long,
     val createdOn: Long,
     val Qty : Int,
     val discount : Int)



