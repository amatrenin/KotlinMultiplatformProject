package com.example.kotlinmultiplatformproject.settings.child.auth.child.register.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val jwt: String?
)
