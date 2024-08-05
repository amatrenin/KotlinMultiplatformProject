package com.example.kotlinmultiplatformproject.categories.model

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.kotlinmultiplatformproject.db.AppDb
import db.categories.CategoryDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class CategoryDao(
    private val db: AppDb,
    private val coroutineContext: CoroutineContext
) {

    private val categoriesQueries = db.categoryDBQueries

    fun getAll(): List<Category> =
        categoriesQueries
            .getAll()
            .executeAsList()
            .map{ it.toEntity() }

    fun getAllFlow(): Flow<List<Category>> =
        categoriesQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineContext)
            .map { categories -> categories.map(CategoryDB::toEntity) }

    suspend fun insert(category: Category) = categoriesQueries.insert(category.toDB())

    suspend fun insertAll(categories: List<Category>) =
        categoriesQueries.transaction {
            categories.forEach { categoriesQueries.insert(it.toDB()) }
        }

    suspend fun delete(id: String) = categoriesQueries.delete(id)
}