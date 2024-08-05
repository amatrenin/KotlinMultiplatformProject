package com.example.kotlinmultiplatformproject.settings.child.auth.child.signin

import com.example.kotlinmultiplatformproject.base.BaseViewModel
import com.example.kotlinmultiplatformproject.network.ApiErrorWrapper
import com.example.kotlinmultiplatformproject.network.AppApi
import com.example.kotlinmultiplatformproject.settings.child.auth.child.register.model.AuthResponse
import com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.model.SignInContract.Event
import com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.model.SignInContract.State
import com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.model.SignInRequest
import com.example.kotlinmultiplatformproject.storage.SettingsManager
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.math.E

class SignInViewModel(
    private val api: AppApi,
    private val settings: SettingsManager
) : BaseViewModel<State, Event>() {

    fun changeEmail(email: String) = updateState { copy(email = email) }
    fun changePassword(pass: String) = updateState { copy(password = pass) }
    fun login() {
        val signInReq = SignInRequest(
            identifier = state.value.email,
            password = state.value.password
        )
        viewModelScope.launch(
            CoroutineExceptionHandler { _, t -> pushEvent(Event.Error(t.message.orEmpty())) }
        ) {
            val response = api.signIn(signInReq)
            if (response.status.isSuccess()) {
                val signInResponse = response.body<AuthResponse>()
                settings.token = signInResponse.jwt.orEmpty()
                settings.email = state.value.email
                pushEvent(Event.Success)
            } else {
                val error = response.body<ApiErrorWrapper>().error
                pushEvent(Event.Error(error?.message ?: response.bodyAsText()))
            }
        }
    }


    override fun initialState() = State.NONE
}