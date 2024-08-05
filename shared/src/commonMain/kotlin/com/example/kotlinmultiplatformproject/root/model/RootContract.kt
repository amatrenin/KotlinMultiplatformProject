package com.example.kotlinmultiplatformproject.root.model

import com.example.kotlinmultiplatformproject.base.BaseViewState
import com.example.kotlinmultiplatformproject.common.ui.theme.AppPrefs

class RootContract {

    data class State(
        val themeIsDark: Boolean,
        val firstDayIsMondey: Boolean,
        val selectedTab: AppTab
    ): BaseViewState {

        val appPrefs: AppPrefs
            get() = AppPrefs(firstDayIsMonday = firstDayIsMondey)
        companion object {
            val NONE = State (
                themeIsDark = true,
                firstDayIsMondey = true,
                selectedTab = AppTab.Events
            )
        }
    }
}