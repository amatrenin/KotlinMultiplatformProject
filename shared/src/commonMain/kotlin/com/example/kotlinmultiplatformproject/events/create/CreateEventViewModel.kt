package com.example.kotlinmultiplatformproject.events.create

import com.example.kotlinmultiplatformproject.base.BaseEvent
import com.example.kotlinmultiplatformproject.base.BaseViewModel
import com.example.kotlinmultiplatformproject.base.BaseViewState
import com.example.kotlinmultiplatformproject.categories.model.Category
import com.example.kotlinmultiplatformproject.events.create.CreateEventViewModel.Event
import com.example.kotlinmultiplatformproject.events.create.CreateEventViewModel.State
import com.example.kotlinmultiplatformproject.events.model.SpendEvent
import com.example.kotlinmultiplatformproject.extensions.now
import com.example.kotlinmultiplatformproject.platform.randomUUID
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class CreateEventViewModel : BaseViewModel<State, Event>() {

    override fun initialState() = State.NONE

    fun selectDate(date: LocalDate?) = updateState { copy(date = date ?: LocalDate.now()) }
    fun resetState() = updateState { State.NONE }
    fun changeTitle(title: String) = updateState { copy(title = title) }
    fun changeNote(note: String) = updateState { copy(note = note) }
    fun changeCost(cost: String) = updateState { copy(cost = cost.toDoubleOrNull() ?: this.cost) }
    fun selectCategory(category: Category) = updateState { copy(category = category) }

    fun finish() {
        val spendEvent = with(state.value){
            val now = LocalDateTime.now()
            SpendEvent(
                id = randomUUID(),
                title = title,
                cost = cost,
                date = date,
                categoryId = category.id,
                createdAt = now,
                updatedAt = now,
                note = note
            )
        }
        resetState()
        pushEvent(Event.Finish(spendEvent))
    }


    data class State(
        val title: String,
        val category: Category,
        val date: LocalDate,
        val cost: Double,
        val note: String
    ) : BaseViewState {
        companion object {
            val NONE = State(
                title = "",
                category = Category.NONE,
                date = LocalDate.now(),
                cost = 0.0,
                note = ""
            )
        }
    }

    sealed interface Event : BaseEvent {
        data class Finish(val spendEvent: SpendEvent) : Event
    }


}