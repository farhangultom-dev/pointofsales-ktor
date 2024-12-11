package com.farhandev.routes

import com.farhandev.model.City
import com.farhandev.presenters.CityService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.cityRoute(cityService: CityService){
    route("/cities"){
        get {
            val cities=cityService.getAllCities()
            call.respond(cities)
        }

        post {
            val city=call.receive<City>()
            cityService.addCity(city)?.let {
                call.respond(HttpStatusCode.Created,it)
            } ?: call.respond(HttpStatusCode.BadRequest,"Error!!")
        }

        get("/userinfo"){
            val userInfo=cityService.getAllUsersInfo()
            call.respond(HttpStatusCode.OK,userInfo)
        }

        delete {
            val city = call.receive<City>()
            val deleting = cityService.deleteCity(city.id)

            if (deleting){
                call.respond(HttpStatusCode.OK,"Delete city success")
            }else{
                call.respond(HttpStatusCode.BadRequest,"Provide Id!!")
            }
        }
    }
}