package com.example.kotlinmultiplatformproject.platform

import java.util.UUID

actual fun randomUUID() = UUID.randomUUID().toString()