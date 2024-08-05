package com.example.kotlinmultiplatformproject.platform

actual class DeviceInfo {
    actual val osName: String
        get() = TODO("Not yet implemented")
    actual val osVersion: String
        get() = TODO("Not yet implemented")
    actual val model: String
        get() = TODO("Not yet implemented")
    actual val cpu: String
        get() = TODO("Not yet implemented")
    actual val screenWidth: Int
        get() = TODO("Not yet implemented")
    actual val screenHeight: Int
        get() = TODO("Not yet implemented")
    actual val screenDestiny: Int
        get() = TODO("Not yet implemented")

    actual fun getSummary(): String {
        TODO("Not yet implemented")
    }

}