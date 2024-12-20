package com.farhandev.responseModel

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse(
    val status: String,
    val message: String,

    @Contextual
    val data: Any? = null
)