package com.example.kotlinmultiplatformproject.events.model

import com.example.kotlinmultiplatformproject.categories.model.Category
import com.example.kotlinmultiplatformproject.common.ui.calendar.model.CalendarLabel
import com.example.kotlinmultiplatformproject.extensions.now
import db.events.EventsDB
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class SpendEvent(
    val id: String,
    val categoryId: String,
    val title: String,
    val cost: Double,
    val date: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val note: String
) {
    companion object {
        val NONE = SpendEvent(
            id = "",
            categoryId = "",
            title = "",
            cost = 0.0,
            date = LocalDate.now(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            note = ""
        )

        fun getStubs() = List(20) { index ->
            NONE.copy(
                id = index.toString(),
                title = "event $index",
                date = Clock.System.now()
                    .plus(index, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
            )
        }
    }
}

fun SpendEvent.toUI(category: Category) = SpendEventUI(
    id = id,
    category = category,
    title = title,
    cost = cost
)

fun SpendEvent.toCalendarLabel(category: Category) = CalendarLabel(
    id = id,
    colorHex = category.colorHex,
    localDate = date
)

fun SpendEvent.toDb() = EventsDB(
    id = id,
    categoryId = categoryId,
    title = title,
    cost = cost,
    date = date,
    createAt = createdAt,
    updateAt = updatedAt,
    note = note
)

fun EventsDB.toEntity() = SpendEvent (
    id = id,
    categoryId = categoryId,
    title = title.orEmpty(),
    cost = cost ?: 0.0,
    date = date,
    createdAt = createAt,
    updatedAt = updateAt,
    note = note.orEmpty()
)

fun SpendEvent.toApi() = SpendEventApi(
    id = id,
    categoryId = categoryId,
    title = title,
    cost = cost,
    date = date,
    createdAt = createdAt,
    updatedAt = updatedAt,
    note = note
)

fun SpendEventApi.toEntity() = SpendEvent(
    id = id.orEmpty(),
    categoryId = categoryId.orEmpty(),
    title = title.orEmpty(),
    cost = cost ?: 0.0,
    date = date ?: LocalDateTime.now().date,
    createdAt = createdAt ?: LocalDateTime.now(),
    updatedAt = updatedAt ?: LocalDateTime.now(),
    note = note.orEmpty()
)