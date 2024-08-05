package com.example.kotlinmultiplatformproject.events.list

import com.example.kotlinmultiplatformproject.base.BaseViewModel
import com.example.kotlinmultiplatformproject.base.BaseViewState
import com.example.kotlinmultiplatformproject.categories.CategoriesRepository
import com.example.kotlinmultiplatformproject.categories.model.Category
import com.example.kotlinmultiplatformproject.common.ui.calendar.model.CalendarDay
import com.example.kotlinmultiplatformproject.common.ui.calendar.model.CalendarLabel
import com.example.kotlinmultiplatformproject.events.EventsRepository
import com.example.kotlinmultiplatformproject.events.list.EventsViewModel.*
import com.example.kotlinmultiplatformproject.events.model.SpendEvent
import com.example.kotlinmultiplatformproject.events.model.SpendEventUI
import com.example.kotlinmultiplatformproject.events.model.toCalendarLabel
import com.example.kotlinmultiplatformproject.events.model.toUI
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class EventsViewModel(
    private val eventsRepository: EventsRepository,
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<State, Nothing>() {

    override fun initialState() = State.NONE

    init {
        activate()
    }

    fun selectDay(day: CalendarDay) {
        updateState { copy(selectedDay = day) }
    }

    fun createEvent(newEvent: SpendEvent) {
        viewModelScope.launch { eventsRepository.create(newEvent) }
    }

    private fun activate() {
        combine(
            eventsRepository.getAllFlow(),
            categoriesRepository.getAllFlow()
        ) { events, categories ->
            val labels = mapEventsCategoriesToLabels(events, categories)
            updateState {
                copy(
                    events = events,
                    categories = categories,
                    calendarLabels = labels
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun mapEventsCategoriesToLabels(
        events: List<SpendEvent>,
        categories: List<Category>
    ): List<CalendarLabel> {
        return events.map { event ->
            val category = categories.firstOrNull {
                it.id == event.categoryId
            } ?: Category.NONE
            event.toCalendarLabel(category)
        }
    }
    data class State(
        val selectedDay: CalendarDay?,
        val events: List<SpendEvent>,
        val categories: List<Category>,
        val calendarLabels: List<CalendarLabel>
    ) : BaseViewState {

        val eventsByDay: List<SpendEventUI>
            get() = events.filter { it.date == selectedDay?.date }
                .map { spendEvent ->
                    spendEvent.toUI(
                        categories.firstOrNull { it.id == spendEvent.categoryId } ?: Category.NONE
                    )
                }

        companion object {
            val NONE = State(
                selectedDay = null,
                events = emptyList(),
                categories = emptyList(),
                calendarLabels = emptyList()
            )
        }
    }
}







