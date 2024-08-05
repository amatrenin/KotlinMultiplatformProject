package com.example.kotlinmultiplatformproject.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.kotlinmultiplatformproject.di.getKoinInstance
import com.example.kotlinmultiplatformproject.root.compose.RootScreen
import com.example.kotlinmultiplatformproject.root.RootViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootScreen()
        }
    }
}
