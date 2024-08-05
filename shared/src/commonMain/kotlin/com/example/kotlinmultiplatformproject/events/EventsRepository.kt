package com.example.kotlinmultiplatformproject.events

import com.example.kotlinmultiplatformproject.events.model.SpendEvent
import com.example.kotlinmultiplatformproject.events.model.SpendEventDao
import com.example.kotlinmultiplatformproject.extensions.appLog
import kotlinx.coroutines.flow.flow

class EventsRepository(
    private val dao: SpendEventDao
) {

    fun getAllFlow() = dao.getAllFlow()

    suspend fun getAll() = dao.getAll()

    suspend fun insertAll(events: List<SpendEvent>) = dao.insertAll(events)

    suspend fun create(spendEvent: SpendEvent) = dao.insert(spendEvent)
}