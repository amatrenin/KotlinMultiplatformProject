package com.example.kotlinmultiplatformproject.settings.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinmultiplatformproject.common.ui.atoms.AppToast
import com.example.kotlinmultiplatformproject.common.ui.atoms.RootBox
import com.example.kotlinmultiplatformproject.common.ui.theme.AppThemeProvider
import com.example.kotlinmultiplatformproject.settings.SettingsContract
import com.example.kotlinmultiplatformproject.settings.SettingsViewModel
import com.example.kotlinmultiplatformproject.settings.child.auth.compose.AuthView
import com.example.kotlinmultiplatformproject.settings.child.sync.compose.SyncView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun BoxScope.SettingsScreen(
    viewModel: SettingsViewModel
) {

    val state by viewModel.state.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is SettingsContract.Event.DataSynced -> showMessage = "data_sync_success"
                is SettingsContract.Event.Error -> "data_sync_error"
            }
        }.launchIn(this)
    }

    RootBox {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {

//            if (state.isAuth) {
            SyncView(
                email = state.email,
                isLoading = state.isLoading,
                syncListener = { viewModel.sync() },
                logoutListener = { viewModel.logout() }
            )
//            } else {
            AuthView { viewModel.sync() }



            Row(
                modifier = Modifier.padding(16.dp)
                    .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "dark_theme", modifier = Modifier.weight(1f),
                    color = AppThemeProvider.colors.onSurface
                )
                Switch(
                    state.themeIsDark, onCheckedChange = { viewModel.switchTheme(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppThemeProvider.colors.accent,
                        uncheckedTrackColor = AppThemeProvider.colors.onSurface.copy(0.5f),
                        checkedTrackColor = AppThemeProvider.colors.accent.copy(0.5f),
                    )
                )
            }

            DeviceInfoView(state.info)
        }

        AppToast(showMessage?.let { it }) { showMessage = null }
    }

}