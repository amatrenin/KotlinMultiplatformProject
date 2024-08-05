package com.example.kotlinmultiplatformproject.settings

import com.example.kotlinmultiplatformproject.base.BaseViewModel
import com.example.kotlinmultiplatformproject.categories.CategoriesRepository
import com.example.kotlinmultiplatformproject.categories.model.CategoryApi
import com.example.kotlinmultiplatformproject.categories.model.toApi
import com.example.kotlinmultiplatformproject.categories.model.toEntity
import com.example.kotlinmultiplatformproject.events.EventsRepository
import com.example.kotlinmultiplatformproject.events.model.SpendEventApi
import com.example.kotlinmultiplatformproject.events.model.toApi
import com.example.kotlinmultiplatformproject.events.model.toEntity
import com.example.kotlinmultiplatformproject.extensions.appLog
import com.example.kotlinmultiplatformproject.network.AppApi
import com.example.kotlinmultiplatformproject.platform.DeviceInfo
import com.example.kotlinmultiplatformproject.settings.SettingsContract.Event
import com.example.kotlinmultiplatformproject.settings.SettingsContract.State
import com.example.kotlinmultiplatformproject.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val deviceInfo: DeviceInfo,
    private val settingsManager: SettingsManager,
    private val categoriesRepository: CategoriesRepository,
    private val eventsRepository: EventsRepository,
    private val api: AppApi
) : BaseViewModel<State, Event>() {

    init {
        bindToEmail()
        bindToTheme()
        bindToToken()
        initDeviceInfo()
    }

    fun switchTheme(isDark: Boolean) {
        settingsManager.themeIsDark = isDark
    }

    fun sync() = viewModelScope.launch(
        CoroutineExceptionHandler { _, t ->
            appLog(t.stackTraceToString())
            pushEvent(Event.Error(t.message.orEmpty()))
        }
    ) {
        updateState { copy(isLoading = true) }
        delay(3000)
        syncCategories()
        syncEvens()
        pushEvent(Event.DataSynced)
        updateState { copy(isLoading = false) }
    }

    fun logout() {
        settingsManager.email = ""
        settingsManager.token = ""
    }

    //*********** region private *************

    private suspend fun syncCategories() {
        val apiCategories = categoriesRepository.getAll().map { it.toApi() }
        val categoriesSyncResponse = api.syncCategories(apiCategories)
        if (categoriesSyncResponse.status.isSuccess()) {
            val remoteCategories = categoriesSyncResponse.body<List<CategoryApi>>()
            categoriesRepository.insertAll(remoteCategories.map(CategoryApi::toEntity))
        }
    }

    private suspend fun syncEvens() {
        val apiEvents = eventsRepository.getAll().map { it.toApi() }
        val eventsSyncResponse = api.syncEvents(apiEvents)
        if (eventsSyncResponse.status.isSuccess()) {
            val remoteEvents = eventsSyncResponse.body<List<SpendEventApi>>()
            eventsRepository.insertAll(remoteEvents.map { it.toEntity() })
        }
    }

    private fun bindToEmail() {
        settingsManager.emailFlow.onEach { email ->
            updateState { copy(email = email) }
        }.launchIn(viewModelScope)
    }

    private fun bindToToken() {
        settingsManager.tokenFlow.onEach { token ->
            updateState { copy(isAuth = token.isNotBlank()) }
        }.launchIn(viewModelScope)
    }

    private fun initDeviceInfo() {
        updateState {
            copy(info = deviceInfo.getSummary())
        }
    }

    private fun bindToTheme() {
        settingsManager.themeIsDarkFlow.onEach {
            updateState { copy(themeIsDark = it) }
        }.launchIn(viewModelScope)
    }

    override fun initialState() = State.NONE

}