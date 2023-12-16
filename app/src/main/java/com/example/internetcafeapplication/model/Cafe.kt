package com.example.internetcafeapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Cafe(
    var id: String = "",
    var name: String = "",
    var coordinate: Coordinate,
    var machines: List<Machine> = listOf(),
    var distance: Double = 0.0
)

@Serializable
data class CafeRepositories(
    val cafes: List<Cafe>
)

@Serializable
data class CafeDto(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var distance: Double = 0.0,
    var machines: List<MachineInfo> = listOf()
)

@Serializable
data class MachineInfo(
    val type: MachineType = MachineType.PC,       // This will contain the machine type
    val total: Int = 0,         // This will contain the count of machines of this type
    val activeCount: Int = 0
)