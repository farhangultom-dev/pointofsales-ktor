package com.farhandev.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int=0,
    val username: String,
    val email: String,
    val name: String,
    val cityId: Int,
    val password: String
)

object Users:Table(){
    val id= integer("id").autoIncrement()
    val username= varchar("username", 255)
    val name= varchar("name", 255)
    val email= varchar("email", 255)
    val cityId= integer("city_id").references(Cities.id, ReferenceOption.CASCADE)
    val password= varchar("password", 255)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}