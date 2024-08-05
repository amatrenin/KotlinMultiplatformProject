package com.example.kotlinmultiplatformproject.root

import com.example.kotlinmultiplatformproject.base.BaseViewModel
import com.example.kotlinmultiplatformproject.root.model.AppTab
import com.example.kotlinmultiplatformproject.root.model.RootContract
import com.example.kotlinmultiplatformproject.storage.SettingsManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RootViewModel(
    private val settingsManager: SettingsManager
): BaseViewModel<RootContract.State, Nothing>() {

    init {
        settingsManager.themeIsDarkFlow.onEach { isDark ->
            updateState { copy(themeIsDark = isDark) }
        }.launchIn(viewModelScope)
    }

    override fun initialState() = RootContract.State.NONE
    fun hundleClickOnTab(appTab: AppTab) = updateState { copy(selectedTab = appTab) }
}