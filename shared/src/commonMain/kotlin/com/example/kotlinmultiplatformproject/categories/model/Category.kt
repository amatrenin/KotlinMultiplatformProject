package com.example.kotlinmultiplatformproject.categories.model

import com.example.kotlinmultiplatformproject.extensions.now
import db.categories.CategoryDB
import kotlinx.datetime.LocalDateTime

data class Category(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val colorHex: String
) {
    companion object {
        val NONE = Category(
            id = "NONE_CATEGORY",
            title = "",
            description = "",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            colorHex = ""
        )

        fun getStubs() = List(20) { index ->
            NONE.copy(id = index.toString(), title = "category $index")
        }
    }
}

fun CategoryDB.toEntity() = Category(
    id = id,
    title = title.orEmpty(),
    description = description.orEmpty(),
    createdAt = createdAt,
    updatedAt = updateAt,
    colorHex = colorHex,
)

fun Category.toDB() = CategoryDB(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updateAt = updatedAt,
    colorHex = colorHex,
)

fun Category.toApi() = CategoryApi(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    colorHex = colorHex,
)

fun CategoryApi.toEntity() = Category(
    id = id.orEmpty(),
    title = title.orEmpty(),
    description = description.orEmpty(),
    createdAt = createdAt ?: LocalDateTime.now(),
    updatedAt = updatedAt ?: LocalDateTime.now(),
    colorHex = colorHex.orEmpty(),
)
