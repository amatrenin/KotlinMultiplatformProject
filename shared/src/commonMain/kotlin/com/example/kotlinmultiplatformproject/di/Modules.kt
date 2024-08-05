package com.example.kotlinmultiplatformproject.di

import com.example.kotlinmultiplatformproject.categories.CategoriesRepository
import com.example.kotlinmultiplatformproject.categories.list.CategoriesViewModel
import com.example.kotlinmultiplatformproject.categories.model.CategoryDao
import com.example.kotlinmultiplatformproject.common.ui.calendar.DatePickerViewModel
import com.example.kotlinmultiplatformproject.db.AppDb
import com.example.kotlinmultiplatformproject.events.EventsRepository
import com.example.kotlinmultiplatformproject.events.create.CreateEventViewModel
import com.example.kotlinmultiplatformproject.events.list.EventsViewModel
import com.example.kotlinmultiplatformproject.events.model.SpendEventDao
import com.example.kotlinmultiplatformproject.extensions.appLog
import com.example.kotlinmultiplatformproject.network.AppApi
import com.example.kotlinmultiplatformproject.network.DateSerializer
import com.example.kotlinmultiplatformproject.network.DateTimeSerializer
import com.example.kotlinmultiplatformproject.platform.DeviceInfo
import com.example.kotlinmultiplatformproject.root.RootViewModel
import com.example.kotlinmultiplatformproject.settings.SettingsViewModel
import com.example.kotlinmultiplatformproject.settings.child.auth.child.register.RegisterViewModel
import com.example.kotlinmultiplatformproject.settings.child.auth.child.signin.SignInViewModel
import com.example.kotlinmultiplatformproject.storage.DbAdapters
import com.example.kotlinmultiplatformproject.storage.SettingsManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module
import org.koin.ext.getFullName

object CoreModule {
    val deviceInfo = module {
        single { DeviceInfo() }
        factory { Dispatchers.Default + SupervisorJob() }
    }
}

object NetworkModule {
    val json = module {
        single {
            Json {
                encodeDefaults = false
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
                prettyPrint = true
                serializersModule = SerializersModule {
                    contextual(LocalDateTime::class, DateTimeSerializer)
                    contextual(LocalDate::class, DateSerializer)
                }
            }
        }
    }

    val httpClient = module {
        single {
            HttpClient(CIO) {
                expectSuccess = false
                install(ContentNegotiation) {
                    json(get())
                }
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            appLog(message)
                        }
                    }
                }
            }
        }
    }

    val api = module { single { AppApi(get(), get()) } }
}

object StorageModule {
    val settings = module {
        single { SettingsManager(get()) }
    }
    val db = module {
        single {
            AppDb(get(), DbAdapters.categoryDbAdapter, DbAdapters.eventsDbAdapter)
        }
    }
    val dao = module {
        single { CategoryDao(get(), get()) }
        single { SpendEventDao(get(), get()) }
    }
}

object RepositoriesModule {
    val repositories = module {
        single { CategoriesRepository(get()) }
        single { EventsRepository(get()) }
    }
}

object ViewModelsModule {
    val viewModels = module {
        single { RootViewModel(get()) }
        single { SettingsViewModel(get(), get(), get(), get(), get()) }
        single(DatePickerSingleQualifier) { DatePickerViewModel() }
        factory(DatePickerFactoryQualifier) { DatePickerViewModel() }
        factory { EventsViewModel(get(), get()) }
        single { CategoriesViewModel(get()) }
        factory { CreateEventViewModel() }
        factory { RegisterViewModel(get(), get()) }
        factory { SignInViewModel(get(), get()) }
    }
}

object DatePickerSingleQualifier : Qualifier {
    override val value: QualifierValue
        get() = this::class.getFullName()
}

object DatePickerFactoryQualifier : Qualifier {
    override val value: QualifierValue
        get() = this::class.getFullName()
}


