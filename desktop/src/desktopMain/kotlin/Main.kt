
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.example.kotlinmultiplatformproject.di.getKoinInstance
import com.example.kotlinmultiplatformproject.di.initKoin
import com.example.kotlinmultiplatformproject.root.RootViewModel
import com.example.kotlinmultiplatformproject.root.compose.RootScreen
import com.example.kotlinmultiplatformproject.settings.SettingsViewModel
import com.example.kotlinmultiplatformproject.settings.compose.SettingsScreen


fun main() {

    initKoin()

    application {
        Window(
            onCloseRequest = {
                exitApplication()
            },
            state = rememberWindowState(),
            title = "desktop"
        ) {
            RootScreen()
        }
    }
}