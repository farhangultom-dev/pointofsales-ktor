package com.farhandev.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val name: String,
    val cityName: String
)