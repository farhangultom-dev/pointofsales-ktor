package com.farhandev.implementations

import at.favre.lib.crypto.bcrypt.BCrypt
import com.farhandev.model.User
import com.farhandev.model.Users
import com.farhandev.plugins.dbQuery
import com.farhandev.presenters.UserServices
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserImplementations: UserServices {
    private fun resultRowToUser(row: ResultRow):User{
        return User(
            id = row[Users.id],
            name = row[Users.name],
            username = row[Users.username],
            email = row[Users.email],
            cityId = row[Users.cityId],
            password = row[Users.password]
        )
    }

    override suspend fun addUser(user: User): User? = dbQuery {
        val encryptedPassword = BCrypt.withDefaults().hashToString(12,user.password.toCharArray())
        val insertStmt= Users.insert {
            it[name]= user.name
            it[username]= user.username
            it[cityId]= user.cityId
            it[email]= user.email
            it[cityId]= user.cityId
            it[password]= encryptedPassword
        }

        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToUser(it) }
    }

    override suspend fun updateUser(user: User): Boolean = dbQuery {
        Users.update({Users.id eq user.id}){
            it[name]= user.name
        }>0
    }

    override suspend fun deleteUser(user: User): Boolean = dbQuery {
        Users.deleteWhere { name eq user.name }>0

    }

    override suspend fun getUsers(): List<User> = dbQuery {
        Users.selectAll().map { resultRowToUser(it) }
    }

    override suspend fun searchUser(query: String): List<User> = dbQuery {
        Users.selectAll().where { (Users.name.lowerCase() like "%${query.lowercase()}%") }
            .map { resultRowToUser(it) }
    }

    override suspend fun getUser(id: Int): User? =  dbQuery {
        Users.selectAll().where { (Users.id eq id) }.map { resultRowToUser(it) }.singleOrNull()
    }
}