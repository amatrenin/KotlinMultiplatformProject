plugins{
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val jvmMain = getByName("desktopMain") {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
    task("testClasses")
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}