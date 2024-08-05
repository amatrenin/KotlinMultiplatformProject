package com.example.kotlinmultiplatformproject.settings.child.auth.child.register.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.kotlinmultiplatformproject.common.ui.atoms.AppButton
import com.example.kotlinmultiplatformproject.common.ui.atoms.AppTextField
import com.example.kotlinmultiplatformproject.common.ui.atoms.AppToast
import com.example.kotlinmultiplatformproject.common.ui.theme.AppThemeProvider
import com.example.kotlinmultiplatformproject.settings.child.auth.child.register.RegisterViewModel
import com.example.kotlinmultiplatformproject.settings.child.auth.child.register.model.RegisterContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun RegisterDialog(
    viewModel: RegisterViewModel,
    successListener: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is RegisterContract.Event.Error -> showMessage = event.message
                RegisterContract.Event.Success -> successListener()
            }
        }.launchIn(this)
    }

    Box {

        Column(
            modifier = Modifier
                .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTextField(
                state.email,
                "email",
                onValueChange = viewModel::changeEmail
            )

            AppTextField(
                state.password,
               "password",
                onValueChange = viewModel::changePassword
            )

            AppTextField(
                state.confirmPassword,
                "confirm_password",
                onValueChange = viewModel::changeConfirmPassword
            )

            AnimatedVisibility(
                state.sendIsActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AppButton("register", onClick = viewModel::register)
            }
        }

        AppToast(showMessage) { showMessage = null }
    }
}