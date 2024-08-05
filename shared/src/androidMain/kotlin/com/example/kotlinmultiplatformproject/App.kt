package com.example.kotlinmultiplatformproject

import android.app.Application
import android.content.Context
import com.example.kotlinmultiplatformproject.di.initKoin
import org.koin.dsl.module

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(appModule = module {
            single<Context> { this@App.applicationContext }
        })
        instanse = this
    }
    companion object {
        lateinit var instanse: App
    }
}