package com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.model

import com.example.kotlinmultiplatformproject.base.BaseEvent
import com.example.kotlinmultiplatformproject.base.BaseViewState

class SignInContract {

    data class State(
        val email: String,
        val password: String
    ) : BaseViewState {
        companion object {
            val NONE = State(
                email = "",
                password = ""
            )
        }
    }

    sealed interface Event : BaseEvent {
        data object Success : Event
        data class Error(val message: String) : Event
    }
}