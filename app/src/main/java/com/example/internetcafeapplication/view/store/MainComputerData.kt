package com.example.internetcafeapplication.view.store

import com.example.internetcafeapplication.model.Machine

data class MainComputerData(
    //val id: Int,
    val type: String,
    val nonAvaList: List<Machine>,
    val  avaList: List<Machine>
){

}
