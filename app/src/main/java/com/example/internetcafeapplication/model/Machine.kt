package com.example.internetcafeapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Machine(
    var id: String = "",
    var name: String = "",
    var type:String = "",
    var activated: Boolean = false,
    var cafeId: String =""

    )

@Serializable
data class MachineRepositories(
    val orders: List<Machine> = listOf()
)

@Serializable
data class MachineDto(
    val type: MachineType = MachineType.PC,
    val activated: List<Machine> = listOf(),
    val notActivated: List<Machine> = listOf()
)
