package com.example.kotlinmultiplatformproject.categories.create

import com.example.kotlinmultiplatformproject.categories.model.Category
import com.example.kotlinmultiplatformproject.platform.randomUUID
import kotlinx.datetime.LocalDateTime

data class CreateCategoryData(
    val title: String,
    val subtitle: String,
    val colorHex: String
)

fun CreateCategoryData.toCategory(dateTime: LocalDateTime) = Category(
    id = randomUUID(),
    title = title,
    description = subtitle,
    colorHex = colorHex,
    createdAt = dateTime,
    updatedAt = dateTime
)