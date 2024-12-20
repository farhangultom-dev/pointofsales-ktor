package com.farhandev.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.farhandev.model.Login
import com.farhandev.model.User
import com.farhandev.presenters.UserServices
import com.farhandev.responseModel.GenericResponse
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun Routing.userRoute(userServices: UserServices){
    route("/users"){
        get {
            val users = userServices.getUsers()
            call.respond(HttpStatusCode.OK,users)
        }

        post {
            val user=call.receive<User>()
            try {
                val result= userServices.addUser(user)
                result?.let {
                    call.respond(HttpStatusCode.Created,it)
                } ?: call.respond(HttpStatusCode.NotImplemented,"Error adding user")
            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.BadRequest,e.message ?: "SQL Exception!!")
            }
        }

        put{
            try {
                val user=call.receive<User>()
                val result=userServices.updateUser(user)
                if (result){
                    call.respond(HttpStatusCode.OK,"Update successful")
                }else{
                    call.respond(HttpStatusCode.NotImplemented,"Update not done")
                }
            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.BadRequest,e.message ?: "SQL Exception!!")
            }
        }

        delete{
            val user= call.receive<User>()
            val result= userServices.deleteUser(user)
            if (result){
                call.respond(HttpStatusCode.OK,"Delete successful")
            }else{
                call.respond(HttpStatusCode.NotImplemented,"Delete not done")
            }
        }

        get("/search"){
            val query=call.request.queryParameters["q"].toString()
            val users=userServices.searchUser(query)
            call.respond(HttpStatusCode.OK,users)
        }

        get("/{id}") {
            val id=call.parameters["id"]?.toInt()
            id?.let {
                userServices.getUser(it)?.let {user->
                    call.respond(HttpStatusCode.OK,user)
                } ?: call.respond(HttpStatusCode.NotFound,"User not found")
            } ?: call.respond(HttpStatusCode.BadGateway,"Provide Input!!")
        }

        post("/login") {
            val loginParameter = call.receive<Login>()
            val checkUser = userServices.getUserByUsername(loginParameter.username)

            if (checkUser.isEmpty()){
                call.respond(HttpStatusCode.NotFound, GenericResponse(
                    status = "failed",
                    message = "Username not registered yet"
                ))
                return@post
            }

            val hashPassword = checkUser.singleOrNull()?.password
            val checkPassword = BCrypt.verifyer().verify(loginParameter.password.toCharArray(), hashPassword)

            if (checkPassword.verified){
                call.respond(HttpStatusCode.OK, GenericResponse(
                    status = "success",
                    message = "Login successfully",
                    data = checkUser.singleOrNull()
                ))
                return@post
            } else {
                call.respond(HttpStatusCode.BadRequest, GenericResponse(
                    status = "failed",
                    message = "Wrong password, please check your password again"
                ))
            }
        }
    }
}