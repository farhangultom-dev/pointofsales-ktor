package com.farhandev.presenters

import com.farhandev.model.City
import com.farhandev.model.UserInfo

interface CityService {
    suspend fun addCity(city: City):City?
    suspend fun getAllCities():List<City>
    suspend fun deleteCity(id:Int):Boolean
    suspend fun getAllUsersInfo():List<UserInfo>
}