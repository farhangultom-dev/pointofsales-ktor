package com.farhandev.di

import com.farhandev.implementations.CityImplementations
import com.farhandev.implementations.UserImplementations
import com.farhandev.presenters.CityService
import com.farhandev.presenters.UserServices
import org.koin.dsl.module

val appModule= module {
    single<UserServices> {
        UserImplementations()
    }

    single<CityService> {
        CityImplementations()
    }
}