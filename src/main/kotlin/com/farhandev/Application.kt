package com.farhandev

import com.farhandev.plugins.configureDatabases
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureDI()
    configureDatabases()
    configureRouting()
}
