package com.example.kotlinmultiplatformproject.root.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.kotlinmultiplatformproject.categories.list.CategoriesViewModel
import com.example.kotlinmultiplatformproject.categories.list.compose.CategoriesScreen
import com.example.kotlinmultiplatformproject.common.ui.theme.AppTheme
import com.example.kotlinmultiplatformproject.common.ui.theme.AppThemeProvider
import com.example.kotlinmultiplatformproject.di.getKoinInstance
import com.example.kotlinmultiplatformproject.events.list.EventsViewModel
import com.example.kotlinmultiplatformproject.events.list.compose.EventsScreen
import com.example.kotlinmultiplatformproject.root.RootViewModel
import com.example.kotlinmultiplatformproject.root.model.AppTab
import com.example.kotlinmultiplatformproject.settings.compose.SettingsScreen

@Composable
fun RootScreen() {

    val viewModel = getKoinInstance<RootViewModel>()
    val state by viewModel.state.collectAsState()

    AppTheme(
        themeIsDark = state.themeIsDark,
        appPrefs = state.appPrefs
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(AppThemeProvider.colors.background)
        ) {
            RootNavigation(state.selectedTab)
            RootBottomBar(state.selectedTab) { appTab ->
                viewModel.hundleClickOnTab(appTab)
            }
        }
    }
}

@Composable
fun BoxScope.RootNavigation(selectedTab: AppTab) {

    when (selectedTab) {
        AppTab.Categories -> CategoriesScreen(getKoinInstance())
        AppTab.Events -> EventsScreen(getKoinInstance())
        AppTab.Settings -> SettingsScreen(getKoinInstance())
    }
}