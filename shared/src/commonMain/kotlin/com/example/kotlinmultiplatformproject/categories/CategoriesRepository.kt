package com.example.kotlinmultiplatformproject.categories

import com.example.kotlinmultiplatformproject.categories.model.Category
import com.example.kotlinmultiplatformproject.categories.model.CategoryDao
import com.example.kotlinmultiplatformproject.extensions.appLog
import kotlinx.coroutines.flow.flow


class CategoriesRepository(
    private val dao: CategoryDao
) {

    fun getAllFlow() = dao.getAllFlow()

    suspend fun getAll() = dao.getAll()

    suspend fun insertAll(categories: List<Category>) = dao.insertAll(categories)

    suspend fun create(category: Category) = dao.insert(category)
}