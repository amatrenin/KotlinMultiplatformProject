package com.example.kotlinmultiplatformproject.platform

actual class DeviceInfo actual constructor(){
    actual val osName = System.getProperty("os.name") ?: "Desktop"
    actual val osVersion = System.getProperty("os.version") ?: "unknow version"
    actual val model = "Desktop"
    actual val cpu = System.getProperty("os.arch") ?: "unknow arch"
    actual val screenWidth = 0
    actual val screenHeight = 0
    actual val screenDestiny = 0

    actual fun getSummary() =
        "osName: $osName\n" +
                "\n" +
                "osVersion: $osVersion\n" +
                "\n" +
                "model: $model\n" +
                "\n" +
                "cpu: $cpu\n"
}