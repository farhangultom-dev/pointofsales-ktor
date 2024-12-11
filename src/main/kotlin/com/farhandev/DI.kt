package com.farhandev

import com.farhandev.di.appModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureDI(){
    install(Koin){
        modules(appModule)
    }
}