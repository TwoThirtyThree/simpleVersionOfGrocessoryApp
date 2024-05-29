package com.example.task1.data.models

data class Product
    (
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    var quantity :Int,
    var discount: Int)


