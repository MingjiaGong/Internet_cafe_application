package com.example.internetcafeapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,

)

@Serializable
data class CoordinateRepositories(
    val coordinates: List<Coordinate> = listOf()
)


