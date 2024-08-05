package com.example.kotlinmultiplatformproject.categories.list

import com.example.kotlinmultiplatformproject.base.BaseViewModel
import com.example.kotlinmultiplatformproject.base.BaseViewState
import com.example.kotlinmultiplatformproject.categories.CategoriesRepository
import com.example.kotlinmultiplatformproject.categories.create.CreateCategoryData
import com.example.kotlinmultiplatformproject.categories.create.toCategory
import com.example.kotlinmultiplatformproject.categories.list.CategoriesViewModel.*
import com.example.kotlinmultiplatformproject.categories.model.Category
import com.example.kotlinmultiplatformproject.extensions.now
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class CategoriesViewModel (
    private val repository: CategoriesRepository
) : BaseViewModel<State, Nothing>(){

    override fun initialState() = State.NONE

    init {
        activate()
    }

    private fun activate(){
        repository.getAllFlow().onEach {
            updateState { copy(categoties = it) }
        }.launchIn(viewModelScope)
    }

    fun createCategory(data: CreateCategoryData){
        val now = LocalDateTime.now()
        val category = data.toCategory(now)
        viewModelScope.launch {
            repository.create(category)
        }
    }

    data class State(
        val categoties: List<Category>
    ) : BaseViewState {

        companion object {
            val NONE = State(emptyList())
        }
    }
}