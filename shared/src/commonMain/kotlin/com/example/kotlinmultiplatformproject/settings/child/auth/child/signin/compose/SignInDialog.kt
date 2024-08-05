package com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.compose

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
import com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.SignInViewModel
import com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.model.SignInContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInDialog(
    viewModel: SignInViewModel,
    successListener: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is SignInContract.Event.Error -> showMessage = event.message
                SignInContract.Event.Success -> successListener()
            }
        }.launchIn(this)
    }

    Box {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    AppThemeProvider.colors.surface,
                    RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
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

            AppButton("login", onClick = viewModel::login)
        }

        AppToast(showMessage) { showMessage = null }
    }

}