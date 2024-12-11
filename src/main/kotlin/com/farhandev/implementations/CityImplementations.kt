package com.farhandev.implementations

import com.farhandev.model.Cities
import com.farhandev.model.City
import com.farhandev.model.UserInfo
import com.farhandev.model.Users
import com.farhandev.plugins.dbQuery
import com.farhandev.presenters.CityService
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class CityImplementations: CityService {
    private fun resultRowToCity(resultRow: ResultRow):City{
        return City(
            cityName = resultRow[Cities.cityName],
            id = resultRow[Cities.id]
        )
    }

    override suspend fun addCity(city: City): City? = dbQuery {
        val insertCity= Cities.insert {
            it[cityName]= city.cityName
        }

        insertCity.resultedValues?.singleOrNull()?.let { resultRowToCity(it) }
    }

    override suspend fun getAllCities(): List<City> = dbQuery {
        Cities.selectAll().map { resultRowToCity(it) }
    }

    override suspend fun deleteCity(id: Int): Boolean = dbQuery {
        Cities.deleteWhere { Cities.id eq id }>0
    }

    override suspend fun getAllUsersInfo(): List<UserInfo> = dbQuery {
        (Users innerJoin Cities)
            .select(Users.name, Cities.cityName)
            .map { UserInfo(
                name = it[Users.name],
                cityName = it[Cities.cityName]
            )}
    }
}