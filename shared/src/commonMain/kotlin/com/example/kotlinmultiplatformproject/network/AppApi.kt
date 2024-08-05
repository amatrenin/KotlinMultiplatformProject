package com.example.kotlinmultiplatformproject.network

import com.example.kotlinmultiplatformproject.categories.model.CategoryApi
import com.example.kotlinmultiplatformproject.events.model.SpendEventApi
import com.example.kotlinmultiplatformproject.settings.child.auth.child.register.model.RegisterRequest
import com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.model.SignInRequest
import com.example.kotlinmultiplatformproject.storage.SettingsManager
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AppApi(
    private val httpClient: HttpClient,
    private val settings: SettingsManager
) {

    suspend fun register(req: RegisterRequest) =
        httpClient.post("${settings.serverUrl}/api/2") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun signIn(req: SignInRequest) =
        httpClient.post("${settings.serverUrl}/api/2") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun syncCategories(categories: List<CategoryApi>) =
        httpClient.post("${settings.serverUrl}/api/2") {
            contentType(ContentType.Application.Json)
            bearerAuth(settings.token)
            setBody(categories)
        }

    suspend fun syncEvents(events: List<SpendEventApi>) =
        httpClient.post("${settings.serverUrl}/api/2") {
            contentType(ContentType.Application.Json)
            bearerAuth(settings.token)
            setBody(events)
        }
}