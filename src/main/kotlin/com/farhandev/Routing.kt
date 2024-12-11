package com.farhandev

import com.farhandev.presenters.CityService
import com.farhandev.presenters.UserServices
import com.farhandev.routes.cityRoute
import com.farhandev.routes.userRoute
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.koin.ktor.ext.get
import org.slf4j.event.*

fun Application.configureRouting(userService: UserServices = get(),
                                 cityServices: CityService = get()) {
    routing {
        userRoute(userService)
        cityRoute(cityServices)
    }
}
