package com.example.internetcafeapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    var id: String = "",
    var mid: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var expired: Boolean = false
)

@Serializable
data class OrderRepositories(
    val orders: List<Order> = listOf()
)

@Serializable
data class OrderDto(
    var orderId: String ="",
    var machineName: String ="",
    var moneyForNow: Double = 0.0,
    var timeForNow: Double = 0.0
)
