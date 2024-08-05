package com.example.kotlinmultiplatformproject.storage

import app.cash.sqldelight.ColumnAdapter
import db.categories.CategoryDB
import db.events.EventsDB
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime

object DbAdapters {
    val categoryDbAdapter = CategoryDB.Adapter(
        LocalDateTimeAdapter, LocalDateTimeAdapter
    )
    val eventsDbAdapter = EventsDB.Adapter(
        LocalDateAdapter, LocalDateTimeAdapter, LocalDateTimeAdapter
    )
}

object LocalDateTimeAdapter: ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String) = databaseValue.toLocalDateTime()

    override fun encode(value: LocalDateTime): String = value.toString()

}
object LocalDateAdapter: ColumnAdapter<LocalDate, String> {
    override fun decode(databaseValue: String) = databaseValue.toLocalDate()

    override fun encode(value: LocalDate): String = value.toString()

}