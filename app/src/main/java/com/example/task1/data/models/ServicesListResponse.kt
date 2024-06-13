package com.example.task1.data.models

import com.example.task1.data.models.Banner

import com.example.task1.data.models.Items

data class ServicesListResponse(
    val banners: List<Banner>,
    val items: List<Items>
)
