package com.example.internetcafeapplication.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString

@Serializable
data class User(
    var id: String = "",
    var uid: String = "",
    var email: String = "",
    var credit: Double = 0.00,
    var orders: List<Order> = listOf()
)


@Serializable
data class UserRepositories(
    val users: List<User> = listOf()
)
