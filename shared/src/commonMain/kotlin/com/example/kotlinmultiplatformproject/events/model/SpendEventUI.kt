package com.example.kotlinmultiplatformproject.events.model

import com.example.kotlinmultiplatformproject.categories.model.Category

data class SpendEventUI(
    val id: String,
    val category: Category,
    val title: String,
    val cost: Double
)
